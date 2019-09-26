#version 330 core

layout(location = 0) in vec2 vertexPosition;

uniform mat4 Projection;
uniform mat4 View;
uniform float ObjectSize;
uniform mat4 ObjectModelMatrix;

void main() {
    gl_Position = Projection * View * ObjectModelMatrix * vec4(vertexPosition.x * ObjectSize, vertexPosition.y * ObjectSize, 1, 1);
}