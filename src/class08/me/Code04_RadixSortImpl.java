package class08.me;

import class08.Code04_RadixSort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author lei
 */
public class Code04_RadixSortImpl extends Code04_RadixSort {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            new RadixSort().radixSortGraceful(arr1, 0, arr1.length - 1);
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
        new RadixSort().radixSortCommon(arr);
        printArray(arr);
    }

    public static class RadixSort {


        // only for no-negative value 仅仅非负数
        public void radixSortCommon(int[] arr) {
            // 找出最大数字的位数
            // 小于最大数字位数的前面补0
            // 准备10个队列, 表示数字0-9
            // 按照数字由小到大从队列中取出数字
            int max = Integer.MIN_VALUE;
            for (int num : arr) {
                max = Math.max(num, max);
            }
            int decimalBit = getDecimalBit(max);
            // 初始化队列
            List<Queue<Integer>> queues = new ArrayList<>(10);
            for (int i = 0; i < 10; i++) {
                queues.add(new LinkedList<>());
            }
            // 处理基数
            for (int i = 1; i <= decimalBit; i++) {
                for (int num : arr) {
                    Queue<Integer> queue = queues.get(getDigit(num, i));
                    queue.add(num);
                }
                int index = 0;
                for (Queue<Integer> queue : queues) {
                    while (!queue.isEmpty()) {
                        arr[index++] = queue.poll();
                    }
                }
            }
        }


        // 优雅的基数排序，优化掉队列
        // 基数排序 arr[left...right] 
        public void radixSortGraceful(int[] arr, int left, int right) {
            if (arr == null || right >= arr.length || left > right || arr.length == 1) {
                return;
            }
            int i;
            int max = arr[left];
            for (i = left + 1; i <= right; i++) {
                max = Math.max(max, arr[i]);
            }
            int decimalBit = getDecimalBit(max);
            int[] help = new int[right - left + 1];
            for (int d = 1; d <= decimalBit; d++) {
                int[] count = new int[10];
                for (i = left; i <= right; i++) {
                    count[getDigit(arr[i], d)]++;
                }
                // 依次求本身和之前n项和
                for (i = 1; i < count.length; i++) {
                    count[i] = count[i] + count[i - 1];
                }
                // 反向遍历原数组，将其拷贝到help中，然后在从help中拷贝回去
                for (i = right; i >= left; i--) {
                    // 找当前数应该放的位置
                    int digit = getDigit(arr[i], d);
                    help[--count[digit]] = arr[i];
                }
                for (i = 0; i < help.length; i++) {
                    arr[left + i] = help[i];
                }
            }
        }


        private int getMaxNumInArr(int[] arr) {
            int max = Integer.MIN_VALUE;
            for (int num : arr) {
                max = Math.max(max, num);
            }
            return max;
        }

        /**
         * 获取某个数的十进制位数
         */
        private int getDecimalBit(int num) {
            if (num == 0) return 1;
            num = Math.abs(num);
            int ans = 0;
            while (num != 0) {
                num /= 10;
                ans++;
            }
            return ans;
        }

        /**
         * 取十进制数某个位上的数字
         *
         * @param num 十进制数
         * @param d   位 d=1为各位， d=2为十位 。。。
         * @return d为上的数
         */
        private int getDigit(int num, int d) {
            while (d-- > 1) {
                num /= 10;
            }
            return num % 10;
        }
    }
}
