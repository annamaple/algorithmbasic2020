package class18.me;

import class18.Code01_RobotWalk;

import java.util.Arrays;


public class Code01_RobotWalkImpl extends Code01_RobotWalk {


    public static void main(String[] args) {
        int n = 5;
        int start = 2;
        int aim = 4;
        int k = 6;
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
        // force recursion
        System.out.println(new RobotWalk().ways1(n, start, aim, k));
        // force recursion with cache
        System.out.println(new RobotWalk().ways2(n, start, aim, k));
        // dp
        System.out.println(new RobotWalk().ways3(n, start, aim, k));
    }


    /*
     * 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
     * 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
     * 如果机器人来到1位置，那么下一步只能往右来到2位置；
     * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
     * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
     * 规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
     * 给定四个参数 N、M、K、P，返回方法数
     */
    public static class RobotWalk {

        public int ways1(int n, int start, int aim, int k) {
            if (start < 1 || start >= n || k < 1 || aim < 1 || aim >= n) {
                return -1;
            }
            return process(n, start, k, aim);
        }

        public int ways2(int n, int start, int aim, int k) {
            if (start < 1 || start >= n || k < 1 || aim < 1 || aim >= n) {
                return -1;
            }
            int[][] cache = new int[n + 1][k + 1];
            for (int[] ints : cache) {
                Arrays.fill(ints, Integer.MAX_VALUE);
            }
            return process(n, start, k, aim, cache);
        }

        public int ways3(int n, int start, int aim, int k) {
            int[][] dp = new int[n + 1][k + 1];
            dp[aim][0] = 1;
            for (int rest = 1; rest <= k; rest++) {
                dp[1][rest] = dp[2][rest - 1];
                for (int cur = 2; cur < n; cur++) {
                    dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
                }
                dp[n][rest] = dp[n - 1][rest - 1];
            }
            return dp[start][k];
        }


        // n: 机器人可走的位置范围[0, n)
        // now: 机器人当前的位置
        // step: 机器人还有多少步可以走
        // aim: 目标位置
        int process(int n, int now, int step, int aim) {
            if (step == 0) {
                return now == aim ? 1 : 0;
            }
            if (now == 1) {
                return process(n, now + 1, step - 1, aim);
            }
            if (now == n) {
                return process(n, now - 1, step - 1, aim);
            }
            int p1 = process(n, now - 1, step - 1, aim);
            int p2 = process(n, now + 1, step - 1, aim);
            return p1 + p2;
        }

        // n: 机器人可走的位置范围[0, n)
        // now: 机器人当前的位置
        // step: 机器人还有多少步可以走
        // aim: 目标位置
        int process(int n, int now, int step, int aim, int[][] cache) {
            if (cache[now][step] != Integer.MAX_VALUE) {
                return cache[now][step];
            }
            int ans;
            if (step == 0) {
                ans = now == aim ? 1 : 0;
            } else {
                if (now == 1) {
                    ans = process(n, now + 1, step - 1, aim, cache);
                } else if (now == n) {
                    ans = process(n, now - 1, step - 1, aim, cache);
                } else {
                    int p1 = process(n, now + 1, step - 1, aim, cache);
                    int p2 = process(n, now - 1, step - 1, aim, cache);
                    ans = p1 + p2;
                }
            }
            cache[now][step] = ans;
            return ans;
        }
    }
}
