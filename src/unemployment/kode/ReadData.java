package unemployment.kode;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ReadData {

    double maxData;
    double minData;

    int nTrainData;
    int nTestData;
    int dataPattern;

    double[] unemployData;
    String[] unemployLabel;

    double[][] trainData;
    double[] trainTarget;
    String[] trainLabel;

    double[][] testData;
    double[] testTarget;
    String[] testLabel;

    public ReadData(String pathName, int nTrainData, int nTestData, int dataPattern) {
        this.nTrainData = nTrainData;
        this.nTestData = nTestData;
        this.dataPattern = dataPattern;

        ArrayList<Double> dataList = new ArrayList<>();
        ArrayList<String> dataLabel = new ArrayList<>();

        try {
            File file = new File(pathName);
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String label = sc.next();
                double data = sc.nextDouble();
                dataList.add(data);
                dataLabel.add(label);
            }
            sc.close();
        }catch (FileNotFoundException e){
            System.out.println(e);
            System.exit(0);
        }

        unemployData = new double[dataList.size()];
        unemployLabel = new String[dataLabel.size()];
        for (int i = 0; i < dataList.size(); i++) {
            unemployData[i] = dataList.get(i);
            unemployLabel[i] = dataLabel.get(i).replace("-", "");
        }
    }

    public void setTrainData(String[] label, double[] data) {
        trainData = new double[nTrainData][dataPattern + 1];
        trainTarget = new double[nTrainData];
        trainLabel = new String[nTrainData];
        for (int i = 0; i < nTrainData; i++) {
            int x = i;
            for (int j = 0; j <= dataPattern; j++) {
                trainData[i][j] = data[x];
                x++;
            }
            trainTarget[i] = trainData[i][dataPattern];
            trainLabel[i] = label[i + dataPattern];
        }
    }

    public void setTestData(String[] label, double[] data) {
        testData = new double[nTestData][dataPattern + 1];
        testTarget = new double[nTestData];
        testLabel = new String[nTestData];
        int initIndex = data.length - (nTestData + dataPattern);
        for (int i = 0; i < nTestData; i++) {
            int x = initIndex;
            for (int j = 0; j <= dataPattern; j++) {
                testData[i][j] = data[x];
                x++;
            }
            testTarget[i] = testData[i][dataPattern];
            testLabel[i] = label[initIndex + dataPattern];
            initIndex++;
        }
    }

    public void minMaxData(double data[]) {
        this.minData = data[0];
        this.maxData = data[0];
        for (int i = 0; i < data.length; i++) {
            if (maxData < data[i]) {
                maxData = data[i];
            }
            if (minData > data[i]) {
                minData = data[i];
            }
        }
    }

    public double[] getUnemployData() {
        return unemployData;
    }
    public String[] getUnemployLabel() {
        return unemployLabel;
    }
    public double getMaxData() {
        return maxData;
    }
    public double getMinData() {
        return minData;
    }
    public double[][] getTrainData() {
        return trainData;
    }
    public double[] getTrainTarget() {
        return trainTarget;
    }
    public double[][] getTestData() {
        return testData;
    }
    public double[] getTestTarget() {
        return testTarget;
    }
    public String[] getTrainLabel() {
        return trainLabel;
    }
    public String[] getTestLabel() {
        return testLabel;
    }
    public int getnTrainData() {
        return nTrainData;
    }
    public void setnTrainData(int nTrainData) {
        this.nTrainData = nTrainData;
    }
    public int getnTestData() {
        return nTestData;
    }
    public void setnTestData(int nTestData) {
        this.nTestData = nTestData;
    }
    public int getDataPattern() {
        return dataPattern;
    }
    public void setDataPattern(int dataPattern) {
        this.dataPattern = dataPattern;
    }
}
