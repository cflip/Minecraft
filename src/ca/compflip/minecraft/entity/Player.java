package ca.compflip.minecraft.entity;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import ca.compflip.minecraft.level.Level;

public class Player extends Entity {
	private static final float MOVE_SPEED = 0.02f;
	private static final float MOUSE_SENSITIVITY = 0.1f;
	private static final float DRAG = 0.5f;
	
	public Player(Level level) {
		super(level);
	}

	public void tick(float deltaTime) {
		if (Mouse.isGrabbed()) {
			rotation.x += Mouse.getDX() * MOUSE_SENSITIVITY;
			rotation.y -= Mouse.getDY() * MOUSE_SENSITIVITY;

			if (rotation.y > 90)
				rotation.y = 90;
			if (rotation.y < -90)
				rotation.y = -90;

			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				velocity.x += Math.sin(Math.toRadians(rotation.x)) * MOVE_SPEED;
				velocity.z -= Math.cos(Math.toRadians(rotation.x)) * MOVE_SPEED;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				velocity.x -= Math.sin(Math.toRadians(rotation.x)) * MOVE_SPEED;
				velocity.z += Math.cos(Math.toRadians(rotation.x)) * MOVE_SPEED;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				velocity.x -= Math.sin(Math.toRadians(rotation.x + 90)) * MOVE_SPEED;
				velocity.z += Math.cos(Math.toRadians(rotation.x + 90)) * MOVE_SPEED;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				velocity.x -= Math.sin(Math.toRadians(rotation.x - 90)) * MOVE_SPEED;
				velocity.z += Math.cos(Math.toRadians(rotation.x - 90)) * MOVE_SPEED;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) velocity.y += MOVE_SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) velocity.y -= MOVE_SPEED;
		}

		velocity.x *= DRAG;
		velocity.y *= DRAG;
		velocity.z *= DRAG;

		float xa = velocity.x * deltaTime;
		float ya = velocity.y * deltaTime;
		float za = velocity.z * deltaTime;
		
		position.x += xa;
		position.y += ya;
		position.z += za;
	}
	
	public void resetPosition() {
		Random random = new Random();
		position.set(random.nextInt(level.width - 4) + 2, level.depth + 10, random.nextInt(level.width - 4) + 2);
	}
}