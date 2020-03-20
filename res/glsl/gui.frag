#version 330 core

in vec2 v_texCoords;

uniform sampler2D u_texSampler;

void main() {
	gl_FragColor = texture2D(u_texSampler, v_texCoords);
}