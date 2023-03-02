package class16.me;

import java.util.*;

public class BFS {

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
        bfs(graph.nodes.get(1));
    }

    // 宽度优先遍历，随便选一个节点
    public static  <T> void bfs(Node<T> start) {
        if (start == null) return;
        Queue<Node<T>> queue = new LinkedList<>();
        Set<Node<T>> set = new HashSet<>();
        queue.offer(start);
        set.add(start);
        while (!queue.isEmpty()) {
            Node<T> cur = queue.poll();
            System.out.println(cur.val);
            for (Node<T> node : cur.nexts) {
                if (!set.contains(node)) {
                    queue.offer(node);
                    set.add(node);
                }
            }
        }
    }
}
