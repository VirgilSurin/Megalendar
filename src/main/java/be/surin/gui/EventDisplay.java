package be.surin.gui;

import be.surin.engine.Event;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EventDisplay {

    private Event event;

    public EventDisplay(Event event) {
        this.event = event;
    }

    //TODO pane for event
    public VBox display() {
        VBox pane = new VBox();
        Label name = new Label(event.getName());
        Label date = new Label(event.getFromDate().toString());
        Label hour = new Label(""+event.getFromDate());
        Label desc = new Label(event.getDescription());
        pane.getChildren().addAll(name, date, hour, desc);
        return pane;
    }
}
