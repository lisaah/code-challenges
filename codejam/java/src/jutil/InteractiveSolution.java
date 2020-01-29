package jutil;

import java.util.*;

public class InteractiveSolution {
    static int level = 1;
    static int[] workerStatus;
    static Map<Integer, Pair<Integer, Integer>> workerRanges;

    static class Pair<K, V> {
        public K key;
        public V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
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

    public static void resetWorkerStatus(int n) {
        level = 1;
        workerStatus = new int[n];
        Arrays.fill(workerStatus, 0);
        workerRanges = new HashMap<>();
    }

    public static boolean knowAnswer(int b) {
        int knownBroken = 0;
        for (int i = 0; i < workerStatus.length; i++) {
            if (workerStatus[i] == -1) {
                knownBroken++;
            }
        }

        return knownBroken == b;
    }

    public static String[] getBroken(int b) {
        String[] broken = new String[b];
        int index = 0;
        for (int i = 0; i < workerStatus.length; i++) {
            if (workerStatus[i] == -1) {
                broken[index] = "" + i;
            }
        }
        return broken;
    }

    static String generateGuess(int n, int b) {
        int splits = (n + 1) / level;
        StringBuilder sb = new StringBuilder();

        char c = '0';
        for (int i = 0; i < n; i++) {
            if (i % splits == 0) {
                c = c == '0' ? '1' : '0';
            }

            sb.append(c);
        }

        return sb.toString();
    }

    static void markGood(int a, int b) {
        for (int i = a; i < b; i++) {
            workerStatus[i] = 1;
        }
    }

    static void recordGuess(String result, String guess, int n, int b) {
        int splits = (n + 1) / level;
        int splitIndex = -1;

        char c = '0';
        int workerId = 0;
        int j = 0;
        boolean hasBadWorker = false;
        for (int i = 0; i < guess.length(); i++ ) {
            if (i % splits == 0) {
                if (i != 0 && !hasBadWorker) {
                    markGood(splitIndex * splits,
                            Math.min((splitIndex + 1) * splitIndex, guess.length()));
                }
                c = c == '0' ? '1' : '0';
                splitIndex++;
                hasBadWorker = false;
            }

            if (j >= result.length() || (result.charAt(j) != c && workerStatus[i] != 1)) {
                updateWorkerRange(workerId, n, splits, splitIndex);
                hasBadWorker = true;
                workerId++;
            } else {
                j++;
            }
        }
        System.out.println(Arrays.toString(workerStatus));
        System.out.println(workerRanges.toString());
    }

    static void updateWorkerRange(int workerId, int n, int splits, int splitIndex) {
        int low = splitIndex * splits;
        int high = Math.min((splitIndex + 1) * splits, n) - 1;
        Pair<Integer, Integer> range = new Pair<>(low, high);
        workerRanges.put(workerId, range);
    }


    public static void solve(Scanner input, int n, int b) {
        level *= 2;
        String guess = generateGuess(n, b);
        System.out.printf("TEST_STORE %s\n", guess);
        System.out.flush();

        String s = input.next();
        if (s.equals("-1")) {
            return;
        }

        recordGuess(s, guess, n, b);

        if (knowAnswer(b)) {
            System.out.printf("%s", String.join(" ", getBroken(b)));
            System.out.flush();

            // getting through verdict
            input.next();
        } else {
            solve(input, n, b);
        }
    }



    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int T = input.nextInt();
        for (int ks = 1; ks <= T; ks++) {
            int n = input.nextInt();
            int b = input.nextInt();
            int f = input.nextInt();
            resetWorkerStatus(n);
            solve(input, n, b);
        }
    }
}
