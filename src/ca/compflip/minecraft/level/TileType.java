package ca.compflip.minecraft.level;

public enum TileType {
	AIR(false, 2),
	GRASS(true, 0, 3),
	STONE(true, 1),
	DIRT(true, 2),
	COBBLESTONE(true, 16),
	PLANKS(true, 4);
	
	public boolean solid;
	public int textureIndex;
	public int sideTexIndex;
	
	TileType(boolean solid, int textureIndex) {
		this.solid = solid;
		this.textureIndex = textureIndex;
		sideTexIndex = textureIndex;
	}
	
	TileType(boolean solid, int textureIndex, int sideTexIndex) {
		this.solid = solid;
		this.textureIndex = textureIndex;
		this.sideTexIndex = sideTexIndex;
	}
}
