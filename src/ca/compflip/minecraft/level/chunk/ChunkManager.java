package ca.compflip.minecraft.level.chunk;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ca.compflip.minecraft.gfx.Model;
import ca.compflip.minecraft.level.Level;
import ca.compflip.minecraft.level.TileType;

public class ChunkManager {
	public Chunk[] chunks;
	public Model[] meshes;

	private Level level;
	private int xChunks, zChunks;

	public ChunkManager(Level level) {
		this.level = level;
		xChunks = level.width;
		zChunks = level.height;

		chunks = new Chunk[xChunks * zChunks];
		meshes = new Model[xChunks * zChunks];

		for (int y = 0; y < zChunks; y++) {
			for (int x = 0; x < xChunks; x++) {
				chunks[x + y * xChunks] = new Chunk(new Vector3f(x * Chunk.CHUNK_SIZE, 0, y * Chunk.CHUNK_SIZE));
			}
		}
	}

	public void buildMeshes() {
		System.out.println("Beginning mesh generation...");
		long time = System.currentTimeMillis();
		
		for (int i = 0; i < chunks.length; i++) {
			meshes[i] = ChunkMeshGenerator.buildChunkMesh(level, chunks[i]);
		}
		
		System.out.println("Mesh generation done. Took " + Long.toString(System.currentTimeMillis() - time) + " ms.");
	}

	public TileType get(int x, int y, int z) {
		Vector2f chunkPosition = toChunkXZ(x, z);
		int cx = (int) chunkPosition.x;
		int cz = (int) chunkPosition.y;

		if (inBounds(x, y, z)) {
			Chunk chunk = chunks[cx + cz * xChunks];
			return chunk.tiles[calculateBlockIndex(x, y, z)];
		}

		return TileType.AIR;
	}

	public void set(TileType type, int x, int y, int z) {
		Vector2f chunkPosition = toChunkXZ(x, z);
		int cx = (int) chunkPosition.x;
		int cz = (int) chunkPosition.y;

		if (inBounds(x, y, z)) {
			Chunk chunk = chunks[cx + cz * xChunks];
			chunk.tiles[calculateBlockIndex(x, y, z)] = type;
		}
	}

	public int calculateBlockIndex(int x, int y, int z) {
		Vector2f blockPosition = toBlockXZ(x, z);
		int bx = (int) blockPosition.x;
		int bz = (int) blockPosition.y;

		if (inBounds(x, y, z)) {
			return (y * Chunk.CHUNK_SIZE + bz) * Chunk.CHUNK_SIZE + bx;
		}
		return 0;
	}

	public boolean inBounds(int x, int y, int z) {
		return x >= 0 && x < xChunks * Chunk.CHUNK_SIZE && //
				y >= 0 && y < Chunk.CHUNK_DEPTH && //
				z >= 0 && z < zChunks * Chunk.CHUNK_SIZE;//
	}

	public boolean chunkExists(int x, int z) {
		return chunks[x + z * xChunks] != null;
	}

	public Vector2f toChunkXZ(int x, int z) {
		return new Vector2f(x / Chunk.CHUNK_SIZE, z / Chunk.CHUNK_SIZE);
	}

	public Vector2f toBlockXZ(int x, int z) {
		return new Vector2f(x % Chunk.CHUNK_SIZE, z % Chunk.CHUNK_SIZE);
	}
}