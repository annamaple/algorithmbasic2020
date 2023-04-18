package class40.me;

import class40.Code04_AvgLessEqualValueLongestSubarray;

import java.util.TreeMap;

public class Code04_AvgLessEqualValueLongestSubarrayImpl extends Code04_AvgLessEqualValueLongestSubarray {

    // 用于测试
    public static void main(String[] args) {
        System.out.println("测试开始");
        int maxLen = 20;
        int maxValue = 100;
        int testTime = 500000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int value = (int) (Math.random() * maxValue);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            int ans1 = ways1(arr1, value);
            int ans2 = ways2(arr2, value);
            int ans3 = solute(arr3, value);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("测试出错！");
                System.out.print("测试数组：");
                printArray(arr);
                System.out.println("子数组平均值不小于 ：" + value);
                System.out.println("方法1得到的最大长度：" + ans1);
                System.out.println("方法2得到的最大长度：" + ans2);
                System.out.println("方法3得到的最大长度：" + ans3);
                System.out.println("=========================");
            }
        }
        System.out.println("测试结束");
    }

    // ONlogN
    public static int solute2(int[] nums, int k) {
        // TODO 有序表: 待理解
        return -1;
    }

    // O(N)
    // 给定一个整型数组arr，和一个整数K，求平均值小于等于K的所有子数组中，最大长度是多少
    // 数据规模: 1000
    public static int solute(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;
        // 所有数减少k转为求子数组累加和小于等于0的最大长度
        int N = nums.length;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = nums[i] - k;
        }
        return MaxLengthAwesome.maxLen(arr, 0);
    }

    public static class MaxLengthAwesome {

        // 背过
        public static int maxLen(int[] arr, int k) {
            int N = arr.length;
            int[] minSum = new int[N];
            int[] minSumEnd = new int[N];
            minSum[N - 1] = arr[N - 1];
            minSumEnd[N - 1] = N - 1;
            for (int i = N - 2; i >= 0; i--) {
                if (minSum[i + 1] >= 0) {
                    minSum[i] = arr[i];
                    minSumEnd[i] = i;
                } else {
                    minSum[i] = arr[i] + minSum[i + 1];
                    minSumEnd[i] = minSumEnd[i + 1];
                }
            }
            int r = 0, sum = 0, ans = 0;
            for (int i = 0; i < N; i++) {
                while (r < N && sum + minSum[r] <= k) {
                    sum += minSum[r];
                    r = minSumEnd[r] + 1;
                }
                ans = Math.max(ans, r - i);
                if (r > i) {
                    sum -= arr[i];
                } else {
                    r++;
                }
            }
            return ans;
        }
    }

    private static void buildMinInfo(int[] arr, int[] minSum, int[] minSumEnd) {
        int N = arr.length;
        minSum[N - 1] = arr[N - 1];
        minSumEnd[N - 1] = N - 1;
        for (int i = N - 2; i >= 0; i--) {
            minSum[i] = minSum[i + 1] > 0 ? arr[i] : minSum[i + 1] + arr[i];
            minSumEnd[i] = minSum[i] == arr[i] ? i : minSumEnd[i + 1];
        }
    }

}
