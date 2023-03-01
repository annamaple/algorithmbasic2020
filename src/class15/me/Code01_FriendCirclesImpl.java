package class15.me;

import class15.Code01_FriendCircles;

/**
 * @author lei
 */
public class Code01_FriendCirclesImpl extends Code01_FriendCircles {

    public static void main(String[] args) {
        int[][] m = {{1, 1, 0}, {1, 1, 0}, {0, 0, 1}};
        System.out.println(new FriendCircles().findCircleNum(m));
    }

    public static class FriendCircles {

        public int findCircleNum(int[][] M) {
            return new UnionFind(M).sets;
        }
    }

    public static class UnionFind {
        int[] deputy;
        int[] size;
        int sets;
        // for path zip
        int[] help;

        public UnionFind(int[][] m) {
            this(m.length);
            int n = m.length;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (m[i][j] == 1) union(i, j);
                }
            }
        }

        public UnionFind(int n) {
            this.sets = n;
            this.deputy = new int[n];
            this.size = new int[n];
            this.help = new int[n];
            for (int i = 0; i < n; i++) {
                deputy[i] = i;
                size[i] = 1;
            }
        }

        public int findDeputy(int i) {
            int p = 0;
            while (i != deputy[i]) {
                help[p++] = i;
                i = deputy[i];
            }
            while (--p >= 0) {
                deputy[help[p]] = i;
            }
            return i;
        }

        public void union(int a, int b) {
            int deputyA = findDeputy(a);
            int deputyB = findDeputy(b);
            if (deputyA != deputyB) {
                int more = size[deputyA] >= size[deputyB] ? deputyA : deputyB;
                int less = more == deputyA ? deputyB : deputyA;
                deputy[less] = more;
                size[more] += size[less];
                size[less] = 0;
                sets--;
            }
        }
    }
}
