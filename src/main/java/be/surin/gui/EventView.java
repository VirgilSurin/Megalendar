package be.surin.gui;

import be.surin.engine.Event;
import be.surin.engine.HourMin;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
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
        eventListView.setOnEditStart(new EventHandler<ListView.EditEvent<Event>>() {
            @Override
            public void handle(ListView.EditEvent<Event> editEvent) {



                //implement EditEventBox inside the event handler
                Stage window = new Stage();

                window.initModality(Modality.APPLICATION_MODAL); //Blocs events towards the caller.
                window.setTitle("Edit event");
                window.setMinWidth(250);
                window.setMinHeight(250);

                Event event = eventList.get(editEvent.getIndex());
                int index = editEvent.getIndex();


                VBox layout = new VBox(10);
                Label nameL = new Label("Name : ");
                Label dateL = new Label("Dates : ");
                Label descL = new Label("Description : ");
                TextField nameText = new TextField(event.getName());
                TextArea descText = new TextArea(event.getDescription());

                DatePicker fromDatePick = new DatePicker();
                fromDatePick.setValue(event.getFromDate());

                DatePicker toDatePick = new DatePicker();
                toDatePick.setValue(event.getToDate());

                DatePicker soloDatePick = new DatePicker();
                soloDatePick.setValue(event.getFromDate());

                //woaw that's a BIG copy/paste from AddEventBox...
                ChoiceBox<Integer> fromHour = new ChoiceBox<>();
                fromHour.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
                fromHour.setValue(event.getFromHour().getHour());
                ChoiceBox<Integer> fromMin = new ChoiceBox<>();
                fromMin.getItems().addAll(0,5,10,15,20,25,30,35,40,45,50,55);
                fromMin.setValue(event.getFromHour().getMin());
                ChoiceBox<Integer> toHour = new ChoiceBox<>();
                toHour.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
                toHour.setValue(event.getToHour().getHour());
                ChoiceBox<Integer> toMin = new ChoiceBox<>();
                toMin.getItems().addAll(0,5,10,15,20,25,30,35,40,45,50,55);
                toMin.setValue(event.getToHour().getMin());

                Label from = new Label("from : ");
                Label to = new Label("to : ");

                HBox fromBox = new HBox();
                HBox toBox = new HBox();

                fromBox.getChildren().addAll(from, fromDatePick, fromHour, fromMin);
                toBox.getChildren().addAll(to, toDatePick, toHour, toMin);

                VBox dateBox = new VBox();

                CheckBox allDay = new CheckBox();
                if (event.getToDate() == null) {
                    allDay.setSelected(true);
                    dateBox.getChildren().clear();
                    dateBox.getChildren().addAll(dateL, soloDatePick);
                } else {
                    allDay.setSelected(false);
                    dateBox.getChildren().clear();
                    dateBox.getChildren().addAll(dateL, fromBox, toBox);
                }

                allDay.selectedProperty().addListener((v, oldValue, NewValue) -> {
                    if (allDay.isSelected()) {
                        dateBox.getChildren().clear();
                        dateBox.getChildren().addAll(dateL, soloDatePick);
                    } else {
                        dateBox.getChildren().clear();
                        dateBox.getChildren().addAll(dateL, fromBox, toBox);
                    }
                });




                Button closeButton = new Button("Finish editing");
                closeButton.setOnAction(e -> {
                    //woaw that's a BIG copy/paste from AddEventBox... round 2
                    if ( (allDay.isSelected() && soloDatePick.getValue() == null)
                            || (!allDay.isSelected() && fromDatePick.getValue() == null)
                            || (!allDay.isSelected() && toDatePick.getValue() == null)
                            || nameText.getText() == null
                            || descText.getText() == null) {
                        AlertBox.Display("Wrong parameters", "please enter correct parameters.");
                    } else {
                        LocalDate fromD = fromDatePick.getValue();
                        LocalDate toD = toDatePick.getValue();
                        LocalDate soloD = soloDatePick.getValue();
                        HourMin fromH = new HourMin(fromHour.getValue(), fromMin.getValue());
                        HourMin toH = new HourMin(toHour.getValue(), toMin.getValue());
                        if (allDay.selectedProperty().getValue()) {
                            Event newEvent = new Event(soloD, soloD.plusDays(1),
                                    new HourMin(0, 0), new HourMin(0, 0),
                                    nameText.getText(), descText.getText());
                            editEvent.getSource().getItems().set(index, newEvent);
                        } else {
                            Event newEvent = new Event(fromD, toD, fromH, toH, nameText.getText(), descText.getText());
                            editEvent.getSource().getItems().set(index, newEvent);
                        }
                    }
                    editEvent.consume();
                    window.close();
                });


                layout.getChildren().addAll(nameL, nameText,
                        allDay,
                        dateBox,
                        descL, descText,
                        closeButton);
                layout.setAlignment(Pos.CENTER);

                Scene scene = new Scene(layout);
                window.setScene(scene);
                window.showAndWait(); //Prevent doing anything before minimising or closing the window.



            }
        });


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

}
