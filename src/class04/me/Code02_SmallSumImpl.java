package class04.me;

import class04.Code02_SmallSum;

import java.util.Arrays;

/**
 * @author lei
 */
public class Code02_SmallSumImpl extends Code02_SmallSum {

    public static void main(String[] args) {
        int[] arr = {4, 1, 3, 5};
        System.out.println(new SmallSum().smallSum(arr));
        System.out.println(Arrays.toString(arr));
    }

    // [4, 1, 2, 5, 6, 9] 
    // smallSum: (4 + 1 + 2) + (4 + 1 + 2 + 5) + (4 + 1 + 2 + 5 + 6)  
    public static class SmallSum {

        public int smallSum(int[] arr) {
            if (arr == null || arr.length <= 1) return 0;
            return process(arr, 0, arr.length - 1);
        }

        int process(int[] arr, int left, int right) {
            // base case
            if (left == right) {
                return 0;
            }
            int mid = left + ((right - left) >> 1);
            return process(arr, left, mid)
                    + process(arr, mid + 1, right) 
                    + merge(arr, left, mid, right);
        }

        // 4 1 2 | 5 6 9
        // p1      p2
        int merge(int[] arr, int left, int mid, int right) {
            int[] help = new int[right - left + 1];
            int i = 0;
            int p1 = left;
            int p2 = mid + 1;
            int res = 0;
            while (p1 <= mid && p2 <= right) {
                // [p2, right]个数
                res += arr[p1] < arr[p2] ? arr[p1] * (right - p2 + 1) : 0;
                help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
            }
            while (p1 <= mid) {
                help[i++] = arr[p1++];
            }
            while (p2 <= right) {
                help[i++] = arr[p2++];
            }
            for (i = 0; i < help.length; i++) {
                arr[left + i] = help[i];
            }
            return res;
        }
    }
}