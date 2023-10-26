import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

public abstract class CellGameEngine implements Simulable {
    protected GUISimulator gui;
    protected Grid grid;
    protected int cellWidth;
    protected int gridSize;
    protected int cellNumber;

    protected int stateNumber;

    public CellGameEngine(GUISimulator gui, int gridSize, int cellNumber, int stateNumber) {
        this.gui = gui;
        this.gridSize = gridSize;
        this.cellNumber = cellNumber;
        this.stateNumber = stateNumber;
        grid = new Grid(gridSize, this.stateNumber);
        //cellwidth is the minimum of the width and height of the window divided by the size of the grid
        this.cellWidth = Math.min(gui.getPanelWidth() - 60, gui.getPanelHeight() - 60) / gridSize;
        gui.setSimulable(this);
        init();
    }

    @Override
    public void restart(){
        grid = new Grid(this.gridSize, this.stateNumber);
        firstGeneration(this.cellNumber);
        draw();
    }

    @Override
    public void next(){
        nextGeneration();
        draw();
    }

    //Used to init things
    protected void init(){
        firstGeneration(cellNumber);
        draw();
    }

    protected abstract void firstGeneration(int cellNumber);

    protected abstract void nextGeneration();

    protected void draw(){
        gui.reset();	// clear the window
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                gui.addGraphicalElement(new Rectangle(i * this.cellWidth + 60, j * this.cellWidth+60, grid.getCell(i,j).getColor(), grid.getCell(i,j).getColor(), this.cellWidth));
            }
        }
    }

    protected abstract void calculateNeighbours();

}
