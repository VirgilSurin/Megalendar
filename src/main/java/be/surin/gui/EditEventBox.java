package be.surin.gui;

import be.surin.engine.Event;
import be.surin.engine.HourMin;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class EditEventBox {
    public static void Display(int index, EventView eventView){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); //Blocs events towards the caller.
        window.setTitle("Edit event");
        window.setMinWidth(250);
        window.setMinHeight(250);

        ArrayList<Event> eventList = eventView.getEventList();
        Event event = eventView.getEventList().get(index);

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
                    eventList.set( index, newEvent);
                    eventView.refresh(newEvent);
                } else {
                    Event newEvent = new Event(fromD, toD, fromH, toH, nameText.getText(), descText.getText());
                    eventList.set( index, newEvent);
                    eventView.refresh(newEvent);
                }
            }

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
}
