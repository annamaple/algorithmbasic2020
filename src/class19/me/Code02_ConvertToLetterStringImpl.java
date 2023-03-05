package class19.me;

import class19.Code02_ConvertToLetterString;

public class Code02_ConvertToLetterStringImpl extends Code02_ConvertToLetterString {

    public static void main(String[] args) {
        int N = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            String s = randomString(len);
            int ans0 = number(s);
            int ans1 = new ConvertToLetterString().number(s);
            int ans2 = new ConvertToLetterString().numberDp(s);
            if (ans0 != ans1 || ans0 != ans2) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

    /**
     * 规定1和A对应、2和B对应、3和C对应...26和Z对应
     * 那么一个数字字符串比如"111”就可以转化为:
     * "AAA"、"KA"和"AK"
     * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
     */
    public static class ConvertToLetterString {

        public int number(String s) {
            if (s == null || s.length() == 0) {
                return 0;
            }
            return f(s.toCharArray(), 0);
        }

        public int numberDp(String s) {
            if (s == null || s.length() == 0) {
                return 0;
            }
            char[] str = s.toCharArray();
            int N = str.length;
            int[] dp = new int[N + 1];
            dp[N] = 1;
            for (int i = N - 1; i >= 0; i--) {
                if (str[i] == '0') {
                    dp[i] = 0;
                } else {
                    int p1 = dp[i + 1];
                    int p2 = 0;
                    if (i + 1 < N && ((str[i] - '0') * 10 + (str[i + 1] - '0')) <= 26) {
                        p2 = dp[i + 2];
                    }
                    dp[i] = p1 + p2;
                }
            }
            return dp[0];
        }

        int f(char[] str, int index) {
            if (index == str.length) {
                return 1;
            }
            if (str[index] == '0') {
                return 0;
            }
            int p1 = f(str, index + 1);
            int p2 = 0;
            if (index + 1 < str.length && ((str[index] - '0') * 10 + str[index + 1] - '0') <= 26) {
                p2 = f(str, index + 2);
            }
            return p1 + p2;
        }
    }
}
