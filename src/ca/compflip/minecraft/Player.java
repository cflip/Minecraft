package ca.compflip.minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import ca.compflip.minecraft.entity.Entity;
import ca.compflip.minecraft.level.Level;

public class Player extends Entity {
	private static final float MOVE_SPEED = 0.2f;
	private static final float TURN_SPEED = 0.1f;

	private double xa, ya, za;

	public Player(Level level) {
		super(level);
	}

	public void tick() {
		if (Mouse.isGrabbed()) {
			rotation.x += Mouse.getDX() * TURN_SPEED;
			rotation.y -= Mouse.getDY() * TURN_SPEED;

			if (rotation.y > 90) rotation.y = 90;
			if (rotation.y < -90) rotation.y = -90;

			za = ya = xa = 0;

			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				xa += Math.sin(Math.toRadians(rotation.x)) * MOVE_SPEED;
				za -= Math.cos(Math.toRadians(rotation.x)) * MOVE_SPEED;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				xa -= Math.sin(Math.toRadians(rotation.x)) * MOVE_SPEED;
				za += Math.cos(Math.toRadians(rotation.x)) * MOVE_SPEED;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				xa -= Math.sin(Math.toRadians(rotation.x + 90)) * MOVE_SPEED;
				za += Math.cos(Math.toRadians(rotation.x + 90)) * MOVE_SPEED;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				xa -= Math.sin(Math.toRadians(rotation.x - 90)) * MOVE_SPEED;
				za += Math.cos(Math.toRadians(rotation.x - 90)) * MOVE_SPEED;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) ya += MOVE_SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) ya -= MOVE_SPEED;

			if (canPass((float) xa, 0, 0)) position.x += xa;
			if (canPass(0, (float) ya, 0)) position.y += ya;
			if (canPass(0, 0, (float) za)) position.z += za;
		}
	}

	private boolean canPass(float xa, float ya, float za) {
		return true;
	}
}