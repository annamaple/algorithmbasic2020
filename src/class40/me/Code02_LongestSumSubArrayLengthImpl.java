package class40.me;

import class40.Code02_LongestSumSubArrayLength;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lei
 */
public class Code02_LongestSumSubArrayLengthImpl extends Code02_LongestSumSubArrayLength {

    // 给定一个整数组成的无序数组arr，值可能正、可能负、可能0
    // 给定一个整数值K
    // 找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
    // 返回其长度
    public static int maxLength(int[] arr, int k) {
        // 前缀和 + 辅助结构
        if (arr == null) return 0;
        int N = arr.length;
        // K: 累计和 V: 对于arr最早下标
        Map<Integer, Integer> map = new HashMap<>();
        // 精髓
        map.put(0, -1);
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            // arr[0~i]累加和
            sum += arr[i];
            if (map.containsKey(sum - k)) {
                // [map.get(sum - k) + 1, i]
                ans = Math.max(ans, i - map.get(sum - k));
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
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
            int[] arr = generateRandomArray(len, value);
            int K = (int) (Math.random() * value) - (int) (Math.random() * value);
            int ans1 = maxLength(arr, K);
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
