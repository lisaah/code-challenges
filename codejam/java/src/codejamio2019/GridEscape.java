package codejamio2019;

import java.io.*;
import java.util.*;

public class GridEscape {

    public String calculate(int rows, int cols, int k) {
        if (rows * cols - k == 1) {
            return "IMPOSSIBLE";
        }

        StringBuffer sb = new StringBuffer("POSSIBLE");

        int currentPerson = 0;

        if (rows == 1) {
            sb.append("\n");
            for (int j = 0; j < cols; j++) {
                currentPerson++;
                if (currentPerson <= k) {
                    // escape
                    sb.append("W");
                } else if (j < cols - 1) {
                    // no escape
                    sb.append("E");
                } else {
                    // last
                    sb.append("W");
                }
            }

            return sb.toString();
        }


        if (cols == 1) {
            for (int i = 0; i < rows; i++) {
                sb.append("\n");
                currentPerson++;
                if (currentPerson <= k) {
                    // escape
                    sb.append("N");
                } else if (i < rows - 1) {
                    // no escape
                    sb.append("S");
                } else {
                    // last
                    sb.append("N");
                }
            }

            return sb.toString();
        }


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                currentPerson++;

                if (j == 0) {
                    sb.append("\n");
                }


                if (currentPerson <= k) {
                    sb.append('N');
                } else {
                    if (i == rows - 1) {
                        if (j == cols - 1) {
                            sb.append('W');
                        } else {
                            sb.append('E');
                        }
                    } else {
                        sb.append('S');
                    }
                }
            }

        }

        return sb.toString();
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

            List<Integer> input = readLineToInt(in, 3);
            int rows = input.get(0);
            int cols = input.get(1);
            int k = input.get(2);
            String result = calculate(rows, cols, k);

            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
//        Scanner in = new Scanner(new FileInputStream(args[0]));
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new GridEscape().run(in, out);

        out.flush();
        out.close();
    }
}
