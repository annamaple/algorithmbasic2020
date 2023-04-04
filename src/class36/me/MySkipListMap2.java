package class36.me;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Skip List 跳表
 */
public class MySkipListMap2<K extends Comparable<K>, V> {

    public static void main(String[] args) {
        Map<Integer, Integer> map = new TreeMap<>();
        MySkipListMap2<Integer, Integer> myMap = new MySkipListMap2<>();
        Random random = new Random();
        List<Integer> kList = new ArrayList<>();
        int times = 10000;
        a:
        while (--times >= 0) {
            int t = 100;
            while (--t >= 0) {
                int k = random.nextInt(1000);
                kList.add(k);
                int v = random.nextInt(1 << 10);
                map.put(k, v);
                myMap.put(k, v);
                int findK = kList.get((int) (Math.random() * kList.size()));
                int mv1 = map.get(findK);
                int mv2 = myMap.get(findK);
                if (mv1 != mv2 && map.size() == myMap.size) {
                    System.out.println(StrUtil.format("mv1: {}, mv2: {}. fuck！！！",
                            mv1, mv2));
                    break a;
                }
            }
        }
        System.out.println("nice");
    }

    private static final Double PROBABILITY = 0.5D;

    private Node<K, V> head;
    private int maxLevel;
    private int size;

    public MySkipListMap2() {
        this.head = new Node<>(null, null, 0);
        this.head.nextNodes.add(null);
        this.maxLevel = 0;
        this.size = 0;
    }

    public void put(K k, V v) {
        Node<K, V> node = getNode(k);
        if (node != null) {
            node.v = v;
        } else {
            this.size++;
            add(k, v);
        }
    }

    public void remove(K k) {
        if (containsKey(k)) {
            del(k);
            this.size--;
        }
    }

    public int size() {
        return this.size;
    }

    public V get(K k) {
        Node<K, V> node;
        return (node = getNode(k)) == null ? null : node.v;
    }

    private Node<K, V> getNode(K k) {
        if (k == null) return null;
        Node<K, V> pre = mostRightLessInMap(k), next;
        return (next = pre.nextNodes.get(0)) != null && next.isEqualK(k) ? next : null;
    }

    public boolean containsKey(K k) {
        if (k == null) return false;
        Node<K, V> pre = mostRightLessInMap(k), next;
        return (next = pre.nextNodes.get(0)) != null && next.isEqualK(k);
    }

    // 上游调用时判断重复
    private void add(K k, V v) {
        int level = randomLevel();
        while (level > this.maxLevel) {
            this.head.nextNodes.add(null);
            this.maxLevel++;
        }
        int curLevel = this.maxLevel;
        Node<K, V> node = new Node<>(k, v, level);
        Node<K, V> pre;
        while (curLevel >= 0) {
            pre = mostRightLessKInLevel(this.head, k, curLevel);
            if (level >= curLevel) {
                node.nextNodes.set(curLevel, pre.nextNodes.get(curLevel));
                pre.nextNodes.set(curLevel, node);
            }
            curLevel--;
        }
    }

    // 上游调用判断是否存在
    private void del(K k) {
        int curLevel = this.maxLevel;
        Node<K, V> pre, next;
        while (curLevel >= 0) {
            pre = mostRightLessKInLevel(this.head, k, curLevel);
            next = pre.nextNodes.get(curLevel);
            if (next != null && next.isEqualK(k)) {
                pre.nextNodes.set(curLevel, next.nextNodes.get(curLevel));
            }
            if (curLevel != 0 && pre == this.head && pre.nextNodes.get(curLevel) == null) {
                pre.nextNodes.remove(curLevel);
            }
            curLevel--;
        }
    }

    // 当前跳表中小于K且距离K最近的
    public Node<K, V> mostRightLessInMap(K k) {
        if (k == null) return null;
        // 从顶层往下层找
        int level = this.maxLevel;
        Node<K, V> cur = this.head;
        while (level >= 0) {
            cur = mostRightLessKInLevel(cur, k, level--);
        }
        return cur;
    }

    // 在当前层中找到小于k且离k最近的
    public Node<K, V> mostRightLessKInLevel(Node<K, V> cur, K k, int level) {
        Node<K, V> next = cur.nextNodes.get(level);
        while (next != null && next.isLessK(k)) {
            cur = next;
            next = next.nextNodes.get(level);
        }
        return cur;
    }


    private static class Node<K extends Comparable<K>, V> {
        K k;
        V v;
        List<Node<K, V>> nextNodes;

//        public Node(K k, V v) {
//            this.k = k;
//            this.v = v;
//            this.nextNodes = new ArrayList<>();
//        }

        public Node(K k, V v, int level) {
            this.k = k;
            this.v = v;
            this.nextNodes = new ArrayList<>();
            for (int i = 0; i <= level; i++) {
                nextNodes.add(null);
            }
        }

        private boolean isLessK(K k) {
            return this.k == null || (k != null && this.k.compareTo(k) < 0);
        }

        private boolean isEqualK(K k) {
            return (this.k == null && k == null) || (this.k != null && k != null && this.k.compareTo(k) == 0);
        }
    }

    private int randomLevel() {
        int level = 0;
        while (Math.random() < PROBABILITY) {
            level++;
        }
        return level;
    }
}
