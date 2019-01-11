package unemployment.kode;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.util.Random;
import java.text.DecimalFormat;

public class PSO {
    private double v[][];//particle velocity
    private double x[][];//particle
    private double fitness[];
    private double pbest[][];
    private double fitnessPbest[];
    private double gbest[];
    private double fitnessGbest;
    private int popSize;
    private int inputLayer;
    private int hiddenLayer;
    private int dimension;
    private double wMin;
    private double wMax;
    private double c1;
    private double c2;
    private int maxIterasi;
    private Random rand = new Random();
    private DecimalFormat df = new DecimalFormat("#.######");

    public PSO(int popSize, int inputLayer, int hiddenLayer, double wMin, double wMax, double c1, double c2, int maxIterasi) {
        this.popSize = popSize;
        this.inputLayer = inputLayer;
        this.hiddenLayer = hiddenLayer;
        this.wMin = wMin;
        this.wMax = wMax;
        this.c1 = c1;
        this.c2 = c2;
        this.maxIterasi = maxIterasi;
        dimension = (inputLayer + 1) * hiddenLayer + (hiddenLayer + 1) * 1;
    }

    public void initVelocity() {
        v = new double[popSize][dimension];
        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < dimension; j++) {
                v[i][j] = 0;
            }
        }
    }

    public void initParticle() {
        x = new double[popSize][dimension];
        double min = -1.0;//initialize search space
        double max = 1.0;
        int round = 1;
        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < dimension; j++) {
                x[i][j] = min + (max - min) * rand.nextDouble();
                BigDecimal bd = new BigDecimal(x[i][j]);
                bd = bd.setScale(round, BigDecimal.ROUND_UP);
                x[i][j] = bd.doubleValue();
            }
        }
    }

    public double[][] initZ(int trainData, int hiddenlayer) {
        double zNet[][] = new double[trainData][hiddenlayer];
        for (int i = 0; i < trainData; i++) {
            for (int j = 0; j < hiddenlayer; j++) {
                zNet[i][j] = Math.random();
            }
        }
        return zNet;
    }

    public double[] initY(int trainData) {
        double yNet[] = new double[trainData];
        for (int i = 0; i < trainData; i++) {
            yNet[i] = 0;
        }
        return yNet;
    }

    public void initPbest() {
        pbest = new double[popSize][dimension];
        fitnessPbest = new double[popSize];
        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < dimension; j++) {
                pbest[i][j] = x[i][j];
            }
            fitnessPbest[i] = Double.valueOf(df.format(fitness[i]));
        }
    }

    public void initGbest() {
        gbest = pbest[0];
        fitnessGbest = fitnessPbest[0];
        for (int i = 0; i < popSize; i++) {
            double[] tempGbest = pbest[i];
            double tempFitnessGbest = fitnessPbest[i];
            if (tempFitnessGbest >= fitnessGbest) {
                gbest = tempGbest;
                fitnessGbest = Double.valueOf(df.format(tempFitnessGbest));
            }
        }
    }

    public void CalcFitness(double[][] trainData, double[] trainTarget,
                            int inputLayer, int hiddenLayer) {
        fitness = new double[popSize];
        for (int i = 0; i < popSize; i++) {
            double zNet[][] = initZ(trainData.length, hiddenLayer);
            double zJ[][] = initZ(trainData.length, hiddenLayer);
            double yNet[] = initY(trainData.length);
            double yK[] = initY(trainData.length);

            for (int j = 0; j < trainData.length; j++) {
                int inputWeightIndex = hiddenLayer;
                for (int k = 0; k < hiddenLayer; k++) {
                    for (int l = 0; l < inputLayer; l++) {
                        double inputWeight = x[i][inputWeightIndex];
                        double temp = (trainData[j][l] * inputWeight);
                        zNet[j][k] += temp;
                        inputWeightIndex++;
                    }
                    double inputBias = x[i][k];
                    zNet[j][k] = inputBias + zNet[j][k];
                }
            }

            for (int m = 0; m < zNet.length; m++) {
                for (int n = 0; n < zNet[0].length; n++) {
                    double power = (zNet[m][n] * -1);
                    zJ[m][n] = 1.0 / (1.0 + (Math.exp(power)));
                }
            }

            //Inisialisai indeks untuk mengambil bias pada lapisan tersembunyi pada x
            int hiddenBiasIndex = hiddenLayer + (inputLayer * hiddenLayer);

            for (int o = 0; o < zJ.length; o++) {
                int hiddenWeightIndex = hiddenBiasIndex + 1;
                for (int p = 0; p < zJ[0].length; p++) {
                    double hiddenWeight = x[i][hiddenWeightIndex];
                    double temp = (zJ[o][p] * hiddenWeight);
                    yNet[o] = yNet[o] + temp;
                    hiddenWeightIndex++;
                }
                double hiddenBias = x[i][hiddenBiasIndex];
                yNet[o] = hiddenBias + yNet[o];
            }

            double error = 0.0;
            for (int t = 0; t < trainData.length; t++) {
                double power = (yNet[t] * -1);
                yK[t] = 1.0 / (1.0 + (Math.exp(power)));
                double diff = yK[t] - trainTarget[t];
                double diffSqrt = (Math.pow(diff, 2));
                error += diffSqrt;
            }
            //Hitung fitness MSE
            fitness[i] = 1.0 / Double.valueOf(df.format((1.0 / trainData.length) * error));
        }
    }

    public void updateVelocity(int iteration) {
        double n1 = Math.sqrt(iteration + 1);
        double n2 = wMax - wMin;
        double n3 = Math.sqrt(maxIterasi + 1);
        double w = wMax - ((n1 * n2) / (n3));

        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < dimension; j++) {
                double r1 = rand.nextDouble();
                double r2 = rand.nextDouble();
                v[i][j] = (w * v[i][j]) + ((c1 * r1) * (pbest[i][j] - x[i][j])) + ((c2 * r2) * (gbest[j] - x[i][j]));
                if (v[i][j] > 0.1) {
                    v[i][j] = 0.1;
                } else if (v[i][j] < -0.1) {
                    v[i][j] = -0.1;
                } else {
                    v[i][j] = v[i][j];
                }
            }
        }

    }

    public void updateParticle() {
        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < dimension; j++) {
                double temp = x[i][j];
                this.x[i][j] = 0.0;
                this.x[i][j] = temp + v[i][j];
                //batas posisi minimum -1 maksimum 1, sehingga harus diset agar tidak melewati batas  
                if (x[i][j] > 1.0) {
                    x[i][j] = 1.0;
                } else if (x[i][j] < -1.0) {
                    x[i][j] = -1.0;
                } else {
                    x[i][j] = x[i][j];
                }
            }
        }
    }

    public void updatePbest() {
        for (int i = 0; i < popSize; i++) {
            if (fitnessPbest[i] >= fitness[i]) {
                this.fitnessPbest[i] = Double.valueOf(df.format(fitnessPbest[i]));
                for (int j = 0; j < dimension; j++) {
                    this.pbest[i][j] = pbest[i][j];
                }
            } else if (fitness[i] >= fitnessPbest[i]) {
                this.fitnessPbest[i] = Double.valueOf(df.format(fitness[i]));
                for (int j = 0; j < dimension; j++) {
                    this.pbest[i][j] = x[i][j];
                }
            }
        }
    }

    public void updateGbest() {
        int minIndex = 0;
        for (int i = 0; i < popSize; i++) {
            if (fitnessPbest[i] >= fitnessGbest) {
                fitnessGbest = Double.valueOf(df.format(fitnessPbest[i]));
                minIndex = i;
            }
        }

        for (int j = 0; j < dimension; j++) {
            gbest[j] = pbest[minIndex][j];
        }
    }

    public long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.getCurrentThreadCpuTime();
    }

    public double[][] getV() {
        return v;
    }
    public double[][] getX() {
        return x;
    }
    public double[] getFitness() {
        return fitness;
    }
    public double[][] getPbest() {
        return pbest;
    }
    public double[] getFitnessPbest() {
        return fitnessPbest;
    }
    public double[] getGbest() {
        return gbest;
    }
    public double getFitnessGbest() {
        return fitnessGbest;
    }
    public int getPopSize() {
        return popSize;
    }
    public int getInputLayer() {
        return inputLayer;
    }
    public int getHiddenLayer() {
        return hiddenLayer;
    }
    public int getDimension() {
        return dimension;
    }
    public double getwMin() {
        return wMin;
    }
    public double getwMax() {
        return wMax;
    }
    public double getC1() {
        return c1;
    }
    public double getC2() {
        return c2;
    }
    public int getMaxIterasi() {
        return maxIterasi;
    }
}
