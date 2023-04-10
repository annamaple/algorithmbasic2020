package class40.me;

import class40.Code01_LongestSumSubArrayLengthInPositiveArray;


public class Code01_LongestSumSubArrayLengthInPositiveArrayImpl extends Code01_LongestSumSubArrayLengthInPositiveArray {

    // 给定一个正整数组成的无序数组arr，给定一个正整数值K
    // 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
    // 返回其长度
    // 利用单调性O(n)
    public static int getMaxLength(int[] arr, int k) {
        if (arr == null || k < 0) return 0;
        int l = 0, r = 0, ans = 0, sum = 0;
        // 这个代码不好理解, l r
//        for (; r < arr.length; r++) {
//            sum += arr[r];
//            while (sum > k) {
//                sum -= arr[l++];
//            }
//            if (sum == k) {
//                ans = Math.max(ans, r - l + 1);
//            }
//        }
        sum = arr[0];
        while (r < arr.length) {
            if (sum == k) {
                ans = Math.max(ans, r - l + 1);
                sum -= arr[l++];
            } else if (sum < k) {
                r++;
                if (r == arr.length) break;
                sum += arr[r];
            } else {
                sum -= arr[l++];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 500000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generatePositiveArray(len, value);
            int K = (int) (Math.random() * value) + 1;
            int ans1 = getMaxLength(arr, K);
            int ans2 = right(arr, K);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println("K : " + K);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test end");
    }
}
