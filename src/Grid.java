public class Grid {
    private Cell[][] grid;
    private int size;

    public Grid(int size, int stateNumber) {
        this.size = size;
        grid = new Cell[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++){
                grid[i][j] = new Cell(stateNumber, i, j);
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setCell(int i, int j, Cell cell) {
        grid[i][j] = cell;
    }

    public Cell getCell(int i, int j) {
        return grid[i][j];
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++){
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
