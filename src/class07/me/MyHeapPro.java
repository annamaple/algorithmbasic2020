package class07.me;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 增强堆
 * 最小堆
 *
 * @author lei
 */
public class MyHeapPro<T> {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyHeapPro<Integer> minHeap = new MyHeapPro<>((a, b) -> a - b);
        while (true) {
            int num = scanner.nextInt();
            if (num == 0) {
                break;
            }
            minHeap.offer(num);
        }
        scanner.close();
    }


    private ArrayList<T> heap;
    private int heapSize;
    private Map<T, Integer> indexMap;
    private Comparator<T> comparator;

    public MyHeapPro(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.heapSize = 0;
        this.indexMap = new HashMap<>();
        this.comparator = comparator;
    }

    public void offer(T t) {
        heap.add(t);
        indexMap.put(t, heapSize);
        heapSize++;
        heapInsert(heapSize - 1);
    }

    public T poll() {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        heap.remove(heapSize - 1);
        heapSize--;
        heapify(0);
        return ans;
    }
    
    public void remove(T t) {
        int indexT = indexMap.get(t);
        swap(indexT, heapSize - 1);
        indexMap.remove(t);
        heap.remove(heapSize - 1);
        heapSize--;
        resign(indexT);
    }
    
    public List<T> getAllElements() {
        return new ArrayList<>(heap);
    }
    
    public T peek() {
        return heap.get(0);
    }
    
    public boolean isEmpty() {
        return heapSize == 0;
    }

    public void heapInsert(int index) {
        while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && comparator.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            largest = comparator.compare(heap.get(largest), heap.get(index)) < 0 ? largest : index;
            if (index == largest) {
                break;
            }
            swap(index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }
    
    public void resign(int index) {
        heapInsert(index);
        heapify(index);
    }


    private void swap(int indexA, int indexB) {
        T a = heap.get(indexA);
        T b = heap.get(indexB);
        heap.set(indexA, b);
        heap.set(indexB, a);
        indexMap.put(a, indexB);
        indexMap.put(b, indexA);
    }


}
