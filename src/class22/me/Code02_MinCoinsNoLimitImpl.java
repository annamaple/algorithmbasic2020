package class22.me;

import class22.Code02_MinCoinsNoLimit;

public class Code02_MinCoinsNoLimitImpl extends Code02_MinCoinsNoLimit {

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            // int ans1 = minCoins(arr, aim);
            int ans1 = new MinCoinsNoLimit().minCoins(arr, aim);
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

    public static class MinCoinsNoLimit {

        // arr: 面值数组
        // aim： 目标值
        // 每一张面值使用不限量, 返回组合成aim的使用的最少张叔
        public int minCoins(int[] arr, int aim) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            return process(arr, 0, aim);
        }

        // arr: 面值数组
        // index: 当前到了哪一张面值
        // 剩下待组成的面值
        int process(int[] arr, int index, int rest) {
            if (index == arr.length) {
                return rest == 0 ? 0 : Integer.MAX_VALUE;
            }
            int ans = Integer.MAX_VALUE;
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                ans = Math.min(ans, process(arr, index + 1, rest - zhang * arr[index]));
            }
            return ans;
        }
    }
}
