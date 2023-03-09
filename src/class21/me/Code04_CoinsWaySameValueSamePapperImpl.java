package class21.me;

import class21.Code04_CoinsWaySameValueSamePapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author lei
 */
public class Code04_CoinsWaySameValueSamePapperImpl extends Code04_CoinsWaySameValueSamePapper {

    // 为了测试
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
//            int ans1 = new CoinsWaySameValueSamePaper().coinsWay(arr, aim);
//            int ans1 = new CoinsWaySameValueSamePaper().coinsWayDp(arr, aim);
//            int ans1 = new CoinsWaySameValueSamePaper().coinsWayDpPro(arr, aim);
            int ans1 = new CoinsWaySameValueSamePaper().coinsWayDpBest(arr, aim);
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


    public static class CoinsWaySameValueSamePaper {

        private static class Info {
            int[] value;
            int[] count;

            public Info(int[] value, int[] count) {
                this.value = value;
                this.count = count;
            }
        }

        private Info getValueAndCount(int[] arr) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int n : arr) {
                Integer count = map.computeIfAbsent(n, k -> 0);
                map.put(n, count + 1);
            }
            int[] value = new int[map.size()];
            int[] count = new int[map.size()];
            int index = 0;
            for (Entry<Integer, Integer> entry : map.entrySet()) {
                value[index] = entry.getKey();
                count[index++] = entry.getValue();
            }
            return new Info(value, count);
        }

        // arr 值 有重复：如[2, 2, 3, 1, 2]
        // aim 目标值
        // 请从arr中挑选来组成aim, 返回所有不重复方法数
        // 例： aim: 4 有[1, 3] [2,2] 两种
        public int coinsWay(int[] arr, int aim) {
            if (arr == null || arr.length == 0) return 0;
            Info info = getValueAndCount(arr);
            int[] v = info.value;
            int[] c = info.count;
            return process(v, c, 0, aim);
        }

        // arr 值 有重复：如[2, 2, 3, 1, 2]
        // aim 目标值
        // 请从arr中挑选来组成aim, 返回所有不重复方法数
        // 例： aim: 4 有[1, 3] [2,2] 两种
        public int coinsWayDp(int[] arr, int aim) {
            if (arr == null || arr.length == 0) return 0;
            Info info = getValueAndCount(arr);
            int[] v = info.value;
            int[] c = info.count;
            int row = v.length + 1;
            int col = aim + 1;
            int[][] dp = new int[row][col];
            dp[v.length][0] = 1;
            for (int index = row - 2; index >= 0; index--) {
                for (int rest = 0; rest <= aim; rest++) {
                    int way = 0;
                    for (int zhang = 0; zhang <= c[index] && rest - zhang * v[index] >= 0; zhang++) {
                        way += dp[index + 1][rest - zhang * v[index]];
                    }
                    dp[index][rest] = way;
                }
            }

            return dp[0][aim];
        }

        // arr 值 有重复：如[2, 2, 3, 1, 2]
        // aim 目标值
        // 请从arr中挑选来组成aim, 返回所有不重复方法数
        // 例： aim: 4 有[1, 3] [2,2] 两种
        public int coinsWayDpPro(int[] arr, int aim) {
            if (arr == null || arr.length == 0) return 0;
            Info info = getValueAndCount(arr);
            int[] v = info.value;
            int[] c = info.count;
            int row = v.length + 1;
            int col = aim + 1;
            int[][] dp = new int[row][col];
            dp[v.length][0] = 1;
            for (int index = row - 2; index >= 0; index--) {
                // 每一行 v[index] = v; c[index] = z确定 
                // z = 2, 即：
                // dp[a][r] ==> dp[a - 1][r] + dp[a - 1][r - v] + dp[a - 1][r - 2v]
                // dp[a][r + v] ==> dp[a - 1][r + v] + dp[a - 1][r] + dp[a - 1][r - v]
                //                = dp[a - 1][r + v] + dp[a][r] - dp[a - 1][r - 2v]
                for (int rest = 0; rest <= aim; rest++) {
                    int way = dp[index + 1][rest];
                    // 存在左边
                    if (rest - v[index] >= 0) {
                        way += dp[index][rest - v[index]];
                    }
                    // 张数是否用完
                    if (rest - v[index] * (c[index] + 1) >= 0) {
                        way -= dp[index + 1][rest - v[index] * (c[index] + 1)];
                    }
                    dp[index][rest] = way;
                }
            }

            return dp[0][aim];
        }

        public int coinsWayDpBest(int[] arr, int aim) {
            if (arr == null || arr.length == 0) return 0;
            Info info = getValueAndCount(arr);
            int[] v = info.value;
            int[] c = info.count;
            int row = v.length + 1;
            int col = aim + 1;
            // 从最后一行到第一行
            int[] dp = new int[col];
            int[] doneDp = new int[col];
            // dp[v.length][0] = 1
            doneDp[0] = 1;
            int[] temp;
            for (int index = row - 2; index >= 0; index--) {
                for (int rest = 0; rest <= aim; rest++) {
                    int way = doneDp[rest];
                    if (rest - v[index] >= 0) {
                        way += dp[rest - v[index]];
                    }
                    if (rest - v[index] * (c[index] + 1) >= 0) {
                        way -= doneDp[rest - v[index] * (c[index] + 1)];
                    }
                    dp[rest] = way;
                }
                temp = doneDp;
                doneDp = dp;
                dp = temp;
            }
            return doneDp[aim];
        }

        int process(int[] v, int[] c, int index, int rest) {
            if (index == v.length) {
                return rest == 0 ? 1 : 0;
            }
            // int value = v[index];
            // int times = c[index];
            int way = 0;
            for (int zhang = 0; zhang <= c[index] && rest - zhang * v[index] >= 0; zhang++) {
                way += process(v, c, index + 1, rest - zhang * v[index]);
            }
            return way;
        }

    }

}
