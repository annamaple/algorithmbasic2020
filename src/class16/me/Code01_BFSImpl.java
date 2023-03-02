package class16.me;

import class16.Code01_BFS;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lei
 */
public class Code01_BFSImpl extends Code01_BFS {

    public static void main(String[] args) {
        // 1 2 5 3
        //  5 4 
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
        List<Node<Integer>> list = bfs(graph.nodes.get(1));
        System.out.println("bf1");
        list.forEach(n -> System.out.print(n.val + " "));
        System.out.println();
        System.out.println("bf2");
        list = bfs2(graph.nodes.get(1));
        list.forEach(n -> System.out.print(n.val + " "));
        System.out.println();
    }

    public static <T> List<Node<T>> bfs(Node<T> node) {
        List<Node<T>> ans = new LinkedList<>();
        if (node != null) {
            Queue<Node<T>> queue = new LinkedList<>();
            Set<Node<T>> set = new HashSet<>();
            // 进队列之前加判断，进队列同时进set
            set.add(node);
            queue.add(node);
            while (!queue.isEmpty()) {
                Node<T> cur = queue.poll();
                ans.add(cur);
                List<Node<T>> next = cur.nexts.stream().filter(e -> !set.contains(e)).collect(Collectors.toList());
                set.addAll(next);
                queue.addAll(next);
            }
        }
        return ans;
    }

    public static <T> List<Node<T>> bfs2(Node<T> node) {
        List<Node<T>> ans = new LinkedList<>();
        if (node != null) {
            Queue<Node<T>> queue = new LinkedList<>();
            Set<Node<T>> set = new HashSet<>();
            queue.offer(node);
            while (!queue.isEmpty()) {
                Node<T> cur = queue.poll();
                if (!set.contains(cur)) {
                    ans.add(cur);
                    set.add(cur);
                    cur.nexts.forEach(queue::offer);
                }
            }
        }
        return ans;
    }
}
