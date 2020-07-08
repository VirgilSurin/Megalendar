package be.surin.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import be.surin.engine.Profile;

public class Menu extends Application {

    private static Scene primaryScene;
    private BorderPane mainPane;
    private HBox buttonBox;
    private Profile[] profiles = new Profile[] {new Profile("Rayghun"), new Profile("Return print(v)")};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        mainPane = new BorderPane();
        mainPane.setPrefSize(550,600);

        this.buttonBox = new HBox();
        //TODO Delete TestProfile0,1 when the save/load are implemented
        Button testProfile0 = new Button("Rayghun");
        Button testProfile1 = new Button("Return print(v)");
        testProfile0.setOnAction(e -> {
            CalendarMenu.setCurrentProfile(profiles[0]);
            primaryStage.setScene(CalendarMenu.DisplayScene(primaryStage));
        });
        testProfile1.setOnAction(e -> {
            CalendarMenu.setCurrentProfile(profiles[1]);
            primaryStage.setScene(CalendarMenu.DisplayScene(primaryStage));
        });

        buttonBox.getChildren().addAll(testProfile0, testProfile1);
        mainPane.setCenter(buttonBox);

        Scene mainScene = new Scene(mainPane);

        primaryStage.setScene(mainScene);
        this.primaryScene = mainScene;
        primaryStage.show();
    }

    public static Scene getPrimaryScene() {
        return primaryScene;
    }
}