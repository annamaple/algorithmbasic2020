package class01.me;

import class01.Code04_BSExist;

import java.util.Arrays;

public class Code04_BSExistImpl extends Code04_BSExist {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != new BSExist().exist(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    public static class BSExist {

        public boolean exist(int[] arr, int target) {
            if (arr == null || arr.length == 0) return false;
            int left = 0;
            int right = arr.length - 1;
            while (left < right) {
                int mid = left + ((right - left) >> 1);
                if (arr[mid] == target) {
                    return true;
                } else if (arr[mid] > target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            return arr[left] == target;
        }
    }
}
