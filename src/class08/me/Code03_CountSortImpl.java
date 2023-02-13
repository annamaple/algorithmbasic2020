package class08.me;

import class08.Code03_CountSort;

/**
 * @author lei
 */
public class Code03_CountSortImpl extends Code03_CountSort {

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 150;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            new CountSort().countSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        new CountSort().countSort(arr);
        printArray(arr);

    }
    

    public static class CountSort {

        // only for 0 ~ 999 value
        public void countSort(int[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }
            int max = arr[0];
            for (int i = 1; i < arr.length; i++) {
                max = Math.max(max, arr[i]);
            }
            int[] bucket = new int[max + 1];
            for (int num : arr) {
                bucket[num]++;
            }
            int index = 0;
            for (int i = 0; i < bucket.length; i++) {
                while (bucket[i]-- > 0) {
                    arr[index++] = i;
                }
            }
        }
    }
}
