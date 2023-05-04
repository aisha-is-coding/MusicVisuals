package ie.tudublin;

import processing.core.PApplet;
import ddf.minim.*;
//import ddf.minim.analysis.*;
//import processing.opengl.*;

public class AudioVisual {
    protected PApplet ui;
    protected Minim minim;
    protected AudioPlayer song;

    //constructor
    public AudioVisual(PApplet ui) 
    {
        this.ui = ui;
        minim = new Minim(ui);
        song = minim.loadFile("MusicVisuals/java/data/Victoria_Mon_t_ft_Khalid_-_Experience.mp3", 1024);
        song.play();
    }

    public void render() 
    {
        // Override this method in derived classes
    }

    public void close() 
    {
        song.close();
        minim.stop();
    }
}


