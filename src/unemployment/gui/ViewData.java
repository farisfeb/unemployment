package unemployment.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import unemployment.gui.model.Data;

public class ViewData {
    @FXML private TableView<Data> viewDataTable;
    @FXML private TableColumn<Data, String> yearColumn;
    @FXML private TableColumn<Data, Number> unemploymentColumn;

    public void showData(String[] label, double[] dbData) {
        ObservableList<Data> data = FXCollections.observableArrayList();

        for (int i = 0; i < dbData.length; i++) {
            data.add(new Data(label[i], dbData[i]));
        }
        viewDataTable.setItems(data);

        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        unemploymentColumn.setCellValueFactory(cellData -> cellData.getValue().unemploymentProperty());
    }
}
