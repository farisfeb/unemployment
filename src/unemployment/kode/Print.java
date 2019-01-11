package unemployment.kode;

public class Print {
    public void printTarget(String[] label, double[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.println(label[i] + " " + data[i]);
        }
        System.out.println();
    }

    public void printData(double[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.print(data[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printParticle(double[][] x, double[] fitness){
        for (int i = 0; i < x.length; i++) {
            System.out.print("x" + (i+1));
            for (int j = 0; j < x[0].length; j++) {
                System.out.print(" |" + x[i][j] + "|  ");
            }
            System.out.print(fitness[i]);
            System.out.println();
        }
        System.out.println();
    }

    public void printGbest(double[] gbest, double fitness){
        for (int i = 0; i < gbest.length; i++) {
            System.out.print("|" + gbest[i] + "|  ");
        }
        System.out.print(fitness);
        System.out.println();
    }

    public void printVelocity(double[][] v){
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length; j++) {
                System.out.print("|" + v[i][j] + "|  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void result(String[] label, double[] result){
        for (int i = 0; i < result.length; i++) {
            System.out.println(label[i] + " " + result[i]);
        }
    }
}
