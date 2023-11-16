import java.lang.reflect.Array;

/**
 * Découcage de la fenêtre graphique en différentes cases pouvant toutes acceuillir des identité propres (comme des cellules ou des Boids)
 * @param <T>: type des éléments qui occuperont les différentes cases de la fênetre graphique.
 */
public class Grid<T> {
    private T[][] grid;
    private int size;

    private int resolution = 0;
    private int cols, rows;
    public Grid(int size) {
        this.size = size;
        grid = (T[][]) new Object[size][size];
    }

    public Grid(int resolution, int width, int height) {
        this.resolution = resolution;
        this.cols = width/this.resolution;
        this.rows = height/this.resolution;
        grid = (T[][]) new Object[this.cols][this.rows];
    }

    @SuppressWarnings("unchecked")
    public Grid(int size, int stateNumber) {
        this.size = size;
        grid = (T[][]) Array.newInstance(Cell.class, size, size);
        if (grid.getClass().getComponentType().getComponentType().equals(Cell.class)) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    grid[i][j] = (T) new Cell(stateNumber, i, j);
                }
            }
        }
    }

    public T[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    public void setGrid(T[][] grid) {
        this.grid = grid;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCase(int i, int j, T gridCase) {
        grid[i][j] = gridCase;
    }

    public T getCase(int i, int j) {
        return grid[i][j];
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public int getResolution() {
        return resolution;
    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(grid[i][j]);
            }
            s.append("\n");
        }
        return s.toString();
    }

}
