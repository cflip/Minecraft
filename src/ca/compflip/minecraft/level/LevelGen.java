package ca.compflip.minecraft.level;

import java.util.Random;

public class LevelGen {
	private Level level;
	private Random random;

	private float[] sample;

	public LevelGen(Level level, int seed) {
		this.level = level;

		random = new Random(seed);
		sample = new float[level.width * level.length];

		for (int i = 0; i < sample.length; i++) {
			sample[i] = random.nextFloat();
		}
	}

	public void generate() {
		float[] heightmap = generateHeightMap();

		for (int z = 0; z < level.length; z++) {
			for (int y = 0; y < level.depth; y++) {
				for (int x = 0; x < level.width; x++) {
					int top = (int) heightmap[x + z * level.width]; 
					
					if (y > top)
						level.set(TileType.AIR, x, y, z);
					else {
						TileType type = y > 24 ? TileType.GRASS : (random.nextBoolean() ? TileType.STONE : TileType.COBBLESTONE);
						level.set(type, x, y, z);
					}
				}
			}
		}
	}

	private float[] generateHeightMap() {
		float[] heightmap = new float[level.width * level.length];

		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level.width; x++) {
				heightmap[x + y * level.width] = getInterpolatedNoise(x/6f, y/6f) * 12+20;
			}
		}

		return heightmap;
	}

	private float getInterpolatedNoise(float x, float y) {
		int xx = (int) x;
		int yy = (int) y;
		
		float xf = x - xx;
		float yf = y - yy;
		
		float v0 = getSmoothedNoise(xx, yy);
		float v1 = getSmoothedNoise(xx+1, yy);
		float v2 = getSmoothedNoise(xx, yy+1);
		float v3 = getSmoothedNoise(xx+1, yy+1);
		
		float i0 = interpolate(v0, v1, xf);
		float i1 = interpolate(v2, v3, xf);
		
		return interpolate(i0, i1, yf);
	}

	private float interpolate(float a, float b, float blend) {
		double theta = blend * Math.PI;
		float f = (float) (1f - Math.cos(theta)) / 2f;
		return a * (1f - f) + b * f;
	}

	private float getSmoothedNoise(int x, int y) {
		float corners = (getNoise(x - 1, y - 1) + getNoise(x + 1, y - 1) + getNoise(x - 1, y + 1) + getNoise(x + 1, y + 1)) / 16f;
		float sides = (getNoise(x - 1, y) + getNoise(x + 1, y) + getNoise(x, y - 1) + getNoise(x, y + 1)) / 8f;
		float center = getNoise(x, y) / 4f;
		return corners + sides + center;
	}

	private float getNoise(int x, int y) {
		if (x < 0 || x >= level.width || y < 0 || y >= level.length) return 0;
		return sample[x + y * level.width];
	}
}