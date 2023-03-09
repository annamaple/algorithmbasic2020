package class21.me;

import class21.Code05_BobDie;

/**
 * 该题目和跳马问题类似
 * lintcode: 1084
 */
public class Code05_BobDieImpl extends Code05_BobDie {

    public static void main(String[] args) {
        int n = 3;
        int k = 2;
        int r = 0;
        int c = 0;
        System.out.println(new KnightProbability().knightProbability(n, k, r, c));
        System.out.println(new KnightProbability().knightProbabilityDp(n, k, r, c));

    }

    // tip: 跳马问题有问题
    /*
     * 已知一个 NxN 的国际象棋棋盘，棋盘的行号和列号都是从 0 开始。即最左上角的格子记为 (0, 0)，最右下角的记为 (N-1, N-1)。
     * 现有一个 “马”（也译作 “骑士”）位于 (r, c) ，并打算进行 K 次移动。
     * 现在 “马” 每一步都从可选的位置（包括棋盘外部的）中独立随机地选择一个进行移动，直到移动了 K 次或跳到了棋盘外面。
     * 求移动结束后，“马” 仍留在棋盘上的概率。
     */
    public static class KnightProbability {

        // (r,c)马的位置
        // k次移动
        // n * n棋盘大下
        public double knightProbability(int n, int k, int r, int c) {
            // Write your code here.
            if (n <= 0) return 0;
            // 8的k次方，很容易越界
            return (double) process(n, r, c, k) / Math.pow(8, k);
        }

        // (r,c)马的位置
        // k次移动
        // n * n棋盘大下
        public double knightProbabilityDp(int n, int k, int r, int c) {
            // Write your code here.
            if (n <= 0) return 0;
            int high = k + 1;
            double[][][] dp = new double[n][n][high];
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    dp[x][y][0] = 1;
                }
            }
            for (int rest = 1; rest <= k; rest++) {
                for (int x = 0; x < n; x++) {
                    for (int y = 0; y < n; y++) {
                        double p1 = getV(dp, x - 1, y + 2, rest - 1);
                        double p2 = getV(dp, x - 1, y - 2, rest - 1);
                        double p3 = getV(dp, x + 1, y + 2, rest - 1);
                        double p4 = getV(dp, x + 1, y - 2, rest - 1);
                        double p5 = getV(dp, x - 2, y - 1, rest - 1);
                        double p6 = getV(dp, x - 2, y + 1, rest - 1);
                        double p7 = getV(dp, x + 2, y - 1, rest - 1);
                        double p8 = getV(dp, x + 2, y + 1, rest - 1);
                        dp[x][y][rest] = (p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8) / 8D;
                    }
                }
            }
            return dp[r][c][k];
        }

        public double getV(double[][][] dp, int x, int y, int z) {
            if (x < 0 || y < 0 || z < 0 || x >= dp.length || y >= dp[0].length || z >= dp[0][0].length) {
                return 0;
            }
            return dp[x][y][z];
        }

        int process(int n, int x, int y, int rest) {
            // 只要跳到外界直接挂
            if (x < 0 || x >= n || y < 0 || y >= n) {
                return 0;
            }
            if (rest == 0) {
                return 1;
            }
            int p1 = process(n, x + 1, y + 2, rest - 1);
            int p2 = process(n, x + 1, y - 2, rest - 1);
            int p3 = process(n, x - 1, y + 2, rest - 1);
            int p4 = process(n, x - 1, y - 2, rest - 1);
            int p5 = process(n, x + 2, y + 1, rest - 1);
            int p6 = process(n, x + 2, y - 1, rest - 1);
            int p7 = process(n, x - 2, y + 1, rest - 1);
            int p8 = process(n, x - 2, y - 1, rest - 1);
            return p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        }


    }


}
