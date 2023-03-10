package class22.me;

import class22.Code01_KillMonster;
import cn.hutool.core.util.StrUtil;


public class Code01_KillMonsterImpl extends Code01_KillMonster {

    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
//            double ans1 = right(N, M, K);
//            double ans1 = new KillMonster().kill(N, M, K);
//            double ans1 = new KillMonster().killDp(N, M, K);
//            double ans1 = new KillMonster().killDpPro(N, M, K);
            double ans1 = new KillMonster().killDpBest(N, M, K);
            double ans2 = dp1(N, M, K);
            double ans3 = dp2(N, M, K);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(StrUtil.format("ans1: {}, ans2:{}, ans3: {}", ans1, ans2, ans3));
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }


    /*
     * N滴血, 砍K刀, 每 刀 掉[0, M]滴血, 问砍完k刀后, 怪挂掉的概率
     */
    public static class KillMonster {

        public double kill(int N, int M, int K) {
            if (N < 1 || M < 1 || K < 1) {
                return 0;
            }
            double all = Math.pow(M + 1, K);
            long kill = process(M, K, N);
            return kill / all;
        }

        public double killDp(int N, int M, int K) {
            if (N < 1 || M < 1 || K < 1) {
                return 0;
            }
            double all = Math.pow(M + 1, K);
            long[][] dp = new long[K + 1][N + 1];
            // dp[0][1...] = 0;
            dp[0][0] = 1;
            for (int restTimes = 1; restTimes <= K; restTimes++) {
                dp[restTimes][0] = (long) Math.pow(M + 1, restTimes);
                for (int restHp = 1; restHp <= N; restHp++) {
                    long way = 0;
                    for (int damage = 0; damage <= M; damage++) {
                        way += (restHp - damage) > 0 ? dp[restTimes - 1][restHp - damage] : Math.pow(M + 1, restTimes - 1);
                    }
                    dp[restTimes][restHp] = way;
                }
            }
            return (double) dp[K][N] / all;
        }

        public double killDpPro(int N, int M, int K) {
            if (N < 1 || M < 1 || K < 1) return 0;
            double all = Math.pow(M + 1, K);
            int[][] dp = new int[K + 1][N + 1];
            // dp[0][1.....] = 1
            dp[0][0] = 1;
            for (int restTime = 1; restTime <= K; restTime++) {
                dp[restTime][0] = (int) Math.pow(M + 1, restTime);
                for (int restHp = 1; restHp <= N; restHp++) {
                    dp[restTime][restHp] = dp[restTime - 1][restHp] + dp[restTime][restHp - 1];
                    dp[restTime][restHp] -= restHp - M - 1 > 0 ? dp[restTime - 1][restHp - M - 1] : dp[restTime - 1][0];
                }
            }
            return (double) dp[K][N] / all;
        }

        public double killDpBest(int N, int M, int K) {
            if (N < 1 || M < 1 || K < 1) return 0;
            // 优化times
            long[] doneDp = new long[N + 1];
            long[] dp = new long[N + 1];
            long[] temp;
            doneDp[0] = 1;
            for (int restTime = 1; restTime <= K; restTime++) {
                dp[0] = (long) Math.pow(M + 1, restTime);
                for (int restHp = 1; restHp <= N; restHp++) {
                    dp[restHp] = doneDp[restHp] + dp[restHp - 1];
                    dp[restHp] -= restHp - M - 1 > 0 ? doneDp[restHp - M - 1] : doneDp[0];
                }
                temp = doneDp;
                doneDp = dp;
                dp = temp;
            }
            return (double) doneDp[N] / (double) doneDp[0];
        }

        // restDp: 当前血量
        // restTimes: 还剩几刀
        // 每 刀 掉[0, M]滴血
        long process(int M, int restTimes, int restDp) {
            if (restDp <= 0) {
                return (long) Math.pow(M + 1, restTimes);
            }
            if (restTimes == 0) {
                return 0;
            }
            long way = 0;
            for (int damage = 0; damage <= M; damage++) {
                way += process(M, restTimes - 1, restDp - damage);
            }
            return way;
        }
    }
}
