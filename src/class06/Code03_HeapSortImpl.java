package class06;

import java.util.PriorityQueue;

public class Code03_HeapSortImpl extends Code03_HeapSort {

    // for test
    public static void main(String[] args) {

        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(6);
        heap.add(8);
        heap.add(0);
        heap.add(2);
        heap.add(9);
        heap.add(1);

        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            new HeapSort().sort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        new HeapSort().sort(arr);
        printArray(arr);
    }

    // 堆排序
    public static class HeapSort {

        public void sort(int[] arr) {
            if (arr == null || arr.length < 2) {
                return;
            }
            // 构建堆(最大堆)
            // 1) 从上往下构建 time: O(NlogN)
//            for (int i = 0; i < arr.length; i++) {
//                heapInsert(arr, i);
//            }
            // 2) 从下往上构建 time: O(N)
            for (int i = arr.length - 1; i >= 0; i--) {
                heapify(arr, i, arr.length);
            }

            // 依次弹出到对应位置
            int size = arr.length;
            swap(arr, 0, --size);
            while (size > 1) {
                heapify(arr, 0, size);
                swap(arr, 0, --size);
            }
        }

        // 最大堆
        // 请严格保证传入的参数合法
        private void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        // 最大堆
        // 请严格保证传入的参数合法
        private void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
//                largest = arr[largest] > arr[index] ? largest : index;
//                if (largest == index) {
//                    break;
//                }
                if (arr[largest] <= arr[index]) {
                    break;
                }
                swap(arr, index, largest);
                index = largest;
                left = index * 2 + 1;
            }
        }

        private void swap(int[] arr, int a, int b) {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    }


}
