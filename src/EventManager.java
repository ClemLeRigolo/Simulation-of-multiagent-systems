import java.util.HashSet; // import the HashMap class

public class EventManager {
    private long currentDate;
    HashSet<Event> listEvent = new HashSet<>();

    public EventManager() {
        this.currentDate = 0;
    }

    public void addEvent(Event event) {
        this.listEvent.add(event);
    }

    public void next() {
        this.currentDate++;
        HashSet<Event> copyEvent = new HashSet<>(listEvent);

        for (Event event : copyEvent) {
            if (event.getDate() == this.currentDate) {
                event.execute();
                listEvent.remove(event);
            }
        }
    }

    public void restart() {
        this.currentDate = 0;
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
