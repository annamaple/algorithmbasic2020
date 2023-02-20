package class11.me;

import class11.Code03_EncodeNaryTreeToBinaryTree;
import cn.hutool.core.collection.ListUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lei
 */
public class Code03_EncodeNaryTreeToBinaryTreeImpl extends Code03_EncodeNaryTreeToBinaryTree {

    public static void main(String[] args) {
        List<Integer> list = ListUtil.of(3, 2, 1, 5, 9);
        Heap<Integer> heap = new Heap<>(list);
        list = new LinkedList<>();
        while (!heap.isEmpty()) {
            list.add(heap.pop());
        }
        System.out.println(list);
    }
    
    
    
    // 加强堆（增加了一样索引表） 默认为最小堆
    public static class Heap<T extends Comparable<T>> {

        // ArrayList
        List<T> dataList;
        Map<T, Integer> indexMap;
        int heapSize;
        Comparator<T> comparator;
        
        public Heap(List<T> data) {
            init(null, data, null);
        }
        
        public Heap(List<T> data, Comparator<T> comparator) {
            init(null, data, comparator);
        }

        public Heap(int initSize, List<T> data, Comparator<T> comparator) {
            init(initSize, data, comparator);
        }

        private void init(Integer initSize, List<T> data, Comparator<T> comparator) {
            this.comparator = comparator;
            this.dataList = initSize == null ? new ArrayList<>() : new ArrayList<>(initSize);
            this.indexMap = new HashMap<>();
            this.heapSize = 0;
            if (data != null && !data.isEmpty()) {
                 data.forEach(this::push);
                // TODO 优化版
            }
        }

        public void push(T t) {
            dataList.add(t);
            indexMap.put(t, heapSize);
            heapSize++;
            heapInsert(heapSize - 1);
        }

        public T pop() {
            T t = dataList.get(0);
            swap(dataList.get(0), dataList.get(heapSize - 1));
            dataList.remove(heapSize - 1);
            indexMap.remove(t);
            heapSize--;
            heapify(0);
            return t;
        }
        
        public int size() {
            return heapSize;
        }
        
        public boolean isEmpty() {
            return size() == 0;
        }

        // 堆中最后一个元素依次向上比
        public void heapInsert(int i) {
            while (compare(dataList.get(i), dataList.get((i - 1) / 2)) < 0) {
                swap(dataList.get(i), dataList.get((i - 1) / 2));
                i = (i - 1) / 2;
            }
        }

        // 堆中第一个元素依次向下比
        public void heapify(int i) {
            int left;
            while ((left = 2 * i + 1) < heapSize) {
                int better = left;
                if (left + 1 < heapSize && compare(dataList.get(left + 1), dataList.get(left)) < 0) {
                    better = left + 1;
                }
                better = compare(dataList.get(better), dataList.get(i)) < 0 ? better : i;
                if (better == i) {
                    break;
                }
                swap(dataList.get(i), dataList.get(better));
                i = better;
            }
        }

        public void swap(T t1, T t2) {
            int index1 = indexMap.get(t1);
            int index2 = indexMap.get(t2);
            dataList.set(index1, t2);
            dataList.set(index2, t1);
            indexMap.put(t2, index1);
            indexMap.put(t1, index2);
        }

        public int compare(T t1, T t2) {
            return comparator == null ? t1.compareTo(t2): this.comparator.compare(t1, t2);
        }
    }

}
