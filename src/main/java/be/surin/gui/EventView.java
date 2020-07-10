package be.surin.gui;

import be.surin.engine.Agenda;
import be.surin.engine.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

public class EventView {

    private final GridPane nextEventPane;
    private final GridPane eventListPane;
    private final GridPane viewPane;
    private ArrayList<Event> eventList;

    public EventView(ArrayList<Event> eventList) {
        this.nextEventPane = new GridPane();
        this.eventListPane = new GridPane();
        this.viewPane = new GridPane();
        refresh(eventList);

        //view set-up
        Button nextEventButton = new Button("next event");
        nextEventButton.setOnAction( e -> {
            viewPane.getRowConstraints().remove(1);
            viewPane.addRow(1, nextEventPane);
        });

        Button eventListButton = new Button("event list");
        eventListButton.setOnAction( e -> {
            viewPane.getRowConstraints().remove(1);
            viewPane.addRow(1, eventListPane);
        });

        nextEventButton.setRotate(-90);
        eventListButton.setRotate(-90);

        viewPane.addRow(0, nextEventButton, eventListButton);
        viewPane.addRow(1, nextEventPane);
    }

    public void refresh(ArrayList<Event> eventList) {
        this.eventList = eventList;

        //nextEvent set-up
        Event nextEvent = getNextEvent(eventList);
        if (nextEvent == null) {
            nextEventPane.addColumn(0, new Label("You have no event to come"));
        } else {
            nextEventPane.addColumn(0, new Label(nextEvent.getName()));
            nextEventPane.addColumn(1, new Label(nextEvent.toString()));
            nextEventPane.addColumn(1, new Label(nextEvent.getDescription()));
        }
        nextEventPane.setVgap(5);

        //eventList set-up
        for (int i = 0; i < eventList.size(); i++) {
            Event e = eventList.get(i);
            Label eventLabel = new Label(e.getName() + " | " + e.toString());
            eventListPane.addColumn(i, eventLabel);
        }
        eventListPane.setVgap(5);
    }

    public static Event getNextEvent(ArrayList<Event> eventList) {
        //TODO add function that add events in chronological order into the eventList
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

    public GridPane view() {
        return viewPane;
    }

}
