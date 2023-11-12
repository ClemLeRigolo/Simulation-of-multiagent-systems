import java.awt.*;
import java.util.HashSet;

public class Boids extends Cell {
    private int x;
    private int y;
    private int nbStates;
    private int previousState;
    private int currentState;
    private boolean isMoved;
    private int posx;
    private int posy;
    private int vitessex;
    private int vitessey;

    private HashSet<Boids> nbNeighbours;

    public Boids(int nbStates, int x, int y) {
        super(nbStates, x, y);
        setIsMoved(false);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public int getNbStates() {
        return nbStates;
    }

    public void setNbStates(int nbStates) {
        this.nbStates = nbStates;
    }

    public void nextState() {
        setPreviousState(getCurrentState());
        setCurrentState((getCurrentState() + 1) % nbStates);
    }

    public int getCurrentState() {
        return currentState;
    }

    public int getPreviousState() {
        return previousState;
    }

    public void setCurrentState(int state) {
        this.currentState = state;
    }

    public void setPreviousState(int state) {
        this.previousState = state;
    }

    public HashSet<Boids> getNeighbours() {
        return nbNeighbours;
    }

    public void setNbNeighbours(HashSet<Boids> nbNeighbours) {
        this.nbNeighbours = nbNeighbours;
    }

    public Color getColor() {
        // state 0 is white
        return new Color((int) (255 * (nbStates - 1 - currentState) / (nbStates - 1)),
                (int) (255 * (nbStates - 1 - currentState) / (nbStates - 1)),
                (int) (255 * (nbStates - 1 - currentState) / (nbStates - 1)));

    }

    public void setIsMoved(boolean bool) {
        this.isMoved = false;
    }

    public boolean getIsMoved() {
        return this.isMoved;
    }

    public int getvitx() {
        return this.vitessex;
    }

    public void setvitx(int vitesse) {
        this.vitessex = vitesse;
    }

    public int getvity() {
        return this.vitessey;
    }

    public void setvity(int vitesse) {
        this.vitessey = vitesse;
    }

    public int getposx() {
        return this.posx;
    }

    public void setposx(int position) {
        this.posx = position;
    }

    public int getposy() {
        return this.posy;
    }

    public void setposy(int position) {
        this.posy = position;
    }

    public String toString() {
        return "Boid [" + x + "][" + y + "]\n" +
                "current: " + currentState +
                "\nprevious: " + previousState;
    }

}
