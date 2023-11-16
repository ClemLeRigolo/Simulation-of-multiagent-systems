import gui.GUISimulator;
import gui.Rectangle;
import java.awt.*;
import java.util.HashSet;

/**
 * Je teste le SchellingGame
 */

public class SchellingGame {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        SchellingGameEngine game = new SchellingGameEngine(gui, 100, 5000, 3, 4);
    }
}

/**
 * SchellingGame
 * cet objet est associé à une fenêtre graphique GUISimulator, dans laquelle
 * il peut se dessiner.
 * De plus il hérite de Simulable, donc il définit deux méthodes next() et
 * restart() invoquées par la fenêtre graphique de simulation selon les
 * commandes entrées par l'utilisateur.
 * Il permet de montrer l'ampleur de l'effet de la ségrégation au sein d'un groupe d'individu.
 */
class SchellingGameEngine extends CellGameEngine {
    private int threshold;
    private int colorNumber;

    // Array of Color
    private Color[] colors;
    HashSet<Cell> coloredCells = new HashSet<>();
    HashSet<Cell> emptyCells = new HashSet<>();

    public SchellingGameEngine(GUISimulator gui, int gridSize, int cellNumber, int threshold, int colorNumber) {
        super(gui, gridSize, cellNumber, colorNumber);
        this.threshold = threshold;
        this.colorNumber = colorNumber;
        this.colors = new Color[colorNumber+1];

        // Generate random and different colors
        colors[0] = Color.WHITE;
        for (int i = 1; i <= colorNumber; i++) {
            colors[i] = new Color((int) (Math.random() * 0x1000000));
            for (int j = 0; j < i; j++) {
                if (colors[i] == colors[j]) {
                    i--;
                }
            }
        }
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                emptyCells.add(grid.getGrid()[i][j]);
            }
        }

        next();
        next();
    }

    @Override
    protected void init() {
        // All cells are in emptyCells

    }

    @Override
    protected void draw() {
        gui.reset();
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                gui.addGraphicalElement(new Rectangle(i * this.cellWidth + 60, j * this.cellWidth + 60,
                        Color.LIGHT_GRAY, colors[grid.getCase(i, j).getCurrentState()],
                        this.cellWidth));
            }
        }

    }

    @Override
    public void restartFunc() {
        grid = new Grid<>(this.gridSize, this.stateNumber);
        for (int i = 1; i < colorNumber; i++) {
            colors[i] = new Color((int) (Math.random() * 0x1000000));
            for (int j = 0; j < i; j++) {
                if (colors[i] == colors[j]) {
                    i--;
                }
            }
        }


        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {

                grid.getCase(i,j).setCurrentState(0);
                grid.getCase(i,j).setPreviousState(0);

                if (!(emptyCells.contains(grid.getCase(i, j)))) {
                    emptyCells.add(grid.getGrid()[i][j]);
                }

                if (coloredCells.contains(grid.getCase(i, j))) {
                    coloredCells.remove(grid.getGrid()[i][j]);
                }

            }
        }

    }

    public void calculateNeighbours() {
        // Neighbours of coloredCells are the neighbours cells with a different state
        // different from 0

        for (Cell cell : coloredCells) {
            int nbNeighbours = 0;
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {
                    if (!(k == 0 && l == 0) && cell.getX() + k >= 0 && cell.getX() + k < gridSize
                            && cell.getY() + l >= 0 && cell.getY() + l < gridSize) {
                        if (grid.getGrid()[cell.getX() + k][cell.getY() + l].getCurrentState() != 0
                                && grid.getGrid()[cell.getX() + k][cell.getY() + l].getCurrentState() != cell
                                        .getCurrentState()) {
                            nbNeighbours++;
                        }
                    }
                }
            }
            cell.setNbNeighbours(nbNeighbours);
        }
    }

    public void firstGeneration(int cellNumber) {
        // set random cells to random states
        for (int i = 0; i < cellNumber; i++) {
            int x = (int) (Math.random() * gridSize);
            int y = (int) (Math.random() * gridSize);
            int state = (int) (Math.random() * (colorNumber)) + 1;
            grid.getGrid()[x][y].setPreviousState(state);
            grid.getGrid()[x][y].setCurrentState(state);
            coloredCells.add(grid.getGrid()[x][y]);
            emptyCells.remove(grid.getGrid()[x][y]);

        }
        calculateNeighbours();
    }

    @Override
    protected void nextGeneration() {
        // If a cell has more than threshold neighbours with a different state, it
        // becomes an empty cell and moves to a random empty cell
        // Empty cells are those with state 0

        HashSet<Cell> movingCells = new HashSet<>();

        HashSet<Cell> copycolored = new HashSet<>(coloredCells);

        for (Cell cell : copycolored) {
            if (cell.getNbNeighbours() >= threshold && cell.getCurrentState() != 0) {
                movingCells.add(cell);
            }
        }

        HashSet<Cell> copymoved = new HashSet<>(movingCells);

        for (Cell cell : copymoved) {
            // Move the cell to a random empty cell
            int x = (int) (Math.random() * gridSize);
            int y = (int) (Math.random() * gridSize);
            while (!(emptyCells.contains(grid.getCase(x, y)))) {
                x = (int) (Math.random() * gridSize);
                y = (int) (Math.random() * gridSize);
            }

            grid.getGrid()[x][y].setCurrentState(cell.getCurrentState());
            cell.setCurrentState(0);
            coloredCells.remove(cell);
            coloredCells.add(grid.getGrid()[x][y]);
            emptyCells.remove(grid.getGrid()[x][y]);
            emptyCells.add(cell);
            movingCells.remove(cell);
        }

        calculateNeighbours();

    }
}