package codejam2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class YouCanGoYourOwnWay {

    public char getOpposite(char c) {
        if (c == 'E') {
            return 'S';
        } else {
            return 'E';
        }
    }

    public char[] calculate(int size, char[] badPath) {
        int pathSize = badPath.length;
        char[] newPath = new char[pathSize];
        for (int i = 0; i < badPath.length; i++) {
            newPath[i] = getOpposite(badPath[i]);
        }
        return newPath;
    }

    public String solve(int size, char[] path) {
        char[] newPath = calculate(size, path);
        return String.format("%s", new String(newPath));
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
        in.nextLine();

        // return the list
        return res;
    }

    public void run(Scanner in, PrintWriter out) {
        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {

            List<Integer> input = readLineToInt(in, 1);
            int size = input.get(0);
            char[] path = in.nextLine().toCharArray();

            String result = solve(size, path);
            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
//        Scanner in = new Scanner(new FileInputStream(args[0]));
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new YouCanGoYourOwnWay().run(in, out);

        out.flush();
        out.close();
    }
}
