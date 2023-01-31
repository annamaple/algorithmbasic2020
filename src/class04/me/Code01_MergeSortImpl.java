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
            MergeSort mergeSort = new MergeSort();
            mergeSort.sort(arr1);
            mergeSort.sortIteration(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }

    public static class MergeSort {

        // 3, 1, 4, 2, 9, 5

        /**
         * 归并排序-递归实现
         */
        public void sort(int[] arr) {
            if (arr == null || arr.length <= 1) {
                return;
            }
            process(arr, 0, arr.length - 1);
        }

        private void process(int[] arr, int left, int right) {
            // base case
            if (left == right) {
                return;
            }
            int mid = left + ((right - left) >> 2);
            process(arr, left, mid);
            process(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
        // endregion

        /**
         * <br> 归并排序-迭代实现
         * <br> mergeSize为每一次迭代左组的长度
         * <br> 思路：使用mergeSize(初始为1)变量遍历原数组,变量依次增大为原来的2被, 知道mergeSize大于原数组长度
         */
        // 3, 1, 4, 2, 9, 5
        // mergeSize = **时: left = 0; mid = left + mergeSize - 1; right = mid + mergeSize;
        // 考虑边界情况: 
        // 1. left > length 跳出循环
        // 2. mid > length 跳出子循环
        // 3. mid + mergeSize > length时, right = length - 1;
        public void sortIteration(int[] arr) {
            if (arr == null || arr.length <= 1) {
                return;
            }
            int mergeSize = 1;
            int length = arr.length;
            while (mergeSize < length) {
                // 构建left mid right 进行merge, 注意边界
                int left = 0;
                while (left < length) {
                    // [left, left]
                    int mid = left + mergeSize - 1;
                    if (mid >= length) {
                        break;
                    }
                    int right = Math.min(mid + mergeSize, length - 1);
                    merge(arr, left, mid, right);
                    left = right + 1;
                }
                // 防止整数溢出, 如: Integer.MAX_VALUE * 2变为负数
                if (mergeSize > length / 2) {
                    break;
                }
                mergeSize <<= 1;
            }
        }


        // 思路：使用双指针卡住左右组元素, 并定义辅助数组保存元素并拷贝回元素组
        // 私有方法， 请保证传入参数合法
        //    left      mid       right     
        // arr: 1, 4, 5, 6 | 2, 3, 9
        //      p1           p2
        private void merge(int[] arr, int left, int mid, int right) {
            // [left, right]中的元素个数
            int[] help = new int[right - left + 1];
            // 仅供help使用
            int i = 0;
            // 双指针
            int p1 = left;
            int p2 = mid + 1;
            while (p1 <= mid && p2 <= right) {
                help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
            }
            while (p1 <= mid) {
                help[i++] = arr[p1++];
            }
            while (p2 <= right) {
                help[i++] = arr[p2++];
            }
            // 拷贝回原数组
            for (i = 0; i < help.length; i++) {
                arr[left + i] = help[i];
            }
        }
    }
}
