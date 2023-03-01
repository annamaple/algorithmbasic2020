package class15.me;

import class15.Code03_NumberOfIslandsII;

import java.util.LinkedList;
import java.util.List;

public class Code03_NumberOfIslandsIIImpl extends Code03_NumberOfIslandsII {

    public static void main(String[] args) {
        int m = 3;
        int n = 3;
        int[][] positions =
                {{0, 1}, {1, 2}, {2, 1}, {1, 0}, {0, 2}, {0, 0}, {1, 1}};
        System.out.println(new NumberOfIslandsII().numIslands2(m, n, positions));
    }


    public static class NumberOfIslandsII {

        public List<Integer> numIslands2(int m, int n, int[][] positions) {
            if (m < 0 || n < 0 || positions == null) return null;
            UnionFind uf = new UnionFind(m, n);
            List<Integer> ans = new LinkedList<>();
            for (int[] position : positions) {
                ans.add(uf.add(position[0], position[1]));
            }
            return ans;
        }

        public static class UnionFind {
            int[] parent;
            int[] size;
            int sets;
            int col;
            int row;
            int[] help;

            public UnionFind(int m, int n) {
                int size = m * n;
                this.parent = new int[size];
                for (int i = 0; i < size; i++) {
                    parent[i] = -1;
                }
                this.size = new int[size];
                this.sets = 0;
                this.row = m;
                this.col = n;
                this.help = new int[size];
            }

            public int findHead(int cur) {
                if (cur < 0 || cur >= parent.length) return -1;
                if (parent[cur] == -1) return -1;
                int p = 0;
                while (cur != parent[cur]) {
                    help[p++] = cur;
                    cur = parent[cur];
                }
                while (--p >= 0) {
                    parent[help[p]] = cur;
                }
                return cur;
            }

            public int add(int i, int j) {
                int cur = index(i, j);
                if (parent[cur] == -1) {
                    parent[cur] = cur;
                    sets++;
                    union(i, j, i - 1, j);
                    union(i, j, i + 1, j);
                    union(i, j, i, j - 1);
                    union(i, j, i, j + 1);
                }
                return sets;
            }

            public void union(int i1, int j1, int i2, int j2) {
                if (i1 < 0 || j1 < 0 || i2 < 0 || j2 < 0 || i1 >= row || j1 >= col || i2 >= row || j2 >= col) {
                    return;
                }
                union(index(i1, j1), index(i2, j2));
            }

            public void union(int a, int b) {
                int headA = findHead(a);
                int headB = findHead(b);
                if (headA == -1 || headB == -1) {
                    return;
                }
                if (headA != headB) {
                    int sizeA = size[headA];
                    int sizeB = size[headB];
                    int more = sizeA >= sizeB ? headA : headB;
                    int less = more == headA ? headB : headA;
                    parent[less] = more;
                    size[more] = sizeA + sizeB;
                    size[less] = 0;
                    sets--;
                }
            }

            public int index(int i, int j) {
                return i * col + j;
            }

            public int sets() {
                return this.sets;
            }
        }
    }
}
