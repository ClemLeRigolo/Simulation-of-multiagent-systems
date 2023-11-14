import gui.GUISimulator;

import java.awt.*;

public class FishBoids {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        GameOfLifeEngine game = new GameOfLifeEngine(gui, 30, 100);
    }
}


public class FishBoidsEngine extends BoidGameEngine{
    public FishBoidsEngine(GUISimulator gui, int boidNumber){
        super(gui, boidNumber);
    }

    @Override
    protected void firstGeneration(int entityNumber) {

    }

    @Override
    protected void draw() {

    }

    @Override
    public void restart() {

    }

    @Override
    public void next() {

    }
}
