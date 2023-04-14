package class39.me;

import class39.Code01_SubsquenceMaxModM;


// 给定一个非负数组arr，和一个正数m。 返回arr的所有子序列中累加和%m之后的最大值。
public class Code01_SubsquenceMaxModMImpl extends Code01_SubsquenceMaxModM {

    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        int m = 76;
        int testTime = 500000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            // my solution
            int ans1 = dp2(arr, m);
            int ans2 = max2(arr, m);
            int ans3 = max3(arr, m);
            int ans4 = max4(arr, m);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
                return;
            }
        }
        System.out.println("test finish!");
    }

    public static int max1(int[] arr, int m) {
        return f1(arr, m, 0, 0);
    }

    // 从左到右尝试所有子序列的可能, 返回mod M最大值
    private static int f1(int[] arr, int m, int i, int sum) {
        if (i == arr.length) {
            return sum % m;
        }
        int p1 = f1(arr, m, i + 1, sum);
        int p2 = f1(arr, m, i + 1, sum + arr[i]);
        return Math.max(p1, p2);
    }

    // 使用了maxSum作为数组的下标, 适用于arr的累加和不大的情况
    public static int dp1(int[] arr, int m) {
        // boolean dp[i][sum]: arr[0...i]中的子序列的和能否为sum
        // 两种情况: 
        // 1. dp[i - 1][sum] = true; (不含i子序列能够为sum)
        // 2. dp[i - 1][sum - arr[i]] = true; (含i的子序列能够为sum)
        // 查找dp[N - 1][0...sum]为true的sum模m的最大值
        int N = arr.length;
        int maxSum = 0;
        for (int num : arr) maxSum += num;
        boolean[][] dp = new boolean[N][maxSum + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;
        for (int i = 1; i < N; i++) {
            for (int sum = 1; sum <= maxSum; sum++) {
                dp[i][sum] = dp[i - 1][sum] || (sum - arr[i] >= 0 && dp[i - 1][sum - arr[i]]);
            }
        }
        int ans = 0;
        for (int sum = 0; sum <= maxSum; sum++) {
            ans = Math.max(ans, dp[N - 1][sum] ? sum % m : 0);
        }
        return ans;
    }

    public static int dp1Fail(int[] arr, int m) {
        int N = arr.length;
        int maxSum = 0;
        for (int num : arr) maxSum += num;
        int[][] dp = new int[N + 1][maxSum + 1];
        for (int sum = 0; sum <= maxSum; sum++) {
            // 注意子序列的累加和不能满足[0-maxSum]都能得到
            // 此尝试失败
            dp[N][sum] = sum / m;
        }
        return -1;
    }

    // 使用模m作为数组的下标长度(适用于m不大, 尤其是arr[i]很大)
    public static int dp2(int[] arr, int m) {
        // boolean dp[i][j]: arr[0,i]的子序列的和模m能否构成j
        // 两种情况：
        // 1. 不包含i能构成j: dp[i - 1][j] = true 
        // 2. 包含i能构成j: cur = arr[i] % m;
        //      dp[i - 1][j >= cur ? j - cur : j + m - cur] = true
        int N = arr.length;
        boolean[][] dp = new boolean[N][m];
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0] % m] = true;
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < m; j++) {
                int cur = arr[i] % m;
                dp[i][j] = dp[i - 1][j] || dp[i - 1][j >= cur ? j - cur : m + j - cur];
            }
        }
        for (int mod = m - 1; mod > 0; mod--) {
            if (dp[N - 1][mod]) {
                return mod;
            }
        }
        return 0;
    }
    
    // 当m很大且arr[i]也很大但arr.length不大
    public static int f3(int[] arr, int m) {
        // TODO Lei: 分治
        return -1;
    }
}
