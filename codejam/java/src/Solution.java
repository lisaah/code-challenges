
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {
    // sieve
    public  static List<Integer> getPrimes(int n) {
        List<Integer> primes = IntStream.rangeClosed(2, n)
                .boxed().collect(Collectors.toList());
        int i = 0;
        while (i < primes.size()) {
            int prime = primes.get(i);
            primes.removeIf(p -> p > prime && p % prime == 0);
            i++;
        }

        return primes;
    }

    public static Set<Integer> getFactor(int n, List<Integer> primes) {
        for (int i = 0; i < primes.size(); i++) {
            int prime1 = primes.get(i);
            if (n % prime1 == 0) {
                return new HashSet<>(Arrays.asList(prime1, n / prime1));
            }
        }

        return new HashSet<>();
    }

    public static String calculate(int size, List<Integer> cipherText) {
        List<Integer> primes = getPrimes(size);
        Set<Integer> primesUsed = new HashSet<>(26);

        List<List<Integer>> cipherTextValues = Stream.generate(ArrayList<Integer>::new)
                .limit(cipherText.size() + 1).collect(Collectors.toList());
        for (int i = 0; i < cipherText.size(); i++) {
            Collection<Integer> factors = getFactor(cipherText.get(i), primes);
            primesUsed.addAll(factors);

            if (i == 0) {
                cipherTextValues.get(i).addAll(factors);
            } else {
                cipherTextValues.get(i).retainAll(factors);
            }
            cipherTextValues.get(i + 1).addAll(factors);
        }

        List<Integer> sortedPrimes = new ArrayList<>(primesUsed);
        Collections.sort(sortedPrimes);

        // map primes to text
        Map<Integer, Character> alphabetValues = new HashMap<>();
        for (int i = 0; i < sortedPrimes.size(); i++) {
            alphabetValues.put(sortedPrimes.get(i), (char)('A' + i));
        }

        // clean forwards
        for (int i = 0; i < cipherTextValues.size() - 1; i++) {
            Collection<Integer> now = cipherTextValues.get(i);
            Collection<Integer> next = cipherTextValues.get(i + 1);
            if (!next.containsAll(now)) {
                now.removeAll(next);
            }
            if (!now.containsAll(next)) {
                next.removeAll(now);
            }
        }

        // clean backwards
        for (int i = cipherTextValues.size() - 1; i > 0; i--) {
            Collection<Integer> now = cipherTextValues.get(i);
            Collection<Integer> next = cipherTextValues.get(i - 1);
            if (!next.containsAll(now)) {
                now.removeAll(next);
            }
            if (!now.containsAll(next)) {
                next.removeAll(now);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < cipherTextValues.size(); i++) {
            int value = cipherTextValues.get(i).get(0);
            sb.append(alphabetValues.get(value));
        }

        return sb.toString();
    }

    public static String solve(int size, List<Integer> cipherText) {
        String output = calculate(size, cipherText);
        return String.format("%s", output);
    }

    // read a line from the input, and convert it to a list of integers
    public static List<Integer> readLineToInt(Scanner in, Integer num) {

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

    public static void run(Scanner in, PrintWriter out) {
        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {

            List<Integer> input = readLineToInt(in, 2);
            int n = input.get(0);
            int size = input.get(1);
            List<Integer> cipherText = readLineToInt(in, size);

            String result = solve(n, cipherText);
            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
//        Scanner in = new Scanner(new FileInputStream(args[0]));
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(System.out);
//        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        run(in, out);

        out.flush();
        out.close();
    }
}
