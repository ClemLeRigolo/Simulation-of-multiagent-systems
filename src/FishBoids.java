import gui.GUISimulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class FishBoids {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.CYAN);
        FishBoidsEngine game = new FishBoidsEngine(gui, 200);
    }
}


class FishBoidsEngine extends BoidGameEngine{

    Fishes niceFishes;
    Fishes badFishes;
    Sardine[] sardines;
    Shark shark;
    int fishNumber;
    FlowField flowField;
    Vector2 target = new Vector2(500, 500);
    public FishBoidsEngine(GUISimulator gui, int fishNumber){
        super(gui, fishNumber);
    }

    @Override
    protected void firstGeneration(int fishNumber) {

        this.fishNumber = fishNumber;

        niceFishes = new Fishes();
        badFishes = new Fishes();
        sardines = new Sardine[fishNumber];
        shark = new Shark(75);
        //Init sardines
        for (int i = 0; i < fishNumber; i++) {
            //random size
            sardines[i] = new Sardine((int) ((Math.random() * 10) + 20));
        }

        shark.setPreys(niceFishes);
        badFishes.addBoids(new Shark[]{shark});
        //set slayers for each sardine
        for (Sardine sardine : sardines) {
            sardine.setSlayers(badFishes);
        }

        niceFishes.addBoids(sardines);
        niceFishes.addBoids(new Shark[]{shark});


        Consumer<Boid> drawFish = (Boid b) -> {
            gui.addGraphicalElement(b);
            b.location = new Vector2((float) (Math.random() * 800), (float) (Math.random() * 600));
        };

        niceFishes.applyToAllBoids(drawFish);

        flowField = new FlowField(10, 800, 600);
        flowField.initField(FlowEnum.RANDOM);
    }

    //Make a fish rotate a given angle

    @Override
    protected void draw() {
        Consumer<Boid> updateFish = (Boid b) -> {

            niceFishes.cohesion(b, b.size*2.5);
            niceFishes.align(b, b.size);
            niceFishes.separate(b, b.size*1.5);
            b.follow(flowField);
            b.update();
        };
        niceFishes.applyToAllBoids(updateFish);
    }

    @Override
    public void restart() {
        flowField.initField(FlowEnum.RANDOM);
        Consumer<Boid> restart = (Boid b) -> {
            b.location = new Vector2((float) (Math.random() * 800), (float) (Math.random() * 600));
        };
        niceFishes.applyToAllBoids(restart);
    }

    @Override
    public void next() {
        //10% chance to update field
        if(Math.random() < 0.001){
            //1% to switch between random and center
            if(flowField.getFlowEnum() == FlowEnum.RANDOM) {
                flowField.initField(FlowEnum.CENTER);
            }else {
                flowField.initField(FlowEnum.RANDOM);
            }
        }
        draw();
    }

}
