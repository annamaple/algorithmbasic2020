package class16.me;

import class16.Code02_DFS;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * @author lei
 */
public class Code02_DFSImpl extends Code02_DFS {

    public static void main(String[] args) {
        
        // 1 —— 2
        //  \  /
        //   5
        //   |
        //   3 - 4
        int[][] path = {
                {2, 1, 2},
                {2, 2, 1},
                {4, 1, 5},
                {4, 5, 1},
                {6, 2, 5},
                {6, 5, 2},
                {1, 3, 5},
                {1, 5, 3},
                {7, 4, 3},
                {7, 3, 4}
        };
        Graph<Integer> graph = GraphBuilder.build(path);
        System.out.println("df1");
        List<Node<Integer>> list = dfs(graph.nodes.get(1));
        list.forEach(n -> System.out.print(n.val + " "));
        System.out.println();
        System.out.println("df2");
        list = dfs2(graph.nodes.get(1));
        list.forEach(n -> System.out.print(n.val + " "));
        System.out.println();
        System.out.println("递归实现");
        List<Node<Integer>> ans = new LinkedList<>();
        dfs(graph.nodes.get(1), new HashSet<>(), ans);
        ans.stream().map(e -> e.val).forEach(System.out::println);
    }
    
    public static <T> void dfs(Node<T> node, Set<Node<T>> set, List<Node<T>> ans) {
        if (!set.contains(node)) {
            ans.add(node);
            set.add(node);
            for (Node<T> next : node.nexts) {
                dfs(next, set, ans);
            }
        }
    }

    public static <T> List<Node<T>> dfs(Node<T> start) {
        List<Node<T>> ans = new LinkedList<>();
        if (start != null) {
            Stack<Node<T>> stack = new Stack<>();
            Set<Node<T>> set = new HashSet<>();
            // 入栈是进行数据处理
            stack.push(start);
            set.add(start);
            ans.add(start);
            while (!stack.isEmpty()) {
                Node<T> cur = stack.pop();
                for (Node<T> node : cur.nexts) {
                    if (!set.contains(node)) {
                        // 保留现场
                        stack.push(cur);
                        stack.push(node);
                        set.add(node);
                        ans.add(node);
                        break;
                    }
                }
            }
        }
        return ans;
    }


    public static <T> List<Node<T>> dfs2(Node<T> start) {
        List<Node<T>> ans = new LinkedList<>();
        if (start != null) {
            Stack<Node<T>> stack = new Stack<>();
            Set<Node<T>> set = new HashSet<>();
            // 入栈是进行数据处理
            stack.push(start);
            while (!stack.isEmpty()) {
                Node<T> cur = stack.pop();
                if (!set.contains(cur)) {
                    ans.add(cur);
                    set.add(cur);
                    stack.push(cur);
                    stack.addAll(cur.nexts);
                }
            }
        }
        return ans;
    }
}
