package class01.me;

import class01.Code05_BSNearLeft;

import java.util.Arrays;

public class Code05_BSNearLeftImpl extends Code05_BSNearLeft {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            int ans1, ans2;
            if ((ans1 = test(arr, value)) != (ans2 = new BSNearLeft().nearestIndex(arr, value))) {
                printArray(arr);
                System.out.println(value);
                System.out.println(ans1);
                System.out.println(ans2);
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = {1, 2, 2, 3, 4, 5, 5};
        int val = -1;
        System.out.println(new BSNearLeft().nearestIndex(arr, val));
    }


    public static class BSNearLeft {

        // >= val的最左边
        public int nearestIndex(int[] arr, int val) {
            if (arr == null || arr.length == 0) return -1;
            int N = arr.length;
            // [left, right)
            int left = 0, right = N, mid;
            while (left < right) {
                mid = left + (right - left) / 2;
                if (arr[mid] < val) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left == N ? -1 : left;
        }
        
        // [1,2,3,4]
        // 在arr上，找满足>=value的最左位置
        public int nearestIndexZ(int[] arr, int value) {
            int L = 0;
            int R = arr.length - 1;
            int index = -1; // 记录最左的对号
            while (L <= R) { // 至少一个数的时候
                int mid = L + ((R - L) >> 1);
                if (arr[mid] >= value) {
                    index = mid;
                    R = mid - 1;
                } else {
                    L = mid + 1;
                }
            }
            return index;
        }
    }
}
