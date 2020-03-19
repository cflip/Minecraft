package ca.compflip.minecraft.level.chunk;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ca.compflip.minecraft.gfx.Model;
import ca.compflip.minecraft.level.Level;

public class ChunkMeshGenerator {
	private static final float[] FRONT_TEMPLATE = { //
			0, 1, 1, 0.9f, 0, 0, //
			0, 0, 1, 0.9f, 0, 1, //
			1, 0, 1, 0.9f, 1, 1, //
			1, 1, 1, 0.9f, 1, 0, };
	private static final float[] BACK_TEMPLATE = { //
			0, 1, 0, 0.9f, 1, 0, //
			0, 0, 0, 0.9f, 1, 1, //
			1, 0, 0, 0.9f, 0, 1, //
			1, 1, 0, 0.9f, 0, 0, };
	private static final float[] LEFT_TEMPLATE = { //
			0, 1, 0, 0.7f, 0, 0, //
			0, 0, 0, 0.7f, 0, 1, //
			0, 0, 1, 0.7f, 1, 1, //
			0, 1, 1, 0.7f, 1, 0, };
	private static final float[] RIGHT_TEMPLATE = { //
			1, 1, 0, 0.7f, 1, 0, //
			1, 0, 0, 0.7f, 1, 1, //
			1, 0, 1, 0.7f, 0, 1, //
			1, 1, 1, 0.7f, 0, 0, };
	private static final float[] TOP_TEMPLATE = { //
			0, 1, 0, 1, 0, 0, //
			0, 1, 1, 1, 0, 1, //
			1, 1, 1, 1, 1, 1, //
			1, 1, 0, 1, 1, 0, };
	private static final float[] BOTTOM_TEMPLATE = { //
			0, 0, 0, 0.6f, 0, 0, //
			0, 0, 1, 0.6f, 0, 1, //
			1, 0, 1, 0.6f, 1, 1, //
			1, 0, 0, 0.6f, 1, 0, };

	public static Model buildChunkMesh(Level level, Chunk chunk) {
		ChunkMesh mesh = new ChunkMesh();

		for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
			for (int y = 0; y < Chunk.CHUNK_DEPTH; y++) {
				for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
					Vector3f pos = new Vector3f(chunk.position.x + x, chunk.position.y + y, chunk.position.z + z);
					int xx = (int) pos.x;
					int yy = (int) pos.y;
					int zz = (int) pos.z;

					if (level.solid(xx, yy, zz)) {
						if (!level.solid(xx, yy, zz + 1))
							mesh.addFace(FRONT_TEMPLATE, pos, new Vector2f(level.get(xx, yy, zz).sideTexIndex % 16, level.get(xx, yy, zz).sideTexIndex / 16));
						if (!level.solid(xx, yy, zz - 1))
							mesh.addFace(BACK_TEMPLATE, pos, new Vector2f(level.get(xx, yy, zz).sideTexIndex % 16, level.get(xx, yy, zz).sideTexIndex / 16));
						if (!level.solid(xx - 1, yy, zz))
							mesh.addFace(LEFT_TEMPLATE, pos, new Vector2f(level.get(xx, yy, zz).sideTexIndex % 16, level.get(xx, yy, zz).sideTexIndex / 16));
						if (!level.solid(xx + 1, yy, zz))
							mesh.addFace(RIGHT_TEMPLATE, pos, new Vector2f(level.get(xx, yy, zz).sideTexIndex % 16, level.get(xx, yy, zz).sideTexIndex / 16));
						if (!level.solid(xx, yy + 1, zz))
							mesh.addFace(TOP_TEMPLATE, pos, new Vector2f(level.get(xx, yy, zz).textureIndex % 16, level.get(xx, yy, zz).textureIndex / 16));
						if (!level.solid(xx, yy - 1, zz))
							mesh.addFace(BOTTOM_TEMPLATE, pos, new Vector2f(level.get(xx, yy, zz).textureIndex % 16, level.get(xx, yy, zz).textureIndex / 16));
					}
				}
			}
		}

		return new Model(mesh.getVertices(), mesh.getIndices());
	}
}