package be.surin.gui;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.Text;

public class DailyTimelineBox {
    public static void Display(String title, String message, int minWidth){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); //Blocs events towards the caller.
        window.setTitle(title);
        window.setMinWidth(minWidth);
        window.setMinHeight(400);

        Label label = new Label();
        label.setText(message);

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

        // Create the first timeline grid pane
        //TODO Link the events of the day to the timeline
        GridPane timeline1 = new GridPane();
        // The prefHeight of the timeline needs to be bigger than the total height of all the AnchorPane
        // Else, the AnchorPane might not have the right proportions
        timeline1.setPrefSize(120, 60*24);
        timeline1.setGridLinesVisible(true);

        for (int i = 0; i < 24; i++) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(120,60);
            timeline1.add(ap,0,i);
        }

        // Create a second timeline grid pane
        // (just a duplicate of the first to see how it's supposed to look in the future)
        //TODO Delete this timeline later
        GridPane timeline2 = new GridPane();
        // The prefHeight of the timeline needs to be bigger than the total height of all the AnchorPane
        // Else, the AnchorPane might not have the right proportions
        timeline2.setPrefSize(120, 60*24);
        timeline2.setGridLinesVisible(true);

        for (int i = 0; i < 24; i++) {
            AnchorPane ap = new AnchorPane();
            ap.setPrefSize(120,60);
            timeline2.add(ap,0,i);
        }

        HBox timelineHBox = new HBox(10);
        timelineHBox.getChildren().addAll(hourLabels, timeline1, timeline2);
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
}
