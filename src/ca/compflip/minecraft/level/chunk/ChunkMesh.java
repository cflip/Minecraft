package ca.compflip.minecraft.level.chunk;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ca.compflip.minecraft.gfx.Model;
import ca.compflip.minecraft.gfx.TextureAtlas;

public class ChunkMesh {
	public List<Float> vertices = new ArrayList<Float>();
	public List<Integer> indices = new ArrayList<Integer>();

	private int indicesCounter;

	public void addFace(float[] faceTemplate, Vector3f position, Vector2f texCoords) {
		for (int i = 0; i < 4; i++) {
			float[] vertex = new float[Model.FLOATS_PER_VERTEX];

			vertex[0] = faceTemplate[i * Model.FLOATS_PER_VERTEX + 0] + position.x;
			vertex[1] = faceTemplate[i * Model.FLOATS_PER_VERTEX + 1] + position.y;
			vertex[2] = faceTemplate[i * Model.FLOATS_PER_VERTEX + 2] + position.z;
			vertex[3] = faceTemplate[i * Model.FLOATS_PER_VERTEX + 3];
			vertex[4] = faceTemplate[i * Model.FLOATS_PER_VERTEX + 4];
			vertex[5] = faceTemplate[i * Model.FLOATS_PER_VERTEX + 5];

			vertex = TextureAtlas.terrain.scaleTexCoords(vertex, (int) texCoords.x, (int) texCoords.y);
			
			vertices.add(vertex[0]);
			vertices.add(vertex[1]);
			vertices.add(vertex[2]);
			vertices.add(vertex[3]);
			vertices.add(vertex[4]);
			vertices.add(vertex[5]);
		}

		indices.add(indicesCounter);
		indices.add(indicesCounter + 1);
		indices.add(indicesCounter + 3);
		indices.add(indicesCounter + 3);
		indices.add(indicesCounter + 1);
		indices.add(indicesCounter + 2);
		indicesCounter += 4;
	}

	public float[] getVertices() {
		float[] array = new float[vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			array[i] = vertices.get(i);
		}
		return array;
	}

	public int[] getIndices() {
		int[] array = new int[indices.size()];
		for (int i = 0; i < indices.size(); i++) {
			array[i] = indices.get(i);
		}
		return array;
	}
}
