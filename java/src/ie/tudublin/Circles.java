package ie.tudublin;

import processing.core.PApplet;
import ddf.minim.*;
import processing.opengl.*;
import ddf.minim.analysis.*;


public class Circles extends PApplet {
    Minim minim;
    AudioPlayer song;
    float angle = 0;

    public void setup() {
        size(600, 600, P3D);
        colorMode(HSB);
  
        minim = new Minim(this);
        song = minim.loadFile("experience.mp3");
        song.play();
    }



}
