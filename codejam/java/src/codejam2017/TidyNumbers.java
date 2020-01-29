package codejam2017;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.IntStream;

public class TidyNumbers {

    static long calculate(long x) {

        if (!isTidy(x)) {
            x = makeTidier(x);
        }

        return x;
    }

    static long makeTidier(long x) {
        Stack<Integer> stack = new Stack();
        while (x > 0) {
            stack.push((int) x % 10 );
            x = x / 10;
        }

        int[] digits = new int[stack.size()];
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            digits[i] = stack.pop();
        }

        int digitToRight = digits[digits.length - 1];
        int fillIndex = -1;
        for (int i = digits.length - 2; i >= 0; i--) {
            int digit = digits[i];
            if (digit > digitToRight) {
                if (digits[i] > 0) {
                    digits[i]--;
                    fillIndex = i + 1;
                } else {
                    // No leading 0s this should never go < 0 and not got 9'd
                    digits[i-1]--;
                    fillIndex = i;
                }
            }

            digitToRight = digits[i];
        }

        if (fillIndex > -1) {
            for (int i = fillIndex; i < digits.length; i++) {
                digits[i] = 9;
            }
        }

        StringJoiner join = new StringJoiner("");
        IntStream.of(digits).forEach(y -> join.add(String.valueOf(y)));

        return Long.valueOf(join.toString());
    }

    static boolean isTidy(long x) {
        long current = 10;
        while (x > 0) {
            long mod = x % 10;
            if (current < mod) {
                return false;
            }
            x /= 10; // floor
            current = mod;
        }

        return true;
    }

    // read a line from the input, and convert it to a list of integers
    public List<Long> readLineToIntegers(Scanner in, Integer numCars) {

        // the result list
        List<Long> res = new ArrayList<>();

        // the line as a string
        for (int i = 0; i < numCars; i++) {
            if (in.hasNextLong()) {
                res.add(in.nextLong());
            }
        }

        // return the list
        return res;
    }

    public void run(Scanner in, PrintWriter out) {

        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {
            long result = calculate(Long.valueOf(in.nextLine()));

            out.printf("Case #%d: %d\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
        Scanner in = new Scanner(new FileInputStream(args[0]));
        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".txt", "_sol.txt")));

        new TidyNumbers().run(in, out);

        out.flush();
        out.close();
    }
}
