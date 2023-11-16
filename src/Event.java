/**
 * Un événement ?
 * Classe abstraite définissant les méthodes que tout événement devrait avoir: une date et quelque chose à effectuer.
 */
public abstract class Event implements Comparable<Event>{
    private long date;

    public Event(long date) {
        this.date = date;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    abstract void execute();

    public int compareTo(Event event) {
        if (event.getDate() == this.getDate()) {
            return 0;
        }
        if (event.getDate() > this.getDate()) {
            return -1;
        }

        return 1;
    }
}
