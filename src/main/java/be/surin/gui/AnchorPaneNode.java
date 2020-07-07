package be.surin.gui;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDate;

/**
 * Create an anchor pane that can store additional data.
 */
public class AnchorPaneNode extends AnchorPane {

    // Date associated with this pane
    private LocalDate date;

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     */
    public AnchorPaneNode(Node... children) {
        super(children);
        //Display a timeline of the events for the day
        this.setOnMouseClicked(e ->
            DailyTimelineBox.Display("Timeline", "Timeline of the day : " + date, 600));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
