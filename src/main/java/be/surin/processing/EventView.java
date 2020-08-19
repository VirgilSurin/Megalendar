package be.surin.processing;

import be.surin.engine.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

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

        eventListTab.setContent(eventListView);
        eventListTab.setText("Event list");
        eventListView.setOnEditStart(new EventHandler<ListView.EditEvent<Event>>() {
            @Override
            public void handle(ListView.EditEvent<Event> editEvent) {

                //new code - it's a try
                System.out.println(eventList.get(editEvent.getIndex()));
                startEdit2(editEvent.getIndex());
            }
        });


        //view pane set-up
        viewPane.getTabs().add(nextEventTab); //tab 0
        viewPane.getTabs().add(eventListTab); // tab 1
        viewPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    public void refresh(Event newEvent) {
        eventList.add(newEvent);
        Event e = getNextEvent();
        nextEventView.getChildren().clear();
        nextEventView.getChildren().addAll(new Label(e.getName()), new Label(e.getDateStr()), new Label(e.getDescription()));
        viewPane.getTabs().get(0).setContent(nextEventView);
        eventListObs.clear();
        //it must be terrible... but had to create a new observable list
        eventListObs = FXCollections.observableList(eventList);
        eventListView.setItems(eventListObs);
        eventListView.setEditable(true);
        
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

    public void editList(int index) {
        EditEventBox.Display(index, this);
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public TabPane view() {
        return viewPane;
    }

    @Override
    public void startEdit() {
        System.out.println("ye... Cell edit");
        System.out.println("event :" + getItem());
        super.startEdit();
    }

    public void startEdit2(int index) {
        System.out.println("ouch I'm the event event too much event LOL " + eventList.get(index));
        EditEventBox.Display(index, this); //display not working :)
        cancelEdit();
    }
}
