package class26.me;

import class26.Code01_SumOfSubarrayMinimums;

public class Code01_SumOfSubarrayMinimumsImpl extends Code01_SumOfSubarrayMinimums {

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 20;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            // int ans1 = subArrayMinSum1(arr);
            int ans1 = new SumOfSubarrayMinimums().sumSubarrayMins(arr);
            int ans2 = subArrayMinSum2(arr);
            int ans3 = sumSubarrayMins(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.printf("第%d次出错了！", i);
                System.out.println();
                break;
            }
        }
        System.out.println("测试结束");
    }

    // 所有子数组最小值之和
    // 给定一个整数数组 arr，找到 min(b)的总和，其中 b 的范围为 arr 的每个（连续）子数组。
    // 由于答案可能很大，因此 返回答案模 10^9 + 7 。
    // 链接：https://leetcode.cn/problems/sum-of-subarray-minimums
    public static class SumOfSubarrayMinimums {

        // [3, 2, 1, 4]
        // min:3 -> [3]
        // min:2 -> [2] [3, 2]
        // min:1 -> [1] [1, 4] [2, 1] [2, 1, 4] [3, 2, 1] [3, 2, 1, 4]
        // mid:4 -> [4]
        // ans = 3 + 4 + 6 + 4 = 17

        //  0  1  2  3
        // [3, 2, 2, 1]
        // min: 3    -> [3]
        // min: 2(1] -> [2] [3, 2]
        // min: 2(2) -> [2] [2, 2] [3, 2, 2]
        // min: 1    -> [1] [2, 1] [2, 2, 1] [3, 2, 2, 1]
        // ans = 3 + 4 + 6 + 4 = 17;
        public int sumSubarrayMins(int[] arr) {
            if (arr == null || arr.length == 0) return 0;
            long MOD = (long) (Math.pow(10, 9) + 7);
            int N = arr.length;
            Stack stack = new Stack(N);
            long sum = 0;
            for (int i = 0; i < N; i++) {
                while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                    int cur = stack.pop();
                    int left = stack.isEmpty() ? -1 : stack.peek();
                    sum += ((long) (cur - left) * (i - cur) * arr[cur]) % MOD;
                }
                stack.push(i);
            }
            while (!stack.isEmpty()) {
                int cur = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                sum += ((long) (cur - left) * (N - cur) * arr[cur]) % MOD;
            }
            return (int) (sum % MOD);
        }

        public static class Stack {
            int[] arr;
            int size;

            public Stack(int limit) {
                this.arr = new int[limit];
                this.size = 0;
            }

            public void push(int val) {
                arr[size++] = val;
            }

            public int pop() {
                return arr[--size];
            }

            public int peek() {
                return arr[size - 1];
            }

            public boolean isEmpty() {
                return size == 0;
            }
        }
    }
}
