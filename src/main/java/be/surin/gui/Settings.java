package be.surin.gui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Settings {

    private static TabPane settingsPane;


    public static Scene settingsSetup() {
        BorderPane mainPane = new BorderPane();
        settingsPane = new TabPane();

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
        bp.setCenter(courseSelect);
        return bp;
    }

    public static TabPane getSettingsTabs() {
        return settingsPane;
    }
}
