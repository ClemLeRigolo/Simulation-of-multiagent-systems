import gui.GUISimulator;
import gui.Rectangle;
import java.awt.*;

/**
 * Teste le jeu de l'immigration
 */
public class InmigrationGame {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        InmigrationGameEngine game = new InmigrationGameEngine(gui, 50, -1);
    }
}

/**
 * Jeu de l'immigration
 * cet objet est associé à une fenêtre graphique GUISimulator, dans laquelle
 * il peut se dessiner.
 * De plus il hérite de Simulable, donc il définit deux méthodes next() et
 * restart() invoquées par la fenêtre graphique de simulation selon les
 * commandes entrées par l'utilisateur.
 * Ce jeu est une version à plus de 2 états du jeu de la vie. Chaque changement d'états fait passer à l'état k+1 (au lieu de 1 à 0 et 0 à 1 dans le jeu de la vie).
 */
class InmigrationGameEngine extends CellGameEngine {

    public InmigrationGameEngine(GUISimulator gui, int gridSize, int cellNumber) {
        super(gui, gridSize, cellNumber, 4);
        super.init();
    }

    public void calculateNeighbours(){
        //Neighbours are the neighbours cells with a higher state
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++){
                int nbNeighbours = 0;
                //For each adjacent cell
                for(int k = -1; k <= 1; k++) {
                    for(int l = -1; l <= 1; l++){
                        //If the cell is not the current cell and is in the grid
                        if(!(k == 0 && l == 0) && i + k >= 0 && i + k < gridSize && j + l >= 0 && j + l < gridSize){
                            //If the adjacent cell has a higher state or if the current state is the highest and the adjacent cell has state 0
                            if(grid.getGrid()[i + k][j + l].getPreviousState() == grid.getGrid()[i][j].getPreviousState() + 1 || (grid.getGrid()[i][j].getPreviousState() == stateNumber - 1 && grid.getGrid()[i + k][j + l].getPreviousState() == 0)){
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
            int x = i / gridSize;
            int y = i % gridSize;
            int state = (int) (Math.random() * stateNumber);
            grid.getGrid()[x][y].setPreviousState(state);
            grid.getGrid()[x][y].setCurrentState(state);
        }
        calculateNeighbours();
    }

    public void nextGeneration() {
        //Calculate the next state of each cell
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++){
                if(grid.getGrid()[i][j].getNbNeighbours() >= 3){
                    grid.getGrid()[i][j].nextState();
                    //Cell's previous state becomes current state
                    grid.getGrid()[i][j].setPreviousState(grid.getGrid()[i][j].getCurrentState());

                }
            }
        }
        calculateNeighbours();
    }

}
