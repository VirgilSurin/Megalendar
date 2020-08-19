package be.surin.gui;

import be.surin.engine.Profile;
import be.surin.processing.AddEventBox;
import be.surin.processing.CalendarHandler;
import be.surin.processing.EventView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.YearMonth;

public class CalendarScene extends Scene {

    private static EventView eventView;

    public CalendarScene(Profile profile) {
        super(createCalendarParent(profile), 600, 600);
    }

    private static Parent createCalendarParent(Profile profile) {
        BorderPane mainPane = new BorderPane();
        mainPane.setPrefSize(550,600);

        HBox buttonBox = new HBox();
        Button addEvent = new Button("New Event");
        addEvent.setOnAction(e -> {
            AddEventBox.Display("New Event", "Create a new event",
                    AppLauncher.currentProfile.getAgenda().getEventList());
        });
        Button changeProfile = new Button("Change profile");
        changeProfile.setOnAction(e -> {
            AppLauncher.stage.setScene(new ProfileScene());
        });
        buttonBox.getChildren().addAll(addEvent, changeProfile);

        VBox calendar = new CalendarHandler(YearMonth.now()).getView();

        eventView = new EventView(AppLauncher.currentProfile.getAgenda().getEventList());

        mainPane.setTop(buttonBox);
        mainPane.setCenter(calendar);
        mainPane.setBottom(eventView.view());

        return mainPane;
    }

    public static EventView getEventView() {
        return eventView;
    }
}
