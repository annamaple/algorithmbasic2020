package class03;

/**
 * @author lei
 */
public class Code04_RingArrayImpl extends Code04_RingArray {

    public static class Queue {
        int[] arr;
        int head;
        int tail;
        int size;
        final int limit;

        public Queue(int limit) {
            this.arr = new int[limit];
            this.head = 0;
            this.tail = 0;
            this.size = 0;
            this.limit = limit;
        }

        public void push(int num) {
            if (size == limit) {
                throw new RuntimeException("this queue is fulled");
            }
            size++;
            arr[head] = num;
            head = nextIndex(head);
        }

        public int pop() {
            if (size == 0) {
                throw new RuntimeException("this queue is empty");
            }
            size--;
            int res = arr[tail];
            tail = nextIndex(tail);
            return res;
        }

        private int nextIndex(int index) {
            return index < limit - 1 ? index : 0;
        }
    }
}
