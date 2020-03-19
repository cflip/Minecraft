package ca.compflip.minecraft.level.chunk;

import org.lwjgl.util.vector.Vector3f;

import ca.compflip.minecraft.level.TileType;

public class Chunk {
	public static final int CHUNK_SIZE = 16;
	public static final int CHUNK_DEPTH = 32;
	public static final int VOLUME = CHUNK_SIZE * CHUNK_DEPTH * CHUNK_SIZE;

	public Vector3f position;
	public TileType[] tiles;
	
	public Chunk(Vector3f position) {
		this.position = position;
		tiles = new TileType[VOLUME];
	}
}