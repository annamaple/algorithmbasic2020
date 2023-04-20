package class45.me;

import class45.Code03_LongestCommonSubstringConquerByHeight;

// 最长公共字符串
// dp + 空间优化（重要）
// DC3了解
public class Code03_LongestCommonSubstringConquerByHeightImpl extends Code03_LongestCommonSubstringConquerByHeight {

    public static void main(String[] args) {
        int len = 30;
        int range = 5;
        int testTime = 100000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N1 = (int) (Math.random() * len);
            int N2 = (int) (Math.random() * len);
            String str1 = randomNumberString(N1, range);
            String str2 = randomNumberString(N2, range);
            int ans1 = lcsM1(str1, str2);
            int ans2 = lcsM2(str1, str2);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("功能测试结束");
        System.out.println("==========");
    }

    // dp
    public static int lcsM1(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length, M = str2.length;
        // dp[i][j]: str1[0~i]str2[0~j]严格已i,j结尾的最大公共子串
        // 状态转移：
        // i = 0; dp[0][j] = str1[0] == str2[j] ? 1 : 0;
        // j = 0; dp[i][0] = str2[0] == str1[i] ? 1 : 0;
        // i > 0 && j > 0: 
        //   1) str1[i] != str2[j] -> dp[i][j] = 0;
        //   2) str1[i] == str2[j] -> dp[i][j] = dp[i - 1][j - 1] + 1;
        int[][] dp = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 0; i < N; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : 0;
        }
        for (int j = 0; j < M; j++) {
            dp[0][j] = str2[j] == str1[0] ? 1 : 0;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                if (str1[i] == str2[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans;
    }

    // dp + 空间压缩
    // 从lcs1的dp[i][j]状态转移仅依赖dp[i - 1][j - 1];
    // 逻辑上可以写在填表, 优化物理dp表空间
    // 1334
    // 012346
    public static int lcsM2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray(), str2 = s2.toCharArray();
        int N = str1.length, M = str2.length;
        int row = N - 1, col = M - 1;
        int ans = 0;
        while (row >= 0) {
            int len = 0;
            for (int j = 0, i = row; i < N && j < M; i++, j++) {
                len = str1[i] == str2[j] ? len + 1 : 0;
                ans = Math.max(ans, len);
            }
            row--;
        }
        while (col >= 0) {
            int len = 0;
            for (int i = 0, j = col; i < N && j < M; i++, j++) {
                len = str1[i] == str2[j] ? len + 1 : 0;
                ans = Math.max(ans, len);
            }
            col--;
        }
        return ans + 1;
    }
}
