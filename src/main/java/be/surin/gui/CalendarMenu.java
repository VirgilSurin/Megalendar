package be.surin.gui;

import be.surin.engine.Profile;

import java.time.YearMonth;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarMenu {

    private static Profile currentProfile;
    private static BorderPane mainPane;
    private static HBox buttonBox;
    private static EventView eventView;

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
        //THAT'S NEEEEEEEW
        eventView = new EventView(currentProfile.getAgenda().getEventList());
        mainPane.setBottom(eventView.view());

        return new Scene(mainPane);
    }

    public static EventView getEventView() {
        return eventView;
    }

    public static Profile getCurrentProfile() {
        return currentProfile;
    }
}
