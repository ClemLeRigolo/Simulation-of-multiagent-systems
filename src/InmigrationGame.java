import gui.GUISimulator;
import gui.Rectangle;
import java.awt.*;

public class InmigrationGame {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        InmigrationGameEngine game = new InmigrationGameEngine(gui, 30, 100);
    }
}

class InmigrationGameEngine extends CellGameEngine {

    public InmigrationGameEngine(GUISimulator gui, int gridSize, int cellNumber) {
        super(gui, gridSize, cellNumber, 4);
    }

    public void calculateNeighbours(){
        //Neighbours are the neighbours cells with a higher state
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++){
                int nbNeighbours = 0;
                for(int k = -1; k <= 1; k++) {
                    for(int l = -1; l <= 1; l++){
                        if(!(k == 0 && l == 0) && i + k >= 0 && i + k < gridSize && j + l >= 0 && j + l < gridSize){
                            if(grid.getGrid()[i + k][j + l].getPreviousState() > grid.getGrid()[i][j].getPreviousState()){
                                nbNeighbours++;
                            }
                        }
                    }
                }
                grid.getGrid()[i][j].setNbNeighbours(nbNeighbours);
            }
        }


    }

    public void firstGeneration(int cellNumber){
        // set all cells to random states
        for(int i = 0; i < gridSize * gridSize; i++) {
            int x = (int) (Math.random() * gridSize);
            int y = (int) (Math.random() * gridSize);
            grid.getGrid()[x][y].setPreviousState((int) (Math.random() * stateNumber));
            grid.getGrid()[x][y].setCurrentState(grid.getGrid()[x][y].getPreviousState());
        }
        calculateNeighbours();
    }

    public void nextGeneration() {
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++){
                if(grid.getGrid()[i][j].getNbNeighbours() >= 3){
                    grid.getGrid()[i][j].nextState();
                }
            }
        }
        calculateNeighbours();
    }

}