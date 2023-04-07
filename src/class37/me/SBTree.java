package class37.me;

public class SBTree<K extends Comparable<K>> implements SortList<K> {

    @Override
    public int smaller(K k) {
        int ans = 0;
        SBNode<K> cur = this.root;
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
        SBNode<K> cur = this.root;
        while (cur != null) {
            if (k.compareTo(cur.k) < 0) {
                cur = cur.l;
            } else if (k.compareTo(cur.k) >= 0) {
                ans += cur.all - all(cur.r);
                cur = cur.r;
            }
        }
        return ans;
    }

    @Override
    public void put(K k) {
        this.root = add(this.root, k);
    }

    private SBNode<K> root;

    // 查询小于等于K的SBNode
    private SBNode<K> findLessK(K k) {
        SBNode<K> cur = this.root, ans = null;
        while (cur != null) {
            if (k.compareTo(cur.k) < 0) {
                cur = cur.l;
            } else if (k.compareTo(cur.k) > 0) {
                ans = cur;
                cur = cur.r;
            } else {
                ans = cur;
                break;
            }
        }
        return ans;
    }

    private SBNode<K> add(SBNode<K> cur, K k) {
        if (cur == null) {
            return new SBNode<>(k);
        }
        if (k.compareTo(cur.k) == 0) {
            cur.all++;
            return cur;
        }
        cur.size++;
        cur.all++;
        if (k.compareTo(cur.k) < 0) {
            cur.l = add(cur.l, k);
        } else {
            cur.r = add(cur.r, k);
        }
        return maintain(cur);
    }

    private SBNode<K> maintain(SBNode<K> cur) {
        if (cur == null) return null;
        int l = size(cur.l);
        int ll = l == 0 ? 0 : size(cur.l.l);
        int lr = l == 0 ? 0 : size(cur.l.r);
        int r = size(cur.r);
        int rr = r == 0 ? 0 : size(cur.r.r);
        int rl = r == 0 ? 0 : size(cur.r.l);
        boolean flag = false;
        if (ll > r) {
            cur = rightRotate(cur);
            flag = true;
        } else if (lr > r) {
            cur.l = leftRotate(cur.l);
            cur = rightRotate(cur);
            flag = true;
        } else if (rr > l) {
            cur = leftRotate(cur);
            flag = true;
        } else if (rl > l) {
            cur.r = rightRotate(cur.r);
            cur = leftRotate(cur);
        }
        if (flag) {
            cur.l = maintain(cur.l);
            cur.r = maintain(cur.r);
            cur = maintain(cur);
        }
        return cur;
    }

    private SBNode<K> rightRotate(SBNode<K> cur) {
        int same = all(cur) - all(cur.l) - all(cur.r);
        SBNode<K> l = cur.l;
        cur.l = l.r;
        l.r = cur;
        pushup(cur);
        pushup(l);
        // modify all
        l.all = cur.all;
        cur.all = all(cur.l) + all(cur.r) + same;
        return l;
    }

    private SBNode<K> leftRotate(SBNode<K> cur) {
        int same = all(cur) - all(cur.l) - all(cur.r);
        SBNode<K> r = cur.r;
        cur.r = r.l;
        r.l = cur;
        pushup(cur);
        pushup(r);
        // modify all
        r.all = cur.all;
        cur.all = all(cur.l) + all(cur.r) + same;
        return r;
    }

    private void pushup(SBNode<K> cur) {
        if (cur != null) {
            cur.size = size(cur.l) + size(cur.r) + 1;
        }
    }

    private int size(SBNode<K> cur) {
        return cur == null ? 0 : cur.size;
    }

    private int all(SBNode<K> cur) {
        return cur == null ? 0 : cur.all;
    }

    private static class SBNode<K> {
        K k;
        int size;
        SBNode<K> l, r;
        // 经过当前节点的个数，包括自身
        int all;

        public SBNode(K k) {
            this.k = k;
            this.size = 1;
            this.all = 1;
        }
    }
}