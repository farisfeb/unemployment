package unemployment.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import unemployment.gui.model.Parameter;
import unemployment.kode.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private ReadData read;
    private Parameter parameter;

    public MainApp() {
        read = new ReadData("unemploymentData.txt", 32, 9, 7);
        parameter = new Parameter(30, 7,7,0.4, 0.7, 2, 2, 250,
                32, 9, 7);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Unemployment Prediction Using FNN-PSO");
        this.primaryStage.getIcons().add(new Image("file:resources/code.png"));

        initRootLayout();
        showDashboard();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/Root.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            Root controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/Dashboard.fxml"));
            AnchorPane prediksi = loader.load();

            rootLayout.setCenter(prediksi);

            Dashboard controller = loader.getController();
            controller.setMainApp(this);
            controller.showParameter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean showChangeParameter() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/ChangeParameter.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ubah Parameter");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ChangeParameter controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.getParameter(parameter);

            dialogStage.getIcons().add(new Image("file:resources/edit.png"));
            dialogStage.setResizable(false);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showPredictDetail(String[] year, double[] actualVal, double[] predictVal,
                                  double afer, long computeTime) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/PredictDetails.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Detail Prediksi");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            dialogStage.getIcons().add(new Image("file:resources/bar-chart.png"));

            PredictDetails controller = loader.getController();
            controller.showData(year, actualVal, predictVal);
            controller.showAfer(afer);
            controller.showComputeTime(computeTime);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFitnessDetail(double[] dataFitness) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/FitnessDetails.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Fitness Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            dialogStage.getIcons().add(new Image("file:resources/bar-chart.png"));

            FitnessDetails controller = loader.getController();
            controller.showData(dataFitness);

            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUnemploymentData() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/ViewData.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Unemployment PredictDetails");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            dialogStage.getIcons().add(new Image("file:resources/list.png"));

            ViewData controller = loader.getController();
            controller.showData(read.getUnemployLabel(), read.getUnemployData());
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTrainData() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/ViewData.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Train PredictDetails");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            dialogStage.getIcons().add(new Image("file:resources/list.png"));

            ViewData controller = loader.getController();
            read.setTrainData(read.getUnemployLabel(), read.getUnemployData());
            controller.showData(read.getTrainLabel(), read.getTrainTarget());
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTestData() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("fxml/ViewData.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("View Test PredictDetails");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            dialogStage.getIcons().add(new Image("file:resources/list.png"));

            ViewData controller = loader.getController();
            controller.showData(read.getUnemployLabel(), read.getUnemployData());
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(getPrimaryStage());
        alert.setTitle("Unemployment Prediction Using FNN-PSO");
        alert.setHeaderText("About");
        FlowPane fp = new FlowPane();
        Label lbl = new Label("Coded with love by");
        Hyperlink link = new Hyperlink("Faris Febrianto");
        Label lbl1 = new Label("and");
        Hyperlink link1 = new Hyperlink("Bayu Septyo Adi");
        fp.getChildren().addAll(lbl, link, lbl1, link1);

        link.setOnAction((ActionEvent e) -> {
            getHostServices().showDocument("https://github.com/farisfebrianto/unemployment");
        });

        link1.setOnAction((ActionEvent e) -> {
            getHostServices().showDocument("https://github.com/Bayusa10/PSO-FFNN");
        });

        alert.getDialogPane().contentProperty().set(fp);
        alert.showAndWait();
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ReadData getRead() {
        return read;
    }
    public Parameter getParameter() {
        return parameter;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
