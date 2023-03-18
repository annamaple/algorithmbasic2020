package class24.me;

import class24.Code01_SlidingWindowMaxArray;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class Code01_SlidingWindowMaxArrayImpl extends Code01_SlidingWindowMaxArray {

    public static void main(String[] args) {
        int[] right = getMaxWindow(new int[]{1, -1}, 1);
        System.out.println(Arrays.toString(right));

        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = right(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

    public static class SlidingWindowMax {

        public int[] maxSlidingWindow(int[] nums, int K) {
            if (nums == null || K < 1 || nums.length < 1) return null;
            int N = nums.length;
            Deque<Integer> qmax = new LinkedList<>();
            int[] ans = new int[N - K + 1];
            int index = 0;
            for (int R = 0; R < N; R++) {
                while (!qmax.isEmpty() && qmax.peekLast() <= nums[R]) {
                    qmax.pollLast();
                }
                qmax.offerLast(nums[R]);
                if (qmax.peekFirst() == R - K) {
                    qmax.pollFirst();
                }
                if (R >= K - 1) {
                    ans[index++] = qmax.peekFirst();
                }
            }
            return ans;
        }
    }
}
