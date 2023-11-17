import gui.GUISimulator;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Simule un banc de poissons à l'aide de Boids.
 */
public class FishBoids {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.CYAN);
        FishBoidsEngine game = new FishBoidsEngine(gui, 500);
    }
}

/**
 * BAnc de poisson
 * cet objet est associé à une fenêtre graphique GUISimulator, dans laquelle
 * il peut se dessiner.
 * De plus il hérite de Simulable, donc il définit deux méthodes next() et
 * restart() invoquées par la fenêtre graphique de simulation selon les
 * commandes entrées par l'utilisateur.
 * Utilise la simulation des Boids et les poissons Boids pour simuler le comportement d'un banc de poissons.
 */
class FishBoidsEngine extends BoidGameEngine{

    private EventManager eventManager;

    private BoidShoal niceFishes;
    private BoidShoal badFishes;
    private Sardine[] sardines;
    private Shark shark;
    private int fishNumber;
    private FlowField flowField;

    public FishBoidsEngine(GUISimulator gui, int fishNumber){
        super(gui, fishNumber);
    }

    @Override
    protected void firstGeneration(int fishNumber) {

        this.fishNumber = fishNumber;
        this.eventManager = new EventManager();

        niceFishes = new BoidShoal();
        badFishes = new BoidShoal();
        sardines = new Sardine[fishNumber];
        shark = new Shark(75);
        //Init sardines
        for (int i = 0; i < fishNumber; i++) {
            //random size
            sardines[i] = new Sardine((int) ((Math.random() * 10) + 20));
        }


        badFishes.addBoids(new Shark[]{shark});
        //set predator for each sardine
        for (Sardine sardine : sardines) {
            sardine.setPredators(badFishes);
        }

        niceFishes.addBoids(sardines);
        BoidShoal preyFishes = new BoidShoal();
        preyFishes.addBoids(sardines);
        shark.setPreys(preyFishes);


        Consumer<Boid> drawFish = (Boid b) -> {
            gui.addGraphicalElement(b);
            b.location = new Vector2((float) (Math.random() * 800), (float) (Math.random() * 600));
        };

        niceFishes.applyToAllBoids(drawFish);
        badFishes.applyToAllBoids(drawFish);

        flowField = new FlowField(10, 800, 600);
        flowField.initField(FlowEnum.RANDOM);
        flowField.initField(FlowEnum.RANDOM);
        eventManager.addEvent(new ChangeFieldEvent(100));

        //Lambda function in parameter of the event
        eventManager.addEvent(new UpdateFishesEvent(1, 1, niceFishes, (Boid b) -> {
            niceFishes.cohesion(b, b.size*2.5);
            niceFishes.align(b, b.size);
            niceFishes.separate(b, b.size*1.5);
            b.follow(flowField);
            b.update();
        }));

        eventManager.addEvent(new UpdateFishesEvent(1, 2, badFishes, (Boid b) -> {
            b.follow(flowField);
            b.update();
        }));

    }

    //Make a fish rotate a given angle

    @Override
    protected void draw() {

    }

    @Override
    public void restart() {
        flowField.initField(FlowEnum.RANDOM);
        BoidShoal preyFishes = new BoidShoal();
        preyFishes.addBoids(sardines);
        shark.setPreys(preyFishes);
        Consumer<Boid> restart = (Boid b) -> {
            b.size = (int) ((Math.random() * 10) + 20);
            b.location = new Vector2((float) (Math.random() * 800), (float) (Math.random() * 600));
        };
        niceFishes.applyToAllBoids(restart);
    }

    @Override
    public void next() {
        //10% chance to update field
        eventManager.next();
        draw();
    }

    /**
     * @Events
     */

    class ChangeFieldEvent extends Event {

        public ChangeFieldEvent(int date) {
            super(date);
        }

        @Override
        public void execute() {
            if(flowField.getFlowEnum() == FlowEnum.RANDOM) {
                flowField.initField(FlowEnum.CENTER);
            }else {
                flowField.initField(FlowEnum.RANDOM);
            }
            //random between 1000 and 100000
            int randomTime = (int) (Math.random() * 1000) + 100;
            eventManager.addEvent(new ChangeFieldEvent((int) (eventManager.getCurrentDate() + randomTime)));
        }

    }

    class UpdateFishesEvent extends Event {
        int timeStep;
        BoidShoal shoal;
        Consumer<Boid> updateFish;
        public UpdateFishesEvent(int date, int timeStep, BoidShoal shoal, Consumer<Boid> updateFish) {
            super(date);
            this.shoal = shoal;
            this.timeStep = timeStep;
            this.updateFish = updateFish;
        }

        @Override
        public void execute() {
            this.shoal.applyToAllBoids(updateFish);
            eventManager.addEvent(new UpdateFishesEvent((int) (eventManager.getCurrentDate() + timeStep), timeStep, shoal, updateFish));
        }

    }

}
