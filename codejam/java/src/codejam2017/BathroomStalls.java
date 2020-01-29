package codejam2017;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class BathroomStalls {

    static class Stall {
        long min;
        long max;
    }

    static Stall calculate(long space, long nodes) {
        Stall result = new Stall();

        // Find the depth of the binary tree where 0 is root
        long depth = (long) Math.floor(Math.log10(nodes) / Math.log10(2));
        long leafNumber = Math.max(nodes - (long) Math.pow(2, depth), 0);

        // From depth, calculate distance between leaves for the space
        Map<Long, Integer> values = new HashMap<>();
        Map<Long, Integer> newValues = new HashMap<>();
        values.put(space, 1);
        for (int i = 0; i < depth; i++) {
            for (Map.Entry<Long, Integer> valueToNumLeaves: values.entrySet()) {
                long low = (long) Math.floor((valueToNumLeaves.getKey() - 1) / 2.0);
                long high = (long) Math.ceil((valueToNumLeaves.getKey() - 1) / 2.0);

                newValues.put(low, (newValues.containsKey(low) ? newValues.get(low) : 0) + valueToNumLeaves.getValue());
                newValues.put(high, (newValues.containsKey(high) ? newValues.get(high) : 0) + valueToNumLeaves.getValue());
            }

            // Update
            values.clear();
            values.putAll(newValues);
            newValues.clear();
        }

        // Figure out which leaf we are.
        long maxValue = -1;
        for (Map.Entry<Long, Integer> valueToNumLeaves: values.entrySet()) {
            if (valueToNumLeaves.getKey() > maxValue) {
                maxValue = valueToNumLeaves.getKey();
            }
        }

        if (values.get(maxValue) < leafNumber) {
            maxValue = -1;
            for (Map.Entry<Long, Integer> valueToNumLeaves: values.entrySet()) {
                if (valueToNumLeaves.getKey() > maxValue) {
                    maxValue = valueToNumLeaves.getKey();
                }
            }
        }

        // Calculate the min/max for the leaf.
        result.min = (long) Math.floor((maxValue - 1) / 2.0);
        result.max = (long) Math.ceil((maxValue - 1) / 2.0);

        return result;
    }

    // read a line from the input, and convert it to a list of integers
    public long[] readLineToIntegers(Scanner in, Integer size) {

        // the result list
        long[] res = new long[size];

        // the line as a string
        String[] line = in.nextLine().split("\\W");
        for (int i = 0; i < size; i++) {
            res[i] = (Long.valueOf(line[i]));
        }

        // return the list
        return res;
    }

    public void run(Scanner in, PrintWriter out) {

        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {
            long[] testCase = readLineToIntegers(in, 2);
            long space = testCase[0];
            long nodes = testCase[1];

            Stall result = calculate(space, nodes);

            out.printf("Case #%d: %d %d\n", (i + 1), result.max, result.min);

        }
    }

    public static void main(String args[]) throws Exception  {
        Scanner in = new Scanner(new FileInputStream(args[0]));
        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".txt", "_sol.txt")));

        new BathroomStalls().run(in, out);

        out.flush();
        out.close();
    }
}
