package Group_Assignment;

public class Event {
    private String eventName;
    private String eventDate;
    private int capacity;

    public Event(String eventName, String eventDate, int capacity) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.capacity = capacity;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Event: " + eventName + " | Date: " + eventDate + " | Capacity: " + capacity;
    }
}