package class23.me;

import class23.Code02_SplitSumClosedSizeHalf;

public class Code02_SplitSumClosedSizeHalfImpl extends Code02_SplitSumClosedSizeHalf {

    // for test
    public static void main(String[] args) {
        new SplitSumClosedSizeHalf().split(new int[]{26, 11, 13, 9});
        int maxLen = 10;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
//            int ans1 = right(arr);
//            int ans1 = new SplitSumClosedSizeHalf().split(arr);
            int ans1 = new SplitSumClosedSizeHalf().splitDp(arr);
            int ans2 = dp(arr);
            int ans3 = dp2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }


    // 拆分一个数组为两个数组，原数组元素个数为偶数则拆分后的两个数组元素个数相等，奇数则相差一，且使两个数组的和相差最小，返回较小的和
    private static class SplitSumClosedSizeHalf {

        public int split(int[] arr) {
            if (arr == null || arr.length < 1) return 0;
            int sum = 0;
            int n = arr.length;
            for (int num : arr) sum += num;
            sum /= 2;
            if ((arr.length & 1) == 1) {
                return Math.max(process(arr, 0, n / 2, sum), process(arr, 0, (n + 1) / 2, sum));
            } else {
                return process(arr, 0, n / 2, sum);
            }
        }

        public int splitDp(int[] arr) {
            if (arr == null || arr.length < 1) return 0;
            int sum = 0;
            for (int num : arr) sum += num;
            sum /= 2;
            int N = arr.length;
            int M = (N + 1) / 2;
            int[][][] dp = new int[N + 1][M + 1][sum + 1];
            for (int rest = 0; rest <= sum; rest++) {
                // dp[N][0][...] = 0
                for (int count = 1; count <= M; count++) {
                    dp[N][count][rest] = -1;
                }
            }
            for (int index = N - 1; index >= 0; index--) {
                for (int count = 0; count <= M; count++) {
                    for (int rest = 0; rest <= sum; rest++) {
                        int p1 = dp[index + 1][count][rest];
                        int p2 = -1;
                        if (rest >= arr[index] && count > 0) {
                            p2 = dp[index + 1][count - 1][rest - arr[index]];
                            if (p2 != -1) {
                                // 有效
                                p2 += arr[index];
                            }
                        }
                        dp[index][count][rest] = Math.max(p1, p2);
                    }
                }
            }
            return (N & 1) == 1 ? Math.max(dp[0][N / 2][sum], dp[0][(N + 1) / 2][sum]) : dp[0][N / 2][sum];
        }

        // 从arr的index位置出发，取count个数，返回取的数之和最接近rest的和
        // 原arr
        // 从左到右
        // 需要使用的数量
        // 剩下的值
        int process(int[] arr, int index, int count, int rest) {
            if (index == arr.length) {
                // -1 表示当前方式无效
                return count == 0 ? 0 : -1;
            }
            int p1 = process(arr, index + 1, count, rest);
            int p2 = -1;
            if (rest >= arr[index] && count > 0) {
                p2 = process(arr, index + 1, count - 1, rest - arr[index]);
                if (p2 != -1) {
                    // 有效
                    p2 += arr[index];
                }
            }
            return Math.max(p1, p2);
        }
    }
}
