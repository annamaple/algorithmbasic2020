package class06.me;

import class06.Code04_SortArrayDistanceLessK;

import java.util.PriorityQueue;

/**
 * 已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离一定不超过k，并且k相对于数组长度来说是比较小的。
 * 请选择一个合适的排序策略，对这个数组进行排序。 
 */
public class Code04_SortArrayDistanceLessKImpl extends Code04_SortArrayDistanceLessK {
    
    public static class SortedArrDistanceLessK {
        public void sortedArrDistanceLessK(int[] arr, int k) {
            if (arr == null || arr.length < 2 || k == 0) {
                return;
            }
            // 默认最小堆
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            int index = 0;
            // arr中前k个数放堆中
            while (index < Math.min(arr.length, k)) {
                minHeap.offer(arr[index++]);
            }
            // 从堆中弹出一个元素并放在数组左边,然后堆中再添加一个元素
            int p = 0;
            while (index < arr.length && !minHeap.isEmpty()) {
                arr[p++] = minHeap.poll();
                minHeap.offer(arr[index++]);
            }
            while (!minHeap.isEmpty()) {
                arr[p++] = minHeap.poll();
            }
        }
    }
    

    public static void main(String[] args) {
        System.out.println("test begin");
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = randomArrayNoMoveMoreK(maxSize, maxValue, k);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            // sortedArrDistanceLessK(arr1, k);
            new SortedArrDistanceLessK().sortedArrDistanceLessK(arr1, k);
            comparator(arr2, k);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                System.out.println("K : " + k);
                printArray(arr);
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
    
    
}
