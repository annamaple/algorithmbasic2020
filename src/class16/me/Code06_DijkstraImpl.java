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

    public static class Dijkstra {
        public Map<Node, Integer> dijkstra(Node from) {
            Map<Node, Integer> distanceMap = new HashMap<>();
            Heap heap = new Heap();
            heap.offer(new Info(from, 0));
            while (!heap.isEmpty()) {
                Info cur = heap.poll();
                for (Edge edge : cur.node.edges) {

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
            heapSize++;
            heapInsert(heapSize - 1);
        }

        public Info poll() {
            Info ans = data.get(0);
            swap(0, heapSize - 1);
            heapSize--;
            heapify(0, heapSize);
            return ans;
        }

        public void addOrUpdateOrIgnore(Info info) {

        }

        public void update(Info info) {
            int index = indexMap.get(info);
            heapInsert(index);
            heapify(index, heapSize);
        }

        public void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int less = left + 1 < heapSize && data.get(left + 1).distance < data.get(left).distance ? left + 1 : left;
                less = data.get(less).distance < data.get(index).distance ? left : index;
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
