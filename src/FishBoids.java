import gui.GUISimulator;

import java.awt.*;
import java.util.function.Consumer;

public class FishBoids {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.CYAN);
        FishBoidsEngine game = new FishBoidsEngine(gui, 100);
    }
}


class FishBoidsEngine extends BoidGameEngine{

    Sardines sardines;
    int fishNumber;
    FlowField flowField;
    Vector2 target = new Vector2(500, 500);
    public FishBoidsEngine(GUISimulator gui, int fishNumber){
        super(gui, fishNumber);
    }

    @Override
    protected void firstGeneration(int fishNumber) {

        this.fishNumber = fishNumber;

        sardines = new Sardines(fishNumber);


        Consumer<Boid> drawFish = (Boid b) -> {
            gui.addGraphicalElement(b);
            b.location = new Vector2((float) (Math.random() * 800), (float) (Math.random() * 600));
        };

        sardines.applyToAllBoids(drawFish);

        flowField = new FlowField(10, 800, 600);
        flowField.initField(FlowEnum.CENTER);
        //drawField();
    }

    //Make a fish rotate a given angle

    @Override
    protected void draw() {
        Consumer<Boid> updateFish = (Boid b) -> {

            sardines.align(b, b.size);
            sardines.separate(b, b.size/2);
            b.follow(flowField);
            b.update();
            if(b.location.getX() == Float.NaN){
                System.out.println(b.location.getX() + " " + b.location.getY());
            }
        };
        sardines.applyToAllBoids(updateFish);
    }

    @Override
    public void restart() {
        flowField.initField(FlowEnum.RANDOM);
        Consumer<Boid> restart = (Boid b) -> {
            b.location = new Vector2((float) (Math.random() * 800), (float) (Math.random() * 600));
        };
        sardines.applyToAllBoids(restart);
    }

    @Override
    public void next() {
        draw();
    }

}
