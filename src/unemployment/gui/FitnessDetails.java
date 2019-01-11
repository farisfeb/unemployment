package unemployment.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FitnessDetails {

    @FXML private TableView<double[]> fitnessTable;
    @FXML private TableColumn<double[], Double> iterationColumn;
    @FXML private TableColumn<double[], Double> fitnessColumn;
    private MainApp mainApp = new MainApp();

    public void showData(double[] dataArray) {
        double[][] tempData = new double[dataArray.length][2];
        for (int i = 0; i < dataArray.length; i++) {
            tempData[i][0] = i;
            tempData[i][1] = dataArray[i];
        }

        ObservableList<double[]> data = FXCollections.observableArrayList();
        for (double[] row : tempData) {
            data.add(row);
        }
        fitnessTable.setItems(data);

        iterationColumn.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper((int)param.getValue()[0])
        );

        fitnessColumn.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper(mainApp.round(param.getValue()[1], 2))
        );
    }
}
