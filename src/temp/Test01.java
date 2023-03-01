package temp;

import java.util.Stack;

public class Test01 {

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack);
        f(stack);
        System.out.println(stack);
    }

    static Integer f(Stack<Integer> stack) {
        Integer result = stack.pop();
        if (stack.empty()) {
            return result;
        }
        Integer last = f(stack);
        stack.push(result);
        return last;
    }
}
