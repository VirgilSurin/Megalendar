package be.surin.gui;

import be.surin.engine.Agenda;
import com.sun.javafx.scene.control.DatePickerContent;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.time.YearMonth;


public class Menu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane mainPane = new BorderPane();
        Scene mainScene = new Scene(mainPane);
        mainPane.setPrefSize(550,600);

        Agenda agenda = new Agenda("myAgenda");

        HBox buttonBox = new HBox();
        Button addEvent = new Button("New Event");
        addEvent.setOnAction(e -> {
            AddEventBox.Display("New Event", "Create a new event", agenda.getEventList());
        });
        buttonBox.getChildren().add(addEvent);

        FullCalendarView calendarView = new FullCalendarView(YearMonth.now());

        mainPane.setCenter(calendarView.getView());
        mainPane.setTop(buttonBox);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

}
