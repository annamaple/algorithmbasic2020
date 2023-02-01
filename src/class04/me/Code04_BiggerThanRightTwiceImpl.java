package class04.me;

import class04.Code04_BiggerThanRightTwice;

/**
 * @author lei
 */
public class Code04_BiggerThanRightTwiceImpl extends Code04_BiggerThanRightTwice {

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (new BiggerThanRightTwice().biggerThanRightTwice(arr1) != comparator(arr2)) {
                System.out.println("Oops!");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }
    

    public static class BiggerThanRightTwice {

        public int biggerThanRightTwice(int[] arr) {
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

        int merge(int[] arr, int left, int mid, int right) {
            int ans = 0;
            int p1 = left;
            int p2 = mid + 1;
            while (p1 <= mid) {
                while (p2 <= right && arr[p1] > (arr[p2] << 1)) p2++;
                // [mid + 1, p2)
                ans += p2 - mid - 1;
                p1++;
            }
            // merge
            int[] help = new int[right - left + 1];
            int i = 0;
            p1 = left;
            p2 = mid + 1;
            while (p1 <= mid && p2 <= right) help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
            while (p1 <= mid) help[i++] = arr[p1++];
            while (p2 <= right) help[i++] = arr[p2++];
            while (--i >= 0) arr[left + i] = help[i];
            return ans;
        }
    }
}
