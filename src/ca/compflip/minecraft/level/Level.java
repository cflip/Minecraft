package ca.compflip.minecraft.level;

import ca.compflip.minecraft.Minecraft;
import ca.compflip.minecraft.gfx.ModelRenderer;
import ca.compflip.minecraft.gfx.Texture;
import ca.compflip.minecraft.level.chunk.ChunkManager;

public class Level {
	private ChunkManager chunkManager;
	private LevelGen generator;
	public int width, height;

	public Level(int width, int height) {
		this.width = width;
		this.height = height;

		chunkManager = new ChunkManager(this);
		generator = new LevelGen(this, (int) (Math.random()*Integer.MAX_VALUE));
		
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
