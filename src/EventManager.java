import java.util.PriorityQueue; // import the PriorityQueue class

/**
 * Un Manager ...
 * Sert à créer une liste de différents événements et de les lancer quand nous arrivons à leur date d'éxécution.
 */
public class EventManager {
    private long currentDate;
    PriorityQueue<Event> listEvent = new PriorityQueue<>();

    public EventManager() {
        this.currentDate = 0;
    }

    public void addEvent(Event event) {
        this.listEvent.add(event);
    }

    public void next() {
        this.currentDate++;
        PriorityQueue<Event> copyEvent = new PriorityQueue<>(listEvent);

        for (Event event : copyEvent) {
            if (event.getDate() == this.currentDate) {
                event.execute();
                listEvent.remove(event);
            }
        }
    }

    public void restart() {
        this.setCurrentDate(0);
        this.listEvent.clear();
    }

    public void setCurrentDate(long date) {
        this.currentDate = date;
    }

    public long getCurrentDate() {
        return this.currentDate;
    }

    public boolean isFinished() {
        for (Event event : listEvent) {
            return false;
        }
        return true;
    }
}
