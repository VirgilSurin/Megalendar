package be.surin.engine;

import java.time.LocalDate;

public class Event {

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
}
