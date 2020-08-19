package be.surin.processing;

import be.surin.engine.Event;
import be.surin.engine.HourMin;
import be.surin.gui.AppLauncher;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class DailyTimelineBox {
    public static void Display(String title, LocalDate date, int minWidth){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); //Blocs events towards the caller.
        window.setTitle(title);
        window.setMinWidth(minWidth);
        window.setMinHeight(400);

        Label label = new Label();
        label.setText("Timeline of the day : " + date);

        // Dimensions of all the elements (simpler to modify)
        int labelWidth = 40;
        int paneWidth = 120;
        int hourHeight = 60; // Must multiply 12 to let periods of 5 minutes have an integer height

        // Create the grip pane for hours
        GridPane hourLabels = new GridPane();
        hourLabels.setPrefSize(labelWidth, hourHeight * 24);
        hourLabels.setGridLinesVisible(true);
        for (int i = 0; i < 24; i++) {
            Text txt = new Text(i + ":00");
            AnchorPane ap = CreateEmptyAnchorPane(labelWidth, hourHeight);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            hourLabels.add(ap, 0, i);
        }

        ArrayList<Event> dailyEvents = AppLauncher.currentProfile.getAgenda().getEventsOfTheDay(date);
        Collections.sort(dailyEvents);
        int numberColumns = MaximumCollision(dailyEvents);

        ArrayDeque<Event>[] eventColumns = new ArrayDeque[numberColumns];
        for (int i = 0; i < numberColumns; i++) {
            eventColumns[i] = new ArrayDeque<Event>();
        }

        // Sort the elements by column so that they avoid colliding
        for (Event e : dailyEvents) {
            int index = 0;
            boolean isInserted = false;
            while (! isInserted) {
                // Check if the event e doesn't collide with the last event in the column
                if (eventColumns[index].size() == 0) {
                    eventColumns[index].addLast(e.copy());
                    isInserted = true;
                }
                else {
                    Event last = eventColumns[index].peekLast();
                    if (last != null) {
                        if (!e.copy().collide(last.copy())) {
                            eventColumns[index].addLast(e.copy());
                            isInserted = true;
                        }
                        else // The element cannot be insert so change column
                            index++;
                    } else {
                        eventColumns[index].addLast(e.copy());
                        isInserted = true;
                    }
                }
            }
        }

        GridPane[] timelines = new GridPane[numberColumns];
        for (int i = 0; i < numberColumns; i++) {
            timelines[i] = new GridPane();
            timelines[i].setPrefSize(paneWidth, hourHeight * 24);
            timelines[i].setGridLinesVisible(true);
        }

        // Note: Some conditions here are not written because it's expected that every event
        // is an event of the day "date"
        for (int i = 0; i < numberColumns; i++) {
            HourMin testTime = new HourMin(0, 0);
            Event testEvent = eventColumns[i].pollFirst();
            int index = 0;
            int size;
            while (testEvent != null) {
                if (testEvent.getFromDate().isBefore(date)) {
                    if (testEvent.getToDate().isEqual(date)) {
                        size = Event.length(date, testTime, date, testEvent.getToHour());
                        testTime = testEvent.getToHour();
                    }
                    else {
                        size = 60 * 24;
                        testTime = null;
                    }
                    AnchorPane ap = CreateEventAnchorPane(paneWidth, size, testEvent, date);
                    timelines[i].add(ap, 0, index);
                    testEvent = eventColumns[i].pollFirst();
                    index++;
                }
                else if (testTime.getHour() < testEvent.getFromHour().getHour()) {
                    size = 60 - testTime.getMin();
                    AnchorPane ap = CreateEmptyAnchorPane(paneWidth, size);
                    timelines[i].add(ap, 0, index);
                    testTime = new HourMin(testTime.getHour()+1, 0);
                    index++;
                }
                else if (testTime.getMin() < testEvent.getFromHour().getMin()) {
                    size = testEvent.getFromHour().getMin() - testTime.getMin();
                    AnchorPane ap = CreateEmptyAnchorPane(paneWidth, size);
                    timelines[i].add(ap, 0, index);
                    testTime = testEvent.getFromHour();
                    index++;
                }
                else {
                    if (testEvent.getToDate().isEqual(date)) {
                        size = Event.length(testEvent.getFromDate(), testEvent.getFromHour(),
                                testEvent.getToDate(), testEvent.getToHour());
                        AnchorPane ap = CreateEventAnchorPane(paneWidth, size, testEvent, date);
                        timelines[i].add(ap, 0, index);
                        testTime = testEvent.getToHour();
                        testEvent = eventColumns[i].pollFirst();
                        index++;
                    }
                    else {
                        size = (24 - testEvent.getFromHour().getHour()) * 60 - testEvent.getFromHour().getMin();
                        AnchorPane ap = CreateEventAnchorPane(paneWidth, size, testEvent, date);
                        timelines[i].add(ap, 0, index);
                        testTime = null;
                        testEvent = null;
                        index++;
                    }
                }
            }
            // testTime is null iff the timeline has been already completed
            if (testTime != null) {
                if (testTime.getMin() != 0) {
                    size = 60 - testTime.getMin();
                    AnchorPane ap = CreateEmptyAnchorPane(paneWidth, size);
                    timelines[i].add(ap, 0, index);
                    index++;
                    if (testTime.getHour() == 23)
                        testTime = null;
                    else
                        testTime = new HourMin(testTime.getHour()+1, 0);
                }
                if (testTime != null) {
                    for (int hour = testTime.getHour(); hour < 24; hour++) {
                        size = 60;
                        AnchorPane ap = CreateEmptyAnchorPane(paneWidth, size);
                        timelines[i].add(ap, 0, index);
                        index++;
                    }
                }
            }
        }

        HBox timelineHBox = new HBox(10);
        timelineHBox.getChildren().add(hourLabels);
        for (GridPane t : timelines)
            timelineHBox.getChildren().add(t);
        timelineHBox.setAlignment(Pos.CENTER);

        // To be able to scroll through the timeline
        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(600, 600);
        s1.setContent(timelineHBox);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, s1);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait(); //Prevent doing anything before minimising or closing the window.
    }

    private static int MaximumCollision(ArrayList<Event> events) {
        int maximum = 0;
        if (events.size() < 2)
            return events.size();
        else {
            for (int i = 0; i < events.size()-1; i++) {
                // currentCollision begins at 1 because we need at least 1 column
                int currentCollision = 1;
                for (int j = i+1; j < events.size(); j++) {
                    if (events.get(i).collide(events.get(j)))
                        currentCollision++;
                }
                if (currentCollision > maximum)
                    maximum = currentCollision;
            }
            return maximum;
        }
    }

    private static AnchorPane CreateEmptyAnchorPane(int width, int height) {
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(width, height);
        return ap;
    }

    // TODO Make the events great again (I mean, not ugly like right now) !
    private static AnchorPane CreateEventAnchorPane(int width, int height, Event event, LocalDate date) {
        AnchorPane ap = new AnchorPane();
        ap.setPrefSize(width, height);

        Label label = new Label(event.getName());
        label.setMinWidth(width);
        label.setMaxWidth(width);
        label.setMinHeight(height);
        label.setMaxHeight(height);
        label.setAlignment(Pos.CENTER);

        // TODO Change the color according to the tag
        ap.setStyle("-fx-background-color: #d3e0e0");
        ap.getChildren().add(label);
        return ap;
    }
}
