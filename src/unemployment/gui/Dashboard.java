package unemployment.gui;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import unemployment.kode.Normalize;
import unemployment.kode.PSO;
import unemployment.kode.Predict;
import unemployment.kode.Print;

public class Dashboard {
    @FXML LineChart<String, Double> fitnessLineChart, aferLineChart;
    @FXML Label popSize, inputLayer, hiddenLayer, wMin, wMax, c1, c2,
            nTrainData, nTestData, dataPattern, maxIterasi;

    boolean prosesClicked = false;
    private MainApp mainApp;
    private PSO pso;
    private Normalize norm;
    private Predict predict;
    private Print print;

    //for details hyperlink
    private double[] fitnessIterasi;
    private String[] testLabel;
    private double[] testTarget;
    double[] denormResult;
    private double afer;
    private long taskCpuTime;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void showParameter() {
        popSize.textProperty().bind(mainApp.getParameter().popSizeProperty().asString());
        inputLayer.textProperty().bind(mainApp.getParameter().inputLayerProperty().asString());
        hiddenLayer.textProperty().bind(mainApp.getParameter().hiddenLayerProperty().asString());
        wMin.textProperty().bind(mainApp.getParameter().wMinProperty().asString());
        wMax.textProperty().bind(mainApp.getParameter().wMaxProperty().asString());
        c1.textProperty().bind(mainApp.getParameter().c1Property().asString());
        c2.textProperty().bind(mainApp.getParameter().c2Property().asString());
        nTrainData.textProperty().bind(mainApp.getParameter().nTrainDataProperty().asString());
        nTestData.textProperty().bind(mainApp.getParameter().nTestDataProperty().asString());
        dataPattern.textProperty().bind(mainApp.getParameter().dataPatternProperty().asString());
        maxIterasi.textProperty().bind(mainApp.getParameter().maxIterasiProperty().asString());

    }

    public void showFitness(double[] fitnessIterasi ){
        fitnessLineChart.getData().clear();
        fitnessLineChart.setCreateSymbols(false);
        XYChart.Series<String, Double> series = new XYChart.Series<>();

        for (int i = 0; i < fitnessIterasi.length; i++) {
            series.getData().add(new XYChart.Data<>(Integer.toString(i), fitnessIterasi[i]));
        }
        fitnessLineChart.getData().add(series);
    }

    public void showPredict(String[] label, double[] actual, double[] result){
        aferLineChart.getData().clear();
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        XYChart.Series<String, Double> series2 = new XYChart.Series<>();

        for (int i = 0; i < result.length; i++) {
            series1.getData().add(new XYChart.Data<>(label[i], actual[i]/1000000));
            series2.getData().add(new XYChart.Data<>(label[i], result[i]/1000000));
        }
        series1.setName("Actual Value");
        series2.setName("Predict Value");
        aferLineChart.getData().addAll(series1, series2);
    }

    @FXML
    public void handleProcess(){
        norm = new Normalize();
        predict = new Predict();
        print = new Print();
        pso = new PSO(mainApp.getParameter().getPopSize(), mainApp.getParameter().getInputLayer(),
                mainApp.getParameter().getHiddenLayer(), mainApp.getParameter().getwMin(),
                mainApp.getParameter().getwMax(), mainApp.getParameter().getC1(),
                mainApp.getParameter().getC2(), mainApp.getParameter().getMaxIterasi());

        String unemployLabel[] = mainApp.getRead().getUnemployLabel();
        double unemployData[] = mainApp.getRead().getUnemployData();

        mainApp.getRead().minMaxData(unemployData);
        double minData = mainApp.getRead().getMinData();
        double maxData = mainApp.getRead().getMaxData();

        mainApp.getRead().setTrainData(unemployLabel, unemployData);
        double[][] trainData = mainApp.getRead().getTrainData();
        double[] trainTarget = mainApp.getRead().getTrainTarget();
        double[][] normTrainData = norm.normalize(trainData, minData, maxData);
        double[] normTrainTarget = norm.normalizeTarget(trainTarget, minData, maxData);

        mainApp.getRead().setTestData(unemployLabel, unemployData);
        double[][] testData = mainApp.getRead().getTestData();
        testTarget = mainApp.getRead().getTestTarget();
        double[][] normTestData = norm.normalize(testData, minData, maxData);
        norm.normalizeTarget(testTarget, minData, maxData);
        testLabel = mainApp.getRead().getTestLabel();

        System.out.println("---------------------------------------------------");
        System.out.println("| PELATIHAN FEEDFORWARD NEURAL NETWORK DENGAN PSO |");
        System.out.println("---------------------------------------------------");

        System.out.println(" -- INISIALISASI KECEPATAN AWAL PARTIKEL --");
        long startCpuTime = pso.getCpuTime();
        pso.initVelocity();
        print.printVelocity(pso.getV());

        System.out.println(" -- INISIALISASI POSISI AWAL PARTIKEL --");
        pso.initParticle();
        pso.CalcFitness(normTrainData, normTrainTarget, pso.getInputLayer(), pso.getHiddenLayer());
        print.printParticle(pso.getX(), pso.getFitness());

        System.out.println(" -- INISIALISASI PBEST --");
        pso.initPbest();
        print.printParticle(pso.getPbest(), pso.getFitnessPbest());

        System.out.println(" -- INISIALISASI GBEST --");
        pso.initGbest();
        fitnessIterasi = new double[pso.getMaxIterasi() + 1];
        fitnessIterasi[0] = pso.getFitnessGbest();
        print.printGbest(pso.getGbest(), pso.getFitnessGbest());

        int iterasi = 0;
         for (int i = iterasi + 1; i <= pso.getMaxIterasi(); i++) {
            System.out.println("-- ITERASI KE - " + i);

            System.out.println(" -- UPDATE KECEPATAN PARTIKEL --");
            pso.updateVelocity(i);
            print.printVelocity(pso.getV());

            System.out.println(" -- UPDATE POSISI PARTIKEL --");
            pso.updateParticle();
            pso.CalcFitness(normTrainData, normTrainTarget, pso.getInputLayer(), pso.getHiddenLayer());
            print.printParticle(pso.getX(), pso.getFitness());

            System.out.println(" -- UPDATE PBEST --");
            pso.updatePbest();
            print.printParticle(pso.getPbest(), pso.getFitnessPbest());

            System.out.println(" -- UPDATE GBEST --");
            pso.updateGbest();
            fitnessIterasi[i] = pso.getFitnessGbest();
            print.printGbest(pso.getGbest(), pso.getFitnessGbest());
        }

        double result[] = predict.result(pso.getGbest(), normTestData, pso.getInputLayer(), pso.getHiddenLayer());
        denormResult = norm.denormalize(result, minData, maxData);
        showPredict(testLabel, testTarget, denormResult);
        showFitness(fitnessIterasi);

        afer = predict.afer(denormResult, testTarget);
        taskCpuTime = (pso.getCpuTime() - startCpuTime) / 1000000;

        prosesClicked = true;
    }

    @FXML
    private void handleChangeParameter() {
       mainApp.showChangeParameter();
    }

    @FXML
    private void handlePredictDetails() {
        if (prosesClicked == true) {
            mainApp.showPredictDetail(testLabel, testTarget, denormResult, afer, taskCpuTime);
        } else {
            errorMessage();
        }
    }

    @FXML
    private void handleFitnessDetails() {
        if (prosesClicked == true) {
            mainApp.showFitnessDetail(fitnessIterasi);
        } else {
            errorMessage();
        }
    }

    private void errorMessage(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Klik proses dahulu");
        alert.setHeaderText(null);
        alert.setContentText("Jalankan proses terlebih dahulu");
        alert.showAndWait();
    }
}