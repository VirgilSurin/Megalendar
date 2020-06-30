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

    private Agenda agenda;
    private BorderPane mainPane;
    private HBox buttonBox;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Menu menu = new Menu();
        Scene mainScene = new Scene(mainPane);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public Menu() {

        this.agenda = new Agenda("myAgenda");
        this.mainPane = new BorderPane();
        mainPane.setPrefSize(550,600);
        this.buttonBox = new HBox();

        Button addEvent = new Button("New Event");
        addEvent.setOnAction(e -> {
            AddEventBox.Display("New Event", "Create a new event", agenda.getEventList(), this);
        });
        buttonBox.getChildren().add(addEvent);

        FullCalendarView calendarView = new FullCalendarView(YearMonth.now());

        mainPane.setCenter(calendarView.getView());
        mainPane.setTop(buttonBox);
    }

    protected void refreshEvent() {
        if (agenda.getEventList().size() > 0) {
            EventDisplay eventDisplay = new EventDisplay(agenda.getNextEvent());
            mainPane.setBottom(eventDisplay.display());
        }
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public HBox getButtonBox() {
        return buttonBox;
    }
}
