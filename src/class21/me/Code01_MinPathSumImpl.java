package class21.me;

import class21.Code01_MinPathSum;

/**
 * 左上角到右下角最短路径
 */
public class Code01_MinPathSumImpl extends Code01_MinPathSum {

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int[][] m = generateRandomMatrix(rowSize, colSize);
        System.out.println(minPathSum1(m));
        System.out.println(minPathSum2(m));
        System.out.println(new MinPath().minPath(m));
        System.out.println(new MinPath().minPathDp(m));
        System.out.println(new MinPath().minPathBestDp1(m));
        System.out.println(new MinPath().minPathBestDp2(m));
    }

    public static class MinPath {

        public int minPath(int[][] matrix) {
            return f(matrix, 0, 0);
        }

        public int minPathDp(int[][] matrix) {
            if (matrix == null || matrix.length == 0) return 0;
            int row = matrix.length;
            int col = matrix[0].length;
            int[][] dp = new int[row][col];
            dp[row - 1][col - 1] = matrix[row - 1][col - 1];
            for (int y = col - 2; y >= 0; y--) {
                dp[row - 1][y] = matrix[row - 1][y] + dp[row - 1][y + 1];
            }
            for (int x = row - 2; x >= 0; x--) {
                dp[x][col - 1] = matrix[x][col - 1] + dp[x + 1][col - 1];
            }
            for (int x = row - 2; x >= 0; x--) {
                for (int y = col - 2; y >= 0; y--) {
                    int p1 = dp[x + 1][y];
                    int p2 = dp[x][y + 1];
                    dp[x][y] = Math.min(p1, p2) + matrix[x][y];
                }
            }
            return dp[0][0];
        }

        public int minPathBestDp1(int[][] matrix) {
            if (matrix == null || matrix.length == 0) return 0;
            int col = matrix[0].length;
            int row = matrix.length;
            int[] dp = new int[col];
            dp[col - 1] = matrix[row - 1][col - 1];
            // 初始化最后一行
            for (int j = col - 2; j >= 0; j--) {
                dp[j] = matrix[row - 1][j] + dp[j + 1];
            }
            for (int i = row - 2; i >= 0; i--) {
                dp[col - 1] = matrix[i][col - 1] + dp[col - 1];
                for (int j = col - 2; j >= 0; j--) {
                    int p1 = dp[j + 1];
                    int p2 = dp[j];
                    dp[j] = Math.min(p1, p2) + matrix[i][j];
                }
            }
            return dp[0];
        }

        public int minPathBestDp2(int[][] matrix) {
            if (matrix == null || matrix.length == 0) return 0;
            int row = matrix.length;
            int col = matrix[0].length;
            int[] dp = new int[row];
            dp[row - 1] = matrix[row - 1][col - 1];
            // 初始化最后一列
            for (int i = row - 2; i >= 0; i--) {
                dp[i] = matrix[i][col - 1] + dp[i + 1];
            }
            for (int j = col - 2; j >= 0; j--) {
                dp[row - 1] = matrix[row - 1][j] + dp[row - 1]; 
                for (int i = row - 2; i >= 0; i--) {
                    // 下
                    int p1 = dp[i + 1];
                    // 右
                    int p2 = dp[i];
                    dp[i] = Math.min(p1, p2) + matrix[i][j];
                }
            }
            return dp[0];
        }

        int f(int[][] m, int x, int y) {
            int row = m.length;
            int col = m[0].length;
            if (x < 0 || y < 0 || x >= row || y >= col) {
                return Integer.MAX_VALUE;
            }
            if (x == row - 1 && y == col - 1) {
                return m[x][y];
            }
            int ans = m[x][y];
            int p1 = f(m, x + 1, y);
            int p2 = f(m, x, y + 1);
            return Math.min(p1, p2) + ans;
        }
    }
}
