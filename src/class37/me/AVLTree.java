package class37.me;

public class AVLTree<K extends Comparable<K>> implements SortList<K> {

    @Override
    public int smaller(K k) {
        int ans = 0;
        AVLNode<K> cur = this.root;
        while (cur != null) {
            if (k.compareTo(cur.k) <= 0) {
                cur = cur.l;
            } else if (k.compareTo(cur.k) > 0) {
                ans += all(cur) - all(cur.r);
                cur = cur.r;
            }
        }
        return ans;
    }

    @Override
    public int smallerAndEquals(K k) {
        int ans = 0;
        AVLNode<K> cur = this.root;
        while (cur != null) {
            if (k.compareTo(cur.k) < 0) {
                cur = cur.l;
            } else {
                ans += all(cur) - all(cur.r);
                cur = cur.r;
            }
        }
        return ans;
    }

    @Override
    public void put(K k) {
        this.root = add(this.root, k);
    }

    private AVLNode<K> root;

    private boolean containsK(K k) {
        AVLNode<K> node = findLessK(k);
        return node != null && node.k.compareTo(k) == 0;
    }

    // <= k
    private AVLNode<K> findLessK(K k) {
        AVLNode<K> cur = this.root, ans = null;
        while (cur != null) {
            if (k.compareTo(cur.k) < 0) {
                cur = cur.l;
            } else {
                ans = cur;
                cur = cur.r;
            }
        }
        return ans;
    }

    private AVLNode<K> add(AVLNode<K> cur, K k) {
        if (cur == null) {
            return new AVLNode<>(k);
        }
        if (k.compareTo(cur.k) == 0) {
            cur.all++;
            return cur;
        }
        cur.all++;
        if (k.compareTo(cur.k) < 0) {
            cur.l = add(cur.l, k);
        } else {
            cur.r = add(cur.r, k);
        }
        pushup(cur);
        return maintain(cur);
    }

    private AVLNode<K> maintain(AVLNode<K> cur) {
        if (cur == null) return null;
        int l = high(cur.l);
        int ll = l == 0 ? 0 : high(cur.l.l);
        int lr = l == 0 ? 0 : high(cur.l.r);
        int r = high(cur.r);
        int rr = r == 0 ? 0 : high(cur.r.r);
        int rl = r == 0 ? 0 : high(cur.r.l);
        if (Math.abs(l - r) > 1) {
            if (l > r) {
                if (lr > ll) {
                    cur.l = leftRotate(cur.l);
                }
                cur = rightRotate(cur);
            } else {
                if (rl > rr) {
                    cur.r = rightRotate(cur.r);
                }
                cur = leftRotate(cur);
            }
        }
        return cur;
    }

    private AVLNode<K> rightRotate(AVLNode<K> cur) {
        int same = all(cur) - all(cur.l) - all(cur.r);
        AVLNode<K> l = cur.l;
        cur.l = l.r;
        l.r = cur;
        pushup(cur);
        pushup(l);
        l.all = cur.all;
        cur.all = all(cur.l) + all(cur.r) + same;
        return l;
    }

    private AVLNode<K> leftRotate(AVLNode<K> cur) {
        int same = all(cur) - all(cur.l) - all(cur.r);
        AVLNode<K> r = cur.r;
        cur.r = r.l;
        r.l = cur;
        pushup(cur);
        pushup(r);
        r.all = cur.all;
        cur.all = all(cur.l) + all(cur.r) + same;
        return r;
    }

    private void pushup(AVLNode<K> cur) {
        if (cur != null) {
            cur.h = Math.max(high(cur.l), high(cur.r)) + 1;
        }
    }

    private int high(AVLNode<K> cur) {
        return cur == null ? 0 : cur.h;
    }

    private int all(AVLNode<K> cur) {
        return cur == null ? 0 : cur.all;
    }

    private static class AVLNode<K extends Comparable<K>> {

        K k;
        int h;
        int all;
        AVLNode<K> l, r;

        public AVLNode(K k) {
            this.k = k;
            this.h = 1;
            this.all = 1;
        }
    }


}
