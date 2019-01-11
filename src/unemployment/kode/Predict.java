package unemployment.kode;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Predict {

    DecimalFormat df = new DecimalFormat("#.######");

    public double[] result(double gbest[], double testData[][], int inputLayer, int hiddenLayer) {
        double[] result = new double[testData.length];
        for (int i = 0; i < testData.length; i++) {
            double zNet[] = new double [hiddenLayer];
            double zJ[] = new double [hiddenLayer];
            double yNet = 0;
            double yK;

            int inputWeightIndex = hiddenLayer;
            for (int j = 0; j < hiddenLayer; j++) {
                for (int k = 0; k < inputLayer; k++) {
                    zNet[j] = zNet[j] + (testData[i][k] * gbest[inputWeightIndex]);
                    inputWeightIndex++;
                }
            }

            for (int j = 0; j < hiddenLayer; j++) {
                zNet[j] = gbest[j] + zNet[j];
                zJ[j] = 1.0 / (1.0 + (Math.exp(zNet[j] * -1)));
            }

            int hiddenBiasIndex = (inputLayer * hiddenLayer) + hiddenLayer;
            int hiddenWeightIndex = hiddenBiasIndex + 1;
            for (int j = 0; j < hiddenLayer; j++) {
                yNet = yNet + (zJ[j] * gbest[hiddenWeightIndex]);
                hiddenWeightIndex++;
            }

            yNet = gbest[hiddenBiasIndex] + yNet;
            yK = 1.0 / (1.0 + (Math.exp(yNet * -1)));
            result[i] = Double.valueOf(df.format(yK));
        }
        return result;
    }

    public double afer(double result[], double testTarget[]) {
        int round = 4;
        double temp = 0;
        int nData = result.length;
        for (int i = 0; i < result.length; i++) {
            if (testTarget[i] == 0.0) {
                nData--;
            } else {
                temp = temp + Math.abs((testTarget[i] - result[i]) / testTarget[i]);
            }
        }

        double error = (temp / nData) * 100;
        BigDecimal bd = new BigDecimal(error);
        bd.setScale(round, BigDecimal.ROUND_UP);
        error = bd.doubleValue();
        return error;
    }
}
