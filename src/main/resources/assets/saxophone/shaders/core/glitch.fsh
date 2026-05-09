#version 150



uniform sampler2D Sampler0;
uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform vec2 ScreenSize;
uniform float STime;
uniform float Randomizer;
uniform vec3 RandomRGB;

in vec4 vertexColor;

//Use this one!
in vec2 texCoord0;
in vec2 texCoord1;
in vec2 texCoord2;
in vec4 normal;

out vec4 fragColor;

float hash11(float p) {
    p=fract(.1031*p);
    p*=33.33+p;
    return fract(2.*p*p);
}
//rand function from: https://stackoverflow.com/questions/4200224/random-noise-functions-for-glsl
float rand(vec2 co){
    return fract(sin(dot(co, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (color.a < vertexColor.a) {
        discard;
    }

    int timeoffset=100*int(6.*STime);
    int off=10-int( 2.*hash11( 100.+float(timeoffset) ) );
    int modx=int(texCoord0.x);
    modx +=off;

    //temporary greyscaling.
    float greybit = (fragColor.r+fragColor.g+fragColor.b)/3;
    fragColor = texture(Sampler0,texCoord0);

    if(Randomizer > 0.6){
        fragColor = texture(Sampler0,texCoord0+vec2(rand(vec2(STime)),rand(vec2(rand(vec2(1-STime))))));
    }
    if(Randomizer > 0.8){
        fragColor.rgb = (RandomRGB+fragColor.rgb)/2;
    }else if (Randomizer > 0.6){
        fragColor.rgb = vec3(greybit);
    }

}