import gui.GUISimulator;

import gui.GraphicalElement;
import gui.Rectangle;
import java.awt.*;

public class FishBoids {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        FishBoidsEngine game = new FishBoidsEngine(gui, 1);
    }
}


class FishBoidsEngine extends BoidGameEngine{

    Fish fish;
    public FishBoidsEngine(GUISimulator gui, int boidNumber){
        super(gui, boidNumber);
    }

    @Override
    protected void firstGeneration(int entityNumber) {
        //Draw a fish using rectangles using Fish class
        fish = new Fish(100, 100, 30, Math.PI/4, Color.BLUE, Color.BLUE);
        fish.location = new PVector(100, 100);
        gui.addGraphicalElement(fish);


    }

    //Make a fish rotate a given angle
    public void rotateFish(Fish fish, double angle){
        fish.rotate(angle);
    }

    @Override
    protected void draw() {

    }

    @Override
    public void restart() {

    }

    @Override
    public void next() {
        fish.update();

        fish.seek(new PVector(250,250));
        System.out.println(fish.location.getX() + " " + fish.location.getY());
        //fish.moveTo(500,500);
        //fish.translate(10, 10);
        //fish.rotate(Math.PI/12);
    }
}
