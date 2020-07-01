package be.surin.gui;

import be.surin.engine.Agenda;
import be.surin.engine.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class AddEventBox {
    public static void Display(String title, String message, ArrayList<Event> eventList, Menu menu){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); //Blocs events towards the caller.
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(250);

        Label label = new Label();
        label.setText(message);
        Separator separator = new Separator();

        Label nameLabel = new Label("Enter a name :");
        TextField nameField = new TextField();
        nameField.setMaxSize(250, 250);

        Label dateLabel = new Label("Choose date :");
        DatePicker datePicker = new DatePicker();

        Label hourMin = new Label("Choose an hour :");
        ChoiceBox<Integer> hourPick = new ChoiceBox<>();
        hourPick.getItems().addAll(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);

        ChoiceBox<Integer> minPick = new ChoiceBox<>();
        minPick.getItems().addAll(0,5,10,15,20,25,30,35,40,45,50,55);

        Label descLabel = new Label("description :");
        TextField descField = new TextField();
        descField.setMaxSize(250, 250);

        //create an event with the current parameters and add it to the list
        Button createButton = new Button("Create the event");
        createButton.setOnAction(e -> {
            Instant instant = Instant.now();
            ZoneOffset zO = ZoneOffset.ofHoursMinutes(hourPick.getValue(), minPick.getValue());
                    //TODO must be between -18 and 18 ?
            instant.atOffset(zO);
            Event newEvent = new Event(Date.valueOf(datePicker.getValue()), instant, nameField.getText(), descField.getText());
            eventList.add(newEvent);
            menu.refreshEvent();
            window.close();
        });

        HBox hourBox = new HBox();
        hourBox.getChildren().addAll(hourMin, hourPick, minPick);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, separator,
                nameLabel, nameField,
                dateLabel, datePicker,
                hourBox,
                descLabel, descField,
                createButton); //Permit closing window.
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        layout.setPrefSize(400,450);
        window.setScene(scene);
        window.showAndWait(); //Prevent doing anything before minimising or closing the window.

    }
}
