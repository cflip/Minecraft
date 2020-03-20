package ca.compflip.minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import ca.compflip.minecraft.entity.Entity;
import ca.compflip.minecraft.level.Level;

public class Player extends Entity {
	private static final float MOVE_SPEED = 0.2f;
	private static final float TURN_SPEED = 0.1f;

	private static final float DRAG = 0.5f;
	private static final float GRAVITY = 0.05f;

	private boolean onGround = false;

	public Player(Level level) {
		super(level);
	}

	public void tick() {
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
				velocity.y += 5;
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
				velocity.y -= MOVE_SPEED;
		}

		velocity.x *= DRAG;
		velocity.y *= DRAG;
		velocity.z *= DRAG;

		velocity.y -= GRAVITY;

		if (canPass(velocity.x, 0, 0))
			position.x += velocity.x;
		if (canPass(0, 0, velocity.z))
			position.z += velocity.z;

		if (canPass(0, velocity.y, 0)) {
			position.y += velocity.y;
			onGround = false;
		} else {
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