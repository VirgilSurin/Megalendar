package be.surin.gui;

import be.surin.engine.Agenda;
import be.surin.engine.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
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

        Label nameLabel = new Label("Enter a name :");
        TextField nameField = new TextField();
        nameField.setMaxSize(250, 250);
        Label dateLabel = new Label("Choose date :");
        DatePicker datePicker = new DatePicker();
        Label descLabel = new Label("description :");
        TextField descField = new TextField();
        descField.setMaxSize(250, 250);

        //create an event with the current parameters and add it to the list
        Button createButton = new Button("Create the event");
        createButton.setOnAction(e -> {
            Event newEvent = new Event(Date.valueOf(datePicker.getValue()), nameField.getText(), descField.getText());
            eventList.add(newEvent);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, separator,
                nameLabel, nameField,
                dateLabel, datePicker,
                descLabel, descField,
                createButton); //Permit closing window.
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        layout.setPrefSize(400,450);
        window.setScene(scene);
        window.showAndWait(); //Prevent doing anything before minimising or closing the window.

    }
}
