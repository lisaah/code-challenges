package codejamio2017;

import java.io.*;
import java.util.*;

public class Understudies {
    static double calculate(List<Double> x) {
        Collections.sort(x);
        double probability = 1.0;

        int length = x.size();
        for (int i = 0; i < length / 2; i++) {
            double roleProbability = 1.0 - x.get(i) * x.get(length - i - 1);
            probability *= roleProbability;

        }

        return probability;
    }

    // read a line from the input, and convert it to a list of integers
    public List<Double> readLineToDouble(Scanner in, Integer numCars) {

        // the result list
        List<Double> res = new ArrayList<>();

        // the line as a string
        for (int i = 0; i < numCars * 2; i++) {
            if (in.hasNextDouble()) {
                res.add(in.nextDouble());
            }
        }

        // return the list
        return res;
    }

    public void run(Scanner in, PrintWriter out) {

        int testCases = Integer.valueOf(in.nextInt());

        for (int i = 0; i < testCases; i++) {

            int numRoles = Integer.valueOf(in.nextInt());
            double result = calculate(readLineToDouble(in, numRoles));

            out.printf("Case #%d: %f\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
//        Scanner in = new Scanner(new FileInputStream(args[0]));
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new Understudies().run(in, out);

        out.flush();
        out.close();
    }
}