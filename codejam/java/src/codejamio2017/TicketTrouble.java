package codejamio2017;

import java.io.*;
import java.util.*;

public class TicketTrouble {

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
            if (!this.key.equals(pair.key)) {
                return false;
            }
            return this.value.equals(pair.value);
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

    static int collectionMax(Collection<Integer> c) {
        int max = 0;
        for (Integer i : c) {
            if (i > max) {
                max = i;
            }
        }

        return max;
    }

    static String calculate(Set<Pair<Integer, Integer>> seats) {
        Map<Integer, Integer> matching = new HashMap<>();

        for (Pair<Integer, Integer> seat : seats) {
            Integer row = seat.getKey();
            Integer col = seat.getValue();

            matching.put(row, matching.getOrDefault(row, 0) + 1);

            if (row != col) {
                matching.put(col, matching.getOrDefault(col, 0) + 1);
            }
        }

        int max = collectionMax(matching.values());
        return "" + max;
    }


    // read a line from the input, and convert it to a list of integers
    static List<Integer> readLineToInt(Scanner in, Integer num) {

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

            List<Integer> problemLine = readLineToInt(in, 2);
            int x = problemLine.get(0); // num friends

            Set<Pair<Integer, Integer>> seats = new HashSet<>();
            for (int j = 0; j < x; j++) {
                List<Integer> seat = readLineToInt(in, 2);
                seats.add(new Pair(seat.get(0), seat.get(1)));
            }

            String result = calculate(seats);

            out.printf("Case #%d: %s\n", (i + 1), result);
        }
    }

    public static void main(String args[]) throws Exception  {
        Scanner in = new Scanner(new FileInputStream(args[0]));
//        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new TicketTrouble().run(in, out);

        out.flush();
        out.close();
    }
}
