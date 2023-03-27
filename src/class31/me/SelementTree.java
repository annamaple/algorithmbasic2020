package class31.me;

public class SelementTree {

    int MAX;
    int[] arr;
    int[] sum;
    int[] lazy;
    int[] change;
    boolean[] update;

    public SelementTree(int[] origin) {
        MAX = origin.length + 1;
        arr = new int[MAX];
        System.arraycopy(origin, 0, arr, 1, origin.length);
        sum = new int[MAX << 2];
        lazy = new int[MAX << 2];
        change = new int[MAX << 2];
        update = new boolean[MAX << 2];
    }

    public void pushup(int rt) {
        sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
    }

    public void pushdown(int rt, int ln, int rn) {
        if (update[rt]) {
            update[rt << 1] = true;
            update[rt << 1 | 1] = true;
            change[rt << 1] = change[rt];
            change[rt << 1 | 1] = change[rt];
            lazy[rt << 1] = 0;
            lazy[rt << 1 | 1] = 0;
            sum[rt << 1] = ln * change[rt];
            sum[rt << 1 | 1] = rn * change[rt];
            update[rt] = false;
        }
        if (lazy[rt] != 0) {
            lazy[rt << 1] += lazy[rt];
            lazy[rt << 1 | 1] += lazy[rt];
            sum[rt << 1] += ln * lazy[rt];
            sum[rt << 1 | 1] += rn * lazy[rt];
            lazy[rt] = 0;
        }
    }

    // fill sum
    public void build(int l, int r, int rt) {
        if (l == r) {
            sum[r] = arr[r];
        }
        int mid = l + ((r - l) >> 1);
        build(l, mid, rt << 1);
        build(mid + 1, r, rt << 1 | 1);
        pushup(rt);
    }

    public void add(int L, int R, int C, int l, int r, int rt) {
        if (L <= l && r <= R) {
            lazy[rt] += C;
            sum[rt] += (r - l + 1) * C;
            return;
        }
        int mid = l + ((r - l) >> 1);
        pushdown(rt, mid - l + 1, r - mid);
        if (L <= mid) {
            add(L, R, C, l, mid, rt << 1);
        }
        if (R > mid) {
            add(L, R, C, mid + 1, r, rt << 1 | 1);
        }
        pushup(rt);
    }

    public void update(int L, int R, int C, int l, int r, int rt) {
        if (L <= l && r <= R) {
            update[rt] = true;
            change[rt] = C;
            sum[rt] = (r - l + 1) * C;
            lazy[rt] = 0;
            return;
        }
        int mid = l + ((r - l) >> 1);
        pushdown(rt, mid - l + 1, r - mid);
        if (L <= mid) {
            update(L, R, C, l, mid, rt << 1);
        }
        if (R > mid) {
            update(L, R, C, mid + 1, r, rt << 1 | 1);
        }
        pushup(rt);
    }

    public long query(int L, int R, int l, int r, int rt) {
        if (L <= l && r <= R) {
            return sum[rt];
        }
        int mid = l + ((r - l) >> 1);
        pushdown(rt, mid - l + 1, r - mid);
        int ans = 0;
        if (L <= mid) {
            ans += query(L, R, l, mid, rt << 1);
        }
        if (R > mid) {
            ans += query(L, R, mid + 1, r, rt << 1 | 1);
        }
        return ans;
    }

}
