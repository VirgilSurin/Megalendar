package be.surin.gui;

import be.surin.engine.Event;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class EventView {

    private TabPane viewPane;
    private VBox nextEventView;
    private ListView<Event> eventListView;
    private ArrayList<Event> eventList;

    public EventView(ArrayList<Event> eventList) {
        this.eventList = eventList;
        this.viewPane = new TabPane();
        viewPane.setSide(Side.LEFT);

        //next event view
        Tab nextEventTab = new Tab();
        Event e = getNextEvent();
        nextEventView = new VBox();
        nextEventTab.setContent(nextEventView);
        nextEventTab.setText("Next event");

        //event list view
        Tab eventListTab = new Tab();
        eventListView = new ListView<Event>();
        for (Event event : eventList) {
            eventListView.getItems().add(event);
        }
        eventListTab.setContent(eventListView);
        eventListTab.setText("Event list");

        //view pane set-up
        viewPane.getTabs().add(nextEventTab); //tab 0
        viewPane.getTabs().add(eventListTab); // tab 1
        viewPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public void refresh(ArrayList<Event> eventList) {
        this.eventList = eventList;

        Event e = getNextEvent();
        nextEventView.getChildren().clear();
        nextEventView.getChildren().addAll(new Label(e.getName()), new Label(e.getDateStr()), new Label(e.getDescription()));
        viewPane.getTabs().get(0).setContent(nextEventView);

        eventListView.getItems().clear();
        for (Event event : eventList) {
            eventListView.getItems().add(event);
        }
    }

    public Event getNextEvent() {
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

    public TabPane view() {
        return viewPane;
    }

}
