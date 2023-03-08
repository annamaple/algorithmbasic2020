package class21.me;

import class21.Code02_CoinsWayEveryPaperDifferent;

/**
 * 每个就一张
 * @author lei
 */
public class Code02_CoinsWayEveryPaperDifferentImpl extends Code02_CoinsWayEveryPaperDifferent {

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
//             int ans1 = coinWays(arr, aim);
            int ans1 = new CoinsWayEveryPaperDifferent().coinWaysDpBest(arr, aim);
            int ans2 = dp(arr, aim);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }
    

    public static class CoinsWayEveryPaperDifferent {

        public int coinWays(int[] arr, int aim) {
            return process(arr, 0, aim);
        }
        
        public int coinWaysDp(int[] arr, int aim) {
            int row = arr.length + 1;
            int col = aim + 1;
            int[][] dp = new int[row][col];
            dp[row - 1][0] = 1;
            for (int index = row - 2; index >= 0; index--) {
                for (int rest = 0; rest < col; rest++) {
                    int p1 = dp[index + 1][rest];
                    int minus;
                    int p2 = (minus = rest - arr[index]) >= 0 ? dp[index + 1][minus] : 0;
                    dp[index][rest] = p1 + p2;
                }
            }
            return dp[0][aim];
        }
        
        public int coinWaysDpBest(int[] arr, int aim) {
            int row = arr.length + 1;
            int col = aim + 1;
            // 路径压缩 
            int[] dp = new int[col];
            // 由下往上 row - 1
            dp[0] = 1;
            for (int index = row - 2; index >= 0 ; index--) {
                for (int rest = col - 1; rest >= 0; rest--) {
                    int p1 = dp[rest];
                    int minus;
                    int p2 = (minus = rest - arr[index]) >= 0 ? dp[minus] : 0;
                    dp[rest] = p1 + p2;
                }
            }
            return dp[aim];
        }

        int process(int[] arr, int index, int rest) {
            if (index == arr.length) {
                return rest == 0 ? 1 : 0;
            }
            int p1 = process(arr, index + 1, rest);
            int minus;
            int p2 = (minus = rest - arr[index]) >= 0 ? process(arr, index + 1, minus) : 0;
            return p1 + p2;
        }
    }
}
