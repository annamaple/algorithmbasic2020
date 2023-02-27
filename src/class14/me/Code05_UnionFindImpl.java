package class14.me;

import class14.Code05_UnionFind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Code05_UnionFindImpl extends Code05_UnionFind {

    public static class Node<T> {
        T t;

        public Node(T t) {
            this.t = t;
        }
    }

    public static class UnionFind<T> {
        private Map<T, Node<T>> nodeMap;
        private Map<Node<T>, Node<T>> parentMap;
        private Map<Node<T>, Integer> sizeMap;

        public UnionFind(List<T> values) {
            this.nodeMap = new HashMap<>();
            this.parentMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
            if (values != null && !values.isEmpty()) {
                for (T t : values) {
                    Node<T> node = new Node<>(t);
                    nodeMap.put(t, node);
                    parentMap.put(node, node);
                    sizeMap.put(node, 1);
                }
            }
        }


        // 查找代表节点
        public Node<T> findFather(Node<T> node) {
            Stack<Node<T>> path = new Stack<>();
            while (parentMap.get(node) != node) {
                path.push(node);
                node = parentMap.get(node);
            }
            while (!path.isEmpty()) {
                parentMap.put(path.pop(), node);
            }
            return node;
        }


        // 是否在同一个集合中
        public boolean isSameSet(T t1, T t2) {
            return findFather(nodeMap.get(t1)) == findFather(nodeMap.get(t2));
        }

        // 合并a b所在的集合
        public void union(T a, T b) {
            Node<T> aF = findFather(nodeMap.get(a));
            Node<T> bF = findFather(nodeMap.get(b));
            if (aF != bF) {
                int aSize = sizeMap.get(aF);
                int bSize = sizeMap.get(bF);
                Node<T> more = aSize >= bSize ? aF : bF;
                Node<T> less = more == aF ? bF : aF;
                parentMap.put(less, more);
                sizeMap.put(more, aSize + bSize);
                sizeMap.remove(less);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }
}
