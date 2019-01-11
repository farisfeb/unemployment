package unemployment.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import unemployment.gui.model.Parameter;

public class ChangeParameter {

    private Parameter parameter;
    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML private TextField popSize, inputLayer, hiddenLayer, wMin, wMax, c1, c2,
            maxIterasi, nTrainData, nTestData, dataPattern;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void getParameter(Parameter parameter) {
        this.parameter = parameter;
        popSize.setText(Integer.toString(parameter.getPopSize()));
        inputLayer.setText(Integer.toString(parameter.getInputLayer()));
        hiddenLayer.setText(Integer.toString(parameter.getHiddenLayer()));
        wMin.setText(Double.toString(parameter.getwMin()));
        wMax.setText(Double.toString(parameter.getwMax()));
        c1.setText(Double.toString(parameter.getC1()));
        c2.setText(Double.toString(parameter.getC2()));
        maxIterasi.setText(Integer.toString(parameter.getMaxIterasi()));
        nTrainData.setText(Integer.toString(parameter.getnTrainData()));
        nTestData.setText(Integer.toString(parameter.getnTestData()));
        dataPattern.setText(Integer.toString(parameter.getDataPattern()));
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            parameter.setPopSize(Integer.parseInt(popSize.getText()));
            parameter.setInputLayer(Integer.parseInt(inputLayer.getText()));
            parameter.setHiddenLayer(Integer.parseInt(hiddenLayer.getText()));
            parameter.setwMin(Double.parseDouble(wMin.getText()));
            parameter.setwMax(Double.parseDouble(wMax.getText()));
            parameter.setC1(Double.parseDouble(c1.getText()));
            parameter.setC2(Double.parseDouble(c2.getText()));
            parameter.setMaxIterasi(Integer.parseInt(maxIterasi.getText()));
            parameter.setnTrainData(Integer.parseInt(nTrainData.getText()));
            parameter.setnTestData(Integer.parseInt(nTestData.getText()));
            parameter.setDataPattern(Integer.parseInt(dataPattern.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (popSize.getText() == null || popSize.getText().length() == 0) {
        errorMessage += "Input salah!\n";
        } else if (inputLayer.getText() == null || inputLayer.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else if (hiddenLayer.getText() == null || hiddenLayer.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else if (wMin.getText() == null || wMin.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else if (wMax.getText() == null || wMax.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else if (c1.getText() == null || c1.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else if (c2.getText() == null || c2.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else if (maxIterasi.getText() == null || maxIterasi.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else if (nTrainData.getText() == null || nTrainData.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else if (nTestData.getText() == null || nTestData.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else if (dataPattern.getText() == null || dataPattern.getText().length() == 0) {
            errorMessage += "Input salah!\n";
        } else {
            try {
                Integer.parseInt(popSize.getText());
                Integer.parseInt(inputLayer.getText());
                Integer.parseInt(hiddenLayer.getText());
                Double.parseDouble(wMin.getText());
                Double.parseDouble(wMax.getText());
                Double.parseDouble(c1.getText());
                Double.parseDouble(c2.getText());
                Integer.parseInt(maxIterasi.getText());
                Integer.parseInt(nTrainData.getText());
                Integer.parseInt(nTestData.getText());
                Integer.parseInt(dataPattern.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Input salah!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Input Salah");
            alert.setHeaderText("Harap perbaiki input yang salah!");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
