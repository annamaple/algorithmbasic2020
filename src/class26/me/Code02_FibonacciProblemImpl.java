package class26.me;

import class26.Code02_FibonacciProblem;

import java.util.Random;

public class Code02_FibonacciProblemImpl extends Code02_FibonacciProblem {

    public static void main(String[] args) {
        int times = 10000;
        int maxVal = 1000;
        Random random = new Random(maxVal);
        while (times-- >= 0) {
            int n = random.nextInt(maxVal);
//            int ans1 = fib(n, new int[n + 1]);
            int ans1 = f3(n);
            int ans2 = new FibonacciProblem().fib(n);
            if (ans1 != ans2) {
                System.out.println(n);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("fuck");
                break;
            }
        }
        System.out.println("nice");
    }

    public static int fib(int n, int[] dp) {
        if (n < 0) return -1;
        if (dp[n] != 0) return dp[n];
        if (n <= 1) return n;
        int ans =  fib(n - 1, dp) + fib(n - 2, dp);
        dp[n] = ans;
        return ans;
    }

    public static class FibonacciProblem {

        // 斐波那契额数第n项 ==> 矩阵快速幂计算
        // f(0) = 0
        // f(1) = 1; f(2) = 1
        // f(n) = f(n - 1) + f(n - 2)

        // 套路：
        // F(n) = F(n - 1) + F(n - 2)
        // |F(n), F(n - 1)| = |F(2), F(1)| * |二维矩阵|^(n - 2)
        // 带入计算求得二维矩阵为：
        // | |
        // | |
        public int fib(int n) {
            if (n < 0) return -1;
            if (n <= 1) return n;
            if (n == 2) return 1;
            int[][] m = {
                    {1, 1},
                    {1, 0}
            };
            m = matrixPow(m, n - 2);
            return m[0][0] + m[1][0];
        }


        // 求n * n矩阵的n次方
        public int[][] matrixPow(int[][] mat, int pow) {
            int N = mat.length;
            int[][] ans = new int[N][N];
            int[][] m = mat;
            // 单位矩阵
            for (int i = 0; i < ans.length; i++) {
                ans[i][i] = 1;
            }
            // 二分 logN
            while (pow != 0) {
                if ((pow & 1) == 1) {
                    ans = matrixMulti(ans, m);
                }
                pow >>= 1;
                m = matrixMulti(m, m);
            }
            return ans;
        }

        public int[][] matrixMulti(int[][] a, int[][] b) {
            int N = a.length;
            int[][] ans = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k = 0; k < N; k++) {
                        ans[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
            return ans;
        }
    }
}
