package class36.me;

public class MyAVLTreeMap<K extends Comparable<K>, V> {

    private static class Node<K, V> {
        K k;
        V v;
        int h;
        Node<K, V> l, r;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
            this.h = 1;
        }
    }

    private Node<K, V> root;
    private int size;

    public MyAVLTreeMap() {
        this.root = null;
        this.size = 0;
    }

    public void put(K k, V v) {
        Node<K, V> node = findLastIndex(k);
        if (k.compareTo(node.k) == 0) {
            node.v = v;
        } else {
            size++;
            root = add(root, k, v);
        }
    }

    public void remove(K k) {
        Node<K, V> node = findLastIndex(k);
        if (k.compareTo(node.k) == 0) {
            size--;
            root = del(root, k);
        }
    }

    public V get(K k) {
        Node<K, V> node = findLastIndex(k);
        return k.compareTo(node.k) == 0 ? node.v : null;
    }

    public int size() {
        return size;
    }

    public boolean containsKey(K k) {
        Node<K, V> node = findLastIndex(k);
        return node != null && node.k.compareTo(k)  == 0 ? true : false;
    }


    private Node<K, V> rightRotate(Node<K, V> cur) {
        Node<K, V> l = cur.l;
        cur.l = l.r;
        l.r = cur;
        // fix h
        pushup(cur);
        pushup(l);
        return l;
    }

    private Node<K, V> leftRatate(Node<K, V> cur) {
        Node<K, V> r = cur.r;
        cur.r = r.l;
        r.l = cur;
        pushup(cur);
        pushup(r);
        return r;
    }

    private Node<K, V> maintain(Node<K, V> cur) {
        if (cur == null) return null;
        boolean flag = false;
        if (Math.abs(high(cur.l) - high(cur.r)) > 1) {
            if (high(cur.l) > high(cur.r)) {
                if (high(cur.l.r) > high(cur.l.l)) {
                    cur.l = leftRatate(cur.l);
                }
                cur = rightRotate(cur);
            } else {
                if (high(cur.r.l) > high(cur.r.r)) {
                    cur.r = rightRotate(cur.r);
                }
                cur = leftRatate(cur);
            }
        }
        return cur;
    }

    //  私有方法仅添加，其他判断由上游判断处理
    private Node<K, V> add(Node<K, V> cur, K k, V v) {
        if (cur == null) {
            return new Node<>(k, v);
        }
        if (k.compareTo(cur.k) < 0) {
            cur.l = add(cur.l, k, v);
        } else {
            cur.r = add(cur.r, k, v);
        }
        // 记住维护cur的高度
        pushup(cur);
        return maintain(cur);
    }

    private Node<K, V> del(Node<K, V> cur, K k) {
        if (k.compareTo(cur.k) < 0) {
            cur.l = del(cur.l, k);
        } else if (k.compareTo(cur.k) > 0) {
            cur.r = del(cur.r, k);
        } else {
            if (cur.l == null || cur.r == null) {
                return cur.l == null ? cur.r : cur.l;
            } else {
                Node<K, V> des = cur.r;
                while (des.l != null) {
                    des = des.l;
                }
                cur.r = del(cur.r, des.k);
                des.l = cur.l;
                des.r = cur.r;
                cur = des;
            }
        }
        // 记住维护cur的高度
        pushup(cur);
        return maintain(cur);
    }

    private Node<K, V> findLastIndex(K k) {
        Node<K, V> pre = root, cur = root;
        while (cur != null) {
            pre = cur;
            if (k.compareTo(cur.k) == 0) {
                break;
            } else if (k.compareTo(cur.k) < 0) {
                cur = cur.l;
            } else {
                cur = cur.r;
            }
        }
        return pre;
    }

    private Node<K, V> findLastNoSmallIndex(K k) {
        Node<K, V> ans = null, cur = root;
        while (cur != null) {
            if (k.compareTo(cur.k) == 0) {
                ans = cur;
                break;
            } else if (k.compareTo(cur.k) < 0) {
                ans = cur;
                cur = cur.l;
            } else {
                cur = cur.r;
            }
        }
        return ans;
    }

    private Node<K, V> findLastNoBigIndex(K k) {
        Node<K, V> ans = null, cur = root;
        while (cur != null) {
            if (k.compareTo(cur.k) == 0) {
                ans = cur;
                break;
            } else if (k.compareTo(cur.k) < 0) {
                cur = cur.l;
            } else {
                ans = cur;
                cur = cur.r;
            }
        }
        return ans;
    }


    private int high(Node<K, V> node) {
        return node == null ? 0 : node.h;
    }

    private void pushup(Node<K, V> node) {
        if (node != null) {
            node.h = Math.max(high(node.l), high(node.r)) + 1;
        }
    }
}
