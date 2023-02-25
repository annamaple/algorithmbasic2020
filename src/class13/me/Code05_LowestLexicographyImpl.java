package class13.me;

import class13.Code05_LowestLexicography;

import java.util.Arrays;
import java.util.Comparator;

public class Code05_LowestLexicographyImpl extends Code05_LowestLexicography {

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
//                if (!lowestString1(arr1).equals(lowestString2(arr2))) {
//                    for (String str : arr1) {
//                        System.out.print(str + ",");
//                    }
//                    System.out.println();
//                    System.out.println("Oops!");
//                }
            if (!new LowestLexicography().lowestLexicography(arr1).equals(lowestString2(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    public static class LowestLexicography {

        public String lowestLexicography(String[] args) {
            if (args == null) return null;
            Arrays.sort(args, (o1, o2) -> (o1 + o2).compareTo(o2 + o1));
            StringBuilder ans = new StringBuilder();
            Arrays.stream(args).forEach(ans::append);
            return ans.toString();
        }
    }
}
