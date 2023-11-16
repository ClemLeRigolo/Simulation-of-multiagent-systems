import java.util.HashSet; // import the HashMap class

/**
 * Un Manager ...
 * Sert à créer une liste de différents événements et de les lancer quand nous arrivons à leur date d'éxécution.
 */
public class EventManager {
    private long currentDate;
    //Peut-être plutôt utiliser une queue de priorité pour avoir un accès en O(1) à l'événement le plus proche
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
