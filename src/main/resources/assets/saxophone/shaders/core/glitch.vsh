#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>
//#include veil:camera

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec2 UV1;
in vec2 UV2;
in vec3 Normal;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform float STime;
uniform vec3 ChunkOffset;
uniform int FogShape;
uniform vec3 CameraPos;
uniform mat4 IViewMat;

uniform vec4 RandomMasking;

out vec4 vertexColor;
out vec2 texCoord0;
out vec2 texCoord1;
out vec2 texCoord2;
out vec4 normal;
out float vertexDistance;

vec4 worldSpace(vec4 p){
    //worldPos = (IViewMat * (ModelViewMat * vec4(Position, 1.0))).xyz + VeilCamera.CameraPosition;
    return vec4(CameraPos, 0.0) + IViewMat * p;


}
//rand function from: https://stackoverflow.com/questions/4200224/random-noise-functions-for-glsl
float rand(vec2 co){
    return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {

    vec3 viewPos = Position + ChunkOffset;
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    /*if(worldSpace(gl_Position).y>75.0){

        gl_Position = ProjMat * ModelViewMat * vec4(vec3(Position.x,(Position.y+0.3),Position.z), 1.0);
    }*/

    if (fract(sin(gl_Position.y * 10.0) * 43758.5453) > 0.9) {
        gl_Position.x += sin(STime * 10.0) * 0.1;
    }

    if(
    (gl_Position.x > rand(vec2(RandomMasking.x))) &&
    (gl_Position.x < rand(vec2(RandomMasking.y)))
    ){
        gl_Position.x += ((RandomMasking.x+RandomMasking.y)-1.0);
    }
    if(
    (gl_Position.y > rand(vec2(RandomMasking.z))) &&
    (gl_Position.y < rand(vec2(RandomMasking.w)))
    ){
        gl_Position.x += ((RandomMasking.z+RandomMasking.w)-1.0);
    }


    vertexColor = Color;
    texCoord0 = UV0;

    //TO-DO: proper glitch displacement
    //if(UV0.x>0.5)texCoord0 =vec2(UV0.y,UV0.x);

    texCoord1 = UV1;
    texCoord2 = UV2;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}