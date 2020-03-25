package ca.compflip.minecraft.util;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class MatrixMath {
	public static Matrix4f createTransformMatrix(Vector3f position, Vector3f rotation, Vector3f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		Matrix4f.translate(position, matrix, matrix);
		Matrix4f.rotate((float) (Math.toRadians(rotation.x)), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) (Math.toRadians(rotation.y)), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) (Math.toRadians(rotation.z)), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(scale, matrix, matrix);

		return matrix;
	}

	public static Matrix4f createViewMatrix(Vector3f position, Vector3f rotation) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();

		Matrix4f.rotate((float) (Math.toRadians(rotation.x)), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) (Math.toRadians(rotation.y)), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) (Math.toRadians(rotation.z)), new Vector3f(0, 0, 1), matrix, matrix);

		Matrix4f.translate(new Vector3f(-position.x, -position.y, -position.z), matrix, matrix);

		return matrix;
	}

	public static Matrix4f createProjectionMatrix(float fov, float nearPlane, float farPlane) {
		float aspect = (float) Display.getWidth() / (float) Display.getHeight();
		float yScale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))));
		float xScale = yScale / aspect;
		float frustumSize = farPlane - nearPlane;

		Matrix4f matrix = new Matrix4f();

		matrix.m00 = xScale;
		matrix.m11 = yScale;
		matrix.m22 = -((farPlane + nearPlane) / frustumSize);
		matrix.m23 = -1;
		matrix.m32 = -((2 * nearPlane * farPlane) / frustumSize);
		matrix.m33 = 0;

		return matrix;
	}
}