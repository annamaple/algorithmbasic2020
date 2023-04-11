package class24.me;

import class24.Code01_SlidingWindowMaxArray;
import cn.hutool.core.util.StrUtil;

import java.util.Deque;
import java.util.LinkedList;

public class Code01_SlidingWindowMaxArrayImpl extends Code01_SlidingWindowMaxArray {

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = new SlidingWindowMax().maxSlidWindow(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println(StrUtil.format("ans1: {}, ans2: {}", ans1, ans2));
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish");
    }

    public static class SlidingWindowMax {

        public int[] maxSlidWindow(int[] arr, int K) {
            if (arr == null || arr.length < K || K < 1) return null;
            int N = arr.length;
            int[] ans = new int[N - K + 1];
            LinkedList<Integer> winMax = new LinkedList<>();
            for (int i = 0; i < N; i++) {
                while (!winMax.isEmpty() && arr[winMax.peekLast()] <= arr[i]) {
                    winMax.pollLast();
                }
                winMax.offerLast(i);
                if (i - K == winMax.peekFirst()) {
                    winMax.pollFirst();
                }
                // [0, K - 1] -> K
                if (i >= K - 1) {
                    ans[i - K + 1] = arr[winMax.peekFirst()];
                }
            }
            return ans;
        }
        
        
        
        
        


        public int[] maxSlidingWindow(int[] arr, int K) {
            if (arr == null || K < 1 || arr.length < 1) return null;
            int N = arr.length;
            Deque<Integer> qmax = new LinkedList<>();
            int[] ans = new int[N - K + 1];
            int index = 0;
            for (int r = 0; r < N; r++) {
                while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[r]) {
                    qmax.pollLast();
                }
                qmax.offerLast(r);
                // 窗口左边界
                if (qmax.peekFirst() == r - K) {
                    qmax.pollFirst();
                }
                if (r >= K - 1) {
                    ans[index++] = arr[qmax.peekFirst()];
                }
            }
            return ans;
        }
    }
}
