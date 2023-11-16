import gui.GUISimulator;
import gui.Rectangle;

public abstract class CellGameEngine extends GameEngine {
    protected Grid<Cell> grid;
    protected int cellWidth;
    protected int gridSize;
    protected int cellNumber;

    protected int stateNumber;

    private EventManager eventManager = new EventManager();

    public CellGameEngine(GUISimulator gui, int gridSize, int cellNumber, int stateNumber) {
        super(gui, cellNumber);
        this.gui = gui;
        this.gridSize = gridSize;
        this.cellNumber = cellNumber;
        this.stateNumber = stateNumber;
        grid = new Grid<>(gridSize, this.stateNumber);
        //cellwidth is the minimum of the width and height of the window divided by the size of the grid
        this.cellWidth = Math.min(gui.getPanelWidth() - 60, gui.getPanelHeight() - 60) / gridSize;
        gui.setSimulable(this);
        eventManager.addEvent(new FirstGenerationEvent((int) (eventManager.getCurrentDate() + 1)));

    }

    @Override
    public void restart(){
        grid = new Grid<>(this.gridSize, this.stateNumber);
        eventManager.restart();
        eventManager.addEvent(new FirstGenerationEvent((int) (eventManager.getCurrentDate() + 1)));

        draw();
    }

    @Override
    public void next(){
        //nextGeneration();
        eventManager.next();
        draw();
    }

    protected abstract void nextGeneration();

    protected void draw(){
        gui.reset();	// clear the window
        for (int i = 0; i < grid.getSize(); i++) {
            for (int j = 0; j < grid.getSize(); j++) {
                gui.addGraphicalElement(new Rectangle(i * this.cellWidth + 60, j * this.cellWidth+60, grid.getCase(i,j).getColor(), grid.getCase(i,j).getColor(), this.cellWidth));
            }
        }
    }

    protected abstract void calculateNeighbours();

     class NextGenerationEvent extends Event {

        public NextGenerationEvent(int date) {
            super(date);
        }

        @Override
        public void execute() {
            nextGeneration();
            draw();
            eventManager.addEvent(new NextGenerationEvent((int) (eventManager.getCurrentDate() + 1)));
        }
    }

    class FirstGenerationEvent extends Event {
        public FirstGenerationEvent(long date) {
            super(date);
        }

        public void execute() {
            firstGeneration(cellNumber);
            eventManager.addEvent(new NextGenerationEvent((int) (eventManager.getCurrentDate()+1)));
        }
    }



}
