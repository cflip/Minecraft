package ca.compflip.minecraft.gfx;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class Shader {
	private static final int LOG_SIZE = 512;

	public int id;

	private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(4 * 4);

	public Shader(String vertexPath, String fragmentPath) {
		int vertexShader = loadShader(loadTxt(vertexPath), GL_VERTEX_SHADER);
		int fragmentShader = loadShader(loadTxt(fragmentPath), GL_FRAGMENT_SHADER);

		id = glCreateProgram();
		glAttachShader(id, vertexShader);
		glAttachShader(id, fragmentShader);
		glLinkProgram(id);

		if (glGetProgrami(id, GL_LINK_STATUS) == GL_FALSE) {
			System.err.println("Failed to link program!\n" + glGetProgramInfoLog(id, LOG_SIZE));
		}
	}

	public void storeMatrix(Matrix4f matrix, String uniformName) {
		glUseProgram(id);
		matrix.store(matrixBuffer);
		matrixBuffer.flip();

		glUniformMatrix4(glGetUniformLocation(id, uniformName), false, matrixBuffer);
	}

	private int loadShader(String source, int type) {
		int shader = glCreateShader(type);
		glShaderSource(shader, source);
		glCompileShader(shader);

		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile shader!\n" + glGetShaderInfoLog(shader, LOG_SIZE));
		}

		return shader;
	}

	private String loadTxt(String path) {
		StringBuilder builder = new StringBuilder();
		try {
			InputStream input = Shader.class.getResourceAsStream(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append("\n");
			}
			reader.close();
		} catch (Exception e) {
			System.err.println("Error while loading file from " + path);
			e.printStackTrace();
			System.exit(-1);
		}

		return builder.toString();
	}
}
