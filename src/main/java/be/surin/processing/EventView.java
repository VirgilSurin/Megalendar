package be.surin.processing;

import be.surin.engine.Event;
import be.surin.engine.HourMin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class EventView extends Cell<Event> {

    private TabPane viewPane;
    private VBox nextEventView;
    private ListView<Event> eventListView;
    private ArrayList<Event> eventList;
    private ObservableList<Event> eventListObs;

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
        eventListObs = FXCollections.emptyObservableList();
        eventListObs.addAll(eventList);
        eventListView = new ListView<Event>(eventListObs);
        eventListView.setEditable(false);

        eventListTab.setContent(eventListView);
        eventListTab.setText("Event list");


        //view pane set-up
        viewPane.getTabs().add(nextEventTab); //tab 0
        viewPane.getTabs().add(eventListTab); // tab 1
        viewPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public void refresh(Event newEvent) {
        eventList.add(newEvent);
        eventList.sort(Comparator.naturalOrder());
        Event e = getNextEvent();
        nextEventView.getChildren().clear();
        nextEventView.getChildren().addAll(new Label(e.getName()), new Label(e.getDateStr()), new Label(e.getDescription()));
        viewPane.getTabs().get(0).setContent(nextEventView);

        //it must be terrible... but had to create a new observable list
        ObservableList<Event> newObs = FXCollections.observableList(eventList);
        eventListView.setItems(newObs);

        
    }

    public Event getNextEvent() {
        //TODO add function that add events in chronological order into the eventList
        if (eventList.size() > 0) {
            return eventList.get(eventList.size()-1);
        } else {
            return null;
        }
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public TabPane view() {
        return viewPane;
    }

}
