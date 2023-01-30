package class03.me;

import class03.Code07_TwoQueueImplementStack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author lei
 */
public class Code07_TwoQueueImplementStackImpl extends Code07_TwoQueueImplementStack {

    public static class TwoQueueStack<E> {

        private Queue<E> queue;
        private Queue<E> help;

        public TwoQueueStack() {
            this.queue = new LinkedList<>();
            this.help = new LinkedList<>();
        }

        public void push(E e) {
            queue.add(e);
        }

        public E poll() {
            if (queue.isEmpty()) {
                throw new RuntimeException("this stack is empty");
            }
            int size = queue.size();
            for (int i = 0; i < size - 1; i++) {
                help.add(queue.remove());
            }
            E ans = queue.poll();
            swapQueueAndHelp();
            return ans;
        }
        
        public E peek() {
            if (queue.isEmpty()) {
                throw new RuntimeException("this stack is empty");
            }
            int size = queue.size();
            for (int i = 0; i < size - 1; i++) {
                help.add(queue.remove());
            }
            E ans = queue.remove();
            help.add(ans);
            swapQueueAndHelp();
            return ans;
        }
        
        public boolean isEmpty() {
            return queue.isEmpty();
        }
        
        public void swapQueueAndHelp() {
            Queue<E> temp = queue;
            queue = help;
            help = temp;
        }
    }

    // for test

    public static void main(String[] args) {
        System.out.println("test begin");
        TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
        Stack<Integer> test = new Stack<>();
        int testTime = 1000000;
        int max = 1000000;
        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty()) {
                if (!test.isEmpty()) {
                    System.out.println("Oops");
                }
                int num = (int) (Math.random() * max);
                myStack.push(num);
                test.push(num);
            } else {
                if (Math.random() < 0.25) {
                    int num = (int) (Math.random() * max);
                    myStack.push(num);
                    test.push(num);
                } else if (Math.random() < 0.5) {
                    if (!myStack.peek().equals(test.peek())) {
                        System.out.println("Oops");
                    }
                } else if (Math.random() < 0.75) {
                    if (!myStack.poll().equals(test.pop())) {
                        System.out.println("Oops");
                    }
                } else {
                    if (myStack.isEmpty() != test.isEmpty()) {
                        System.out.println("Oops");
                    }
                }
            }
        }

        System.out.println("test finish!");
    }
}
