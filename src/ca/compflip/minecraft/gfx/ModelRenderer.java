package ca.compflip.minecraft.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import ca.compflip.minecraft.util.MatrixMath;

public class ModelRenderer {
	private static final float FOV = 85f;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000f;
	
	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	
	public Vector3f camPos, camRot;
	
	private Shader shader;
	
	public ModelRenderer(Shader shader) {
		this.shader = shader;
		
		camPos = new Vector3f(0, 0, 0);
		camRot = new Vector3f(0, 0, 0);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		projectionMatrix = MatrixMath.createProjectionMatrix(FOV, NEAR_PLANE, FAR_PLANE);
		shader.storeMatrix(projectionMatrix, "u_pMatrix");
	}

	public void prepare() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.3f, 0.4f, 0.8f, 1.0f);

		glUseProgram(shader.id);

		glEnable(GL_DEPTH_TEST);

		viewMatrix = MatrixMath.createViewMatrix(camPos, camRot);
		shader.storeMatrix(viewMatrix, "u_vMatrix");
	}
	
	public void render(Model[] models, Texture texture) {
		glBindTexture(GL_TEXTURE_2D, texture.id);
		glUseProgram(shader.id);
		
		for (Model model : models) {
			glBindVertexArray(model.vertexArray);
			
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glEnableVertexAttribArray(2);
			
			glBindBuffer(GL_ARRAY_BUFFER, model.vertexBuffer);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, model.indexBuffer);
			
			Matrix4f transformMatrix = MatrixMath.createTransformMatrix(model.position, model.rotation, model.scale);
			shader.storeMatrix(transformMatrix, "u_tMatrix");
			
			glDrawElements(GL_TRIANGLES, model.vertexCount, GL_UNSIGNED_INT, 0);
		}
	}
	
	public void stop() {
		glDisable(GL_DEPTH_TEST);
	}
}