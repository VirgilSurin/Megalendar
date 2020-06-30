package be.surin.gui;

import be.surin.engine.Event;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class EventDisplay {

    private Event event;

    public EventDisplay(Event event) {
        this.event = event;
    }

    //TODO pane for event
    public VBox display() {
        VBox pane = new VBox();
        TextArea name = new TextArea(event.getName());
        TextArea date = new TextArea(event.getDate().toString());
        TextArea desc = new TextArea(event.getDescription());
        pane.getChildren().addAll(name, date);
        return pane;
    }
}
