package codejam2017;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class PancakeFlip {

    static class Input {
        boolean[] pancakes;
        int flipperSize;
    }

    static String calculate(Input x) {
        int flips = 0;
        for (int i = 0; i < x.pancakes.length; i++) {
            boolean pancake = x.pancakes[i];
            if (!pancake) {
                if (i + x.flipperSize > x.pancakes.length) {
                    return "IMPOSSIBLE";
                }

                flip(x.pancakes, i, x.flipperSize);
                flips++;
            }
        }

        for (int i = 0; i < x.pancakes.length; i++) {
            boolean pancake = x.pancakes[i];
            if (!pancake) {
                return "IMPOSSIBLE";
            }
        }

        return flips + "";
    }

    static void flip(boolean[] pancakes, int flipIndex, int flipSize) {
        for (int i = flipIndex; i < flipIndex + flipSize; i++) {
            pancakes[i] = !pancakes[i];
        }
    }

    // read a line from the input, and convert it to a list of integers
    public Input readLine(Scanner in) {
        String line = in.nextLine();
        String[] inputSplit = line.split("\\s");
        boolean[] pancakes = new boolean[inputSplit[0].length()];
        for (int i = 0; i < inputSplit[0].length(); i++) {
            pancakes[i] = inputSplit[0].charAt(i) == '-' ? false : true;
        }

        Input input = new Input();
        input.pancakes = pancakes;
        input.flipperSize = Integer.valueOf(inputSplit[1]);

        // return the list
        return input;
    }

    public void run(Scanner in, PrintWriter out) {

        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {
            Input input = readLine(in);
            String result = calculate(input);
            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
        Scanner in = new Scanner(new FileInputStream(args[0]));
        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".txt", "_sol.txt")));

        new PancakeFlip().run(in, out);

        out.flush();
        out.close();
    }
}
