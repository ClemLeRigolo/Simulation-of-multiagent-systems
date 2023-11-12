import java.util.HashSet;

import gui.GUISimulator;
import gui.Simulable;

public class BoidsEngine extends CellGameEngine {

    protected GridBoids grid;
    protected int distance;

    public BoidsEngine(GUISimulator gui, int gridSize, int boidNumber, int distance) {
        super(gui, gridSize, boidNumber, 1);
        this.distance = distance;
        this.grid = new GridBoids(gridSize, 1);
        gui.setSimulable(this);
        init();
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
                            // If the adjacent boid has a higher state or if the current state is the
                            // highest and the adjacent boid has state 0
                            if (grid.getGrid()[i + k][j + l]
                                    .getPreviousState() == grid.getGrid()[i][j].getPreviousState() + 1
                                    || (grid.getGrid()[i][j].getPreviousState() == stateNumber - 1
                                            && grid.getGrid()[i + k][j + l].getPreviousState() == 0)) {
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
        for (int i = 0; i < gridSize * gridSize; i++) {
            int x = i / gridSize;
            int y = i % gridSize;
            int state = 1;
            System.out.println(state);
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
                        forcex += calcul_force(boids.getvitx(), boids.getposx());
                        forcey += calcul_force(boids.getvity(), boids.getposy());
                    }

                    vitessex += forcex;
                    vitessey += forcey;

                    int new_x = i + vitessex;
                    int new_y = j = vitessey;

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
