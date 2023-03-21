package class27.me;

import class27.Code01_KMP;


public class Code01_KMPImpl extends Code01_KMP {

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (new KMP().indexOf(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("nice");
    }

    public static class KMP {

        // 返回字符串b再a中的位置，无返回-1
        public int indexOf(String a, String b) {
            if (a == null || b == null || a.length() == 0 || b.length() == 0 || a.length() < b.length()) {
                return -1;
            }
            int M = a.length(), N = b.length();
            char[] str1 = a.toCharArray();
            char[] str2 = b.toCharArray();
            int[] nextArr = getNextArray(str2);
            int p1 = 0, p2 = 0;
            while (p1 < M && p2 < N) {
                if (str1[p1] == str2[p2]) {
                    p1++;
                    p2++;
                } else if (/*nextArr[p2] != -1*/ p2 != 0) { // p2 != 0
                    p2 = nextArr[p2];
                } else {
                    p1++;
                }
            }
            // kmp
            return p2 == N ? p1 - p2 : -1;
        }

        public int[] getNextArray(char[] str) {
            if (str.length == 1) {
                return new int[]{-1};
            }
            int N = str.length;
            int[] res = new int[N];
            res[0] = -1;
            res[1] = 0;
            int i = 2, cn = 0;
            // 1123
            while (i < N) {
                if (str[i - 1] == str[cn]) {
                    res[i++] = ++cn;
                } else if (/*res[cn] != -1*/cn != 0) { // cn != 0
                    cn = res[cn];
                } else {
                    // res[i++] = 0;
                    i++;
                }
            }
            return res;
        }

    }
}
