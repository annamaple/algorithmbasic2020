package class21.me;

import class21.Code03_CoinsWayNoLimit;

public class Code03_CoinsWayNoLimitImpl extends Code03_CoinsWayNoLimit {


    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            // int ans1 = coinsWay(arr, aim);
            int ans1 = new CoinsWayNoLimit().coinsWayDpBestPro(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }


    public static class CoinsWayNoLimit {

        public int coinsWay(int[] arr, int aim) {
            if (arr == null || arr.length == 0) return 0;
            return process(arr, 0, aim);
        }

        public int coinsWayDp(int[] arr, int aim) {
            if (arr == null || arr.length == 0) return 0;
            int row = arr.length + 1;
            int col = aim + 1;
            int[][] dp = new int[row][col];
            dp[arr.length][0] = 1;
            for (int index = arr.length - 1; index >= 0; index--) {
                for (int rest = aim; rest >= 0; rest--) {
                    int way = 0;
                    int minus;
                    for (int zhang = 0; (minus = rest - arr[index] * zhang) >= 0; zhang++) {
                        way += dp[index + 1][minus];
                    }
                    dp[index][rest] = way;
                }
            }
            return dp[0][aim];
        }

        public int coinsWayDpBest(int[] arr, int aim) {
            if (arr == null || arr.length == 0) return 0;
            int row = arr.length + 1;
            int col = aim + 1;
            int[][] dp = new int[row][col];
            dp[arr.length][0] = 1;
            for (int index = arr.length - 1; index >= 0; index--) {
                for (int rest = 0; rest <= aim; rest++) {
                    int way = dp[index + 1][rest];
                    if (rest - arr[index] >= 0) {
                        way += dp[index][rest - arr[index]];
                    }
                    dp[index][rest] = way;
                }
            }
            return dp[0][aim];
        }

        public int coinsWayDpBestPro(int[] arr, int aim) {
            if (arr == null || arr.length == 0) return 0;
            int row = arr.length + 1;
            int col = aim + 1;
            // 行从row - 1到0向上
            int[] dp = new int[col];
            dp[0] = 1;
            for (int index = arr.length - 1; index >= 0; index--) {
                for (int rest = aim; rest >= 0; rest--) {
                    int way = dp[rest];
                    if (rest - arr[index] >= 0) {
                        way += dp[rest - arr[index]];
                    }
                    dp[rest] = way;
                }
            }
            return dp[aim];
        }

        int process(int[] arr, int index, int rest) {
            if (index == arr.length) {
                return rest == 0 ? 1 : 0;
            }
            int way = 0;
            int minus;
            for (int zhang = 0; (minus = rest - arr[index] * zhang) >= 0; zhang++) {
                way += process(arr, index + 1, minus);
            }
            return way;
        }
    }
}
