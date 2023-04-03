package class36.me;

public class MySBTreeMap<K extends Comparable<K>, V> {

    private Node<K, V> root;

    public void put(K k, V v) {
        Node<K, V> node = findLastIndex(k);
        if (k.compareTo(node.k) == 0) {
            node.v = v;
        } else {
            root = add(root, k, v);
        }
    }

    public void remove(K k) {
        Node<K, V> node = findLastIndex(k);
        if (k.compareTo(node.k) == 0) {
            root = del(root, k);
        }
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

    private Node<K, V> del(Node<K, V> cur, K k) {
        if (k.compareTo(cur.k) < 0) {
            cur.size--;
            cur.l = del(cur.l, k);
        } else if (k.compareTo(cur.k) > 0) {
            cur.size--;
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
                pushup(cur);
            }
        }
        return cur;
    }

    private Node<K, V> add(Node<K, V> cur, K k, V v) {
        if (cur == null) {
            return new Node<>(k, v);
        }
        cur.size++;
        if (k.compareTo(cur.k) < 0) {
            cur.l = add(cur.l, k, v);
        } else {
            cur.r = add(cur.r, k, v);
        }
        return maintain(cur);
    }

    private Node<K, V> maintain(Node<K, V> cur) {
        if (cur == null) return null;
        int lSize = size(cur.l);
        int llSize = lSize == 0 ? 0 : size(cur.l.l);
        int lrSize = lSize == 0 ? 0 : size(cur.l.r);
        int rSize = size(cur.r);
        int rrSize = rSize == 0 ? 0 : size(cur.r.r);
        int rlSize = rSize == 0 ? 0 : size(cur.r.l);
        boolean flag = false;
        if (llSize > rSize) {
            cur = rightRatate(cur);
            flag = true;
        } else if (lrSize > rSize) {
            cur.l = leftRatate(cur.l);
            cur = rightRatate(cur);
            flag = true;
        } else if (rrSize > lSize) {
            cur = leftRatate(cur);
            flag = true;
        } else if (rlSize > lSize) {
            cur.r = rightRatate(cur.r);
            cur = leftRatate(cur);
            flag = true;
        }
        if (flag) {
            cur.l = maintain(cur.l);
            cur.r = maintain(cur.r);
            cur = maintain(cur);
        }
        return cur;
    }

    private Node<K, V> rightRatate(Node<K, V> cur) {
        Node<K, V> l = cur.l;
        cur.l = l.r;
        l.r = cur;
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

    private void pushup(Node<K, V> node) {
        if (node != null) {
            node.size = size(node.l) + size(node.r) + 1;
        }
    }

    private int size(Node<K, V> node) {
        return node == null ? 0 : node.size;
    }


    private static class Node<K, V> {
        K k;
        V v;
        int size;
        Node<K, V> l, r;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
            this.size = 1;
        }
    }
}
