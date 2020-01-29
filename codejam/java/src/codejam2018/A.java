package codejam2018;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class A {

    static void swap(int a, int b, char[] array) {
        char c = array[a];
        array[a] = array[b];
        array[b] = c;
    }

    static int sum(char[] a) {
        int sum = 0;
        int beam = 1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 'C') {
                beam *= 2;
            } else {
                sum += beam;
            }
        }

        return sum;
    }

    static void reduce(char[] a) {
        boolean canSwap = false;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] == 'C' && canSwap) {
                swap(i, i+ 1, a);
                return;
            } else if (a[i] == 'S') {
                canSwap = true;
            }
        }
    }

    static boolean impossible(String s, int x) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'S') {
                sum++;
            }
        }
        return sum > x;
    }

    static String calculate(int x, String s) {
        if (impossible(s, x)) {
            return "IMPOSSIBLE";
        }

        char[] a = s.toCharArray();

        int moves = 0;
        if (sum(a) <= x) {
            return "" + moves;
        }

        while (sum(a) > x) {
            reduce(a);
            moves++;
        }

        return "" + moves;
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

            String tc = String.valueOf(in.nextLine());
            String[] tcSplit = tc.split(" ");

            int x = Integer.valueOf(tcSplit[0]);
            String y = tcSplit[1];

            String result = calculate(x, y);

            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
//        Scanner in = new Scanner(new FileInputStream(args[0]));
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new A().run(in, out);

        out.flush();
        out.close();
    }
}
