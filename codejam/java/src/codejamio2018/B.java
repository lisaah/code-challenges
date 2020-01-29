package codejamio2018;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class B {

    class Pair implements Comparable<Pair> {

        long numEmployees;
        long expLevel;

        @Override
        public int compareTo(Pair o) {
            if (this.expLevel > o.expLevel) {
                return 1;
            } else if (this.expLevel < o.expLevel) {
                return -1;
            }

            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Pair) {
                return ((Pair) o).expLevel == this.expLevel && ((Pair) o).numEmployees == this.numEmployees;
            }
            return false;
        }

        @Override
        public String toString() {
            return "N=" + numEmployees + "," + "L=" + expLevel;
        }
    }

    static long calculate(List<Pair> x) {
        Collections.sort(x);
        long minLevel = x.get(x.size() - 1).expLevel + 1;
        long peons = x.get(0).numEmployees;
        List<Pair> peonsLeft = new ArrayList<>();
        peonsLeft.add(x.get(0));

        for (int i = 1; i < x.size(); i++) {
            Pair minions = x.get(i);
            peons = Math.max(0, peons - minions.expLevel * minions.numEmployees) + minions.numEmployees;
        };

        return Math.max(peons - minLevel, 0L) + minLevel;
    }


    // read a line from the input, and convert it to a list of pairs
    public List<Pair> readLineToInt(Scanner in, Integer num) {

        // the result list
        List<Pair> res = new ArrayList<>();

        // the line as a string
        for (int i = 0; i < num; i++) {
            if (in.hasNextLong()) {
                Pair pair = new Pair();
                pair.numEmployees = in.nextLong();
                pair.expLevel = in.nextLong();
                res.add(pair);
            }
        }

        // return the list
        return res;
    }

    public void run(Scanner in, PrintWriter out) {

        int testCases = Integer.valueOf(in.nextInt());

        for (int i = 0; i < testCases; i++) {

            int x = Integer.valueOf(in.nextInt());
            long result = calculate(readLineToInt(in, x));

            out.printf("Case #%d: %d\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
        Scanner in = new Scanner(new FileInputStream(args[0]));
        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new B().run(in, out);

        out.flush();
        out.close();
    }
}
