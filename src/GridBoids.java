public class GridBoids {
    private Boids[][] grid;
    private int size;

    public GridBoids(int size, int stateNumber) {
        this.size = size;
        grid = new Boids[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Boids(stateNumber, i, j);
            }
        }
    }

    public Boids[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    public void setGrid(Boids[][] grid) {
        this.grid = grid;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setBoids(int i, int j, Boids boid) {
        grid[i][j] = boid;
    }

    public Boids getBoids(int i, int j) {
        return grid[i][j];
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(grid[i][j].getCurrentState());
            }
            s.append("\n");
        }
        return s.toString();
    }

    public void init(int i, int j) {
        //
    }

}
