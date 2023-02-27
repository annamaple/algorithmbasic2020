package class14.me;

import class14.Code02_LessMoneySplitGold;

/**
 * @author lei
 */
public class Code02_LessMoneySplitGoldImpl extends Code02_LessMoneySplitGold {

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != new LessMoneySplitGold().lessMoney(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    public static class LessMoneySplitGold {

        public int lessMoney(int[] arr) {
            if (arr == null || arr.length == 0) {
                return 0;
            }
            Heap heap = new Heap(arr.length);
            for (int n : arr) {
                heap.offer(n);
            }
            int price = 0;
            while (heap.size() > 1) {
                int cur = heap.poll() + heap.poll();
                price += cur;
                heap.offer(cur);
            }
            return price;
        }
    }


    // 小顶堆
    public static class Heap {
        int[] arr;
        int limit;
        int heapSize;

        public Heap(int limit) {
            this.limit = limit;
            this.arr = new int[limit];
            this.heapSize = 0;
        }

        public void offer(int val) {
            arr[heapSize] = val;
            heapSize++;
            heapInsert(heapSize - 1);
        }

        public int poll() {
            int ans = arr[0];
            swap(0, heapSize - 1);
            heapSize--;
            heapify(0, heapSize);
            return ans;
        }
        
        public boolean isEmpty() {
            return heapSize == 0;
        }
        
        public int size() {
            return heapSize;
        }

        void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int less = left + 1 < heapSize && arr[left + 1] < arr[left] ? left + 1 : left;
                less = arr[less] < arr[index] ? less : index;
                if (less == index) {
                    break;
                }
                swap(less, index);
                index = less;
                left = index * 2 + 1;
            }
        }

        void heapInsert(int index) {
            while (arr[index] < arr[(index - 1) / 2]) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        void swap(int a, int b) {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    }
}
