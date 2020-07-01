package be.surin.engine;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

public class Event {

    private Date date;
    private Instant instant;
    private String name;
    private String description;

    public Event(Date date, Instant instant, String name, String description) {
        this.date = date;
        this.instant = instant;
        this.name = name;
        this.description = description;

    }

    public static void main(String[] args) {
        Instant instant = Instant.now();
        System.out.println(instant.atZone(ZoneOffset.systemDefault()).getHour());
        System.out.println(instant.atZone(ZoneOffset.systemDefault()).getMinute());
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getInstant() {
        return  instant;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }
}
