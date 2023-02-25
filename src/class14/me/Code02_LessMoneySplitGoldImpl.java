package class14.me;

import class14.Code02_LessMoneySplitGold;

import java.util.Arrays;

public class Code02_LessMoneySplitGoldImpl extends Code02_LessMoneySplitGold {

    public static void main(String[] args) {
        int[] rr = {2, 3, 5, 33, 4, 5, 6};
        Heap heap = new Heap(rr.length);
        for (int n : rr) {
            heap.add(n);
        }
        while (heap.heapSize != 0) {
            System.out.print(heap.pop() + "\t");
        }
        System.out.println();
        System.out.println(new LessMoneySplitGold().lessMoney1(rr));

        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
//            if (lessMoney1(arr) != lessMoney2(arr)) {
//                System.out.println("Oops!");
//            }
            if (new LessMoneySplitGold().lessMoney1(arr) != lessMoney1(arr)) {
                System.out.println("Oops!");
                System.out.println(Arrays.toString(arr));
                System.out.println(new LessMoneySplitGold().lessMoney1(arr));
                System.out.println(lessMoney1(arr));
                return;
            }
        }
        System.out.println("finish!");
    }

    public static class LessMoneySplitGold {

        public int lessMoney1(int[] arr) {
            // 贪心 - 堆
            if (arr == null || arr.length == 0) return 0;
            Heap heap = new Heap(arr.length);
            for (int n : arr) {
                heap.add(n);
            }
            int ans = 0;
            int cur;
            while (heap.heapSize > 1) {
                cur = heap.pop() + heap.pop();
                ans += cur;
                heap.add(cur);
            }
            return ans;
        }
    }


    public static class Heap {
        int limit;
        int heapSize;
        int[] data;

        public Heap(int limit) {
            this.limit = limit;
            this.heapSize = 0;
            this.data = new int[limit];
        }

        void add(int num) {
            data[heapSize] = num;
            heapSize++;
            heapInsert(heapSize - 1);
        }

        int pop() {
            int ans = data[0];
            swap(0, heapSize - 1);
            heapSize--;
            heapify(0, heapSize);
            return ans;
        }

        void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int less = left + 1 < heapSize && data[left + 1] < data[left] ? left + 1 : left;
                less = data[less] < data[index] ? less : index;
                if (less == index) {
                    break;
                }
                swap(index, less);
                index = less;
                left = index * 2 + 1;
            }
        }

        public void heapInsert(int index) {
            while (data[index] < data[(index - 1) / 2]) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public void swap(int index1, int index2) {
            int temp = data[index1];
            data[index1] = data[index2];
            data[index2] = temp;
        }
    }
}
