package class03.me;

import class03.Code05_GetMinStack;

import java.util.Stack;

/**
 * @author lei
 */
public class Code05_GetMinStackImpl extends Code05_GetMinStack {

    public static void main(String[] args) {
        MyMaxMinStack stack1 = new MyMaxMinStack();
        stack1.push(3);
        System.out.println(stack1.getmin());
        stack1.push(4);
        System.out.println(stack1.getmin());
        stack1.push(1);
        System.out.println(stack1.getmin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getmin());

        System.out.println("=============");

        MyStack1 stack2 = new MyStack1();
        stack2.push(3);
        System.out.println(stack2.getmin());
        stack2.push(4);
        System.out.println(stack2.getmin());
        stack2.push(1);
        System.out.println(stack2.getmin());
        System.out.println(stack2.pop());
        System.out.println(stack2.getmin());
    }
    
    
    public static class MyMaxMinStack {
        private Stack<Integer> dataStack;
        private Stack<Integer> maxStack;
        private Stack<Integer> minStack;

        public MyMaxMinStack() {
            this.maxStack = new Stack<>();
            this.minStack = new Stack<>();
            this.dataStack = new Stack<>();
        }

        public void push(int value) {
            if (dataStack.isEmpty()) {
                maxStack.push(value);
                minStack.push(value);
            } else {
                maxStack.push(value >= maxStack.peek() ? value : maxStack.peek());
                minStack.push(value <= minStack.peek() ? value : minStack.peek());
            }
            dataStack.push(value);
        }
        
        public int pop() {
            if (dataStack.isEmpty()) {
                throw new RuntimeException("stack is empty");
            }
            minStack.pop();
            maxStack.pop();
            return dataStack.pop();
        }
        
        public int getmax() {
            return maxStack.peek();
        }
        
        public int getmin() {
            return minStack.peek();
        }
    }
}
