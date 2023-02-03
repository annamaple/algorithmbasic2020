package class05.me;

import class05.Code02_PartitionAndQuickSort;
import cn.hutool.core.util.ArrayUtil;

public class Code02_PartitionAndQuickSortImpl extends Code02_PartitionAndQuickSort {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            // quickSort1(arr1);
            new QuickSort().quickSort(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr2, arr3)) {
                succeed = false;
                System.out.println("arr1: " + ArrayUtil.toString(arr1));
                System.out.println("arr2: " + ArrayUtil.toString(arr2));
                System.out.println("arr3: " + ArrayUtil.toString(arr3));
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Oops!");
    }

    /**
     * 快排的核心-荷兰国旗问题;分而治之思想
     */
    public static class QuickSort {

        public void quickSort(int[] arr) {
            if (arr == null || arr.length <= 1) {
                return;
            }
            partition(arr, 0, arr.length - 1);
        }

        // 闭区间
        void partition(int[] arr, int left, int right) {
            // base case
            if (left >= right) {
                return;
            }
            // 随机快排
            // [x, x, 1, 2, 3, 4]
            //  0  1  2  3  4  5
            // left = 3 right = 5; 
            // right - left = 2; 
            // (int) (Math.random() * 2) => [0, 2); 
            // (int) (Math.random() * (2 + 1)) => [0, 2 + 1) ==> 整数范围[0, 2] 
            swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
            int[] equalArea = netherlandsFlag(arr, left, right);
            // 此处可能存在equalArea[0] - 1 < 0, 因此base case 有left < right条件
            partition(arr, left, equalArea[0] - 1);
            partition(arr, equalArea[1] + 1, right);
        }

        // 荷兰国旗问题
        // 给定一个数组arr, 给定一个目标值arr[right]，
        // 要求把小于arr[right]的放左边，等于arr[right]的放中间，大于arr[right]放右边
        // 返回数组[L, R] L, R为数组arr的下标, 表示此时数组arr中位于L...R位置的元素值等于目标值
        public int[] netherlandsFlag(int[] arr, int left, int right) {
            if (left > right) {
                return new int[]{-1, -1};
            }
            if (left == right) {
                return new int[]{left, right};
            }
            // <区 右边界
            int less = left - 1;
            // >区 左边界
            int more = right;
            int index = left;
            while (index < more) {
                if (arr[index] < arr[right]) {
                    // 小于目前值时小于区间有移然后和当前值交换位置,然后当前值移动到下一位
                    swap(arr, ++less, index++);
                } else if (arr[index] == arr[right]) {
                    // 等于目前值，当前index++
                    index++;
                } else {
                    // 大于目标值, 大于区间左移一位，然后和当前值交换。当前index不变
                    swap(arr, index, --more);
                }
            }
            // 最后把arr[right]的值和大于区间的最左边的值交换位置
            swap(arr, right, more);
            return new int[]{less + 1, more};
        }

        public void swap(int[] arr, int a, int b) {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    }
}
