package be.surin.gui;

import be.surin.engine.Profile;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    public static Stage stage;
    public static Profile currentProfile;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setScene(new ProfileScene());
        primaryStage.show();
    }
}