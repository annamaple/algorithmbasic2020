package class28.me;

import class28.Code02_AddShortestEnd;

public class Code02_AddShortestEndImpl extends Code02_AddShortestEnd {

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
        System.out.println(new AddShort().shortestEnd(str1));
    }


    public static class AddShort {

        // 在字符串s后面至少添加哪几个字符使之回文
        public String shortestEnd(String s) {
            if (s == null || s.length() == 0) return "";
            char[] str = manachar(s);
            int N = str.length;
            int[] fArr = new int[N];
            int c = -1, r = -1;
            for (int i = 0; i < N; i++) {
                fArr[i] = r > i ? Math.min(r - i, fArr[2 * c - i]) : 1;
                while (i + fArr[i] < N && i - fArr[i] >= 0 && str[i + fArr[i]] == str[i - fArr[i]]) {
                    fArr[i]++;
                }
                if (i + fArr[i] > r) {
                    r = i + fArr[i];
                    c = i;
                }
                if (r == N) {
                    break;
                }
            }
            char[] chars = s.toCharArray();
            // manachar中只有原始字符可以与原始字符串下标转换
            char[] ans = new char[(c - fArr[c] - 1) / 2 + 1];
            int index = 0;
            for (int i = (c - fArr[c] - 1) / 2; i >= 0; i--) {
                ans[index++] = chars[i];
            }
            return new String(ans);
        }

        private char[] manachar(String s) {
            int N = s.length();
            char[] res = new char[N * 2 + 1];
            int index = 0;
            for (char c : s.toCharArray()) {
                res[index++] = '#';
                res[index++] = c;
            }
            res[index] = '#';
            return res;
        }

    }
}
