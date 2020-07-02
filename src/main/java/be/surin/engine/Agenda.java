package be.surin.engine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Agenda {

    private String name;
    private ArrayList<Event> eventList;
    private GregorianCalendar calendar;


    public Agenda(String name) {
        this.calendar = new GregorianCalendar();
        this.name = name;
        this.eventList = new ArrayList<Event>();
    }

    public String getName() { return name; }

    public void addEvent(Event event) {
        eventList.add(event);
    }

    /**
     *
     * @return the next chronological event
     */
    public Event getNextEvent() {
        //TODO add function that add events in chronological order into the eventList
        //add not yet implemented - currently using a linear search to find the next event
        Event currentNext = eventList.get(0); //first item
        int i = 1; //0 is already done
        while (i < eventList.size()) {
            Event temp = eventList.get(i);
            if (temp.getFromDate().isBefore(currentNext.getFromDate())) {
                currentNext = temp;
            } else if (temp.getFromDate().isEqual(currentNext.getFromDate())
                    && temp.getFromHour().compareTo(currentNext.getFromHour()) < 0 ) {
                currentNext = temp;
            }
            i++;
        }
        return currentNext;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }
}
