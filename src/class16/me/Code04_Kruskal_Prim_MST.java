package class16.me;

import class16.Code04_Kruskal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/**
 * @author lei
 */
public class Code04_Kruskal_Prim_MST {

    public static void main(String[] args) {
        // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
        int len = 5;
        int[][] graph = new int[len][len];
        Random random = new Random();
        Set<Integer> randomSet = new HashSet<>();
        int randomNum;
        for (int i = 0; i < graph.length; i++) {
            for (int j = i + 1; j < graph[i].length; j++) {
                do {
                    randomNum = random.nextInt(100);
                }
                while (randomSet.contains(randomNum));
                randomSet.add(randomNum);
                graph[i][j] = graph[j][i] = randomNum;
            }
        }
        
//        int[][] graph = {
//                {0, 1, 1, Integer.MAX_VALUE},
//                {0, 0, 3, 2},
//                {0, 0, 0, 6},
//                {0, 0, 0, 0}
//        };
        for (int[] arr : graph) {
            for (int n : arr) {
                System.out.print(n + "\t");
            }
            System.out.println();
        }
        // 
        List<Edge> kruskalMST = new MST().kruskalMST(new GraphBuild().build(graph));
        System.out.println("kruskal MST:");
        kruskalMST.forEach(a -> System.out.print(a.weight + " "));
        System.out.println();
        System.out.println("prim MST: ");
        List<Edge> primMST = new MST().primMST(new GraphBuild().build(graph));
        primMST.forEach(a -> System.out.print(a.weight + " "));
        System.out.println();
    }

    // 最小生成树 
    public static class MST {
        
        // K算法：每次找最小权重且不会出现环的边
        public List<Edge> kruskalMST(Graph graph) {
            PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
            for (Edge edge : graph.edges) {
                minHeap.offer(edge);
            }
            UnionFind uf = new UnionFind(graph.nodes.values());
            List<Edge> ans = new ArrayList<>();
            while (uf.sets > 1 && !minHeap.isEmpty()) {
                Edge edge = minHeap.poll();
                if (uf.union(edge.form, edge.to)) {
                    ans.add(edge);
                }
            }
            return ans;
        }
        
        // P算法：先解锁任意点再解锁边(权重最小)再点(点未解锁)再边...
        public List<Edge> primMST(Graph graph) {
            PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.weight));
            // 记住已解锁的点
            Set<Node> nodeSet = new HashSet<>();
            List<Edge> ans = new ArrayList<>();
            for (Node node : graph.nodes.values()) {
                if (!nodeSet.contains(node)) {
                    nodeSet.add(node);
                    minHeap.addAll(node.edges);
                    while (!minHeap.isEmpty()) {
                        Edge edge = minHeap.poll();
                        if (!nodeSet.contains(edge.to)) {
                            Node toNode = edge.to;
                            nodeSet.add(toNode);
                            ans.add(edge);
                            minHeap.addAll(toNode.edges);
                        }
                    }
                }
            }
            return ans;
        }

    }

    
    public static class UnionFind {
        
        Map<Node, Node> parent;
        Map<Node, Integer> sizeMap;
        int sets;

        public UnionFind(Collection<Node> nodes) {
            this.sets = nodes.size();
            this.parent = new HashMap<>(sets);
            this.sizeMap = new HashMap<>(sets);
            for (Node node : nodes) {
                parent.put(node, node);
                sizeMap.put(node, 1);
            }
        }
        
        public Node findHead(Node cur) {
            Queue<Node> queue = new LinkedList<>();
            while (parent.get(cur) != cur) {
                queue.add(cur);
                cur = parent.get(cur);
            }
            while (!queue.isEmpty()) {
                parent.put(queue.poll(), cur);
            }
            return cur;
        }
        
        // true 表示合并； false表示沒
        public boolean union(Node a, Node b) {
            Node headA = findHead(a);
            Node headB = findHead(b);
            boolean flag =  false;
            if (headA != headB) {
                int sizeA = sizeMap.get(headA);
                int sizeB = sizeMap.get(headB);
                Node more = sizeA >= sizeB ? headA : headB;
                Node less = more == headA ? headB : headA;
                parent.put(less, more);
                sizeMap.put(more, sizeA +sizeB);
                sizeMap.put(less, 0);
                sets--;
                flag = true;
            }
            return flag;
        }
        
        public int sets() {
            return this.sets;
        }
    }

    public static class Node {
        int val;
        // 入读
        int in;
        // 出度
        int out;
        // 邻点
        List<Node> nexts = new ArrayList<>();
        // 邻边
        List<Edge> edges = new ArrayList<>();

        public Node(int val) {
            this.val = val;
        }
    }

    public static class Edge {
        int weight;
        Node form;
        Node to;

        public Edge(int weight, Node form, Node to) {
            this.weight = weight;
            this.form = form;
            this.to = to;
        }
    }

    public static class Graph {
        Map<Integer, Node> nodes = new HashMap<>();
        Set<Edge> edges = new HashSet<>();
    }

    public static class GraphBuild {

        // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
        public Graph build(int[][] arr) {
            Graph graph = new Graph();
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    if (i == j) continue;
                    Node from = graph.nodes.computeIfAbsent(i, Node::new);
                    Node to = graph.nodes.computeIfAbsent(j, Node::new);
                    Edge edge = new Edge(arr[i][j], from, to);
                    from.nexts.add(to);
                    from.edges.add(edge);
                    from.out += 1;
                    to.in += 1;
                    graph.edges.add(edge);
                }
            }
            return graph;
        }
    }
}
