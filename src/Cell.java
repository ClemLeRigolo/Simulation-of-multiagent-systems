import java.awt.*;
public class Cell {
    private int x;
    private int y;
    private int nbStates;
    private int previousState;
    private int currentState;

    private int nbNeighbours;

    public Cell(int nbStates, int x, int y) {
        setNbStates(nbStates);
        setCurrentState(0);
        setPreviousState(0);
        setNbNeighbours(0);
        setX(x);
        setY(y);
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

    public int getNbNeighbours() {
        return nbNeighbours;
    }

    public void setNbNeighbours(int nbNeighbours) {
        this.nbNeighbours = nbNeighbours;
    }


    public Color getColor() {
        //I want state 0 to be white and state nbStates - 1 to be black
        return new Color((int) (255 * (nbStates - 1 - currentState) / (nbStates - 1)), (int) (255 * (nbStates - 1 - currentState) / (nbStates - 1)), (int) (255 * (nbStates - 1 - currentState) / (nbStates - 1)));

    }

}
