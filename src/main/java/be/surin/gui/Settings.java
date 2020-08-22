package be.surin.gui;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Settings {

    private static TabPane settingsPane;


    public static Scene settingsSetup() {
        BorderPane mainPane = new BorderPane();
        settingsPane = new TabPane();
        settingsPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab hppTab = new Tab();
        hppTab.setText("HyperPlanning");

        settingsPane.getTabs().add(hppTab);

        Button backButton = new Button("retour");
        backButton.setOnAction(e -> {
            AppLauncher.stage.setScene(new CalendarScene());
        });

        settingsPane.getTabs().get(0).setContent(getHppTab());
        mainPane.setTop(backButton);
        mainPane.setCenter(settingsPane);
        return new Scene(mainPane);
    }

    private static BorderPane getHppTab() {
        BorderPane bp = new BorderPane();

        //column set-up
        TableColumn<String, String> optionColumn = new TableColumn<>("Option");
        optionColumn.setCellValueFactory(new PropertyValueFactory<>("option"));

        TableColumn<String, String> courseColumn = new TableColumn<>("Nom du cours");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("cours"));

        TableColumn<String, String> codeColumn = new TableColumn<>("Code du cours");
        optionColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableView<String> courseSelect = new TableView<>();
        courseSelect.getColumns().addAll(optionColumn, courseColumn, codeColumn);

        courseSelect.setPlaceholder(new Label("Aucun cours disponible"));

        //add a course
        Button addCourse = new Button("Ajouter un cours");
        addCourse.setOnAction(e -> {
            addStage();
        });

        bp.setCenter(courseSelect);
        return bp;
    }

    public static void addStage() {
        Stage addStage = new Stage();
        VBox layout = new VBox();

        Label optionPick = new Label("Choisissez une option");
        ChoiceBox<String> optionChoice = new ChoiceBox<>();
        optionChoice.setItems(FXCollections.observableArrayList(
                "BAC1-Math",
                "BAC2-Math",
                "BAC3-Math",
                "BAC1-Info",
                "BAC2-Info",
                "BAC3-Info",
                "BAC1-Physique",
                "BAC2-Physique",
                "BAC3-Physique",
                "BAC1-Biologie",
                "BAC2-Biologie",
                "BAC3-Biologie",
                "BAC1-Chimie",
                "BAC2-Chimie",
                "BAC3-Chimie" ));
        


        Scene sc = new Scene(layout);
        addStage.setScene(sc);
        addStage.show();
    }

    public static TabPane getSettingsTabs() {
        return settingsPane;
    }
}
