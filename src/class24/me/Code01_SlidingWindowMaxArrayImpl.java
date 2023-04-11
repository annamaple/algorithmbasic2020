package class24.me;

import class03.me.MyDeque;
import class24.Code01_SlidingWindowMaxArray;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
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
            int[] ans2 = new SlidingWindowMax().maxSlidWinMyQue(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println(Arrays.toString(arr));
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

        public int[] maxSlidWinMyQue(int[] arr, int K) {
            if (arr == null || arr.length < K || K < 1) return null;
            int N = arr.length;
            int[] ans = new int[N - K + 1];
            int index = 0;
            MyDeque<Integer> winMax = new MyDeque<>(N);
            for (int i = 0; i < N; i++) {
                while (!winMax.isEmpty() && arr[winMax.peekLast()] <= arr[i]) {
                    winMax.pollLast();
                }
                winMax.offerLast(i);
                if (i - K == winMax.peekFirst()) {
                    winMax.pollFirst();
                }
                if (i >= K - 1) {
                    ans[index++] = arr[winMax.peekFirst()];
                }
            }
            return ans;
        }

        /*public static class MyDeque<E> {
            E[] arr;
            int N;
            int size;
            // 元素[tail~head], size == 0 单独讨论
            int head;
            int tail;

            public MyDeque(int capacity) {
                this.N = capacity;
                this.arr = (E[]) new Object[capacity];
                this.size = 0;
                this.head = 0;
                this.tail = 0;
            }

            public E peekFirst() {
                return isEmpty() ? null : arr[head];
            }

            public E peekLast() {
                return isEmpty() ? null : arr[tail];
            }

            public void offerFirst(E e) {
                if (size == N) {
                    throw new RuntimeException("deque filled");
                }
                if (!isEmpty()) {
                    head = nextIndex(head);
                }
                arr[head] = e;
                size++;
            }

            public void offerLast(E e) {
                if (size == N) {
                    throw new RuntimeException("deque filled");
                }
                if (!isEmpty()) {
                    tail = lastIndex(tail);
                }
                arr[tail] = e;
                size++;
            }

            public E pollFirst() {
                if (size == 0) {
                    throw new RuntimeException("deque isEmpty");
                }
                E e = arr[head];
                size--;
                if (!isEmpty()) {
                    head = lastIndex(head);
                }
                return e;
            }

            public E pollLast() {
                if (size == 0) {
                    throw new RuntimeException("deque isEmpty");
                }
                size--;
                E e = arr[tail];
                if (!isEmpty()) {
                    tail = nextIndex(tail);
                }
                return e;
            }

            public boolean isEmpty() {
                return size == 0;
            }

            private int nextIndex(int i) {
                return i + 1 == N ? 0 : i + 1;
            }

            private int lastIndex(int i) {
                return i == 0 ? N - 1 : i - 1;
            }

        }*/


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
