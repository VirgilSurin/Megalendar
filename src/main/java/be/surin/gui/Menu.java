package be.surin.gui;

import be.surin.engine.Agenda;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane mainPane = new BorderPane();
        Scene mainScene = new Scene(mainPane);

        Agenda agenda = new Agenda("myAgenda");

        HBox buttonBox = new HBox();
        Button addEvent = new Button("New Event");
        addEvent.setOnAction(e -> {
            AddEventBox.Display("New Event", "Create a new event", agenda.getEventList());
        });
        buttonBox.getChildren().add(addEvent);

        mainPane.setTop(buttonBox);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
