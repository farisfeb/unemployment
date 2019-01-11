package unemployment.kode;

public class Normalize {

    public double[][] normalize(double data[][], double min, double max) {
        double[][] normData = new double[data.length][data[0].length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                double selisih = data[i][j] - min;
                normData[i][j] = ((0.8 * selisih) / (max - min)) + 0.1;
            }
        }
        return normData;
    }

    public double[] normalizeTarget(double data[], double min, double max){
        double[] normTarget = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) {
                normTarget[i] = 0;
            } else {
                normTarget[i] = ((0.8 * (data[i] - min)) / (max - min)) + 0.1;
            }
        }
        return normTarget;
    }

    public double[] denormalize(double[] result, double min, double max) {
        double[] denormData = new double[result.length];
        for (int i = 0; i < result.length; i++) {
            denormData[i] = Math.round((((result[i] - 0.1) * (max - min)) / 0.8) + min);
        }
        return denormData;
    }
}
