package class25.me;

import class25.Code02_AllTimesMinToMax;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.Stack;

public class Code02_AllTimesMinToMaxImpl extends Code02_AllTimesMinToMax {

    public static void main(String[] args) {
        int testTimes = 2000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            int myAns = new AllTimesMinToMax().maxSumMinProduct(arr);
            int ans = max2(arr);
            if (myAns != ans) {
                System.out.println(StrUtil.format("arr: {}", Arrays.toString(arr)));
                System.out.println(StrUtil.format("myAns: {}, ans: {}", myAns, ans));
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
    }

    public static class AllTimesMinToMax {

        public int maxSumMinProduct(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            int N = arr.length;
            int[] sums = new int[N];
            sums[0] = arr[0];
            for (int i = 1; i < N; i++) {
                sums[i] = sums[i - 1] + arr[i];
            }
            // !!! 记住单调栈中存的是下标
            Stack<Integer> stack = new Stack<>();
            int max = 0;
            // 思路： 每次处理一批已当前值为基准的一批方案，累计起来能够处理完所有方案
            // 每次找以arr中i位置的值为最小值的数组
            for (int i = 0; i < N; i++) {
                while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                    int index = stack.pop();
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    int sum = left == -1 ? sums[i - 1] : sums[i - 1] - sums[left];
                    max = Math.max(max, sum * arr[index]);
                }
                stack.push(i);
            }
            // 处理栈中剩余, 这些值都是右边没有比当前值小的
            // [1, 2, 3, 4]
            while (!stack.isEmpty()) {
                int index = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                int sum = left == -1 ? sums[N - 1] : sums[N - 1] - sums[left];
                max = Math.max(max, sum * arr[index]);
            }
            return max;
        }
    }

}
