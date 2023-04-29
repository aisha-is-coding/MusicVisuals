package ie.tudublin;

import processing.core.PApplet;
import ddf.minim.*;
import ddf.minim.analysis.*;
import processing.opengl.*;
import ddf.minim.analysis.*;


public class Circles extends PApplet {
    Minim minim;
    AudioPlayer song;
    float angle = 0;

    public void settings() 
    {
        size(1024, 500, P3D);
    }

    public void setup() 
    {
        minim = new Minim(this);
        song = minim.loadFile("MusicVisuals/java/data/Victoria_Mon_t_ft_Khalid_-_Experience.mp3", 1024);
        song.play();
    }

    public void draw() 
    {
        background(0);

        float level = song.mix.level();

        translate(width/2, height/2, 0);
        rotateY(angle);
        rotateX(angle);
    }



}