package unemployment.gui.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class PredictData {
    private StringProperty year;
    private DoubleProperty actualValue;
    private DoubleProperty predictValue;
    private DoubleProperty diff;

    public PredictData(String year, double actualVal, double predictVal){
        this.year = new SimpleStringProperty(year);
        this.actualValue = new SimpleDoubleProperty(actualVal);
        this.predictValue = new SimpleDoubleProperty(predictVal);
        diff = new SimpleDoubleProperty(Math.abs(actualVal - predictVal));
    }

    public StringProperty yearProperty() {
        return year;
    }
    public DoubleProperty actualValueProperty() {
        return actualValue;
    }
    public DoubleProperty predictValueProperty() {
        return predictValue;
    }
    public DoubleProperty diffProperty() {
        return diff;
    }
}
