package be.surin.gui;

import be.surin.engine.Event;
import be.surin.engine.HourMin;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ArrayDeque;
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

        // Create the grip pane for hours
        GridPane hourLabels = new GridPane();
        hourLabels.setPrefSize(40, 60*24);
        hourLabels.setGridLinesVisible(true);
        for (int i = 0; i < 24; i++) {
            Text txt = new Text(i + ":00");
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(40, 60);
            ap.setTopAnchor(txt, 5.0);
            ap.setLeftAnchor(txt, 5.0);
            ap.getChildren().add(txt);
            hourLabels.add(ap, 0, i);
        }

        ArrayList<Event> dailyEvents = CalendarMenu.getCurrentProfile().getAgenda().getEventsOfTheDay(date);
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
                    System.out.println(e.toString());
                    System.out.println(last.toString());
                    if (last != null) {
                        if (!e.copy().collide(last.copy())) { //TODO Put back
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
            timelines[i].setPrefSize(120, 60*24);
            timelines[i].setGridLinesVisible(true);
        }

        for (int i = 0; i < numberColumns; i++) {
            HourMin testTime = new HourMin(0, 0);
            Event testEvent = eventColumns[i].pollFirst();
            int index = 0;
            while (testEvent != null) {
                if (testEvent.getFromDate().isBefore(date)) {
                    AnchorPane ap = new AnchorPane();
                    ap.setPrefSize(120,Event.length(testEvent.getFromDate(), testEvent.getFromHour(),
                            testEvent.getToDate(), testEvent.getToHour()));
                    timelines[i].add(ap,0,index);
                    testTime = testEvent.getToHour();
                    testEvent = eventColumns[i].pollFirst();
                    index++;
                }
                else if (testTime.getHour() == testEvent.getFromHour().getHour()) {
                    if (testTime.getMin() != testEvent.getFromHour().getMin()) {
                        AnchorPane ap = new AnchorPane();
                        ap.setPrefSize(120,Event.length(date, testTime,
                                date, testEvent.getFromHour()));
                        timelines[i].add(ap,0,index);
                        index++;
                    }
                    AnchorPane ap = new AnchorPane();
                    ap.setPrefSize(120,Event.length(date, testEvent.getFromHour(),
                            date, testEvent.getToHour()));
                    timelines[i].add(ap,0,index);
                    testTime = testEvent.getToHour();
                    testEvent = eventColumns[i].pollFirst();
                    index++;
                }
                else {
                    AnchorPane ap = new AnchorPane();
                    ap.setPrefSize(120,60);
                    timelines[i].add(ap,0,index);
                    if (testTime.getHour() != 23) //DEBUG Because of infinite loop go over 24 TODO Remove
                        testTime = new HourMin(testTime.getHour()+1, testTime.getMin());
                    //testTime = testEvent.getToHour();
                    index++;
                }
            }
            //TODO The last tiny bits (after the last events)
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
}
