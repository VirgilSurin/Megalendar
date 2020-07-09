package be.surin.gui;

import be.surin.engine.Profile;

import java.time.YearMonth;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarMenu {

    private static Profile currentProfile;
    private static BorderPane mainPane;
    private static HBox buttonBox;

    public static void setCurrentProfile(Profile currentProfile) {
        CalendarMenu.currentProfile = currentProfile;
    }

    public static Scene DisplayScene(Stage primaryStage) {
        mainPane = new BorderPane();
        mainPane.setPrefSize(550,600);
        buttonBox = new HBox();

        Button addEvent = new Button("New Event");
        addEvent.setOnAction(e -> {
            AddEventBox.Display("New Event", "Create a new event",
                    currentProfile.getAgenda().getEventList());
        });
        Button changeProfile = new Button("Change profile");
        changeProfile.setOnAction(e -> {
            primaryStage.setScene(Menu.getPrimaryScene());
        });
        buttonBox.getChildren().addAll(addEvent, changeProfile);

        FullCalendarView calendarView = new FullCalendarView(YearMonth.now());

        mainPane.setCenter(calendarView.getView());
        mainPane.setTop(buttonBox);
        refreshEvent();
        return new Scene(mainPane);
    }

    protected static void refreshEvent() {
        if (currentProfile.getAgenda().getEventList().size() > 0) {
            EventDisplay eventDisplay = new EventDisplay(currentProfile.getAgenda().getNextEvent());
            mainPane.setBottom(eventDisplay.display());
        }
    }

    public static Profile getCurrentProfile() {
        return currentProfile;
    }
}
