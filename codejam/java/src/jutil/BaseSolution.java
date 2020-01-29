package jutil;

import java.io.*;
import java.util.*;

public class BaseSolution {

    static void swap(int a, int b, long[] array) {
        long c = array[a];
        array[a] = array[b];
        array[b] = c;
    }

    static void troublesort(long[] l) {
        boolean done = false;
        while (!done) {
            done = true;
            for (int i = 0; i < l.length - 2; i++) {
                if (l[i] > l[i+2]) {
                    done = false;
                    swap(i, i+2, l);
                }
            }
        }
    }

    static int sortFail(long[] l) {
        for (int i = 0; i < l.length-1; i++) {
            if (l[i] > l[i+1]) {
                return i;
            }
        }

        return -1;
    }

    static String calculate(long[] l) {
        troublesort(l);
        int f = sortFail(l);
        if (f > -1) {
            return "" + f;
        }
        return "OK";
    }


    // read a line from the input, and convert it to a list of integers
    public static List<Integer> readLineToInt(Scanner in, Integer num) {

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

    static long[] convert(String[] s) {
        long[] l = new long[s.length];
        for (int i = 0; i < s.length; i++) {
            l[i] = Long.valueOf(s[i]);
        }

        return l;
    }

    static void run(Scanner in, PrintWriter out) {

        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {

            int x = Integer.valueOf(in.nextLine());
            String tc = String.valueOf(in.nextLine());
            long[] l = convert(tc.split(" "));
            String result = calculate(l);

            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
        Scanner in = new Scanner(new FileInputStream(args[0]));
//        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        run(in, out);

        out.flush();
        out.close();
    }
}
