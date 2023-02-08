package class07.me;

import cn.hutool.core.lang.Console;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * 加强堆 
 * <br> 系统提供的PriorityQueue只有堆的基础功能(poll() offer() size() peek())
 * <br> 无法获取堆中指定元素对应数组的下标位置
 * <br> 无法在修改堆中某个元素的值后, 使堆继续可用
 * @author lei
 */
public class MyHeapGreater<T>{

    public static void main(String[] args) {
        int num;
        Scanner scanner = new Scanner(System.in);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        MyHeapGreater<Integer> myMinHeap = new MyHeapGreater<>(Comparator.comparingInt(a -> -a));
        while (true) {
            num = scanner.nextInt();
            if (num == 0) {
                break;
            }
            minHeap.offer(num);
            myMinHeap.offer(num);
            if (!Objects.equals(minHeap.peek(), myMinHeap.peek())) {
                Console.log("fuck minHeap: {}; myMinHeap: {}", minHeap.peek(), myMinHeap.peek());
            }
        }
        if (minHeap.size() != myMinHeap.heapSize) {
            Console.log("fuck minHap.size = {}; myMinSize = {}", minHeap.size(), myMinHeap.heapSize);
        }
        while (!minHeap.isEmpty()) {
            int a = minHeap.poll();
            int b = myMinHeap.poll();
            if (a != b) {
                Console.log("fuck a = {}, b = {}", a, b);
            }
        }
    }

    private static class Inner<T>{
        T val;
        
        public Inner(T val) {
            this.val = val;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Inner<?> inner = (Inner<?>) o;
            return Objects.equals(val, inner.val);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }
    }

    // ArrayList T请不要传基础数据类型的包装类
    private List<Inner<T>> data;
    private int heapSize;
    // 反向索引通过元素找到在堆中的下标
    private Map<Inner<T>, Integer> indexMap;
    private Comparator<Inner<T>> comparator;

    public MyHeapGreater(Comparator<T> comparator) {
        this(comparator, 0);
    }

    public MyHeapGreater(Comparator<T> comparator, int initialSize) {
        if (comparator == null) {
            throw new RuntimeException("this comparator is null");
        }
        init(comparator, initialSize);
    }

    private void init(Comparator<T> comparator, int initialSize) {
        this.data = new ArrayList<>(initialSize);
        this.heapSize = 0;
        this.indexMap = new HashMap<>(initialSize);
        this.comparator = (a, b) -> comparator.compare(a.val, b.val) != 0 ? 
                comparator.compare(a.val, b.val) : a.hashCode() - b.hashCode();
    }
    
    public boolean isEmpty() {
        return heapSize == 0;
    }
    
    public void offer(T t) {
        Inner<T> val = new Inner<>(t);
        data.add(val);
        indexMap.put(val, heapSize);
        heapInsert(heapSize++);
    }

    private T peek() {
        return data.get(0).val;
    }
    
    public T poll() {
        Inner<T> ans = data.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        data.remove(--heapSize);
        heapify(0);
        return ans.val;
    }
    
    // 增强堆方法 - 删除堆中指定元素
    public T remove(T t) {
        Inner<T> replace = data.get(heapSize - 1);
        Inner<T> inner = new Inner<>(t);
        int index = indexMap.get(inner);
        Inner<T> ans = data.get(index);
        indexMap.remove(inner);
        data.remove(--heapSize);
        if (!inner.equals(replace)) {
            data.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
        return ans.val;
    }
    
    public void heapInsert(int index) {
        while (comparator.compare(data.get(index), data.get((index - 1) / 2)) > 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }
    
    public void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && 
                    comparator.compare(data.get(left + 1), data.get(left)) > 0 ? left + 1 : left;
            largest = comparator.compare(data.get(largest), data.get(index)) > 0 ? largest : index;
            if (largest == index) {
                break;
            }
            swap(index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }
    
    
    public void swap(int a, int b) {
        Inner<T> vA = data.get(a);
        Inner<T> vB = data.get(b);
        data.set(a, vB);
        data.set(b, vA);
        indexMap.put(vA, b);
        indexMap.put(vB, a);
    }
    
    private void resign(Inner<T> inner) {
        heapInsert(indexMap.get(inner));
        heapify(indexMap.get(inner));
    }
    
}
