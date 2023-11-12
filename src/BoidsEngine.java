import gui.GUISimulator;
import gui.Rectangle;
import java.awt.*;
import java.util.HashSet;

public class BoidsEngine extends CellGameEngine {

    protected GridBoids grid;
    protected int distance;

    private Color[] colors;
    HashSet<Cell> coloredCells = new HashSet<>();
    HashSet<Cell> emptyCells = new HashSet<>();

    public BoidsEngine(GUISimulator gui, int gridSize, int boidNumber, int distance) {
        super(gui, gridSize, boidNumber, 1);
        this.distance = distance;
        this.grid = new GridBoids(gridSize, 1);
        gui.setSimulable(this);

        // Generate random and different colors
        colors[0] = Color.WHITE;
        for (int i = 1; i < 1; i++) {
            colors[i] = new Color((int) (Math.random() * 0x1000000));
            if (colors[i] == colors[0]) {
                i--;
            }
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                emptyCells.add(grid.getGrid()[i][j]);
                grid.getGrid()[i][j].setCurrentState(0);
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
                        Color.LIGHT_GRAY, colors[grid.getBoids(i, j).getCurrentState()],
                        this.cellWidth));
            }
        }

    }

    @Override
    public void restart() {
        grid = new GridBoids(this.gridSize, this.stateNumber);
        colors[0] = Color.WHITE;
        for (int i = 1; i < 1; i++) {
            colors[i] = new Color((int) (Math.random() * 0x1000000));
            if (colors[i] == colors[0]) {
                i--;
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
        // Neighbours are the boids at a distance less than 2.
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                HashSet<Boids> nbNeighbours = new HashSet<>();
                // For each adjacent boid
                for (int k = -this.distance; k <= this.distance; k++) {
                    for (int l = -this.distance; l <= this.distance; l++) {
                        // If the boid is not the current boid and is in the grid
                        if (!(k == 0 && l == 0) && i + k >= 0 && i + k < gridSize && j + l >= 0 && j + l < gridSize) {
                            // If there is an another boids at a less than a set distance
                            if (grid.getGrid()[i + k][j + l].getCurrentState() != 0) {
                                nbNeighbours.add(grid.getGrid()[i + k][j + l]);
                            }
                        }
                    }
                }
                grid.getGrid()[i][j].setNbNeighbours(nbNeighbours);
            }
        }
    }

    public void firstGeneration(int boidNumber) {
        // set all boids to random states
        for (int i = 0; i < boidNumber; i++) {
            int x = (int) (Math.random() * gridSize);
            int y = (int) (Math.random() * gridSize);
            int state = 1;
            grid.getGrid()[x][y].setPreviousState(state);
            grid.getGrid()[x][y].setCurrentState(state);
        }
        calculateNeighbours();
    }

    public void nextGeneration() {
        // Calculate the next state of each boid
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid.getGrid()[i][j].getCurrentState() == 1 && !grid.getGrid()[i][j].getIsMoved()) {
                    int vitessex = grid.getGrid()[i][j].getvitx();
                    int vitessey = grid.getGrid()[i][j].getvity();

                    int forcex = calcul_force(sqr(vitessex), i);
                    int forcey = calcul_force(sqr(vitessey), j);

                    for (Boids boids : grid.getGrid()[i][j].getNeighbours()) {
                        forcex += calcul_force(boids.getvitx(), boids.getX());
                        forcey += calcul_force(boids.getvity(), boids.getY());
                    }

                    vitessex += forcex;
                    vitessey += forcey;

                    int new_x = i + vitessex;
                    int new_y = j + vitessey;

                    grid.getGrid()[new_x][new_y].setvitx(vitessex);
                    grid.getGrid()[new_x][new_y].setvity(vitessey);
                    grid.getGrid()[new_x][new_y].setCurrentState(1);
                    grid.getGrid()[new_x][new_y].setIsMoved(true);
                    grid.getGrid()[i][j].setCurrentState(0);

                }
                grid.getGrid()[i][j].setIsMoved(false);

            }
        }
        calculateNeighbours();
    }

    private int sqr(int nbr) {
        return nbr * nbr;
    }

    private int calcul_force(int vitesse, int position) {
        return sqr(vitesse) / position;
    }

}
