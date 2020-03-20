package ca.compflip.minecraft.gui;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import org.lwjgl.util.vector.Matrix4f;

import ca.compflip.minecraft.gfx.Quad;
import ca.compflip.minecraft.gfx.Shader;
import ca.compflip.minecraft.gfx.Texture;
import ca.compflip.minecraft.util.MatrixMath;

public class GUIObject {
	public Quad quad;
	public Texture texture;

	private static final Shader guiShader = new Shader("/glsl/gui.vert", "/glsl/gui.frag");

	public GUIObject(String textureSrc) {
		quad = new Quad();
		texture = new Texture(textureSrc);
	}

	public void render() {
		glBindTexture(GL_TEXTURE_2D, texture.id);
		glUseProgram(guiShader.id);

		glBindVertexArray(quad.vertexArray);

		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(2);

		glBindBuffer(GL_ARRAY_BUFFER, quad.vertexBuffer);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, quad.indexBuffer);

		Matrix4f transformMatrix = MatrixMath.createTransformMatrix(quad.position, quad.rotation, quad.scale);
		guiShader.storeMatrix(transformMatrix, "u_tMatrix");

		glDrawElements(GL_TRIANGLES, quad.vertexCount, GL_UNSIGNED_INT, 0);
	}
}
