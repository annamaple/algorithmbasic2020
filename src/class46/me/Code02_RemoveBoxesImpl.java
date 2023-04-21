package class46.me;

import class46.Code02_RemoveBoxes;

/**
 * @author lei
 */
public class Code02_RemoveBoxesImpl extends Code02_RemoveBoxes {

    public static void main(String[] args) {
        int[] arr = {1, 3, 2, 2, 2, 3, 4, 3, 1};
        System.out.println(removeBox(arr));
    }

    public static int removeBox(int[] boxes) {
        if (boxes == null || boxes.length == 0) return 0;
        int N = boxes.length;
        return f2(boxes, 0, N - 1, 0, new int[N][N][N]);
    }

    // pre: l前面有连续几个与arr[l]相同
    public static int f(int[] arr, int l, int r, int pre, int[][][] dp) {
        if (l > r) {
            return 0;
        }
        if (dp[l][r][pre] != 0) {
            return dp[l][r][pre];
        }
        int ans;
        ans = (pre + 1) * (pre + 1) + f(arr, l + 1, r, 0, dp);
        for (int i = l + 1; i <= r; i++) {
            if (arr[i] == arr[l]) {
                ans = Math.max(ans, f(arr, l + 1, i - 1, 0, dp) + f(arr, i, r, pre + 1, dp));
            }
        }
        dp[l][r][pre] = ans;
        return ans;
    }

    public static int f2(int[] arr, int l, int r, int pre, int[][][] dp) {
        if (l > r) {
            return 0;
        }
        if (dp[l][r][pre] != 0) {
            return dp[l][r][pre];
        }
        int last = l;
        while (last + 1 <= r && arr[last + 1] == arr[l]) {
            last++;
        }
        pre += last - l;
        int ans = (pre + 1) * (pre + 1) + f(arr, last + 1, r, 0, dp);
        for (int i = last + 1; i <= r; i++) {
            if (arr[i - 1] != arr[last] && arr[i] == arr[last]) {
                ans = Math.max(ans, f(arr, last + 1, i - 1, 0, dp) + f(arr, i, r, pre + 1, dp));
            }
        }
        dp[l][r][pre] = ans;
        return ans;
    }
}
