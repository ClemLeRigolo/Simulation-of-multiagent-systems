import gui.GUISimulator;
import gui.Rectangle;
import java.awt.*;
import java.util.HashSet;

public class SchellingGame {
    public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        SchellingGameEngine game = new SchellingGameEngine(gui, 100, 5000, 2, 4);
    }
}

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
                emptyCells.add(grid.getGrid()[i][j]);
            }
        }
        firstGeneration(cellNumber);
        draw();
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
                // If the cell is in emptyCell
                // System.out.println("color of cell " + i + " " + j + " is " +
                // colors[grid.getCell(i,j).getCurrentState()]+" and state is " +
                // grid.getCell(i,j).getCurrentState());
                gui.addGraphicalElement(new Rectangle(i * this.cellWidth + 60, j * this.cellWidth + 60,
                        Color.LIGHT_GRAY, colors[grid.getCell(i, j).getCurrentState()],
                        this.cellWidth));
            }
        }

    }

    @Override
    public void restart() {
        grid = new Grid(this.gridSize, this.stateNumber);
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
                emptyCells.add(grid.getGrid()[i][j]);
            }
        }
        firstGeneration(this.cellNumber);
        draw();
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
            // System.out.println("Cell at " + cell.getX() + " " + cell.getY() + " has " +
            // nbNeighbours + " neighbours");
        }
    }

    public void firstGeneration(int cellNumber) {
        // set random cells to random states
        System.out.println(cellNumber);
        for (int i = 0; i < cellNumber; i++) {
            int x = (int) (Math.random() * gridSize);
            int y = (int) (Math.random() * gridSize);
            int state = (int) (Math.random() * (colorNumber - 1)) + 1;
            grid.getGrid()[x][y].setPreviousState(state);
            grid.getGrid()[x][y].setCurrentState(state);
            if (state != 0) {
                coloredCells.add(grid.getGrid()[x][y]);
                emptyCells.remove(grid.getGrid()[x][y]);
            }

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
                System.out.println(threshold);
                System.out.println(cell.getNbNeighbours());
                System.out.println(cell.getCurrentState());
                System.out.println("");
                movingCells.add(cell);
            }
        }

        HashSet<Cell> copymoved = new HashSet<>(movingCells);

        for (Cell cell : copymoved) {
            // Move the cell to a random empty cell
            int x = (int) (Math.random() * gridSize);
            int y = (int) (Math.random() * gridSize);
            while (!(emptyCells.contains(grid.getCell(x, y)))) {
                x = (int) (Math.random() * gridSize);
                y = (int) (Math.random() * gridSize);
            }

            // grid.getGrid()[x][y].setPreviousState(cell.getPreviousState());
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