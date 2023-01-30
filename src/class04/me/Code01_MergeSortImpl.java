package class04.me;

import class04.Code01_MergeSort;

public class Code01_MergeSortImpl extends Code01_MergeSort {

    /**
     * 归并排序的递归实现
     */
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int m = l + ((r - l) >> 1);
        process(arr, l, m);
        process(arr, m + 1, r);
        merge(arr, l, m, r);
    }

    // 迭代方式实现
    // todo lei : 归并排序做到理解并默写出来
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int step = 1;
        int len = arr.length;
        while (step < len) {
            int l = 0;
            while (l < len) {
                if (step >= len - l) {
                    break;
                }
                int m = l + step - 1;
                int r = m + Math.min(step, len - m - 1);
                merge(arr, l, m, r);
                l = r + 1;
            }
            // 防止整数溢出
            if (step > len / 2) {
                break;
            }
            step <<= 1;
        }
    }

    public static void merge(int[] arr, int l, int m, int r) {
        int p1 = l;
        int p2 = m + 1;
        int[] help = new int[r - l + 1];
        int i = 0;
        while (p1 <= m && p2 <= r) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort1(arr1);
            mergeSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
