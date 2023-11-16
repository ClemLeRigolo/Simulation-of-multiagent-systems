import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
/**
 * Simulation avec  GUI
 * cet objet est associé à une fenêtre graphique GUISimulator, dans laquelle
 * il peut se dessiner.
 * De plus il hérite de Simulable, donc il définit deux méthodes next() et
 * restart() invoquées par la fenêtre graphique de simulation selon les
 * commandes entrées par l'utilisateur.
 * Cette méthode est abstraite est permet de définir ce que toutes nos simulations doivent définir afin de fonctionner.
 * Un état initial, un moyen d'initialiser les variables, un moyen de dessiner sur la fenetre GUI et une méthode pour refresh la page GUI.
 */
public abstract class GameEngine implements Simulable{
    protected GUISimulator gui;

    protected int entityNumber;

    public GameEngine(GUISimulator gui, int entityNumber){
        this.gui = gui;
        this.entityNumber = entityNumber;
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
