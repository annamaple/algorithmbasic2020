package class05.me;

import class05.Code02_PartitionAndQuickSort;
import cn.hutool.core.util.ArrayUtil;

import java.util.Stack;

// TODO Lei: 学完了所有排序, 写一个排序工具类
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


    public static class NetherlandsFlag {
        // 荷兰国旗问题1.0:
        // 给定一个数组 arr ，和一个整数 num 。
        // 把 <= num 的数放在数组的左边，大于num的数放在数组的右边。
        // 返回index：左第一个<=num的位置
        public int netherlandsSimple(int[] arr, int num) {
            return netherlandsSimple(arr, 0, arr.length - 1, num);
        }

        // 荷兰国旗问题1.0:
        // 转换：
        // 给定一个数组 arr 区间[left, right]，和一个整数 num 。
        // 把 <= num 的数放在数组的左边，大于num的数放在数组的右边。
        // 返回index：左第一个<=num的位置
        int netherlandsSimple(int[] arr, int left, int right, int num) {
            if (arr == null || arr.length == 0) {
                return -1;
            }
            if (left > right) {
                return -1;
            }
            int less = left - 1;
            int index = left;
            while (index <= right) {
                if (arr[index] <= num) {
                    swap(arr, index++, ++less);
                } else {
                    index++;
                }
            }
            return less;
        }

        // 荷兰国旗问题1.0:
        // 转换：
        // 给定一个数组 arr 区间[left, right]，和一个整数 num = arr[right]。
        // 把 <= num 的数放在数组的左边，大于num的数放在数组的右边。
        // 返回index：=num的位置
        int netherlandsSimple(int[] arr, int left, int right) {
            int less = netherlandsSimple(arr, left, right - 1, arr[right]);
            swap(arr, right, less + 1);
            return less + 1;
        }


        // 荷兰国旗问题2.0:
        // 给定一个数组 arr ，和一个整数 num 。
        // 把小于 num 的数放在数组的左边，等于 num 的数放在中间，大于num的数放在数组的右边。
        // 返回[a1, a2] a1,a2为arr中的下标, 表示该区间的元素的值等于num
        int[] netherlandsStand(int[] arr, int num) {
            return netherlandsFlagStand(arr, 0, arr.length - 1, num);
        }


        // 荷兰国旗问题2.0:
        // 转换
        // 给定一个数组 arr，给定区间[left, right] ，和一个整数 num 。
        // 把小于 num 的数放在数组的左边，等于 num 的数放在中间，大于num的数放在数组的右边。
        // 返回[a1, a2] a1,a2为arr中的下标, 表示该区间的元素的值等于num
        int[] netherlandsFlagStand(int[] arr, int left, int right, int num) {
            if (arr == null || arr.length == 0) {
                return new int[]{-1, -1};
            }
            if (left > right) {
                return new int[]{-1, -1};
            }
            int less = left - 1;
            int more = right + 1;
            int index = left;
            while (index < more) {
                if (arr[index] < num) {
                    swap(arr, ++less, index++);
                } else if (arr[index] == num) {
                    index++;
                } else {
                    swap(arr, --more, index);
                }
            }
            return new int[]{less + 1, more - 1};
        }

        // 荷兰国旗问题2.0:
        // 转换
        // 给定一个数组 arr，给定区间[left, right] ，和一个整数 num = arr[right] 。
        // 把小于 num 的数放在数组的左边，等于 num 的数放在中间，大于num的数放在数组的右边。
        // 返回[a1, a2] a1,a2为arr中的下标, 表示该区间的元素的值等于num
        int[] netherlandsFlagStand(int[] arr, int left, int right) {
            // 返回的值等于arr[right]的区间
            int[] equalArr = netherlandsFlagStand(arr, left, right - 1, arr[right]);
            swap(arr, ++equalArr[1], right);
            return equalArr;
        }


        void swap(int[] arr, int a, int b) {
            if (a < 0) {
                System.out.println(a);
            }
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    }


    public static class QuickSortByNetherlandsFlag extends NetherlandsFlag {

        public void sort(int[] arr) {
            if (arr == null || arr.length <= 1) {
                return;
            }
            partition(arr, 0, arr.length - 1);
        }

        void partition(int[] arr, int left, int right) {
            if (left >= right) {
                return;
            }
            // 随机快排
            swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
            int[] equalArr = netherlandsFlagStand(arr, left, right);
            partition(arr, left, equalArr[0] - 1);
            partition(arr, equalArr[1] + 1, right);
        }

        void mergeSort(int[] arr) {
            if (arr == null || arr.length <= 1) {
                return;
            }
            process(arr, 0, arr.length - 1);
        }

        void process(int[] arr, int left, int right) {
            if (left == right) {
                return;
            }
            int mid = left + ((right - left) >> 1);
            process(arr, left, mid);
            process(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
        
        // 请严格保证调用merge的参数合法
        void merge(int[] arr, int left, int mid, int right) {
            int p1 = left;
            int p2 = mid + 1;
            int[] help = new int[right - left + 1];
            int i = 0;
            while (p1 <= mid && p2 <= right) {
                help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
            }
            while (p1 <= mid) {
                help[i++] = arr[p1++];
            }
            while (p2 <= right) {
                help[i++] = arr[p2++];
            }
            while (--i >= 0) {
                arr[left + i] = help[i];
            }
        }
        
        // 使用对象保存left right
        public static class Pair<K, V>{
            K key;
            V value;

            public Pair(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() {
                return key;
            }

            public void setKey(K key) {
                this.key = key;
            }

            public V getValue() {
                return value;
            }

            public void setValue(V value) {
                this.value = value;
            }
        }
        

        // 随机快排 迭代实现
        public void quickSortIteration(int[] arr) {
            if (arr == null || arr.length <= 1) {
                return;
            }
            // 使用栈保存每一步递归调用时的left right
            swap(arr, arr.length - 1, (int) (Math.random() * (arr.length)));
            int[] equalArea = netherlandsFlag(arr, 0, arr.length - 1);
            Stack<Pair<Integer, Integer>> stack = new Stack<>();
            stack.push(new Pair<>(0, equalArea[0] - 1));
            stack.push(new Pair<>(equalArea[1] + 1, arr.length - 1));
            while (!stack.isEmpty()) {
                Pair<Integer, Integer> partitionArea = stack.pop();
                int left = partitionArea.getKey();
                int right = partitionArea.getValue();
                // base case
                if (left >= right) {
                    continue;
                }
                swap(arr, right, left + (int) (Math.random() * (right - left + 1)));
                equalArea = netherlandsFlag(arr, left, right);
                stack.push(new Pair<>(left, equalArea[0] - 1));
                stack.push(new Pair<>(equalArea[1] + 1, right));
            }
        }
    }
    
}
