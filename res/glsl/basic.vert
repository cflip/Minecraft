#version 330 core

in layout(location = 0) vec4 a_pos;
in layout(location = 1) float a_brightness;
in layout(location = 2) vec2 a_texCoords;

uniform mat4 u_pMatrix;
uniform mat4 u_vMatrix;
uniform mat4 u_tMatrix;

out float v_brightness;
out vec2 v_texCoords;

void main() {
	gl_Position = u_pMatrix * u_vMatrix * u_tMatrix * a_pos;
	v_brightness = a_brightness;
	v_texCoords = a_texCoords;
}