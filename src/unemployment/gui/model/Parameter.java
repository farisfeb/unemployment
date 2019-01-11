package unemployment.gui.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Parameter {
    private IntegerProperty popSize;
    private IntegerProperty inputLayer;
    private IntegerProperty hiddenLayer;
    private DoubleProperty wMin;
    private DoubleProperty wMax;
    private DoubleProperty c1;
    private DoubleProperty c2;
    private IntegerProperty maxIterasi;

    private IntegerProperty nTrainData;
    private IntegerProperty nTestData;
    private IntegerProperty dataPattern;

    public Parameter(int popSize, int inputLayer, int hiddenLayer, double wMin,
                     double wMax, double c1, double c2, int maxIterasi,
                     int nTrainData, int nTestData, int dataPattern){
        this.popSize = new SimpleIntegerProperty(popSize);
        this.inputLayer = new SimpleIntegerProperty(inputLayer);
        this.hiddenLayer = new SimpleIntegerProperty(hiddenLayer);
        this.wMin = new SimpleDoubleProperty(wMin);
        this.wMax = new SimpleDoubleProperty(wMax);
        this.c1 = new SimpleDoubleProperty(c1);
        this.c2 = new SimpleDoubleProperty(c2);
        this.maxIterasi = new SimpleIntegerProperty(maxIterasi);

        this.nTrainData = new SimpleIntegerProperty(nTrainData);
        this.nTestData = new SimpleIntegerProperty(nTestData);
        this.dataPattern = new SimpleIntegerProperty(dataPattern);
    }

    public int getPopSize() {
        return popSize.get();
    }
    public IntegerProperty popSizeProperty() {
        return popSize;
    }
    public void setPopSize(int popSize) {
        this.popSize.set(popSize);
    }
    public int getInputLayer() {
        return inputLayer.get();
    }
    public IntegerProperty inputLayerProperty() {
        return inputLayer;
    }
    public void setInputLayer(int inputLayer) {
        this.inputLayer.set(inputLayer);
    }
    public int getHiddenLayer() {
        return hiddenLayer.get();
    }
    public IntegerProperty hiddenLayerProperty() {
        return hiddenLayer;
    }
    public void setHiddenLayer(int hiddenLayer) {
        this.hiddenLayer.set(hiddenLayer);
    }
    public double getwMin() {
        return wMin.get();
    }
    public DoubleProperty wMinProperty() {
        return wMin;
    }
    public void setwMin(double wMin) {
        this.wMin.set(wMin);
    }
    public double getwMax() {
        return wMax.get();
    }
    public DoubleProperty wMaxProperty() {
        return wMax;
    }
    public void setwMax(double wMax) {
        this.wMax.set(wMax);
    }
    public double getC1() {
        return c1.get();
    }
    public DoubleProperty c1Property() {
        return c1;
    }
    public void setC1(double c1) {
        this.c1.set(c1);
    }
    public double getC2() {
        return c2.get();
    }
    public DoubleProperty c2Property() {
        return c2;
    }
    public void setC2(double c2) {
        this.c2.set(c2);
    }
    public int getMaxIterasi() {
        return maxIterasi.get();
    }
    public IntegerProperty maxIterasiProperty() {
        return maxIterasi;
    }
    public void setMaxIterasi(int maxIterasi) {
        this.maxIterasi.set(maxIterasi);
    }
    public int getnTrainData() {
        return nTrainData.get();
    }
    public IntegerProperty nTrainDataProperty() {
        return nTrainData;
    }
    public void setnTrainData(int nTrainData) {
        this.nTrainData.set(nTrainData);
    }
    public int getnTestData() {
        return nTestData.get();
    }
    public IntegerProperty nTestDataProperty() {
        return nTestData;
    }
    public void setnTestData(int nTestData) {
        this.nTestData.set(nTestData);
    }
    public int getDataPattern() {
        return dataPattern.get();
    }
    public IntegerProperty dataPatternProperty() {
        return dataPattern;
    }
    public void setDataPattern(int dataPattern) {
        this.dataPattern.set(dataPattern);
    }
}
