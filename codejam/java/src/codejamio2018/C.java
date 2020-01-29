package codejamio2018;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

public class C {


    static boolean possibleAlphabet(String low, String middle, String high, Set<Character> alphabet) {
        // i < j = -1, i > j = 1
        int[][] orderMap = new int[26][26];
        for (int i = 0; i < low.length(); i++) {
            Character lowChar = low.charAt(i);
            Character highChar = middle.charAt(i);
            if (lowChar != highChar) {
                orderMap[lowChar - 'A'][highChar - 'A'] = -1;
                orderMap[highChar - 'A'][lowChar - 'A'] = 1;
//                System.out.printf("%c < %c\n", lowChar, highChar);
                break;
            }
        }

        for (int i = 0; i < high.length(); i++) {
            Character lowChar = middle.charAt(i);
            Character highChar = high.charAt(i);
            if (lowChar != highChar) {
//                System.out.printf("%c < %c = ?", lowChar, highChar);
//                System.out.println(orderMap[lowChar - 'codejam2018.jutil.BaseSolution'][highChar - 'codejam2018.jutil.BaseSolution'] != 1);
                return orderMap[lowChar - 'A'][highChar - 'A'] != 1;
            }
        }


        return false;
    }

    static String calculate(List<String> x) {
        Set<Character> chars = new HashSet<>();
        for (int i = 0; i < x.size(); i++) {
            String name = x.get(i);
            for (int j = 0; j < name.length(); j++) {
                chars.add(name.charAt(j));
            }
        }

        String[] results = new String[3];
        results[0] = "YES";
        results[1] = "YES";;
        results[2] = "YES";
//        System.out.println(x.toString());

        for (int i = 0; i < x.size(); i++) {
            String low = x.get((i + 2) % 3);
            String middle = x.get(i);
            String high  = x.get((i + 1) % 3);
            String low2  = high;
            String high2 = low;

            if (possibleAlphabet(low, middle, high, chars)) {
                results[i] = "YES";
            } else {
                results[i] = possibleAlphabet(low2, middle, high2, chars) ? "YES" : "NO";
            }


//            System.out.printf("%s %s %s, %s %s %s\n", low, middle, high, low2, middle, high2);
        }


        return String.join(" ", results);
    }

    // read a line from the input, and convert it to a list of integers
    public List<String> readLineToString(Scanner in, Integer num) {

        // the result list
        List<String> res = Arrays.asList(in.nextLine().split("\\s"));

        // return the list
        return res;
    }

    public void run(Scanner in, PrintWriter out) {

        int testCases = Integer.valueOf(in.nextLine());

        for (int i = 0; i < testCases; i++) {

            int x = Integer.valueOf(in.nextLine());
            String result = calculate(readLineToString(in, x));

            out.printf("Case #%d: %s\n", (i + 1), result);

        }
    }

    public static void main(String args[]) throws Exception  {
        Scanner in = new Scanner(new FileInputStream(args[0]));
        PrintWriter out = new PrintWriter(new FileOutputStream(args[0].replace(".in", "_sol.in")));

        new C().run(in, out);

        out.flush();
        out.close();
    }
}
