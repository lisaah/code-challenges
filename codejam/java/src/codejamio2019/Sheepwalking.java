package codejamio2019;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class Sheepwalking {

    class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Pair<?, ?> pair = (Pair<?, ?>) o;

            // call equals() method of the underlying objects
            if (!this.key.equals(pair.getKey())) {
                return false;
            }
            return this.value.equals(pair.getValue());
        }

        @Override
        public int hashCode() {
            return 31 * this.key.hashCode() + this.value.hashCode();
        }

        @Override
        public String toString() {
            return "(" + this.key + ", " + this.value + ")";
        }
    }

    static private Map<Pair, Double> values = new HashMap();

    public Double calculate(int x, int y) {
        // Only care abs value
        x = Math.abs(x);
        y = Math.abs(y);

        // Base cases
        if (x == 0 && y == 0) {
            return 0.0;
        }

        if (x == 0 && y == 1) {
            return 3.0;
        }

        if (x == 1 && y == 0) {
            return 3.0;
        }

        Pair xy = new Pair(x, y);

        // Already calculated, we good
        if (values.containsKey(xy)) {
            return values.get(xy);
        }

        double steps;
        if (x == 0) {
            steps =  (2.0 / 3.0) * calculate(0, y - 1) +
                    (1.0 / 3.0) * calculate(1, y - 1) + 2.0;
        } else if (y == 0) {
            steps =  (2.0 / 3.0) * calculate(x - 1, 0) +
                    (1.0 / 3.0) * calculate(x - 1, 1) + 2.0;
        } else {
            steps = (1.0 / 2.0) * calculate(x - 1, y) +
                    (1.0 / 2.0) * calculate(x, y - 1) + 1.0;
        }

        values.put(xy, steps);

        return steps;
    }

    public String format(int x, int y) {
        double steps = calculate(x, y);
        return String.format("%.6f", steps);
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

            List<Integer> input = readLineToInt(in, 2);
            int x = input.get(0);
            int y = input.get(1);
            String result = format(x, y);

            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
//        Scanner in = new Scanner(new FileInputStream(args[0]));
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new Sheepwalking().run(in, out);

        out.flush();
        out.close();
    }
}
