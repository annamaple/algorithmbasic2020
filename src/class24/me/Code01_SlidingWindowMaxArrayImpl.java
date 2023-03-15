package class24.me;

import class24.Code01_SlidingWindowMaxArray;

import java.util.Deque;
import java.util.LinkedList;

// leetcode 239
public class Code01_SlidingWindowMaxArrayImpl extends Code01_SlidingWindowMaxArray {

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
//            int[] ans1 = getMaxWindow(arr, w);
            int[] ans1 = new SlidingWindowMaxArray().slidingWindowMax(arr, w);
            int[] ans2 = right(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
                return;
            }
        }
        System.out.println("test finish");
    }

    // 一个数组， 一个窗口w, 返回窗口的最大值
    public static class SlidingWindowMaxArray {

        int[] slidingWindowMax(int[] arr, int w) {
            if (arr == null || arr.length == 0 || w < 1) return null;
            int N = arr.length;
            int[] ans = new int[N - w + 1];
            int index = 0;
            Deque<Integer> maxQ = new LinkedList<>();
            for (int r = 0; r < N; r++) {
                // 加
                while (!maxQ.isEmpty() && arr[maxQ.peekLast()] <= arr[r])
                    maxQ.pollLast();
                maxQ.offerLast(r);
                // 减
                if (r - w == maxQ.peekFirst()) {
                    maxQ.pollFirst();
                }
                // 取值
                if (r >= w - 1) {
                    ans[index++] = arr[maxQ.peekFirst()];
                }
            }
            return ans;
        }
    }

}
