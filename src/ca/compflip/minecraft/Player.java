package ca.compflip.minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import ca.compflip.minecraft.entity.Entity;
import ca.compflip.minecraft.level.Level;

public class Player extends Entity {
	private static final float MOVE_SPEED = 0.3f;
	private static final float TURN_SPEED = 0.1f;
	private static final float JUMP_POWER = 1.1f;

	private static final float DRAG = 0.5f;
	private static final float GRAVITY = 0.005f;

	private boolean onGround = false;

	public Player(Level level) {
		super(level);
	}

	public void tick(float deltaTime) {
		if (Mouse.isGrabbed()) {
			rotation.x += Mouse.getDX() * TURN_SPEED;
			rotation.y -= Mouse.getDY() * TURN_SPEED;

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

			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && onGround)
				velocity.y += JUMP_POWER;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				velocity.y -= MOVE_SPEED;
		}

		velocity.x *= DRAG;
		velocity.z *= DRAG;

		velocity.y -= GRAVITY;

		if (canPass(velocity.x, 0, 0)) position.x += velocity.x * deltaTime;
		if (canPass(0, 0, velocity.z)) position.z += velocity.z * deltaTime;

		if (canPass(0, velocity.y, 0)) {
			position.y += velocity.y * deltaTime;
			onGround = false;
		} else {
			velocity.y = 0;
			onGround = true;
		}
	}

	private boolean canPass(float xa, float ya, float za) {
		int xx = (int) (position.x + xa);
		int yy = (int) (position.y + ya);
		int zz = (int) (position.z + za);

		if (level.solid(xx, yy, zz))
			return false;
		return true;
	}
}