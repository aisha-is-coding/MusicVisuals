package ie.tudublin;

import java.util.ArrayList;
import ddf.minim.analysis.FFT;
import processing.core.PConstants;

public class music_note {
    // AudioPlayer ap;
    // AudioInput ai;
    // AudioBuffer ab;
    // float colour = 0;
    // int mode = 0;
    MyVisual mv;

    FFT fft;

    Star[] stars;
    float[] bands1;

    public music_note(MyVisual mv) {
        this.mv = mv;
        // colorMode(HSB, 360, 100, 100);

        mv.pushMatrix();
        stars = new Star[50];
        for (int i = 0; i < stars.length; i++) {
            float x = mv.random(mv.width - 100) + 50;
            float y = mv.random(mv.height - 100) + 50;
            float size = mv.random(20, 100);
            float speed = mv.random(1, 5);
            stars[i] = new Star(mv, x, y, size, speed, mv.height, mv.width, stars);
        }
        mv.popMatrix();

    }

    public void render() {
        draw();
        // System.out.print("here");
    }

    public void draw() {
        mv.background(0);
        float[] bands;
        bands = mv.getSmoothedBands();

        mv.pushMatrix();

        // loop through all stars in the stars array
        for (int i = 0; i < stars.length; i++) {
            Star star = stars[i];
            star.update();
            float size = MyVisual.map(bands[2], 0, 255, 20, 100);
            star.size = size;
            star.update();
            star.display(bands);
        }

        mv.popMatrix();
        drawNotes(bands);
    }

    public void drawNotes(float[] bands) {

        mv.pushMatrix();
        mv.stroke(255);
        mv.strokeWeight(4);

        // for drawing the notes
        int staffHeight = 200;
        int staffWidth = mv.width - 300;
        int staffY = mv.height / 2 + 50;
        int staffX = mv.width / 2 - staffWidth / 2;
        int staffSpacing = mv.height / 10;

        int noteSize = mv.width / 25;
        int noteX = staffX + staffWidth / 10;
        int noteY = staffY - staffHeight / 5;
        int noteDistance = mv.width / 10;

        // for colour of notes
        int frameCount = 0;
        int colourChangeInterval = 20;
        int colour = 0;

        // for drawing the staff on the screen
        mv.strokeWeight(1);
        for (int i = 0; i < 5; i++) {
            int y = staffY - (2 * staffHeight / 5) + (i * staffHeight / 4);
            mv.line(staffX, y, staffX + staffWidth, y);
        }

        // set colour of notes to change
        if (frameCount % colourChangeInterval == 0) {
            colour = (int) MyVisual.map(bands[2], 0, 255, 0, 255);
        }
        frameCount++;

        mv.smooth();
        mv.strokeWeight(4);
        int noteX2 = noteX + staffSpacing * 2;
        int noteY2 = noteY + staffSpacing;

        // Calculate the vertical position of the notes based on the amplitude
        mv.calculateAverageAmplitude();
        float amplitude = mv.getSmoothedAmplitude();
        float yOffset = MyVisual.map(amplitude, 0, 1, -staffHeight / 3, staffHeight / 2);

        mv.fill(colour, 255, 255);

        // draw first note
        mv.ellipse(noteX + noteDistance / 3, noteY2 + yOffset + noteSize, noteSize, noteSize);
        mv.line(noteX + noteDistance / 3 + 26, noteY2 + yOffset + noteSize, noteX + noteDistance / 3 + 26,
                staffY - staffHeight / 7 + yOffset);

        mv.ellipse(noteX2, noteY + (noteDistance / 2 + 30) + yOffset, noteSize, noteSize);
        mv.line(noteX2 + 27, noteY + (noteDistance / 2 + 30) + yOffset, noteX2 + 27,
                staffY - staffHeight / 5 + yOffset);

        // connect note
        mv.line(noteX + noteDistance / 3 + 26, staffY - staffHeight / 7 + yOffset, noteX2 + 27,
                staffY - staffHeight / 5 + yOffset);

        // another note
        mv.fill(colour, 255, 255);

        mv.ellipse((noteX2 + 37) * 2, noteY + (noteDistance + noteSize / 35) + yOffset, noteSize, noteSize);
        mv.line((noteX2 + 50) * 2, noteY + (noteDistance + noteSize / 35) + yOffset, (noteX2 + 50) * 2,
                noteY - staffHeight / 15 + yOffset);

        // first circle
        mv.ellipse((noteX + noteDistance - 30) * 2, noteY2 - (noteDistance - noteSize * 2) + yOffset, noteSize,
                noteSize);
        mv.line((noteX + noteDistance - 18) * 2, noteY2 - (noteDistance - noteY / 3 + 30) + yOffset,
                (noteX + noteDistance - 18) * 2, staffY - staffHeight / 2 + yOffset);

        // connect note
        mv.line((noteX + noteDistance - 18) * 2, staffY - staffHeight / 2 + yOffset, (noteX2 + 50) * 2,
                noteY - staffHeight / 15 + yOffset);

        mv.ellipse((noteX + noteDistance) * 3, noteY2 - (noteDistance - noteSize * 3) + yOffset, noteSize, noteSize);
        mv.line((noteX + noteDistance + 9) * 3, noteY2 - (noteDistance - noteY / 2 + 40) + yOffset,
                (noteX + noteDistance + 9) * 3, staffY - staffHeight / 3 + yOffset);

        mv.popMatrix();
    }

    class Star {
        float x;
        float y;
        float size;
        float speed;
        float height;
        float width;
        float rotate_Star;
        int num_point;
        float bands[];
        Star[] stars;
        int i = 0;

        int colourIndex = (int) mv.random(3);
        ArrayList<Integer> colours = new ArrayList<Integer>();
        int frameCount = 0;
        int colourChangeInterval = 20; // Change colour every 20 frames

        Star(MyVisual mv, float x, float y, float size, float speed, float height, float width, Star[] stars) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = speed;
            this.height = height;
            this.width = width;
            this.rotate_Star = 0;
            this.stars = stars;
            this.num_point = 5;

            // initialise colours array list
            colours.add(mv.color(230, 0, 255));
            colours.add(mv.color(255, 232, 31));
            colours.add(mv.color(255, 209, 220));
        }

        void update() {
            y += speed;
            rotate_Star += 0.01;

            // star still on screen, update it
            if (y > height) {
                y = 0;
                x = mv.random(width);
                size = mv.random(20, 100);
                speed = mv.random(1, 3);
            } else if (y < 0) // reset star to fall from the top of the screen again
            {
                y = height;
            }

        }

        void display(float[] bands) {
            mv.pushMatrix();
            mv.translate(x, y);
            mv.rotate(rotate_Star);
            mv.scale(size / 300);
            mv.noStroke();

            // Only change colour every colourChangeInterval frames
            if (frameCount % colourChangeInterval == 0) {
                colourIndex = (colourIndex + 1) % colours.size();
            }
            frameCount++;

            // change colour of stars
            mv.fill(colours.get(colourIndex));

            mv.beginShape();

            // change number of points for the star based on the fifth audio frequency band
            // value
            num_point = (int) MyVisual.map(bands[4], 0, 255, 5, 10);
            // System.out.print(bands[2]);
            for (int i = 0; i < num_point; i++) {
                // calculate angle of the current point
                float angle = PConstants.TWO_PI * i / num_point;

                // calculate the angle of first vertex
                float x = MyVisual.cos(angle) * 100;
                float y = MyVisual.sin(angle) * 100;

                // add vertex to shape
                mv.vertex(x, y);

                // get next vertex
                angle += PConstants.TWO_PI / (num_point * 2);
                x = MyVisual.cos(angle) * 50;
                y = MyVisual.sin(angle) * 50;

                // add vertex to shape
                mv.vertex(x, y);
            }

            mv.endShape();
            mv.popMatrix();
        }

    }
}
