package class04.me;

import class04.Code02_SmallSum;

import java.util.Arrays;

/**
 * @author lei
 */
public class Code02_SmallSumImpl extends Code02_SmallSum {

    public static void main(String[] args) {
        // 4 * 1 + 1 * 2 + 3 * 1 = 4 + 2 + 3 = 9;
        int[] arr = {4, 1, 3, 5};
        System.out.println(new SmallSum().smallSum(arr));
        System.out.println(Arrays.toString(arr));

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (new SmallSum().smallSum(arr1) != comparator(arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    // [4, 1, 2, 5, 6, 9] 
    // smallSum: (4 + 1 + 2) + (4 + 1 + 2 + 5) + (4 + 1 + 2 + 5 + 6)  
    public static class SmallSum {


        public int smallSum(int[] arr) {
            if (arr == null || arr.length <= 1) return 0;
            return process(arr, 0, arr.length - 1);
        }

        int process(int[] arr, int left, int right) {
            if (left == right) return 0;
            int mid = left + ((right - left) >> 1);
            return process(arr, left, mid)
                    + process(arr, mid + 1, right)
                    + merge(arr, left, mid, right);
        }

        // 請严格保证传入的参数合法
        // 1 3 4 5 | 2 4 6 7
        int merge(int[] arr, int left, int mid, int right) {
            int p1 = left;
            int p2 = mid + 1;
            int ans = 0;
            // 求小和过程
            while (p1 <= mid && p2 <= right) {
                if (arr[p1] < arr[p2]) {
                    // 右组有[p2, right]个数大于arr[p1]
                    ans += (right - p2 + 1) * arr[p1];
                    p1++;
                } else {
                    p2++;
                }
            }
            // 归并merge
            int[] help = new int[right - left + 1];
            int i = 0;
            p1 = left;
            p2 = mid + 1;
            while (p1 <= mid && p2 <= right) help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
            while (p1 <= mid) help[i++] = arr[p1++];
            while (p2 <= mid) help[i++] = arr[p2++];
            while (--i >= 0) arr[left + i] = help[i];
            return ans;
        }

    }
}