package class20.me;

import class20.Code02_HorseJump;
import cn.hutool.core.date.StopWatch;

import java.util.concurrent.TimeUnit;

public class Code02_HorseJumpImpl extends Code02_HorseJump {

    public static void main(String[] args) {
        int a = 7;
        int b = 7;
        int step = 10;
        StopWatch watch = new StopWatch("Horse Jump");
        watch.start("zuo dp: ");
        System.out.println(dp(a, b, step));
        watch.stop();

        watch.start(" force recursion");
        System.out.println(new Horse().jump(a, b, step));
        watch.stop();

        watch.start(" my Horse Dp");
        System.out.println(new Horse().jumpDp(a, b, step));
        watch.stop();
        System.out.println(watch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    public static class Horse {

        // 马在(a,b)位置需要跳转k步来到(a,b)位置，有多少跳法？
        // 棋盘大小 10 * 9
        public int jump(int a, int b, int step) {
            return f(a, b, 0, 0, step);
        }

        public int jumpDp(int a, int b, int step) {
            // x y k
            int[][][] dp = new int[10][9][step + 1];
            dp[a][b][0] = 1;
            for (int k = 1; k <= step; k++) {
                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 9; y++) {
                        int p1 = getDpVal(dp, x + 1, y + 2, k - 1);
                        int p2 = getDpVal(dp, x + 1, y - 2, k - 1);
                        int p3 = getDpVal(dp, x - 1, y + 2, k - 1);
                        int p4 = getDpVal(dp, x - 1, y - 2, k - 1);
                        int p5 = getDpVal(dp, x + 2, y + 1, k - 1);
                        int p6 = getDpVal(dp, x + 2, y - 1, k - 1);
                        int p7 = getDpVal(dp, x - 2, y + 1, k - 1);
                        int p8 = getDpVal(dp, x - 2, y - 1, k - 1);
                        dp[x][y][k] = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
                    }
                }
            }
            return dp[0][0][step];
        }

        int getDpVal(int[][][] dp, int x, int y, int k) {
            if (x < 0 || x >= dp.length || y < 0 || y >= dp[0].length || k < 0 || k >= dp[0][0].length) {
                return 0;
            } else {
                return dp[x][y][k];
            }
        }

        int f(int a, int b, int x, int y, int k) {
            if (x < 0 || x >= 10 || y < 0 || y >= 9) {
                return 0;
            }
            if (k == 0) {
                return x == a && y == b ? 1 : 0;
            }
            int p1 = f(a, b, x + 1, y + 2, k - 1);
            int p2 = f(a, b, x + 1, y - 2, k - 1);
            int p3 = f(a, b, x - 1, y + 2, k - 1);
            int p4 = f(a, b, x - 1, y - 2, k - 1);
            int p5 = f(a, b, x + 2, y + 1, k - 1);
            int p6 = f(a, b, x + 2, y - 1, k - 1);
            int p7 = f(a, b, x - 2, y + 1, k - 1);
            int p8 = f(a, b, x - 2, y - 1, k - 1);
            return p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        }
    }
}
