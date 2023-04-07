package class37.me;

import java.util.ArrayList;
import java.util.List;

public class SkipList<K extends Comparable<K>> implements SortList<K> {

    @Override
    public int smaller(K k) {
        int ans = 0;
        SLNode<K> cur = this.head.nexts.get(0);
        while (cur != null && cur.k.compareTo(k) < 0) {
            ans += cur.count;
            cur = cur.nexts.get(0);
        }
        return ans;
    }

    @Override
    public int smallerAndEquals(K k) {
        int ans = 0;
        SLNode<K> cur = this.head.nexts.get(0);
        while (cur != null && cur.k.compareTo(k) <= 0) {
            ans += cur.count;
            cur = cur.nexts.get(0);
        }
        return ans;
    }

    @Override
    public void put(K k) {
        add(k);
    }

    private static final double PROBABILITY = 0.5D;
    private SLNode<K> head;
    private int size;
    private int maxLevel;

    public SkipList() {
        this.size = 0;
        this.head = new SLNode<>(null);
        this.head.nexts.add(null);
        this.maxLevel = 0;
    }


    private void add(K k) {
        int rLevel = randomRevel();
        while (maxLevel < rLevel) {
            this.head.nexts.add(null);
            maxLevel++;
        }
        int level = this.maxLevel;
        SLNode<K> node = new SLNode<>(k);
        SLNode<K> pre = this.head, next;
        while (level >= 0) {
            pre = findLessKInLevel(pre, level, k);
            next = pre.nexts.get(level);
            if (next != null && next.k.compareTo(k) == 0) {
                next.count++;
                return;
            }
            if (rLevel >= level) {
                node.nexts.set(level, pre.nexts.get(level));
                pre.nexts.set(level, node);
            }
            level--;
        }
    }

    private int randomRevel() {
        int level = 0;
        while (Math.random() < PROBABILITY) {
            level++;
        }
        return level;
    }

    private SLNode<K> findLessKInTree(K k) {
        SLNode<K> cur = this.head;
        int level = this.maxLevel;
        while (level >= 0) {
            cur = findLessKInLevel(cur, level--, k);
        }
        return cur;
    }

    // 当前层查询小于K的节点， cur当前层节点
    private SLNode<K> findLessKInLevel(SLNode<K> cur, int level, K k) {
        SLNode<K> pre = cur;
        while (cur != null && k.compareTo(cur.k) > 0) {
            pre = cur;
            cur = cur.nexts.get(level);
        }
        return pre;
    }

    private static class SLNode<K> {

        K k;
        // k的元素有多少个
        int count;
        List<SLNode<K>> nexts;

        public SLNode(K k) {
            this.k = k;
            this.nexts = new ArrayList<>();
            this.count = 1;
        }
    }

}
