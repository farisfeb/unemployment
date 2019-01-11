package unemployment.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import unemployment.gui.model.PredictData;

public class PredictDetails {
    @FXML private TableView<PredictData> predictTable;
    @FXML private TableColumn<PredictData, String> yearColumn;
    @FXML private TableColumn<PredictData, Number> actualColumn;
    @FXML private TableColumn<PredictData, Number> predictColumn;
    @FXML private TableColumn<PredictData, Number> diffColumn;
    @FXML private Label afer, computeTime;
//    private MainApp mainApp = new MainApp();

    public void showData(String[] year, double[] actualVal, double[] predictVal) {
        ObservableList<PredictData> predictData = FXCollections.observableArrayList();

        for (int i = 0; i < actualVal.length; i++) {
            predictData.add(new PredictData(year[i], actualVal[i], predictVal[i]));
        }
        predictTable.setItems(predictData);

        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        actualColumn.setCellValueFactory(cellData -> cellData.getValue().actualValueProperty());
        predictColumn.setCellValueFactory(cellData -> cellData.getValue().predictValueProperty());
        diffColumn.setCellValueFactory(cellData -> cellData.getValue().diffProperty());
    }

    public void showAfer(double aferData){
        afer.setText(Double.toString(aferData));
    }

    public void showComputeTime(long cpuTime){
        computeTime.setText(Long.toString(cpuTime));
    }
}
