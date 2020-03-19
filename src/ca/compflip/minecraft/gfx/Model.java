package ca.compflip.minecraft.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;

public class Model {
	public static final int FLOATS_PER_VERTEX = 6;
	
	public int vertexBuffer, indexBuffer;
	public int vertexArray, texCoordArray;
	public int vertexCount;

	public Vector3f position, rotation, scale;
	
	public Model(float[] vertices, int[] indices) {
		vertexCount = indices.length;

		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = new Vector3f(1, 1, 1);
		
		vertexBuffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);

		FloatBuffer vertexData = BufferUtils.createFloatBuffer(vertices.length);
		vertexData.put(vertices);
		vertexData.flip();

		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);

		indexBuffer = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);

		IntBuffer indexData = BufferUtils.createIntBuffer(indices.length);
		indexData.put(indices);
		indexData.flip();

		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexData, GL_STATIC_DRAW);

		vertexArray = glGenVertexArrays();
		glBindVertexArray(vertexArray);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, FLOATS_PER_VERTEX * 4, 0);
		glVertexAttribPointer(1, 1, GL_FLOAT, false, FLOATS_PER_VERTEX * 4, 3*4);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, FLOATS_PER_VERTEX * 4, 4*4);
	}
}
