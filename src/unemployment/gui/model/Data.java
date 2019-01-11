package unemployment.gui.model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;


public class Data {
    private StringProperty year;
    private DoubleProperty unemployment;

    public Data(String year, double unemployment){
        this.year = new SimpleStringProperty(year);
        this.unemployment = new SimpleDoubleProperty(unemployment);
    }

    public StringProperty yearProperty() {
        return year;
    }
    public double getUnemployment() {
        return unemployment.get();
    }
    public DoubleProperty unemploymentProperty() {
        return unemployment;
    }
    public void setUnemployment(double unemployment) {
        this.unemployment.set(unemployment);
    }
}
