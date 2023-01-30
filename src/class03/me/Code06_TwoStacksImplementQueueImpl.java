package class03.me;

import class03.Code06_TwoStacksImplementQueue;

import java.util.Stack;

public class Code06_TwoStacksImplementQueueImpl extends Code06_TwoStacksImplementQueue {

    public static class TwoStacksQueue<E> {

        Stack<E> pushStack;
        Stack<E> popStack;

        public TwoStacksQueue() {
            this.pushStack = new Stack<>();
            this.popStack = new Stack<>();
        }

        public void push(E e) {
            pushStack.add(e);
            pushToPop();
        }

        public E pop() {
            pushToPop();
            return popStack.pop();
        }

        public E peek() {
            pushToPop();
            return popStack.peek();
        }

        public void pushToPop() {
            if (popStack.empty()) {
                while (!pushStack.empty()) {
                    popStack.push(pushStack.pop());
                }
            }
        }
    }

}
