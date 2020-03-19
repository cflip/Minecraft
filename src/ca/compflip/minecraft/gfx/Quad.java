package ca.compflip.minecraft.gfx;

public class Quad extends Model {
	private static final float[] QUAD_VERTS = { 
			0, 1, 0, 1, 0, 0, 
			0, 0, 0, 1, 0, 1, 
			1, 0, 0, 1, 1, 1, 
			1, 1, 0, 1, 1, 0, 
			};
	private static final int[] QUAD_INDICES = { 0, 1, 3, 3, 1, 2 };

	public Quad() {
		super(QUAD_VERTS, QUAD_INDICES);
	}

}