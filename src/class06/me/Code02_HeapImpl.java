package class06.me;

import class06.Code02_Heap;
import cn.hutool.core.util.StrUtil;

public class Code02_HeapImpl extends Code02_Heap {


    public static void main(String[] args) {
        System.out.println(StrUtil.format("- 1 / 2 = {}", -1 / 2));

        int value = 1000;
        int limit = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            MyMaxHeap my = new MyMaxHeap(curLimit);
            RightMaxHeap test = new RightMaxHeap(curLimit);
            int curOpTimes = (int) (Math.random() * limit);
            for (int j = 0; j < curOpTimes; j++) {
                if (my.isEmpty() != test.isEmpty()) {
                    System.out.println("Oops!");
                }
                if (my.isFull() != test.isFull()) {
                    System.out.println("Oops!");
                }
                if (my.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    my.push(curValue);
                    test.push(curValue);
                } else if (my.isFull()) {
                    if (my.pop() != test.pop()) {
                        System.out.println("Oops!");
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        my.push(curValue);
                        test.push(curValue);
                    } else {
                        if (my.pop() != test.pop()) {
                            System.out.println("Oops!");
                        }
                    }
                }
            }
        }
        System.out.println("finish!");
    }


    public static class MyMaxHeap {
        private int[] data;
        private int size;
        private final int limit;

        public MyMaxHeap(int limit) {
            this.limit = limit;
            data = new int[limit];
            this.size = 0;
        }

        public void push(int val) {
            if (isFull()) {
                throw new RuntimeException("heap is full");
            }
            data[size] = val;
            // 最新的值下标
            heapInsert(data, size++);
        }

        public int pop() {
            if (isEmpty()) {
                throw new RuntimeException("heap is empty");
            }
            int ans = data[0];
            swap(data, 0, size - 1);
            heapify(data, 0, --size);
            return ans;
        }

        // 和父节点比较, 大于父节点的值交换并继续比较。直到<=父节点的值或父节点为null
        private void heapInsert(int[] arr, int index) {
            // -1 / 2 = 0
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        // 和子节点比较大小，若子节点的较大值大于当前节点，交换
        private void heapify(int[] arr, int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                // 找到子节点最大的那一个最大值
                int largest = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
                // 子节点最大的那个是否大于当前节点
                // 这儿少个跳出循环的标志， 否则当arr[largest] <= arr[index]时，会死循环
                if (arr[index] >= arr[largest]) {
                    break;
                }
                // arr[largest] > arr[index]
                swap(arr, largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        private void swap(int[] arr, int a, int b) {
            int temp = arr[a];
            arr[a] = arr[b];
            arr[b] = temp;
        }
    }
}
