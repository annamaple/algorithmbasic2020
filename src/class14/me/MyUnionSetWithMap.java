package class14.me;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


// 并查集核心方法：findFather, union
// 核心逻辑：parents表 size表
public class MyUnionSetWithMap<V> {

    Map<V, Node<V>> nodes;
    Map<Node<V>, Node<V>> parents;
    Map<Node<V>, Integer> sizeMap;

    public MyUnionSetWithMap(List<V> list) {
        nodes = new HashMap<>();
        parents = new HashMap<>();
        sizeMap = new HashMap<>();
        Node<V> node;
        for (V v : list) {
            node = new Node<>(v);
            nodes.put(v, node);
            parents.put(node, node);
            sizeMap.put(node, 1);
        }
    }

    public Node<V> findFather(Node<V> cur) {
        Stack<Node<V>> path = new Stack<>();
        while (cur != parents.get(cur)) {
            path.push(cur);
            cur = parents.get(cur);
        }
        while (!path.isEmpty()) {
            parents.put(path.pop(), cur);
        }
        return cur;
    }

    public boolean isSameSet(V a, V b) {
        return findFather(nodes.get(a)) == findFather(nodes.get(b));
    }

    public void union(V a, V b) {
        Node<V> aHead = findFather(nodes.get(a));
        Node<V> bHead = findFather(nodes.get(b));
        if (aHead != bHead) {
            int aSize = sizeMap.get(aHead);
            int bSize = sizeMap.get(bHead);
            Node<V> big = aSize >= bSize ? aHead : bHead, small = big == aHead ? bHead : aHead;
            parents.put(small, big);
            sizeMap.put(big, aSize + bSize);
            sizeMap.remove(small);
        }
    }

    public int sets() {
        return sizeMap.size();
    }

    public static class Node<V> {
        V v;

        public Node(V v) {
            this.v = v;
        }
    }
}
