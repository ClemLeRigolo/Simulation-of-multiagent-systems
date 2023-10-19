import gui.GUISimulator;
import gui.Rectangle;
import java.awt.*;

public class GameOfLife {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        GameOfLifeEngine game = new GameOfLifeEngine(gui, 30, 100);
    }
}

class GameOfLifeEngine extends CellGameEngine {

    public GameOfLifeEngine(GUISimulator gui, int gridSize, int cellNumber) {
        super(gui, gridSize, cellNumber, 2);
    }

    @Override
    protected void draw(){
        gui.reset();	// clear the window
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                if(grid.getCell(i, j).getCurrentState() == 1){
                    gui.addGraphicalElement(new Rectangle(i * this.cellWidth + 60, j * this.cellWidth+60, Color.BLUE, Color.BLUE, this.cellWidth));
                }else{
                    gui.addGraphicalElement(new Rectangle(i * this.cellWidth + 60, j * this.cellWidth+60, Color.LIGHT_GRAY, Color.WHITE, this.cellWidth));
                }
            }
        }
    }

    public void calculateNeighbours(){
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++){
                int nbNeighbours = 0;
                for(int k = -1; k <= 1; k++) {
                    for(int l = -1; l <= 1; l++){
                        if(!(k == 0 && l == 0) && i + k >= 0 && i + k < gridSize && j + l >= 0 && j + l < gridSize){
                            if(grid.getGrid()[i + k][j + l].getPreviousState() == 1){
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
        // set random cells to true
        for(int i = 0; i < cellNumber; i++) {
            int x = (int) (Math.random() * gridSize);
            int y = (int) (Math.random() * gridSize);
            grid.getGrid()[x][y].setPreviousState(1);
            grid.getGrid()[x][y].setCurrentState(1);
        }
        calculateNeighbours();
    }

    public void nextGeneration() {
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++){
                grid.getGrid()[i][j].setPreviousState(grid.getGrid()[i][j].getCurrentState());
                grid.getGrid()[i][j].setCurrentState(grid.getGrid()[i][j].getNbNeighbours() == 3 || (grid.getGrid()[i][j].getNbNeighbours() == 2 && grid.getGrid()[i][j].getCurrentState() == 1) ? 1 : 0);
            }
        }

        //Set the previous state to the current state
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++){
                if(grid.getGrid()[i][j].getNbNeighbours() >= 3){
                    grid.getGrid()[i][j].setPreviousState(grid.getGrid()[i][j].getCurrentState());
                }
            }
        }
        calculateNeighbours();
    }

}
