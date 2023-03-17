package class25.me;

import class25.Code01_MonotonousStack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Code01_MonotonousStackImpl extends Code01_MonotonousStack {

    public static void main(String[] args) {
//         int[] arr = {1, 2, 0, 9, 8};
//         printArray(new MonotonousStack().getNearLess(arr));
        int size = 10;
        int max = 20;
        int testTimes = 2000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(new MonotonousStack().getNearLess(arr1), rightWay(arr1))) {
                System.out.println("Oops!");
                printArray(arr1);
                break;
            }
            if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
                System.out.println("Oops!");
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");

    }


    // 给定数组arr, 返回数组中每个元素左右边比其小的第一个值的位置值，返回二维数组
    // [1, 2, 0, 9, 8]
    //  0  1  2  3  4
    // [[-1, 2],[0, 2],[-1, -1],[2, 4],[2, -1]]
    public static class MonotonousStack {

        // arr = [ 3, 1, 2, 3]
        //         0  1  2  3
        //  [
        //     0 : [-1,  1]
        //     1 : [-1, -1]
        //     2 : [ 1, -1]
        //     3 : [ 2, -1]
        //  ]
        int[][] getNearLessNoRepeat(int[] arr) {
            if (arr == null || arr.length == 0) return null;
            int N = arr.length;
            int[][] ans = new int[N][2];
            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < N; i++) {
                while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                    int index = stack.pop();
                    ans[index][0] = stack.isEmpty() ? -1 : stack.peek();
                    ans[index][1] = i;
                }
                stack.push(i);
            }
            while (!stack.isEmpty()) {
                int index = stack.pop();
                ans[index][0] = stack.isEmpty() ? -1 : stack.peek();
                ans[index][1] = -1;
            }
            return ans;
        }


        int[][] getNearLess(int[] arr) {
            if (arr == null || arr.length == 0) return null;
            int N = arr.length;
            int[][] ans = new int[N][2];
            Stack<List<Integer>> stack = new Stack<>();
            for (int i = 0; i < N; i++) {
                while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                    List<Integer> indexList = stack.pop();
                    for (Integer index : indexList) {
                        ans[index][0] = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                        ans[index][1] = i;
                    }
                }
                if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                    stack.peek().add(i);
                } else {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    stack.push(list);
                }
            }
            while (!stack.isEmpty()) {
                List<Integer> indexList = stack.pop();
                for (Integer index : indexList) {
                    ans[index][0] = stack.isEmpty() ? -1 : stack.peek().get(0);
                    ans[index][1] = -1;
                }
            }
            return ans;
        }
    }
}
