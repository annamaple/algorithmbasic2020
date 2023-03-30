package class35.me;

import class35.Code01_AVLTreeMap;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Code01_AVLTreeMapImpl extends Code01_AVLTreeMap {


    public static void main(String[] args) {

        TreeMap<Integer, String> map = new TreeMap<>();
        AVLTreeMap<Integer, String> avlTreeMap = new AVLTreeMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, "@" + i + "@");
            avlTreeMap.put(i, "@" + i + "@");
        }
        List<AVLTreeMap.AVLNode<Integer, String>> list = avlTreeMap.getAll();
        AtomicInteger i = new AtomicInteger();
        map.forEach((k, v) -> {
            if (!v.equals(list.get(i.get()).v)) {
                System.out.println(StrUtil.format(
                        "map: k = {}, v = {}; avlTreeMap: k = {}, v = {}", 
                        k, v, list.get(i.get()).k, list.get(i.get()).v
                ));
                System.out.println("fuck");
                System.exit(-1);
            }
            i.getAndIncrement();
        });
        System.out.println("nice");
    }

    public static class AVLTreeMap<K extends Comparable<K>, V> {

        private AVLNode<K, V> root;
        private int size;

        public AVLTreeMap() {
            this.root = null;
            this.size = 0;
        }

        public void put(K k, V v) {
            if (k == null) return;
            AVLNode<K, V> lastNode = findLastIndex(k);
            if (lastNode != null && k.compareTo(lastNode.k) == 0) {
                lastNode.v = v;
            } else {
                size++;
                root = add(root, k, v);
            }
        }

        public V get(K k) {
            return containsKey(k) ? findLastIndex(k).v : null;
        }

        public List<AVLNode<K, V>> getAll() {
            List<AVLNode<K, V>> res = new ArrayList<>();
            AVLNode<K, V> cur = root, mostRight;
            while (cur != null) {
                mostRight = cur.l;
                if (mostRight != null) {
                    while (mostRight.r != null && mostRight.r != cur) {
                        mostRight = mostRight.r;
                    }
                    if (mostRight.r == null) {
                        mostRight.r = cur;
                        cur = cur.l;
                        continue;
                    } else {
                        mostRight.r = null;
                    }
                }
                res.add(cur);
                cur = cur.r;
            }
            return res;
        }


        public void remove(K k) {
            if (k == null) return;
            if (containsKey(k)) {
                size--;
                root = del(root, k);
            }
        }


        public boolean containsKey(K k) {
            AVLNode<K, V> lastNode;
            return k != null && (lastNode = findLastIndex(k)) != null && lastNode.k.compareTo(k) == 0;
        }


        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> cur = root, pre = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) == 0) {
                    break;
                }
                if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }


        // 旋转当前节点 - 并把新的头节点返回
        //      cur
        //      / \
        //     l   r
        //    / \
        //   ll lr
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            // 维护高度
            cur.h = Math.max(high(cur.l), high(cur.r)) + 1;
            left.h = Math.max(high(left.l), high(left.r)) + 1;
            return left;
        }

        // 旋转当前节点 - 并把新的头节点返回
        //         cur
        //         / \ 
        //        l   r
        //           / \
        //          rl  rr
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> r = cur.r;
            cur.r = r.l;
            r.l = cur;
            // 维护高度
            cur.h = Math.max(high(cur.l), high(cur.r)) + 1;
            r.h = Math.max(high(r.l), high(r.r)) + 1;
            return r;
        }

        // 向上调整平衡
        private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            if (Math.abs(high(cur.l) - high(cur.r)) > 1) {
                if (high(cur.l) > high(cur.r)) {
                    if (high(cur.l.l) < high(cur.l.r)) {
                        cur.l = leftRotate(cur.l);
                    }
                    cur = rightRotate(cur);
                } else {
                    if (high(cur.r.r) < high(cur.r.l)) {
                        cur.r = rightRotate(cur.r);
                    }
                    cur = leftRotate(cur);
                }
            }
            return cur;
        }


        // 不考虑cur.key == key，上游忽略
        // 在cur树上，添加（key, value）
        // 返回cur这棵树的新头
        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new AVLNode<>(key, value);
            }
            if (key.compareTo(cur.k) < 0) {
                cur.l = add(cur.l, key, value);
            } else {
                cur.r = add(cur.r, key, value);
            }
            // 维度高度
            cur.h = Math.max(high(cur.l), high(cur.r)) + 1;
            return maintain(cur);
        }

        //        p
        //       /
        //      cur
        //     / \
        //    l   r
        //       /
        //      rl
        // 不考虑key不存在
        // 在cur树上，删除k = key的节点
        // 返回cur这棵树的新头
        private AVLNode<K, V> del(AVLNode<K, V> cur, K key) {
            if (key.compareTo(cur.k) < 0) {
                cur.l = del(cur.l, key);
            } else if (key.compareTo(cur.k) > 0) {
                cur.r = del(cur.r, key);
            } else {
                if (cur.l == null || cur.r == null) {
                    cur = cur.r == null ? cur.l : cur.r;
                } else {
                    AVLNode<K, V> des = cur.r;
                    while (des.l != null) {
                        des = des.l;
                    }
                    cur.r = del(cur, des.k);
                    des.l = cur.l;
                    des.r = cur.r;
                    cur = des;
                }
            }
            if (cur != null) {
                cur.h = Math.max(high(cur.l), high(cur.r)) + 1;
            }
            return maintain(cur);
        }


        public static class AVLNode<K extends Comparable<K>, V> {
            K k;
            V v;
            AVLNode<K, V> l, r;
            int h;

            public AVLNode(K k, V v) {
                this.k = k;
                this.v = v;
                this.h = 1;
            }
        }

        private int high(AVLNode<K, V> node) {
            return node == null ? 0 : node.h;
        }

    }
}
