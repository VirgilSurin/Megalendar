package be.surin.engine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.time.LocalDate;

public class Agenda {

    private String name;
    private ArrayList<Event> eventList;


    public Agenda(String name) {
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
    @Deprecated
    public Event getNextEvent() {
        //TODO moved to EventView
        //add not yet implemented - currently using a linear search to find the next event
        if (eventList.size() > 0) {
            Event currentNext = eventList.get(0); //first item
            int i = 1; //0 is already done
            while (i < eventList.size()) {
                Event temp = eventList.get(i);
                if (temp.getFromDate().isBefore(currentNext.getFromDate())) {
                    currentNext = temp;
                } else if (temp.getFromDate().isEqual(currentNext.getFromDate())
                        && temp.getFromHour().compareTo(currentNext.getFromHour()) < 0) {
                    currentNext = temp;
                }
                i++;
            }
            return currentNext;
        } else {
            return null;
        }
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public ArrayList<Event> getEventsOfTheDay(LocalDate date) {
        ArrayList<Event> dailyEvents = new ArrayList<>();
        for (Event e : eventList) {
            if (e.getFromDate().isEqual(date))
                dailyEvents.add(e.copy());
                //The second statement avoid events which end on the day at 0:00
            else if (e.getToDate().isEqual(date) && e.getToHour().compareTo(new HourMin(0, 0)) > 0)
                dailyEvents.add(e.copy());
            else if (e.getFromDate().isBefore(date) && e.getToDate().isAfter(date))
                dailyEvents.add(e.copy());
        }
        return dailyEvents;
    }
}
