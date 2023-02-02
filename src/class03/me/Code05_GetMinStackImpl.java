package class03.me;

import class03.Code05_GetMinStack;

import java.util.Stack;

/**
 * @author lei
 */
public class Code05_GetMinStackImpl extends Code05_GetMinStack {
    
    public static class MyMaxMinStack {
        private Stack<Integer> dataStack;
        private Stack<Integer> maxStack;
        private Stack<Integer> minStack;
        
        public void push(int value) {
            dataStack.push(value);
            
        }
    }
}
