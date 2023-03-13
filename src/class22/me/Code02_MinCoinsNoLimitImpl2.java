package class22.me;

import class22.Code02_MinCoinsNoLimit;

/**
 * @author lei
 */
public class Code02_MinCoinsNoLimitImpl2 extends Code02_MinCoinsNoLimit {

    // 组成指定金额的最小张数


    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
//            int ans1 = new MinCoins().minCoins(arr, aim);
//            int ans1 = new MinCoins().minCoinsDp(arr, aim);
            int ans1 = new MinCoins().minCoinsDpPro(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");
    }


    public static class MinCoins {

        public int minCoins(int[] arr, int aim) {
            if (aim <= 0) return 0;
            return process(arr, 0, aim);
        }

        public int minCoinsDp(int[] arr, int aim) {
            if (aim <= 0) return 0;
            int N = arr.length;
            int[][] dp = new int[N + 1][aim + 1];
            // dp[N][0] = 0;
            for (int j = 1; j <= aim; j++) {
                dp[N][j] = Integer.MAX_VALUE;
            }
            for (int index = N - 1; index >= 0; index--) {
                for (int rest = 0; rest <= aim; rest++) {
                    int ans = Integer.MAX_VALUE;
                    int next;
                    for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                        if ((next = dp[index + 1][rest - zhang * arr[index]]) != Integer.MAX_VALUE) {
                            ans = Math.min(ans, zhang + next);
                        }
                    }
                    dp[index][rest] = ans;
                }
            }
            return dp[0][aim];
        }

        public int minCoinsDpPro(int[] arr, int aim) {
            if (aim <= 0) return 0;
            int N = arr.length;
            int[][] dp = new int[N + 1][aim + 1];
            for (int rest = 1; rest <= aim; rest++) {
                dp[N][rest] = Integer.MAX_VALUE;
            }
            for (int index = N - 1; index >= 0; index--) {
                for (int rest = 0; rest <= aim; rest++) {
                    dp[index][rest] = dp[index + 1][rest];
                    // 左边第一个为dp[index][rest] 使用一张时为: dp[index][rest - 1 * arr[index]]
                    if (rest - arr[index] >= 0 && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                        dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
                    }
                }
            }
            return dp[0][aim];
        }

        int process(int[] arr, int index, int rest) {
            if (index == arr.length) {
                return rest == 0 ? 0 : Integer.MAX_VALUE;
            }
            int ans = Integer.MAX_VALUE;
            int next;
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                if ((next = process(arr, index + 1, rest - zhang * arr[index])) != Integer.MAX_VALUE) {
                    ans = Math.min(ans, zhang + next);
                }
            }
            return ans;
        }
    }
}
