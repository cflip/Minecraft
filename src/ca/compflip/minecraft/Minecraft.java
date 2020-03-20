package ca.compflip.minecraft;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import ca.compflip.minecraft.gfx.ModelRenderer;
import ca.compflip.minecraft.gfx.Shader;
import ca.compflip.minecraft.gui.GUIObject;
import ca.compflip.minecraft.level.Level;
import ca.compflip.minecraft.util.Timer;

public class Minecraft implements Runnable {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 760;
	private static final String TITLE = "Minecraft";

	public static boolean customTextures = false;
	private boolean f7down = false;

	private Shader shader;
	private Timer timer;

	private ModelRenderer renderer;
	private Level level;
	private GUIObject crosshair;

	private Player player;

	private void init() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.setTitle(TITLE);
		Display.create();

		shader = new Shader("/glsl/basic.vert", "/glsl/basic.frag");
		renderer = new ModelRenderer(shader);
		timer = new Timer();

		level = new Level(16, 16);
		player = new Player(level);

		player.position.x = 64;
		player.position.y = 40;
		player.position.z = 64;

		crosshair = new GUIObject("/tex/crosshair.png");
		crosshair.quad.scale.x = 0.05f;
		crosshair.quad.scale.y = 0.07f;
		crosshair.quad.position.x -= crosshair.quad.scale.x / 2;
		crosshair.quad.position.y -= crosshair.quad.scale.y / 2;

		Mouse.setGrabbed(true);
		Mouse.setCursorPosition(0, 0);
	}

	private void start() {
		new Thread(this).start();
	}

	public void run() {
		try {
			init();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		long fpsTimer;
		int fps = 0;

		timer.getDelta();
		fpsTimer = timer.getTime();

		while (!Display.isCloseRequested()) {
			tick(timer.getDelta());
			render();

			fps++;

			Display.update();

			if (timer.getTime() - fpsTimer > 1000) {
				Display.setTitle(TITLE + " [" + fps + " fps]");
				fps = 0;
				fpsTimer += 1000;
			}
		}
	}

	private void tick(float deltaTime) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && Mouse.isGrabbed()) {
			Mouse.setGrabbed(false);
			Mouse.setCursorPosition(WIDTH / 2, HEIGHT / 2);
		} else if (Mouse.isButtonDown(0) && !Mouse.isGrabbed() && Mouse.isInsideWindow()) {
			Mouse.setGrabbed(true);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_F7) && !f7down) {
			customTextures = !customTextures;
			f7down = true;
		} else if (!Keyboard.isKeyDown(Keyboard.KEY_F7) && f7down) {
			f7down = false;
		}

		player.tick(deltaTime);
		renderer.camPos.set(player.position.x, player.position.y, player.position.z);
		renderer.camRot.set(player.rotation.y, player.rotation.x, 0);
	}

	private void render() {
		renderer.prepare();
		level.render(renderer);
		renderer.stop();
		crosshair.render();
	}

	public static void main(String[] args) {
		new Minecraft().start();
	}
}
