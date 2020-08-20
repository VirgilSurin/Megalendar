package be.surin.processing;

import be.surin.engine.Event;
import be.surin.engine.HourMin;
import be.surin.gui.CalendarScene;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddEventBox {
    public static void Display(String title, String message, ArrayList<Event> eventList){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); //Blocs events towards the caller.
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(250);

        Label label = new Label();
        label.setText(message);
        Separator separator = new Separator();

        //event name ---------------------------------------------------------------------------------------------------
        HBox nameBox = new HBox();
        Label nameLabel = new Label("Event name :");
        TextArea nameText = new TextArea();
        nameText.setMaxSize(75,25);
        nameText.setMinSize(75,25);
        nameBox.getChildren().addAll(nameLabel, nameText);
        nameBox.setAlignment(Pos.CENTER);

        //time zone ----------------------------------------------------------------------------------------------------
        HBox fromBox = new HBox();
        HBox toBox = new HBox();
        //event date (from-to)---------------------------------------------------------------------------------------
        HBox dateBox = new HBox();
        Label dateLabel = new Label("Pick date(s) :");
        CheckBox allDay = new CheckBox();
        Label allDayText = new Label("All day event :");
        dateBox.getChildren().addAll(allDayText, allDay, dateLabel);

        //from-to option
        Label fromLabel = new Label("from ");
        Label toLabel = new Label("to ");
        //date
        DatePicker fromDatePick = new DatePicker();
        fromDatePick.setMinSize(115, 22);
        fromDatePick.setMaxSize(115,22);
        DatePicker toDatePick = new DatePicker();
        toDatePick.setMinSize(115,22);
        toDatePick.setMaxSize(115,22);

        //event hour (from-to)---------------------------------------------------------------------------------------
        //TODO get rid of the duplicated code

        ChoiceBox<Integer> fromHour = new ChoiceBox<>();
        fromHour.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
        fromHour.setValue(0);
        ChoiceBox<Integer> fromMin = new ChoiceBox<>();
        fromMin.getItems().addAll(0,5,10,15,20,25,30,35,40,45,50,55);
        fromMin.setValue(0);
        ChoiceBox<Integer> toHour = new ChoiceBox<>();
        toHour.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
        toHour.setValue(0);
        ChoiceBox<Integer> toMin = new ChoiceBox<>();
        toMin.getItems().addAll(0,5,10,15,20,25,30,35,40,45,50,55);
        toMin.setValue(0);

        fromHour.setMinSize(50,22);
        fromHour.setMaxSize(50,22);
        toHour.setMinSize(50,22);
        toHour.setMaxSize(50,22);

        fromMin.setMaxSize(50,22);
        fromMin.setMinSize(50,22);
        toMin.setMaxSize(50,22);
        toMin.setMinSize(50,22);

        DatePicker soloDatePick = new DatePicker();
        soloDatePick.setMaxSize(115,22);

        VBox stageDateBox = new VBox();

        VBox dateBoxPick = new VBox();

        //TODO case where toTime is before fromTime (dumb user...)
        //set-up of date/hour selection according to allDay status (default is unchecked)
        allDay.setSelected(false);
        dateBoxPick.getChildren().clear();
        fromBox.getChildren().clear();
        toBox.getChildren().clear();
        fromBox.getChildren().addAll(fromLabel, fromDatePick, fromHour, fromMin);
        toBox.getChildren().addAll(toLabel, toDatePick, toHour, toMin);
        dateBoxPick.getChildren().addAll(fromBox, toBox);

        allDay.selectedProperty().addListener((v, oldValue, newValue)  -> {
            if (!allDay.selectedProperty().getValue()) {
                dateBoxPick.getChildren().clear();
                fromBox.getChildren().clear();
                toBox.getChildren().clear();
                fromBox.getChildren().addAll(fromLabel, fromDatePick, fromHour, fromMin);
                toBox.getChildren().addAll(toLabel, toDatePick, toHour, toMin);
                dateBoxPick.getChildren().addAll(fromBox, toBox);
            } else {
                dateBoxPick.getChildren().clear();
                dateBoxPick.getChildren().add(soloDatePick);
            }
        } );
        fromBox.setAlignment(Pos.CENTER);
        toBox.setAlignment(Pos.CENTER);
        dateBoxPick.setAlignment(Pos.CENTER);
        dateBox.setAlignment(Pos.CENTER);
        dateBoxPick.setSpacing(10);
        stageDateBox.getChildren().addAll(dateBox, dateBoxPick);
        stageDateBox.setAlignment(Pos.CENTER);

        //Description --------------------------------------------------------------------------------------------------
        VBox descBox = new VBox();
        Label descLabel = new Label("Description");
        TextArea descText = new TextArea();
        descText.setMaxSize(200,150);
        descBox.getChildren().addAll(descLabel, descText);
        descBox.setAlignment(Pos.CENTER);


        //create an event with the current parameters and add it to the list
        Button createButton = new Button("Create the event");
        createButton.setOnAction(e -> {
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
                    CalendarScene.getEventView().refresh(newEvent);
                } else {
                    Event newEvent = new Event(fromD, toD, fromH, toH, nameText.getText(), descText.getText());
                    CalendarScene.getEventView().refresh(newEvent);
                }

                window.close();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, separator,
                nameBox,
                stageDateBox,
                descBox,
                createButton); //Permit closing window.
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        layout.setPrefSize(400,450);
        layout.setSpacing(10);
        window.setScene(scene);
        window.showAndWait(); //Prevent doing anything before minimising or closing the window.

    }
}
