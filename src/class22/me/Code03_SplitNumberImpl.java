package class22.me;

import class22.Code03_SplitNumber;

import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Code03_SplitNumberImpl extends Code03_SplitNumber {
    
    public static void main(String[] args) {
        int test = 39;
        System.out.println(ways(test));
        System.out.println(dp1(test));
        System.out.println(dp2(test));
        System.out.println(new SplitNumber().ways(test));
        System.out.println(new SplitNumber().waysDp(test));
        System.out.println(new SplitNumber().waysDpPro(test));
    }

    // 拆分一个数, 使前面的数字小于等于后面的数，有几种方式
    // 如：
    // 4: 1111
    //    112
    //    13
    //    22
    //    4
    public static class SplitNumber {

        int ways(int n) {
            if (n <= 0) return 0;
            return process(1, n);
        }

        int waysDp(int n) {
            if (n <= 0) return 0;
            // pro [1, n / 2]
            // rest [(n + 1) / 2, n]
            int[][] dp = new int[n + 1][n + 1];
            for (int i = 1; i < dp.length; i++) {
                dp[i][i] = 1;
            }
            for (int pro = dp.length - 1; pro >= 1; pro--) {
                for (int rest = pro; rest <= n; rest++) {
                    int ans = 0;
                    for (int nextPro = pro; rest - nextPro >= nextPro; nextPro++) {
                        ans += dp[nextPro][rest - nextPro];
                    }
                    // 加1表示不分割了
                    dp[pro][rest] = ans + 1;
                }
            }
            return dp[1][n];
        }

        int waysDpPro(int n) {
            if (n <= 0) return 0;
            int[][] dp = new int[n + 1][n + 1];
            for (int i = 1; i < dp.length; i++) {
                dp[i][i] = 1;
            }
            for (int pre = dp.length - 2; pre >= 1; pre--) {
                for (int rest = pre + 1; rest <= n; rest++) {
                    // dp[pre][rest] = dp[pre + 1][rest];
                    // dp[pre][rest] += dp[pre][rest - pre];
                    // (2, 6) => (2 4) (3 3) 1
                    // (3, 6) => (3 3) 1
                    dp[pre][rest] = dp[pre + 1][rest];
                    if (rest - pre >= pre) {
                        dp[pre][rest] += dp[pre][rest - pre];
                    }
                }
            }
            return dp[1][n];
        }

        int process(int pro, int rest) {
            if (pro == rest) return 1;
            int ans = 0;
            for (int nextPro = pro; rest - nextPro >= nextPro; nextPro++) {
                ans += process(nextPro, rest - nextPro);
            }
            // 加1表示不分割了
            return ans + 1;
        }
    }
}
