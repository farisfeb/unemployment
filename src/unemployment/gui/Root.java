package unemployment.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Root {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML private void handleExit() {
        System.exit(0);
    }

    @FXML private void handleUnemploymentData() {
        mainApp.showUnemploymentData();
    }

    @FXML private void handleTrainData() {
        mainApp.showTrainData();
    }

    @FXML private void handleTestData() {
        mainApp.showTestData();
    }

    @FXML private void handleAbout() {
        mainApp.showAbout();
    }
}
