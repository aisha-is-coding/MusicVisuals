package ie.tudublin;

import processing.core.PApplet;

public class AishaVisual extends PApplet 
{
    private SpinningSpheres visual;

    public void settings() {
        size(500, 500, P3D);
      }
    
    public void setup() 
    {
        colorMode(HSB);
        visual = new SpinningSpheres(this);
    }

    public void draw() 
    {
        visual.render();
    }

    public void stop() 
    {
        visual.close();
        super.stop();
    }

    public static void main(String[] args) 
    {
        PApplet.main(new String[] { AishaVisual.class.getName() });
    }
    

   
}

