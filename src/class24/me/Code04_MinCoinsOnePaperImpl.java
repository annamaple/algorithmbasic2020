package class24.me;

import class24.Code04_MinCoinsOnePaper;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Code04_MinCoinsOnePaperImpl extends Code04_MinCoinsOnePaper {


    // 找零最少张数问题
    public static class MinCoinsOnePaper {

        // 重复纸币不压缩
        int minCoins(int[] arr, int aim) {
            return process(arr, 0, aim);
        }

        int process(int[] arr, int index, int rest) {
            if (index == arr.length) {
                return rest == 0 ? 0 : Integer.MAX_VALUE;
            }
            int p1 = process(arr, index + 1, rest);
            int p2 = Integer.MAX_VALUE;
            if (rest - arr[index] >= 0 &&
                    (p2 = process(arr, index + 1, rest - arr[index])) != Integer.MAX_VALUE) {
                p2 += 1;
            }
            return Math.min(p1, p2);
        }

        int minCoinsDp1(int[] arr, int aim) {
            int N = arr.length;
            int[][] dp = new int[N + 1][aim + 1];
            // dp[N][0] = 0;
            for (int rest = 1; rest <= aim; rest++) {
                dp[N][rest] = Integer.MAX_VALUE;
            }
            for (int index = N - 1; index >= 0; index--) {
                for (int rest = 0; rest <= aim; rest++) {
                    int p1 = dp[index + 1][rest];
                    int p2 = Integer.MAX_VALUE;
                    if (rest - arr[index] >= 0 && (p2 = dp[index + 1][rest - arr[index]]) != Integer.MAX_VALUE) {
                        p2 += 1;
                    }
                    dp[index][rest] = Math.min(p1, p2);
                }
            }
            return dp[0][aim];
        }


        // 重复纸币压缩
        int minCoinsZip(int[] arr, int aim) {
            Info info = getValueAndCount(arr);
            int[] v = info.value;
            int[] c = info.count;
            return precessZip(v, c, 0, aim);
        }

        int minCoinsZipDp(int[] arr, int aim) {
            Info info = getValueAndCount(arr);
            int[] v = info.value;
            int[] c = info.count;
            int N = v.length;
            int[][] dp = new int[N + 1][aim + 1];
            dp[N][0] = 0;
            for (int rest = 1; rest <= aim; rest++) {
                dp[N][rest] = Integer.MAX_VALUE;
            }
            for (int index = N - 1; index >= 0; index--) {
                for (int rest = 0; rest <= aim; rest++) {
                    int ans = Integer.MAX_VALUE;
                    for (int zhang = 0; zhang <= c[index] && rest >= zhang * v[index]; zhang++) {
                        int next = dp[index + 1][rest - zhang * v[index]];
                        if (next != Integer.MAX_VALUE) {
                            ans = Math.min(ans, next + zhang);
                        }
                    }
                    dp[index][rest] = ans;
                }
            }
            return dp[0][aim];
        }

        int minCoinsZipDpPro(int[] arr, int aim) {
            Info info = getValueAndCount(arr);
            int[] v = info.value;
            int[] c = info.count;
            int N = v.length;
            int[][] dp = new int[N + 1][aim + 1];
            for (int rest = 1; rest <= aim; rest++) {
                dp[N][rest] = Integer.MAX_VALUE;
            }
            for (int index = N - 1; index >= 0; index--) {
                // 使用窗口更新结构优化
                for (int mod = 0; mod < v[index]; mod++) {
                    // 窗口存张数
                    Deque<Integer> win = new LinkedList<>();
                    for (int zhang = 0; zhang * v[index] + mod <= aim; zhang++) {
                        while (!win.isEmpty() &&
                                (dp[index + 1][win.peekLast() * v[index] + mod] == Integer.MAX_VALUE ||
                                        dp[index + 1][win.peekLast() * v[index] + mod] + (zhang - win.peekLast()) >= dp[index + 1][zhang * v[index] + mod])) {
                            win.pollLast();
                        }
                        win.offerLast(zhang);
                        // 左
                        // win.peekFirst() == intervalNum - 可用张数 - 1 
                        if (win.peekFirst() < zhang - c[index]) {
                            win.pollFirst();
                        }
                        dp[index][mod + v[index] * zhang] = dp[index + 1][win.peekFirst() * v[index] + mod] + zhang - win.peekFirst();
                    }
                }
            }
            return dp[0][aim];
        }

        int minCoinsZipDpPro2(int[] arr, int aim) {
            Info info = getValueAndCount(arr);
            int[] v = info.value;
            int[] c = info.count;
            int N = v.length;
            int[][] dp = new int[N + 1][aim + 1];
            // dp[N][0] = 0
            for (int rest = 1; rest <= aim; rest++) {
                dp[N][rest] = Integer.MAX_VALUE;
            }
            for (int index = N - 1; index >= 0; index--) {
                // 最开始的所有偏移量
                for (int mod = 0; mod < v[index]; mod++) {
                    // 开始一张不用，直接去下一位置
//                    dp[index][mod] = dp[index + 1][mod];
                    // 存dp中的列下标
                    Deque<Integer> win = new LinkedList<>();
//                    win.offerLast(mod);
                    for (int rest = mod; rest <= aim; rest += v[index]) {
                        while (!win.isEmpty() && (dp[index + 1][win.peekLast()] == Integer.MAX_VALUE ||
                                dp[index + 1][win.peekLast()] + (rest - win.peekLast()) / v[index] >= dp[index + 1][rest])) {
                            win.pollLast();
                        }
                        win.offerLast(rest);
                        // win.peekFirst() < rest - c[index] * v[index]

                        if (win.peekFirst() == rest - v[index] * (c[index] + 1)) {
                            win.pollFirst();
                        }
                        dp[index][rest] = dp[index + 1][win.peekFirst()] + (rest - win.peekFirst()) / v[index];
                    }
                }
            }

            return dp[0][aim];
        }

        int precessZip(int[] v, int[] c, int index, int rest) {
            if (index == v.length) {
                return rest == 0 ? 0 : Integer.MAX_VALUE;
            }
            int ans = Integer.MAX_VALUE;
            for (int zhang = 0; zhang <= c[index] && rest >= zhang * v[index]; zhang++) {
                int next = precessZip(v, c, index + 1, rest - zhang * v[index]);
                if (next != Integer.MAX_VALUE) {
                    ans = Math.min(ans, next + zhang);
                }
            }
            return ans;
        }


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
                // Integer count = map.computeIfAbsent(n, k -> 0);
                int count = 0;
                if (map.containsKey(n)) {
                    count = map.get(n);
                }
                map.put(n, count + 1);
            }
            int[] v = new int[map.size()];
            int[] c = new int[map.size()];
            int index = 0;
            for (Entry<Integer, Integer> entry : map.entrySet()) {
                v[index] = entry.getKey();
                c[index++] = entry.getValue();
            }
            return new Info(v, c);
        }
    }


    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
//            int ans1 = minCoins(arr, aim);
            int ans1 = new MinCoinsOnePaper().minCoinsZipDpPro2(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            int ans4 = dp3(arr, aim);
            if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                return;
            }
        }
        System.out.println("功能测试结束");

        System.out.println("==========");

        int aim = 0;
        int[] arr = null;
        long start;
        long end;
        int ans2;
        int ans3;

        System.out.println("性能测试开始");
        maxLen = 30000;
        maxValue = 20;
        aim = 60000;
        arr = randomArray(maxLen, maxValue);

        start = System.currentTimeMillis();
        ans2 = dp2(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp2答案 : " + ans2 + ", dp2运行时间 : " + (end - start) + " ms");

        start = System.currentTimeMillis();
        ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3答案 : " + ans3 + ", dp3运行时间 : " + (end - start) + " ms");
        System.out.println("性能测试结束");

        System.out.println("===========");

        System.out.println("货币大量重复出现情况下，");
        System.out.println("大数据量测试dp3开始");
        maxLen = 20000000;
        aim = 10000;
        maxValue = 10000;
        arr = randomArray(maxLen, maxValue);
        start = System.currentTimeMillis();
        ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3运行时间 : " + (end - start) + " ms");
        System.out.println("大数据量测试dp3结束");

        System.out.println("===========");

        System.out.println("当货币很少出现重复，dp2比dp3有常数时间优势");
        System.out.println("当货币大量出现重复，dp3时间复杂度明显优于dp2");
        System.out.println("dp3的优化用到了窗口内最小值的更新结构");
    }

}
