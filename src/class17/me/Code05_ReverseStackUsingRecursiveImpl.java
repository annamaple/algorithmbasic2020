package class17.me;

import class17.Code05_ReverseStackUsingRecursive;

import java.util.Stack;

/**
 * 使用递归的方式反转栈
 */
public class Code05_ReverseStackUsingRecursiveImpl extends Code05_ReverseStackUsingRecursive {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        System.out.println("stack: ");
        System.out.println(stack);
        System.out.println("reverse");
        new ReverseStackUsingRecursive().reverse(stack);
        System.out.println("stack: ");
        System.out.println(stack);
    }


    public static class ReverseStackUsingRecursive {

        public void reverse(Stack<Integer> stack) {
            if (stack.size() <= 1) return;
            int bottom = getBottom(stack);
            reverse(stack);
            stack.push(bottom);
        }

        /**
         * 获取栈最低部元素，不改变栈其他元素的顺序
         */
        int getBottom(Stack<Integer> stack) {
            int cur = stack.pop();
            if (stack.isEmpty()) {
                return cur;
            }
            int ans = getBottom(stack);
            stack.push(cur);
            return ans;
        }
    }
}
