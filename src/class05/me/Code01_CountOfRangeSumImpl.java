package class05.me;

import class05.Code01_CountOfRangeSum;

/**
 * @author lei
 */
public class Code01_CountOfRangeSumImpl extends Code01_CountOfRangeSum {

    public static void main(String[] args) {
        int[] nums = {-2, 5, -1};
        int lower = -2;
        int upper = 2;
        // 3 [-2 2]
        // sum[-2, 3, 2]
        // -2 2 (2 - 3)
        System.out.println(new CountOfRangeSum().countRangeSum(nums, lower, upper));
    }

    public static class CountOfRangeSum {

        public int countRangeSum(int[] nums, int lower, int upper) {
            if (nums == null || nums.length == 0) return 0;
            // 转为前缀和数组, 使用long考虑到前缀和整型溢出
            long[] sum = new long[nums.length];
            sum[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                sum[i] = sum[i - 1] + nums[i];
            }
            return process(sum, 0, sum.length - 1, lower, upper);
        }

        int process(long[] sum, int left, int right, int lower, int upper) {
            if (left == right) {
                return sum[left] >= lower && sum[left] <= upper ? 1 : 0;
            }
            int mid = left + ((right - left) >> 1);
            int leftPart = process(sum, left, mid, lower, upper);
            int rightPart = process(sum, mid + 1, right, lower, upper);
            int curPart = merge(sum, left, mid, right, lower, upper);
            return leftPart + rightPart + curPart;
        }

        // sum:  1 3 5 6 | 2 4 5 7
        // windowL         p
        // windowR
        int merge(long[] sum, int left, int mid, int right, int lower, int upper) {
            int windowL = left;
            int windowR = left;
            int ans = 0;
            for (int p = mid + 1; p <= right; p++) {
                // [sum[p] - upper, sum[p] - lower]
                long min = sum[p] - upper;
                long max = sum[p] - lower;
                // 窗口
                while (windowL <= mid && sum[windowL] < min) {
                    windowL++;
                }
                while (windowR <= mid && sum[windowR] <= max) {
                    windowR++;
                }
                ans += windowR - windowL;
            }
            // merge
            int p1 = left;
            int p2 = mid + 1;
            long[] help = new long[right - left + 1];
            int i = 0;
            while (p1 <= mid && p2 <= right) {
                help[i++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
            }
            while (p1 <= mid) {
                help[i++] = sum[p1++];
            }
            while (p2 <= right) {
                help[i++] = sum[p2++];
            }
            while (--i >= 0) {
                sum[left + i] = help[i];
            }
            return ans;
        }
    }
}
