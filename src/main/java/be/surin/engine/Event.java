package be.surin.engine;

import java.time.LocalDate;

public class Event implements Comparable<Event>{

    private LocalDate fromDate;
    private LocalDate toDate;
    private HourMin fromHour;
    private HourMin toHour;
    private String name;
    private String description;


    public Event(LocalDate fromDate, LocalDate toDate, HourMin fromHour, HourMin toHour, String name, String description) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.fromHour = fromHour;
        this.toHour = toHour;
        this.name = name;
        this.description = description;

    }

    //getters and setters, lust be fully editable

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public HourMin getFromHour() {
        return fromHour;
    }

    public void setFromHour(HourMin fromHour) {
        this.fromHour = fromHour;
    }

    public HourMin getToHour() {
        return toHour;
    }

    public void setToHour(HourMin toHour) {
        this.toHour = toHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Check if there is a collision between two events
    public boolean collide(Event o) {
        if (this.getToDate().isBefore(o.getFromDate()))
            return false;
        else if (this.getToDate().isEqual(o.getFromDate()) && (this.getToHour().compareTo(o.getFromHour()) <= 0))
            return false;
        else if (o.getToDate().isBefore(this.getFromDate()))
            return false;
        else if (o.getToDate().isEqual(this.getFromDate()) && (o.getToHour().compareTo(this.getFromHour()) <= 0))
            return false;
        else
            return true;
    }

    // Compare the fromDate and the frontHour of the two events
    @Override
    public int compareTo(Event o) {
        if (this.getFromDate().isBefore(o.getFromDate()))
            return -1;
        else if (this.getFromDate().isAfter(o.getFromDate()))
            return 1;
        else
            return this.getFromHour().compareTo(o.getFromHour());
    }

    // Return the length in minutes of the event
    public static int length(LocalDate fromDate, HourMin fromHour, LocalDate toDate, HourMin toHour) {
        int length = 0;
        while (! fromDate.equals(toDate)) {
            fromDate.plusDays(1);
            length += 60 * 24;
        }
        length += (toHour.getHour() - fromHour.getHour()) * 60;
        length += toHour.getMin() - fromHour.getMin();
        return length;
    }

    public Event copy() {
        return new Event(fromDate, toDate, fromHour, toHour, name, description);
    }

    @Override
    public String toString() {
        return name + " " +
                fromDate.toString() + " " +
                fromHour.getHour() + ":" +
                fromHour.getMin() + " " +
                toDate.toString() + " " +
                toHour.getHour() + ":" +
                toHour.getMin();
    }
}