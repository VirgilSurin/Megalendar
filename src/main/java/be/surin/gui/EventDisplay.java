package be.surin.gui;

import be.surin.engine.Event;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.time.ZoneOffset;

public class EventDisplay {

    private Event event;

    public EventDisplay(Event event) {
        this.event = event;
    }

    //TODO pane for event
    public VBox display() {
        VBox pane = new VBox();
        Label name = new Label(event.getName());
        Label date = new Label(event.getDate().toString());
        Label hour = new Label(""+event.getInstant());
        Label desc = new Label(event.getDescription());
        pane.getChildren().addAll(name, date, hour, desc);
        return pane;
    }
}
