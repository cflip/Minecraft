package ca.compflip.minecraft.util;

import org.lwjgl.Sys;

public class Timer {
	private long lastTime;
	
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time-lastTime);
		lastTime = time;
		
		return delta;
	}
}
