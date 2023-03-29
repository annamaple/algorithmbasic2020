package class31.me;

import class31.Code02_FallingSquares;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

// Le699
public class Code02_FallingSquaresImpl extends Code02_FallingSquares {

    public static void main(String[] args) {

        int times = 10000;
        while (--times >= 0) {
            int maxLeft = (int) (Math.random() * 20 + 1);
            int maxLen = (int) (Math.random() * 100 + 11);
            int N = (int) (Math.random() * 100 + 11);
            int[][] positions = getTwoArr(maxLeft, maxLen, N);
//            int[][] positions = {{2, 1}, {2, 2}, {1, 2}, {1, 1}};
            List<Integer> ans1 = new FallingSquares().fallingSquares(positions);
            List<Integer> ans2 = new Code02_FallingSquares().fallingSquares(positions);
            if (!equalArr(ans1, ans2)) {
                System.out.println("times: " + (10000 - times) + " gg ");
                printArr(positions);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("fuck");
                return;
            }
        }
        System.out.println("nice");
    }

    public static void printArr(int[][] arr) {
        int[] r = new int[arr.length];
        for (int j = 0; j < arr[0].length; j++) {
            for (int i = 0; i < arr.length; i++) {
                r[i] = arr[i][j];
            }
            printArr(r);
        }
    }

    public static void printArr(int[] arr) {
        for (int n : arr) {
            System.out.print(n + "\t");
        }
        System.out.println();
    }


    public static int[][] getTwoArr(int maxLeft, int maxLen, int N) {
        int[][] arr = new int[N][2];
        for (int i = 0; i < N; i++) {
            arr[i][0] = (int) ((Math.random() * maxLeft) + 1);
            arr[i][1] = (int) (Math.random() * maxLen + 1);
        }
        return arr;
    }

    public static boolean equalArr(List<Integer> a, List<Integer> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < a.size(); i++) {
            if (!Objects.equals(a.get(i), b.get(i))) {
                return false;
            }
        }
        return true;
    }

    // 落方块问题：返回最高高度
    public static class FallingSquares {

        public HashMap<Integer, Integer> index(int[][] positions) {
            TreeSet<Integer> pos = new TreeSet<>();
            for (int[] arr : positions) {
                pos.add(arr[0]);
                pos.add(arr[0] + arr[1] - 1);
            }
            HashMap<Integer, Integer> map = new HashMap<>();
            int count = 0;
            for (Integer index : pos) {
                map.put(index, ++count);
            }
            return map;
        }

        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        // positions[0] left位置
        // positions[1] 正方形方块边长
        public List<Integer> fallingSquares(int[][] positions) {
            List<Integer> ans = new LinkedList<>();
            int N = Integer.MIN_VALUE;
            for (int[] p : positions) {
                N = Math.max(N, p[0] + p[1]);
            }
            SegmentTree segmentTree = new SegmentTree(--N);
            int max = 0;
            for (int[] p : positions) {
                int L = p[0];
                int R = p[0] + p[1] - 1;
                int height = segmentTree.query(L, R, 1, N, 1) + p[1];
                max = Math.max(max, height);
                ans.add(max);
                segmentTree.update(L, R, height, 1, N, 1);
            }
            return ans;
        }

        private static class SegmentTree {

            int MAX;
            int[] arr;
            int[] max;
            int[] change;
            boolean[] update;

            public SegmentTree(int len) {
                this.MAX = len + 1;
                this.arr = new int[MAX];
                this.max = new int[MAX << 2];
                this.change = new int[MAX << 2];
                this.update = new boolean[MAX << 2];
            }

            public void pushup(int rt) {
                max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
            }

            public void pushdown(int rt) {
                if (update[rt]) {
                    update[rt << 1] = true;
                    update[rt << 1 | 1] = true;
                    change[rt << 1] = change[rt];
                    change[rt << 1 | 1] = change[rt];
                    max[rt << 1] = change[rt];
                    max[rt << 1 | 1] = change[rt];
                    update[rt] = false;
                }
            }

            public void update(int L, int R, int C, int l, int r, int rt) {
                if (L <= l && r <= R) {
                    max[rt] = C;
                    change[rt] = C;
                    update[rt] = true;
                    return;
                }
                int mid = l + (r - l) / 2;
                pushdown(rt);
                if (L <= mid) {
                    update(L, R, C, l, mid, rt << 1);
                }
                if (R > mid) {
                    update(L, R, C, mid + 1, r, rt << 1 | 1);
                }
                pushup(rt);
            }

            public int query(int L, int R, int l, int r, int rt) {
                if (L <= l && r <= R) {
                    return max[rt];
                }
                int mid = l + ((r - l) >> 1);
                pushdown(rt);
                int max = Integer.MIN_VALUE;
                if (L <= mid) {
                    max = Math.max(max, query(L, R, l, mid, rt << 1));
                }
                if (R > mid) {
                    max = Math.max(max, query(L, R, mid + 1, r, rt << 1 | 1));
                }
                return max;
            }

        }

    }
}
