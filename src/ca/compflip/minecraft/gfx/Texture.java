package ca.compflip.minecraft.gfx;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	public static final Texture terrain = new Texture("/tex/terrain.png");
	public static final Texture customTerrain = new Texture("/tex/customterrain.png");
	
	public int width, height;
	public int[] pixels;
	public int id;

	public Texture(String path) {
		BufferedImage image = getImageData(path);

		width = image.getWidth();
		height = image.getHeight();
		pixels = new int[width * height];
		id = glGenTextures();
		
		image.getRGB(0, 0, width, height, pixels, 0, width);

		ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(pixels.length * 4);
		for (int i = 0; i < width * height; i++) {
			byte a = (byte) ((pixels[i] >> 24) & 0xff);
			byte r = (byte) ((pixels[i] >> 16) & 0xff);
			byte g = (byte) ((pixels[i] >> 8) & 0xff);
			byte b = (byte) (pixels[i] & 0xff);

			pixelBuffer.put(r);
			pixelBuffer.put(g);
			pixelBuffer.put(b);
			pixelBuffer.put(a);
		}
		pixelBuffer.flip();
		
		glBindTexture(GL_TEXTURE_2D, id);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelBuffer);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	}

	public BufferedImage getImageData(String path) {
		try {
			return ImageIO.read(Texture.class.getResourceAsStream(path));
		} catch (IOException e) {
			System.err.println("Could not load image from " + path);
			e.printStackTrace();
			System.exit(-1);
			return null;
		}
	}
}
