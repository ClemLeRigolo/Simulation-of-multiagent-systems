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

class BallsSimulator implements Simulable{
    private GUISimulator gui;
    private Balls balls = new Balls();

    public BallsSimulator(GUISimulator gui) {
        this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
    }


    @Override
    public void next() {
        balls.translate(10, 10);
        draw();
        System.out.println(balls.toString());
    }

    @Override
    public void restart() {
        balls.reInit();
        draw();
    }

    private void draw(){
        gui.reset();	// clear the window
        for (int i = 0; i < balls.size(); i++) {
            gui.addGraphicalElement(new Oval((int) balls.getBall(i).getX(), (int) balls.getBall(i).getY(), Color.RED, Color.RED, 10));
        }
    }

}