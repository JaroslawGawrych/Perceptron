import java.util.ArrayList;
import java.util.Random;

public class Perceptron {
    public String name;
    public double alpha;
    public double beta;
    public int a;
    public double bias;
    public double[] weights;

    public Perceptron(String name, int a, double alpha, double beta) {
        this.name = name;
        this.alpha = alpha;
        this.beta = beta;
        this.a = a;
        bias = 1;
        weights = new double[a];
        Random rand = new Random();
        for (int i = 0; i < a; i++) {
            weights[i] = rand.nextDouble() + 0.01;
//            weights[i] = 1;
        }
    }

    public void train(Iris iris) {
        if(guess(iris.attrs)!=name.equals(iris.name)) {
            int d = name.equals(iris.name) ? 1 : 0;
            int y = guess(iris.attrs) ? 1 : 0;
            double delta = (d - y);
            for (int i = 0; i < a; i++) {
                weights[i] += delta * alpha * iris.attrs.get(i);
            }
            bias -= delta * beta;
            normalise();
        }
    }

    public boolean guess(ArrayList<Double> attrs) {
        double sum = 0;
        for (int i = 0; i < a; i++) {
            sum += attrs.get(i) * weights[i];
        }
        return sum >= bias;
    }

    public void normalise() {
        double sum = 0;
        for (double d :
                weights) {
            sum += d * d;
        }
        sum = Math.sqrt(sum);
        for (int i = 0; i < weights.length; i++) {
            weights[i] /= sum;
        }
    }
}
