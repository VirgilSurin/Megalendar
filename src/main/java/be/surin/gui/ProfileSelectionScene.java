package be.surin.gui;

import be.surin.engine.Profile;
import be.surin.processing.ProfileSelectionHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ProfileSelectionScene extends Scene {

    public ProfileSelectionScene() {
        super(createProfileParent(), 600, 600);
    }

    private static Parent createProfileParent() {
        BorderPane mainPane = new BorderPane();
        mainPane.setPrefSize(550,600);

        HBox buttonBox = new HBox();
        Profile[] profileArray = ProfileSelectionHandler.getProfiles();
        for (Profile profile : profileArray) {
            Button button = new Button(profile.getName());
            button.setOnAction(e -> {
                AppLauncher.currentProfile = profile;
                AppLauncher.stage.setScene(new CalendarScene());
            });
            buttonBox.getChildren().add(button);
        }

        mainPane.setCenter(buttonBox);

        return mainPane;
    }
}
