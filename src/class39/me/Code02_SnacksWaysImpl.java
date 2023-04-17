package class39.me;

import class39.Code02_SnacksWays;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.TreeMap;

// 牛牛的背包问题
public class Code02_SnacksWaysImpl extends Code02_SnacksWays {

    // 背包容量为w
    // 一共有n袋零食, 第i袋零食体积为v[i]
    // 总体积不超过背包容量的情况下，
    // 一共有多少种零食放法？(总体积为0也算一种放法)。
    
    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue, boolean z) {
        // Math.random()   [0,1)  
        // Math.random() * N  [0,N)
        // (int)(Math.random() * N)  [0, N-1]
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (z) {
                arr[i] = Math.abs(arr[i]);
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int times = 1000;
        while (--times >= 0) {
            int[] arr = generateRandomArray(10, 50, true);
            int sum = 0;
            for (int num : arr) sum += num;
            int w = (int) (sum * Math.random());
//            int my = myWay1(arr, w);
//            int my = myWay2(arr, w);
            int my = myWay3(arr, w);
            int ans = ways2(arr, w);
            if (my != ans) {
                System.out.println(Arrays.toString(arr));
                System.out.println(w);
                System.out.println(StrUtil.format("my: {}, ans: {}", my, ans));
                System.out.println("fuck");
                myWay3(arr, w);
            }
        }
        System.out.println("nice");
    }

    
    public static int myWay1(int[] arr, int w) {
        // 经典背包解法, 左到右 
        return process(arr, 0, w);
    }

    // arr[index, N - 1]剩余rest空间有多少放法
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            // 做完所有决策了算一种 
            return 1;
        }
        int p1 = process(arr, index + 1, rest);
        int p2 = 0;
        if (rest - arr[index] >= 0) {
            p2 = process(arr, index + 1, rest - arr[index]);
        }
        return p1 + p2;
    }

    public static int dp1(int[] arr, int w) {
        int N = arr.length;
        int[][] dp = new int[N + 1][w + 1];
        for (int rest = 0; rest <= w; rest++) {
            dp[N][rest] = 1;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= w; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = 0;
                if (rest - arr[index] >= 0) {
                    p2 = dp[index + 1][rest - arr[index]];
                }
                dp[index][rest] = p1 + p2;
            }
        }
        return dp[0][w];
    }

    // 注意：考虑arr[i] = 0的情况。
    // 计算严格使用[0~w]容量，可以装的方式，所有方式之和就是答案
    public static int myWay2(int[] arr, int w) {
        if (arr == null || arr.length == 0) return 1;
        // 计算严格使用[0~w]容量，可以装的方式，所有方式之和就是答案
        int N = arr.length;
        int[][] dp = new int[N][w + 1];
        // 这个不是baseCase: 忽略了arr[i]可能为0的情况 
        /*for (int i = 0; i < N; i++) {
            dp[i][0] = 1;
        }*/
        // baseCase: arr[0~0]只有两总情况
        dp[0][0] = 1;
        if (arr[0] <= w) {
            // 第一个时，只有dp[0][0]与dp[0][arr[0]] = 1;
            dp[0][arr[0]] += 1;
        }
        // dp[i][j]: arr[0, i]严格使用j的空间的方式数
        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= w; j++) {
                int p1 = dp[i - 1][j];
                int p2 = 0;
                if (j - arr[i] >= 0) {
                    p2 = dp[i - 1][j - arr[i]];
                }
                dp[i][j] = p1 + p2;
            }
        }
        int ans = 0;
        for (int j = 0; j <= w; j++) {
            ans += dp[N - 1][j];
        }
        return ans;
    }

    // 分治 arr.length <= 30
    public static int myWay3(int[] arr, int w) {
        int N = arr.length;
        if (N == 1) return w >= arr[0] ? 2 : 1;
        // 1. 枚举左右来两边的答案
        int mid = (N - 1) >> 1;
        TreeMap<Long, Long> sortMap1 = new TreeMap<>();
        int ans = 0;
        ans += process3(arr, 0, mid, 0, false, w, sortMap1);
        TreeMap<Long, Long> sortMap2 = new TreeMap<>();
        ans += process3(arr, mid + 1, N - 1, 0, false, w, sortMap2);
        // 2. 整合整体的答案
        // sortMap2转为前缀和
        long pre = 0;
        for (Long i : sortMap2.keySet()) {
            sortMap2.put(i, pre + sortMap2.get(i));
            pre = sortMap2.get(i);
        }
        for (long key : sortMap1.keySet()) {
            Long floorKey = sortMap2.floorKey(w - key);
            if (floorKey != null) {
                // 注意此处相乘
                ans += sortMap1.get(key) * sortMap2.get(floorKey);
            }
        }
        return ans + 1;
    }

    // 枚举arr[i~end]所有可能的方案的累加和,
    // 去掉什么都不选的情况
    public static int process3(int[] arr, int i, int end, long sum, boolean choose, int w, TreeMap<Long, Long> sortMap) {
        if (i == end + 1) {
            if (choose) {
                sortMap.put(sum, sortMap.getOrDefault(sum, 0L) + 1);
            }
            return choose ? 1 : 0;
        }
        int p1 = process3(arr, i + 1, end, sum, choose, w, sortMap);
        int p2 = 0;
        if (w - (sum + arr[i]) >= 0) {
            p2 = process3(arr, i + 1, end, sum + arr[i], true, w, sortMap);
        }
        return p1 + p2;
    }


}
