package class16.me;

import class16.Code06_Dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lei
 */
public class Code06_DijkstraImpl extends Code06_Dijkstra {

    public static void main(String[] args) {
        // (1)--1--(2)--5--(4)
        //   3\            /
        //    (3)---------2
        //
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Edge edge1 = new Edge(1, node1, node2);
        Edge edge5 = new Edge(5, node2, node3);
        Edge edge3 = new Edge(3, node1, node3);
        Edge edge2 = new Edge(2, node3, node4);
        node1.edges.add(edge1);
        node1.edges.add(edge3);
        node2.edges.add(edge5);
        node3.edges.add(edge2);
        node1.nexts.add(node2);
        node1.nexts.add(node3);
        node2.nexts.add(node4);
        node3.nexts.add(node4);
        Node from = node1;
        System.out.println("my dijkstra");
        new Dijkstra().dijkstra(from)
                .forEach((k, v) -> System.out.println("node: " + k.val + ", distance: " + v));
    }

    public static class Dijkstra {

        public Map<Node, Integer> dijkstra(Node from) {
            Map<Node, Integer> distanceMap = new HashMap<>();
            Heap heap = new Heap();
            heap.addOrUpdateOrIgnore(from, 0);
            while (!heap.isEmpty()) {
                Info info = heap.poll();
                distanceMap.put(info.node, info.distance);
                int distance = info.distance;
                for (Edge edge : info.node.edges) {
                    heap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
                }
            }

            return distanceMap;
        }

    }

    public static class Heap {
        Map<Node, Info> relateMap = new HashMap<>();
        Map<Info, Integer> indexMap = new HashMap<>();
        List<Info> data = new ArrayList<>();
        int heapSize;

        private void offer(Info info) {
            if (indexMap.containsKey(info)) {
                return;
            }
            data.add(info);
            indexMap.put(info, heapSize);
            relateMap.put(info.node, info);
            heapSize++;
            heapInsert(heapSize - 1);
        }

        public Info poll() {
            Info ans = data.get(0);
            swap(0, heapSize - 1);
            data.remove(heapSize - 1);
            heapSize--;
            heapify(0, heapSize);
            indexMap.remove(ans);
            relateMap.remove(ans.node);
            return ans;
        }

        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (!relateMap.containsKey(node)) {
                // add
                Info info = new Info(node, distance);
                offer(info);
            } else {
                Info info = relateMap.get(node);
                if (distance < info.distance) {
                    info.distance = distance;
                    heapInsert(indexMap.get(info));
                }
            }
        }

        public void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int less = left + 1 < heapSize && data.get(left + 1).distance < data.get(left).distance ? left + 1 : left;
                less = data.get(less).distance < data.get(index).distance ? less : index;
                if (less == index) {
                    break;
                }
                swap(index, less);
                index = less;
                left = index * 2 + 1;
            }
        }

        public void heapInsert(int index) {
            while (data.get(index).distance < data.get((index - 1) / 2).distance) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        void swap(int idx1, int inx2) {
            Info a = data.get(idx1);
            Info b = data.get(inx2);
            data.set(idx1, b);
            data.set(inx2, a);
            indexMap.put(b, idx1);
            indexMap.put(a, inx2);
        }
    }

    public static class Info {
        Node node;
        int distance;

        public Info(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class Node {
        int val;
        int in;
        int out;
        List<Node> nexts;
        List<Edge> edges;

        public Node(int val) {
            this.val = val;
            this.nexts = new ArrayList<>();
            this.edges = new ArrayList<>();
        }
    }

    public static class Edge {
        int weight;
        Node from;
        Node to;

        public Edge(int weight, Node from, Node to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }
}
