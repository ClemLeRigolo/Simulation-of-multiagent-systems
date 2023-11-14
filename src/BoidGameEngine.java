import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public abstract class BoidGameEngine extends GameEngine {

    protected int boidNumber;

    public BoidGameEngine(GUISimulator gui, int boidNumber) {
        super(gui, boidNumber);
        this.gui = gui;
        gui.setSimulable(this);
        init();
    }

    @Override
    public void restart(){

    }

    @Override
    public void next(){

    }

    protected abstract void firstGeneration(int entityNumber);

    protected void draw(){

    }

}

