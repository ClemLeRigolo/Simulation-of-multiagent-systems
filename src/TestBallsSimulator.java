import java.awt.Color;
import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

public class TestBallsSimulator {
    public static void main ( String [] args ) {
        GUISimulator gui = new GUISimulator (500 , 500 , Color.BLACK );
        gui.setSimulable (new BallsSimulator(gui) ) ;
    }

}
/**
 * Des balles qui rebondissent...
 * cet objet est associé à une fenêtre graphique GUISimulator, dans laquelle
 * il peut se dessiner.
 * De plus il hérite de Simulable, donc il définit deux méthodes next() et
 * restart() invoquées par la fenêtre graphique de simulation selon les
 * commandes entrées par l'utilisateur.
 */

class BallsSimulator implements Simulable{
    private GUISimulator gui;
    private Balls balls = new Balls();

    private EventManager eventManager = new EventManager();

    public BallsSimulator(GUISimulator gui) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        eventManager.addEvent(new ResetBallsEvent(1));
        draw();
    }


    @Override
    public void next() {
        eventManager.next();
    }

    @Override
    public void restart() {
        eventManager.restart();
        eventManager.addEvent(new ResetBallsEvent(1));
        eventManager.next();
    }

    private void draw(){
        gui.reset();	// clear the window
        for (int i = 0; i < balls.size(); i++) {
            gui.addGraphicalElement(new Oval((int) balls.getBall(i).getX(), (int) balls.getBall(i).getY(), Color.RED, Color.RED, 10));
        }
    }

    class MoveBallsEvent extends Event {

        public MoveBallsEvent(int date) {
            super(date);
        }

        @Override
        public void execute() {
            balls.translate(10, 10);
            draw();
            eventManager.addEvent(new MoveBallsEvent((int) (eventManager.getCurrentDate() + 1)));
        }

    }

    class ResetBallsEvent extends Event {


        public ResetBallsEvent(int date) {
            super(date);
        }

        @Override
        public void execute() {
            balls.reInit();
            eventManager.addEvent(new MoveBallsEvent((int) (eventManager.getCurrentDate() + 1)));
            draw();
        }

    }

}



