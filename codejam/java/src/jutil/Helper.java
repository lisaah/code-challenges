package jutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * Compilation of methods/classes often useful.
 */
public class Helper {

    class Pair<K, V> {
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

    public void reverse(char[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            int j = arr.length - i;
            char temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    static void swap(int a, int b, long[] array) {
        long c = array[a];
        array[a] = array[b];
        array[b] = c;
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



    static int arrayMax(int[] c, int s, int e) {
        int m = 0;
        for (int i = s; i < e; i++) {
            if (c[i] > m) {
                m = c[i];
            }
        }

        return m;
    }

    static int arrayMin(int[] c, int s, int e) {
        int m = 0;
        for (int i = s; i < e; i++) {
            if (c[i] < m) {
                m = c[i];
            }
        }

        return m;
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

    static long[] convert(String[] s) {
        long[] l = new long[s.length];
        for (int i = 0; i < s.length; i++) {
            l[i] = Long.valueOf(s[i]);
        }

        return l;
    }
}
