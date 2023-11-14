import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public abstract class GameEngine implements Simulable{
    protected GUISimulator gui;

    protected int entityNumber;

    public GameEngine(GUISimulator gui, int entityNumber){
        this.gui = gui;
        gui.setSimulable(this);
    }

    public abstract void restart();

    protected abstract void firstGeneration(int entityNumber);

    protected abstract void draw();
    protected void init(){
        firstGeneration(entityNumber);
        draw();
    }

}
