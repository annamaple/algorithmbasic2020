package class23.me;

import class23.Code01_SplitSumClosed;


public class Code01_SplitSumClosedImpl extends Code01_SplitSumClosed {

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
//            int ans1 = right(arr);
//            int ans1 = new SplitSumClosed().split(arr);
            int ans1 = new SplitSumClosed().splitDp(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }


    public static class SplitSumClosed {


        // 拆分一个数组中的所有元素为两个数组，使两个数组的和尽量相等，返回较小的和
        public int split(int[] arr) {
            if (arr == null || arr.length < 1) return 0;
            int sum = 0;
            for (int n : arr) {
                sum += n;
            }
            return process(arr, 0, sum / 2);
        }

        // 2 3 5 ....
        // f(2) f(3) f(5)
        // f(0) f(0) f(5)
        public int splitDp(int[] arr) {
            if (arr == null || arr.length == 0) return 0;
            int sum = 0;
            for (int n : arr) sum += n;
            sum /= 2;
            int n = arr.length;
            int[][] dp = new int[n + 1][sum + 1];
            // dp[n][...] = 0;
            for (int index = n - 1; index >= 0; index--) {
                for (int rest = 0; rest <= sum; rest++) {
                    int p1 = dp[index + 1][rest];
                    int p2 = 0;
                    if (arr[index] <= rest) {
                        p2 = arr[index] + dp[index + 1][rest - arr[index]];
                    }
                    dp[index][rest] = Math.max(p1, p2);
                }
            }
            return dp[0][sum];
        }

        // 原arr
        // index 从左到右
        // rest 剩下的值得限制，一开始为sum / 2;
        int process(int[] arr, int index, int rest) {
            if (index == arr.length) return 0;
            int p1 = process(arr, index + 1, rest);
            int p2 = 0;
            if (arr[index] <= rest) {
                p2 = arr[index] + process(arr, index + 1, rest - arr[index]);
            }
            return Math.max(p1, p2);
        }
    }

}
