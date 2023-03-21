package class01.me;

import class01.Code05_BSNearRight;

import java.util.Arrays;

public class Code05_BSNearRightImpl extends Code05_BSNearRight {


    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != new BSNearRight().nearRight(arr, value)) {
                printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(nearestIndex(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
    
    
    public static class BSNearRight {


        // [1,2,3,4]  
        // 3 ==> 1
        // 1 0
        // 0 0
        // 在arr上，找满足<=value的最右位置 上界
        public int nearRight(int[] arr, int val) {
            if (arr == null || arr.length == 0) return -1;
            int left = -1, right = arr.length - 1, mid;
            while (left < right) {
                mid = left + ((right - left + 1) / 2);
                if (arr[mid] <= val) {
                    left = mid;
                } else {
                    right = mid - 1;
                }
            }
            return left;
        }

        // 在arr上，找满足<=value的最右位置 上界
        public static int nearestIndex(int[] arr, int value) {
            int L = 0;
            int R = arr.length - 1;
            int index = -1; // 记录最右的对号
            while (L <= R) {
                int mid = L + ((R - L) >> 1);
                if (arr[mid] <= value) {
                    index = mid;
                    L = mid + 1;
                } else {
                    R = mid - 1;
                }
            }
            return index;
        }
    }
}
