package class31.me;

import class31.Code02_FallingSquares;

import java.util.LinkedList;
import java.util.List;

// Le699
public class Code02_FallingSquaresImpl extends Code02_FallingSquares {

    public static void main(String[] args) {
        int[][] positions = {
                {1, 2},
                {2, 3},
                {6, 1}
        };
         FallingSquares f = new FallingSquares();
        List<Integer> ans = f.fallingSquares(positions);
        System.out.println(ans);
    }

    // 落方块问题：返回最高高度
    public static class FallingSquares {

        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        // positions[0] left位置
        // positions[1] 正方形方块边长
        public List<Integer> fallingSquares(int[][] positions) {
            List<Integer> ans = new LinkedList<>();
            int max = Integer.MIN_VALUE;
            for (int[] p : positions) {
                max = Math.max(max, p[0] + p[1]);
            }
            SegmentTree segmentTree = new SegmentTree(max);
            for (int[] p : positions) {
                segmentTree.add(p[0], p[0] + p[1] - 1, p[1], 1, max, 1);
                ans.add(segmentTree.queryMax());
            }
            return ans;
        }

        private static class SegmentTree {

            int MAX;
            int[] arr;
            int[] max;
            int[] lazy;

            public SegmentTree(int len) {
                this.MAX = len;
                this.arr = new int[MAX];
                this.max = new int[MAX << 2];
                this.lazy = new int[MAX << 2];
            }

            public void pushup(int rt) {
                max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
            }

            public void pushdown(int rt) {
                if (lazy[rt] != 0) {
                    lazy[rt << 1] += lazy[rt];
                    max[rt << 1] += lazy[rt];
                    lazy[rt << 1 | 1] += lazy[rt];
                    max[rt << 1 | 1] += lazy[rt];
                    lazy[rt] = 0;
                }
            }

            public void add(int L, int R, int C, int l, int r, int rt) {
                if (L <= l && r <= R) {
                    max[rt] += C;
                    lazy[rt] += C;
                    return;
                }
                int mid = l + (r - l) / 2;
                pushdown(rt);
                if (L <= mid) {
                    add(L, R, C, l, mid, rt << 1);
                }
                if (R > mid) {
                    add(L, R, C, mid + 1, r, rt << 1 | 1);
                }
                pushup(rt);
            }

            public int queryMax() {
                return max[1];
            }

        }

    }
}
