package class03.me;

public class MyDeque<E> {

    E[] arr;
    int size;
    int limit;
    int head;
    int tail;

    public MyDeque(int limit) {
        this.limit = limit;
        this.arr = (E[]) new Object[limit];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void offerFirst(E e) {
        if (!isEmpty()) {
            head = nextIndex(head);
        }
        arr[head] = e;
        size++;
    }

    public void offerLast(E e) {
        if (!isEmpty()) {
            tail = lastIndex(tail);
        }
        arr[tail] = e;
        size++;
    }

    public E pollFirst() {
        E e = arr[head];
        if (size > 1) {
            head = lastIndex(head);
        }
        size--;
        return e;
    }

    public E pollLast() {
        E e = arr[tail];
        if (size > 1) {
            tail = nextIndex(tail);
        }
        size--;
        return e;
    }

    public E peekFirst() {
        return arr[head];
    }

    public E peekLast() {
        return arr[tail];
    }

    private int nextIndex(int i) {
        return i == limit - 1 ? 0 : i + 1;
    }

    private int lastIndex(int i) {
        return i == 0 ? limit - 1 : i - 1;
    }
}
