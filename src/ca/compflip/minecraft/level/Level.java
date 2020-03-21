package ca.compflip.minecraft.level;

import ca.compflip.minecraft.Minecraft;
import ca.compflip.minecraft.gfx.ModelRenderer;
import ca.compflip.minecraft.gfx.Texture;
import ca.compflip.minecraft.level.chunk.Chunk;
import ca.compflip.minecraft.level.chunk.ChunkManager;

public class Level {
	private ChunkManager chunkManager;
	private LevelGen generator;
	public int xChunks, zChunks;

	public int width, depth, length;

	public Level(int xChunks, int zChunks) {
		this.xChunks = xChunks;
		this.zChunks = zChunks;

		width = Chunk.CHUNK_SIZE * xChunks;
		depth = Chunk.CHUNK_DEPTH;
		length = Chunk.CHUNK_SIZE * zChunks;

		chunkManager = new ChunkManager(this);
		generator = new LevelGen(this, (int) (Math.random() * Integer.MAX_VALUE));

		generator.generate();
		chunkManager.buildMeshes();
	}

	public void render(ModelRenderer renderer) {
		renderer.render(chunkManager.meshes, Minecraft.customTextures ? Texture.customTerrain : Texture.terrain);
	}

	public TileType get(int x, int y, int z) {
		return chunkManager.get(x, y, z);
	}

	public void set(TileType type, int x, int y, int z) {
		chunkManager.set(type, x, y, z);
	}

	public boolean solid(int x, int y, int z) {
		return chunkManager.get(x, y, z).solid;
	}
}
