package ca.compflip.minecraft;

import org.lwjgl.util.vector.Vector3f;

public class AABB {
	public Vector3f min, max;
	
	public AABB(Vector3f min, Vector3f max) {
		this.min = min;
		this.max = max;
	}
	
	public void collide(AABB other) {
		
	}
}