#version 330 core

in layout(location = 0) vec4 a_pos;
in layout(location = 2) vec2 a_texCoords;

uniform mat4 u_tMatrix;

out vec2 v_texCoords;

void main() {
	gl_Position = u_tMatrix * a_pos;
	v_texCoords = a_texCoords;
}