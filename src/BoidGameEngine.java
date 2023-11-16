import gui.GUISimulator;

/**
 * Simulateur de Boid
 * cet objet est associé à une fenêtre graphique GUISimulator, dans laquelle
 * il peut se dessiner.
 * De plus il hérite de Simulable, donc il définit deux méthodes next() et
 * restart() invoquées par la fenêtre graphique de simulation selon les
 * commandes entrées par l'utilisateur..
 */
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

