package class40.me;

import class40.Code03_LongestLessSumSubArrayLength;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

public class Code03_LongestLessSumSubArrayLengthImpl extends Code03_LongestLessSumSubArrayLength {

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 10000000; i++) {
            int[] arr = generateRandomArray(10, 20);
            int k = (int) (Math.random() * 20) - 5;
            int my, ans;
            if ((my = maxLengthAwesome(arr, k)) != (ans = maxLength(arr, k))) {
                System.out.println(Arrays.toString(arr));
                System.out.println(k);
                System.out.println(StrUtil.format("my: {}, ans: {}", my, ans));
                System.out.println("Oops!");
                return;
            }
        }
        System.out.println("test finish");
    }

    // 给定一个整数组成的无序数组arr，值可能正、可能负、可能0
    // 给定一个整数值K
    // 找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
    // 返回其长度
    public static int maxLengthAwesome(int[] arr, int k) {
        if (arr == null || arr.length == 0) return 0;
        int N = arr.length;
        // minSum[i]: arr中从下标i开始的子数组最小的累加和的值
        // minSumEnd[i]: arr中从下标i开始的子数组取得最小累加和的下标结尾位置(值相同取长的)
        int[] minSum = new int[N];
        int[] minSumEnd = new int[N];
        minSum[N - 1] = arr[N - 1];
        minSumEnd[N - 1] = N - 1;
        for (int i = N - 2; i >= 0; i--) {
            if (minSum[i + 1] > 0) {
                minSum[i] = arr[i];
                minSumEnd[i] = i;
            } else {
                minSum[i] = arr[i] + minSum[i + 1];
                minSumEnd[i] = minSumEnd[i + 1];
            }
        }
        // 扩不进来的哪一块的开头位置
        int end = 0;
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            // while循环结束之后：
            // 1) 如果以i开头的情况下，累加和<=k的最长子数组是arr[i..end-1]，看看这个子数组长度能不能更新res；
            // 2) 如果以i开头的情况下，累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新res都不会影响最终结果；
            while (end < N && sum + minSum[end] <= k) {
                sum += minSum[end];
                end = minSumEnd[end] + 1;
            }
            ans = Math.max(ans, end - i);
            if (end > i) { // 还有窗口，哪怕窗口没有数字 [i~end) [4,4)
                sum -= arr[i];
            } else { // i == end,  即将 i++, i > end, 此时窗口概念维持不住了，所以end跟着i一起走
                end++;
            }
        }

        return ans;
    }


}
