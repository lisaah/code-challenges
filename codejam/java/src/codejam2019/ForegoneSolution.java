package codejam2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ForegoneSolution {

    public int[] calculate(int input) {
        int[] pair = new int[2];
        int x = 0;
        int y = 0;

        int power = 1;
        while (input != 0) {
            int remainder = input % 10;
            if (remainder == 4) {
                x += (remainder - 1) * power;
                y += power;
            } else {
                x += remainder * power;
            }

            input = (input - remainder) / 10;
            power *= 10;
        }

        pair[0] = x;
        pair[1] = y;

        return pair;
    }

    public String solve(int input) {
        int[] pair = calculate(input);
        return String.format("%d %d", pair[0], pair[1]);
    }

    // read a line from the input, and convert it to a list of integers
    public List<Integer> readLineToInt(Scanner in, Integer num) {

        // the result list
        List<Integer> res = new ArrayList<>();

        // the line as a string
        for (int i = 0; i < num; i++) {
            if (in.hasNextInt()) {
                res.add(in.nextInt());
            }
        }

        // return the list
        return res;
    }

    public void run(Scanner in, PrintWriter out) {
        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {

            List<Integer> input = readLineToInt(in, 1);
            int x = input.get(0);

            String result = solve(x);
            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
//        Scanner in = new Scanner(new FileInputStream(args[0]));
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new ForegoneSolution().run(in, out);

        out.flush();
        out.close();
    }
}
