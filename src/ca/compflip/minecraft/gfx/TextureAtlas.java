package ca.compflip.minecraft.gfx;

public class TextureAtlas {
	public static final TextureAtlas terrain = new TextureAtlas(256, 256, 16);
	
	private int width, height;
	private int tileSize;

	public TextureAtlas(int width, int height, int tileSize) {
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
	}

	public float[] scaleTexCoords(float[] vertices, int x, int y) {
		float[] result = vertices.clone();
		
		int xTiles = width / tileSize;
		int yTiles = height / tileSize;

		for (int i = 0; i < vertices.length / Model.FLOATS_PER_VERTEX; i++) {
			float ox = result[i * Model.FLOATS_PER_VERTEX + 4];
			float oy = result[i * Model.FLOATS_PER_VERTEX + 5];

			result[i * Model.FLOATS_PER_VERTEX + 4] = (ox + x) / xTiles;
			result[i * Model.FLOATS_PER_VERTEX + 5] = (oy + y) / yTiles;
		}
		
		return result;
	}
}