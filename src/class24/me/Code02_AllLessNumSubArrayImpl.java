package class24.me;

import class24.Code02_AllLessNumSubArray;

import java.util.Deque;
import java.util.LinkedList;


public class Code02_AllLessNumSubArrayImpl extends Code02_AllLessNumSubArray {


    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 1;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
//            int ans1 = right(arr, sum);
            int ans1 = new AllLessNumSubArray().AllLessNumSubArrayCount(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");

    }


    public static class AllLessNumSubArray {

        // 给定一个数组，返回满足子数组其最大值减最小值小于指定值的子数组个数
        public int AllLessNumSubArrayCount(int[] arr, int num) {
            if (arr == null || arr.length == 0) return 0;
            // 有窗口，不回退
            Deque<Integer> maxQ = new LinkedList<>();
            Deque<Integer> minQ = new LinkedList<>();
            int r = 0;
            int count = 0;
            for (int l = 0; l < arr.length; l++) {
                while (r < arr.length) {
                    while (!maxQ.isEmpty() && arr[maxQ.peekLast()] <= arr[r]) {
                        maxQ.pollLast();
                    }
                    maxQ.offerLast(r);
                    while (!minQ.isEmpty() && arr[minQ.peekLast()] >= arr[r]) {
                        minQ.pollLast();
                    }
                    minQ.offerLast(r);
                    if (arr[maxQ.peekFirst()] - arr[minQ.peekFirst()] <= num) {
                        r++;
                    } else {
                        break;
                    }
                }
                // [l, r)
                count += r - l;
                if (maxQ.peekFirst() == l) {
                    maxQ.pollFirst();
                }
                if (minQ.peekFirst() == l) {
                    minQ.pollFirst();
                }
            }
            return count;
        }
    }
}
