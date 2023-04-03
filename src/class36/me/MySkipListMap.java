package class36.me;

import java.util.ArrayList;
import java.util.List;

public class MySkipListMap<K extends Comparable<K>, V> {

    private static class Node<K extends Comparable<K>, V> {
        K k;
        V v;
        List<Node<K, V>> nexts;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
            this.nexts = new ArrayList<>();
        }

        // 考虑头节点的null最小
        // this.k 是否比其他key小
        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (k == null || k.compareTo(otherKey) < 0);
        }

        public boolean isKeyEqual(K otherKey) {
            return (this.k == null && otherKey == null) || (this.k != null && otherKey != null);
        }
    }

    private static final double PROBABILITY = 0.5D;

    private Node<K, V> head;
    private int size;
    private int maxLevel;

    public MySkipListMap() {
        this.head = new Node<>(null, null);
        // 0层最末尾null
        this.head.nexts.add(null);
        this.size = 0;
        this.maxLevel = 0;
    }

    public V get(K k) {
        Node<K, V> less = mostRightLessNodeInTree(k);
        Node<K, V> find = less.nexts.get(0);
        return find != null && find.isKeyEqual(k) ? find.v : null;
    }

    public void put(K k, V v) {
        if (k == null) return;
        Node<K, V> less = mostRightLessNodeInTree(k);
        Node<K, V> find = less.nexts.get(0);
        if (find != null && find.isKeyEqual(k)) {
            find.v = v;
        } else {
            this.size++;
            int newNodeLevel = getNewNodeLevelLevel();
            while (newNodeLevel > maxLevel) {
                this.head.nexts.add(null);
                maxLevel++;
            }
            Node<K, V> newNode = new Node<>(k, v);
            for (int i = 0; i < newNodeLevel; i++) {
                newNode.nexts.add(null);
            }
            int level = maxLevel;
            Node<K, V> pre = this.head;
            while (level >= 0) {
                pre = mostRightLessNodeInLevel(k, pre, level);
                if (level <= newNodeLevel) {
                    newNode.nexts.set(level, pre.nexts.get(level));
                    pre.nexts.set(level, newNode);
                }
                level--;
            }
        }
    }

    public void remove(K k) {
        if (!containsKey(k)) {
            return;
        }
        this.size--;
        int level = this.maxLevel;
        Node<K, V> pre = this.head, next;
        while (level >= 0) {
            pre = mostRightLessNodeInLevel(k, pre, level);
            next = pre.nexts.get(level);
            // 判断当前层是否有k
            if (next != null && next.isKeyEqual(k)) {
                pre.nexts.set(level, next.nexts.get(level));
            }
            // 清除顶层的空(削层) help gc
            if (level != 0 && pre == head && pre.nexts.get(level) == null) {
                head.nexts.remove(level);
                maxLevel--;
            }
            level--;
        }
    }

    public boolean containsKey(K k) {
        if (k == null) {
            return false;
        }
        Node<K, V> less = mostRightLessNodeInTree(k);
        Node<K, V> node = less.nexts.get(0);
        return node != null && node.isKeyEqual(k);
    }


    private int getNewNodeLevelLevel() {
        int level = 0;
        while (Math.random() < PROBABILITY) {
            level++;
        }
        return level;
    }

    // 从最高层开始，找
    // 最终，找到第0层的key的最右节点
    private Node<K, V> mostRightLessNodeInTree(K k) {
        if (k == null) return null;
        int level = maxLevel;
        Node<K, V> cur = this.head;
        while (level >= 0) {
            cur = mostRightLessNodeInLevel(k, cur, level--);
        }
        return cur;
    }

    // cur 找到小于k最离k最近的节点
    private Node<K, V> mostRightLessNodeInLevel(K k, Node<K, V> cur, int level) {
        Node<K, V> next = cur.nexts.get(level);
        while (next != null && next.isKeyLess(k)) {
            cur = next;
            next = cur.nexts.get(level);
        }
        return cur;
    }


}
