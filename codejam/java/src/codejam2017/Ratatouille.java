package codejam2017;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Ratatouille {

    static class Result {
        long packages;
    }

    static Result calculate(int numIngredients, int numPackages,
                            Integer[] gramsForServing, List<List<Integer>> gramsPerIngredientPackage) {
        Result result = new Result();
        for (int i = 0; i < numPackages; i++) {
            int serving;
            for (int j = 0; j < numIngredients; j++) {

            }
        }


        return result;
    }

    // read a line from the input, and convert it to a list of integers
    public Integer[] readLineToIntegers(Scanner in, Integer size) {

        // the result list
        Integer[] res = new Integer[size];

        // the line as a string
        String[] line = in.nextLine().split("\\W");
        for (int i = 0; i < size; i++) {
            res[i] = (Integer.valueOf(line[i]));
        }

        // return the list
        return res;
    }

    public void run(Scanner in, PrintWriter out) {

        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {
            Integer[] testCase = readLineToIntegers(in, 2);
            int numIngredients = testCase[0];
            int numPackages = testCase[1];

            Integer[] gramsForServing = readLineToIntegers(in, numIngredients);
            List<List<Integer>> gramsPerIngredientsPackage = new ArrayList<>(numIngredients);

            for (int j = 0; j < numIngredients; j++) {
                List<Integer> gramsPerPackage = Arrays.asList(readLineToIntegers(in, numPackages));
                Collections.sort(gramsPerPackage);
                gramsPerIngredientsPackage.add(gramsPerPackage);
            }

            Result result = calculate(numIngredients, numPackages, gramsForServing, gramsPerIngredientsPackage);

            out.printf("Case #%d: %d\n", (i + 1), result.packages);

        }
    }

    public static void main(String args[]) throws Exception  {
        Scanner in = new Scanner(new FileInputStream(args[0]));
        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".txt", "_sol.txt")));

        new Ratatouille().run(in, out);

        out.flush();
        out.close();
    }
}
