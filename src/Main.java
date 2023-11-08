import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public final static File trainingFile = new File("iris_training.txt");
    public final static File testFile = new File("iris_test.txt");
    public static int a;
    public final static String name = "Iris-setosa";
    public static ArrayList<Iris> training = new ArrayList<>();
    public static ArrayList<Iris> test = new ArrayList<>();
    public static ArrayList<Double> maxes = new ArrayList<>();
    public static ArrayList<Double> mins = new ArrayList<>();

    public static void main(String[] args) {

        training = loadFile(trainingFile);

        a = training.get(0).attrs.size();

        test = loadFile(testFile);

        Scanner scanner = new Scanner(System.in);
        System.out.println("czy znormalizowac dane?\n1 - tak\n2 - nie");
        int in = scanner.nextInt();
        if(in == 1) {
            maxes = findMaxes(training);
            mins = findMins(training);

            normailse(training);
            normailse(test);
        }

        Perceptron perceptron = new Perceptron(name, a, 0.00001, 0.00001);
        double wr = 0;
        while(wr!=1) {
            for (Iris i :
                    training) {
                perceptron.train(i);
            }
            int w = 0;
            for (Iris i :
                    training) {
                w += (perceptron.guess(i.attrs)==name.equals(i.name)) ? 1 : 0;
            }
            wr = (double) w / training.size();
            System.out.print("weights: ");
            for (double d :
                    perceptron.weights) {
                System.out.print(d + "\t");
            }
            System.out.println("\nbias: " + perceptron.bias);
            System.out.println();
            System.out.println(w + "/" + training.size());
            System.out.println(wr * 100 + "%");
        }
        System.out.println("===============================");
        int w = 0;
        for (Iris i :
                test) {
            w += perceptron.guess(i.attrs)==name.equals(i.name) ? 1 : 0;
        }
        wr = (double) w / test.size();
        System.out.println(w + "/" + test.size());
        System.out.println(wr * 100 + "%");
        boolean b = true;
        while(b) {
            System.out.println("czy chcesz podac atrybuty?\n1 - tak\n2 - nie");
            int tmp = scanner.nextInt();
            switch (tmp) {
                case 1:
                    ArrayList<Double> attrs = new ArrayList<>();
                    for (int i = 0; i < a; i++) {
                        System.out.print("atrybut " + (i + 1) + ": ");
                        attrs.add(scanner.nextDouble());
                    }
                    for (int i = 0; i < a; i++) {
                        if(in == 1)
                            attrs.set(i, (attrs.get(i) - mins.get(i)) / (maxes.get(i) - mins.get(i)));
                    }
                    System.out.println((perceptron.guess(attrs) ? "zidentyfikowano " : "nie zidentyfikowano ") + name);
                    break;
                case 2:
                    b = false;
                    break;
                default:
                    break;
            }
        }
        scanner.close();
    }

    public static ArrayList<Iris> loadFile(File file) {
        ArrayList<Iris> list = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                String[] s = scanner.nextLine().split("\t");
                list.add(new Iris(s));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Double> findMaxes(ArrayList<Iris> list) {
        ArrayList<Double> out = new ArrayList<>(a);
        for (int i = 0; i < a; i++) {
            double max = list.get(0).attrs.get(i);
            for (Iris iris : list) {
                if(iris.attrs.get(i) > max)
                    max = iris.attrs.get(i);
            }
            out.add(max);
        }
        return out;
    }

    public static ArrayList<Double> findMins(ArrayList<Iris> list) {
        ArrayList<Double> out = new ArrayList<>(a);
        for (int i = 0; i < a; i++) {
            double min = list.get(0).attrs.get(i);
            for (Iris iris : list) {
                if(iris.attrs.get(i) < min)
                    min = iris.attrs.get(i);
            }
            out.add(min);
        }
        return out;
    }

    public static void normailse(ArrayList<Iris> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < a; j++) {
                list.get(i).attrs.set(j, (list.get(i).attrs.get(j) - mins.get(j)) / (maxes.get(j) - mins.get(j)));
            }
        }
        for (Iris i :
                list) {
            for (Double d :
                    i.attrs) {
                System.out.print(d + "\t");
            }
            System.out.println();
        }
    }
}
