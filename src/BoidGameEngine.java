import gui.GUISimulator;

public abstract class BoidGameEngine extends GameEngine {

    protected int fishNumber;

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

