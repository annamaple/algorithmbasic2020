package class38.me;

import class38.Code04_MoneyProblem;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;


public class Code04_MoneyProblemImpl extends Code04_MoneyProblem {

    // 打怪兽需要花的最小钱数

    // int[] d，d[i]：i号怪兽的能力
    // int[] p，p[i]：i号怪兽要求的钱
    // 开始时你的能力是0，你的目标是从0号怪兽开始，通过所有的怪兽。
    // 如果你当前的能力，小于i号怪兽的能力，你必须付出p[i]的钱，贿赂这个怪兽，然后怪兽就会加入你，他的能力直接累加到你的能力上；
    // 如果你当前的能力，大于等于i号怪兽的能力，你可以选择直接通过，你的能力并不会下降，你也可以选择贿赂这个怪兽，然后怪兽就会加入你，他的能力直接累加到你的能力上。
    // 返回通过所有的怪兽，需要花的最小钱数。

    // 考虑以下情况
    // 1. 怪很多
    // 2. 怪不多，但怪的能力值巨大
    public static long moneyProblem(int[] d, int[] p) {
//        return f1(d, p, 0, 0);
//        return dp1(d, p) + 1;
        return dp2(d, p);
    }

    // 
    // 当前来到i位置, 1~i已做角色，返回i~N的最小钱数
    public static long f1(int[] d, int[] p, int i, long ability) {
        if (i == d.length) {
            return 0;
        }
        long p1 = p[i] + f1(d, p, i + 1, ability + d[i]);
        long p2 = Integer.MAX_VALUE;
        if (ability >= d[i]) {
            p2 = f1(d, p, i + 1, ability);
        }
        return Math.min(p1, p2);
    }

    // from f1
    public static long dp1(int[] d, int[] p) {
        int N = d.length;
        int maxAbility = 0;
        for (int a : d) {
            maxAbility += a;
        }
        // tip:若每一个怪的能力值很大，直接gg
        int M = maxAbility;
        // dp[i][j] 0~i的怪, 
        long[][] dp = new long[N + 1][M + 1];
        // dp[N][...] = 0;
        for (int i = N - 1; i >= 0; i--) {
            for (int ability = 0; ability <= maxAbility; ability++) {
                long p1 = Integer.MAX_VALUE;
                if (ability + d[i] <= maxAbility) {
                    p1 = p[i] + dp[i + 1][ability + d[i]];
                }
                long p2 = Integer.MAX_VALUE;
                if (ability >= d[i]) {
                    p2 = dp[i + 1][ability];
                }
                dp[i][ability] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }

    public static long fun2(int[] d, int[] p) {
        long maxMoney = 0;
        for (int m : p) {
            maxMoney += m;
        }
        int N = d.length;
        for (int money = 0; money < maxMoney; money++) {
            if (f2(d, p, N - 1, money) != -1) {
                return money;
            }
        }
        return maxMoney;
    }

    // 严格用完money的钱, 请解决[N~i]的挂。
    // 若能解决放回值为最大能力值, 不能解决返回值-1
    // 这种尝试方式得从N - i, 题目要求打怪从左到右, 算法需要之前决策的能力值
    public static long f2(int[] d, int[] p, int i, int money) {
        if (i == 0) {
            return money == p[i] ? d[i] : -1;
        }
        long p1 = -1, p2 = -1, preAbility;
        if (money >= p[i] && (preAbility = f2(d, p, i - 1, money - p[i])) != -1) {
            p1 = preAbility + d[i];
        }
        if ((preAbility = f2(d, p, i - 1, money)) != -1 && preAbility >= d[i]) {
            p2 = preAbility;
        }
        return Math.max(p1, p2);
    }

    public static long dp2(int[] d, int[] p) {
        int allMoney = 0;
        for (int m : p) allMoney += m;
        int N = d.length;
        long[][] dp = new long[N][allMoney + 1];
        // base case
        for (int money = 0; money <= allMoney; money++) {
            dp[0][money] = -1;
        }
        dp[0][p[0]] = d[0];
        for (int i = 1; i < N; i++) {
            for (int money = 0; money <= allMoney; money++) {
                long p1 = -1, p2 = -1, preAbility;
                if (money >= p[i] && (preAbility = dp[i - 1][money - p[i]]) != -1) {
                    p1 = preAbility + d[i];
                }
                if ((preAbility = dp[i - 1][money]) != -1 && preAbility >= d[i]) {
                    p2 = preAbility;
                }
                dp[i][money] = Math.max(p1, p2);
            }
        }
        for (int money = 0; money <= allMoney; money++) {
            if (dp[N - 1][money] != -1) {
                return money;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] d = arrs[0];
            int[] p = arrs[1];
            long ans1 = func1(d, p);
            long ans2 = func2(d, p);
            long ans3 = func3(d, p);
//            long ans4 = minMoney2(d,p);
            long ans4 = moneyProblem(d, p);
            if (ans1 != ans2 || ans2 != ans3 || ans1 != ans4) {
                System.out.println(Arrays.toString(d));
                System.out.println(Arrays.toString(p));
                System.out.println(StrUtil.format("my: {}, ans: {}", ans4, ans2));
                System.out.println("fuck!");
                return;
            }
        }
        System.out.println("nice");

    }
}
