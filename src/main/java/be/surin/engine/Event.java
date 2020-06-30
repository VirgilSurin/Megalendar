package be.surin.engine;

import java.util.Date;

public class Event {

    private Date date;
    private String name;
    private String description;

    public Event(Date date, String name, String description) {
        this.date = date;
        this.name = name;
        this.description = description;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
