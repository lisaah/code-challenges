package codejamio2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class ParcelPosts {

    public int findEndMarker(int[] l, int startIndex) {
        int leftMin = l[startIndex];
        int leftMax = l[startIndex];
        for (int i = startIndex; i < l.length - 2; i++) {
            int middleValue = l[i + 1];
            int endValue = l[i + 2];

            if (leftMin < middleValue &&
                    endValue < middleValue) {
                return i + 2 ;
            }

            if (leftMax > middleValue &&
                    endValue > middleValue) {
                return i + 2;
            }

            leftMax = Math.max(leftMax, middleValue);
            leftMin = Math.min(leftMin, middleValue);
        }

        return -1;
    }

    public String calculate(int[] l) {
        int c = 0;

        int startMaker = 0;
        while (startMaker != -1) {
            startMaker = findEndMarker(l, startMaker);
            if (startMaker != -1) {
                c++;
            }
        }
        return "" + Math.max(c - 1, 0);
    }

    static int[] convert(String[] s) {
        int[] l = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            l[i] = Integer.valueOf(s[i]);
        }

        return l;
    }

    public void run(Scanner in, PrintWriter out) {

        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {

            int x = Integer.valueOf(in.nextLine()); // length
            String tc = String.valueOf(in.nextLine());
            int[] l = convert(tc.split(" "));
            String result = calculate(l);

            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
//        Scanner in = new Scanner(new FileInputStream(args[0]));
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new ParcelPosts().run(in, out);

        out.flush();
        out.close();
    }
}
