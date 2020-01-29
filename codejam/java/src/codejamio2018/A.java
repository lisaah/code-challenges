package codejamio2018;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class A {

    static int calculate(List<Integer> x) {
        Collections.sort(x);
        int errors = 0;

        int length = x.size();
        for (int i = 0; i < length; i++) {
            errors += (x.get(i) - (i / 2)) * (x.get(i) - (i / 2));
        }

        return errors;
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

        int testCases = Integer.valueOf(in.nextInt());

        for (int i = 0; i < testCases; i++) {

            int x = Integer.valueOf(in.nextInt());
            int result = calculate(readLineToInt(in, x));

            out.printf("Case #%d: %d\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
        Scanner in = new Scanner(new FileInputStream(args[0]));
        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new A().run(in, out);

        out.flush();
        out.close();
    }
}
