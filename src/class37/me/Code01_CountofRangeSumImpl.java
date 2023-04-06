package class37.me;

import class37.Code01_CountofRangeSum;

public class Code01_CountofRangeSumImpl extends Code01_CountofRangeSum {

    // 求数组arr的子数组累加和在range范围（闭区间）的个数range [low, high]
    // 思路与转换：累加和(注意整形越界) => 前缀和数组sum + 已i结尾的子数组中，有哪些前缀和在range范围中
    // i > j, 若sum[i] - sum[j] in [low, high], 则arr中[j + 1, j]区间和in[low, high]
    public static void main(String[] args) {
        int len = 6;
        int varible = 50;
        for (int i = 0; i < 10000; i++) {
            int[] test = generateArray(len, varible);
            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
            int upper = lower + (int) (Math.random() * varible);
            int ans1 = countRangeSum1(test, lower, upper);
            int ans2 = new CountOfRangeSumSortedMap().countOfRangeSum(test, lower, upper);
            if (ans1 != ans2) {
                printArray(test);
                System.out.println(lower);
                System.out.println(upper);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("fuck");
                return;
            }
        }
        System.out.println("nice");

    }


    private static class CountOfRangeSumMergeSort {

        public int countOfRangeSum(int[] arr, int low, int upper) {
            if (arr == null || upper < low) return 0;
            int N = arr.length;
            long[] sum = new long[N + 1];
            for (int i = 0; i < N; i++) {
                sum[i + 1] = sum[i] + arr[i];
            }
            // int[] sum = new int[N];
            // sum[0] = arr[0];
            // for (int i = 1; i < N; i++) {
            //     sum[i] = sum[i - 1] + arr[i];
            // }
            return mergeSort(sum, 0, N, low, upper);
        }

        private int mergeSort(long[] sum, int left, int right, int low, int upper) {
            if (left == right) {
                // 单独自身已经在sum[right] - sum[0]处理
                return 0;
//                return sum[left] >= low && sum[right] <= upper ? 1 : 0;
            }
            // 问题转换为分别已当前？结尾的子数组累加和在[low, upper]内
            // 利用merge的有序，窗口不回退
            int mid = left + ((right - left) >> 1);
            return mergeSort(sum, left, mid, low, upper) +
                    mergeSort(sum, mid + 1, right, low, upper) +
                    merge(sum, left, mid, right, low, upper);
        }

        private int merge(long[] sum, int left, int mid, int right, int low, int upper) {
            int l1 = left, l2 = left;
            // sum[r] - sum[l] in [low, upper]
            // sum[l] in  [sum[r] - upper, sum[r] - low]
            int count = 0;
            for (int r = mid + 1; r <= right; r++) {
                while (l1 <= mid && sum[l1] < sum[r] - upper) {
                    l1++;
                }
                while (l2 <= mid && sum[l2] <= sum[r] - low) {
                    l2++;
                }
                count += l2 - l1;
            }
            // merge
            int p1 = left, p2 = mid + 1, i = 0;
            long[] help = new long[right - left + 1];
            while (p1 <= mid && p2 <= right)
                help[i++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
            while (p1 <= mid)
                help[i++] = sum[p1++];
            while (p2 <= right)
                help[i++] = sum[p2++];
            System.arraycopy(help, 0, sum, left, help.length);
            return count;
        }

    }

    private static class CountOfRangeSumSegTree {

        public int countOfRangeSum(int[] arr, int lower, int upper) {
            if (arr == null || upper < lower) return 0;
            int N = arr.length;
            long[] sum = new long[N + 1];
            for (int i = 0; i < N; i++) {
                sum[i + 1] = sum[i] + arr[i];
            }
            SegmentTree sg = new SegmentTree(N);
            // [0, N - 1] -> [1, N] 
            // 0(N2LogN) gg
            int count = 0;
            for (int i = 1; i <= N; i++) {
                sg.add(i, i, arr[i - 1], 1, N, 1);
                for (int start = 1; start <= i; start++) {
                    long query = sg.query(start, i, 1, N, 1);
                    if (query >= lower && query <= upper) {
                        count++;
                    }
                }
            }
            return count;
        }

        private static class SegmentTree {
            int MAX;
            int[] arr;
            long[] sum;
            int[] lazy;
            int[] change;
            boolean[] update;

            public SegmentTree(int[] origin) {
                this.MAX = origin.length + 1;
                this.arr = new int[MAX];
                System.arraycopy(origin, 0, this.arr, 1, origin.length);
                this.sum = new long[MAX << 2];
                this.lazy = new int[MAX << 2];
                this.change = new int[MAX << 2];
                this.update = new boolean[MAX << 2];
            }

            public SegmentTree(int len) {
                this.MAX = len + 1;
                this.arr = new int[MAX];
                this.sum = new long[MAX << 2];
                this.lazy = new int[MAX << 2];
                this.change = new int[MAX << 2];
                this.update = new boolean[MAX << 2];
            }

            private void pushup(int rt) {
                sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
            }

            private void pushdown(int rt, int ln, int rn) {
                if (update[rt]) {
                    update[rt << 1] = true;
                    update[rt << 1 | 1] = true;
                    change[rt << 1] = change[rt];
                    change[rt << 1 | 1] = change[rt];
                    lazy[rt << 1] = 0;
                    lazy[rt << 1 | 1] = 0;
                    sum[rt << 1] = (long) change[rt] * ln;
                    sum[rt << 1 | 1] = (long) change[rt] * rn;
                    update[rt] = false;
                }
                if (lazy[rt] != 0) {
                    sum[rt << 1] += (long) lazy[rt] * ln;
                    sum[rt << 1 | 1] += (long) lazy[rt] * rn;
                    lazy[rt << 1] += lazy[rt];
                    lazy[rt << 1 | 1] += lazy[rt];
                    lazy[rt] = 0;
                }
            }

            public void build(int l, int r, int rt) {
                if (l == r) {
                    sum[rt] = arr[l];
                    return;
                }
                int mid = l + ((r - l) >> 1);
                build(l, mid, rt << 1);
                build(mid + 1, r, rt << 1 | 1);
                pushup(rt);
            }

            // 原[L~R]范围上的元素增加C
            public void add(int L, int R, int C, int l, int r, int rt) {
                if (L <= l && r <= R) {
                    sum[rt] += (long) C * (r - l + 1);
                    lazy[rt] += C;
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

            // 原[L~R]范围上的元素变为C
            public void update(int L, int R, int C, int l, int r, int rt) {
                if (L <= l && r <= R) {
                    sum[rt] = (long) C * (r - l + 1);
                    update[rt] = true;
                    change[rt] = C;
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

            // 查询原[L~R]范围的累加和
            public long query(int L, int R, int l, int r, int rt) {
                if (L <= l && r <= R) {
                    return sum[rt];
                }
                int mid = l + ((r - l) >> 1);
                pushdown(rt, mid - l + 1, r - mid);
                int res = 0;
                if (L <= mid) {
                    res += query(L, R, l, mid, rt << 1);
                }
                if (R > mid) {
                    res += query(L, R, mid + 1, R, rt << 1 | 1);
                }
                // 不需要pushup, query当前sum[rt]的值不变
                // pushup(rt);
                return res;
            }


        }
    }

    private static class CountOfRangeSumSortedMap {

        public int countOfRangeSum(int[] arr, int lower, int upper) {
            if (arr == null || upper < lower) return 0;
            int N = arr.length;
            int[] sum = new int[N + 1];
            for (int i = 0; i < N; i++) {
                sum[i + 1] = sum[i] + arr[i];
            }
            SortList<Integer> sortTable = new SBTree<>();
            // 每次找到已i结尾的子数组有多少累加和在[lower, upper]
            // sum[i] - sum[j] in [lower, upper]; j < i;
            // sum[j] in [sum[i] - lower, sum[i] - upper]
            int count = 0;
            for (int i = 0; i <= N; i++) {
                count += sortTable.between(sum[i] - upper, sum[i] - lower);
                sortTable.put(sum[i]);
            }
            return count;
        }
        
    }
}
