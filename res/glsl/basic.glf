#version 330 core

in float v_brightness;
in vec2 v_texCoords;

uniform sampler2D u_texSampler;

void main() {
	vec4 texColour = texture2D(u_texSampler, v_texCoords); 
	gl_FragColor = vec4(texColour.xyz * v_brightness, texColour.w);
}