package unemployment.kode;

public class Unemployment {
    public static void main(String[] args) {
        Normalize norm = new Normalize();
        Predict predict = new Predict();
        Print print = new Print();
        ReadData read = new ReadData("unemploymentData.txt", 32, 9, 7);
        PSO pso = new PSO(30, 7,7,0.4, 0.7, 2, 2, 250);

        String unemployLabel[] = read.getUnemployLabel();
        double unemployData[] = read.getUnemployData();

        read.minMaxData(unemployData);
        double minData = read.getMinData();
        double maxData = read.getMaxData();

        read.setTrainData(unemployLabel, unemployData);
        double[][] trainData = read.getTrainData();
        double[] trainTarget = read.getTrainTarget();
        double[][] normTrainData = norm.normalize(trainData, minData, maxData);
        double[] normTrainTarget = norm.normalizeTarget(trainTarget, minData, maxData);
        String[] trainLabel = read.getTrainLabel();

        System.out.println("DATA LATIH");
        print.printData(trainData);
        System.out.println("TARGET DATA LATIH");
        print.printTarget(trainLabel, trainTarget);

        System.out.println("NORMALISASI DATA LATIH");
        print.printData(normTrainData);
        System.out.println("NORMALISASI TARGET DATA LATIH");
        print.printTarget(trainLabel, normTrainTarget);

        read.setTestData(unemployLabel, unemployData);
        double[][] testData = read.getTestData();
        double[] testTarget = read.getTestTarget();
        double[][] normTestData = norm.normalize(testData, minData, maxData);
        double[] normTestTarget = norm.normalizeTarget(testTarget, minData, maxData);
        String[] testLabel = read.getTestLabel();

        System.out.println("DATA UJI");
        print.printData(testData);
        System.out.println("TARGET DATA UJI");
        print.printTarget(testLabel, testTarget);
        System.out.println("NORMALISASI DATA UJI");
        print.printData(normTestData);
        System.out.println("NORMALISASI TARGET DATA UJI");
        print.printTarget(testLabel, normTestTarget);

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
            print.printGbest(pso.getGbest(), pso.getFitnessGbest());
        }

        System.out.println(" -- HASIL PREDIKSI --");
        double result[] = predict.result(pso.getGbest(), normTestData, pso.getInputLayer(), pso.getHiddenLayer());
        double[] denorm = norm.denormalize(result, minData, maxData);
        print.result(testLabel, denorm);

        System.out.println(" -- AFER --");
        double afer = predict.afer(denorm, testTarget);
        System.out.println(afer);

        long taskCpuTime = (pso.getCpuTime() - startCpuTime) / 1000000;
        System.out.println("Waktu Komputasi " + taskCpuTime + "ms");
    }
}

        
    
    
