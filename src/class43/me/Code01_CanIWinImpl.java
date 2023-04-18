package class43.me;

import class43.Code01_CanIWin;

import java.util.HashMap;
import java.util.Map;

// https://leetcode.cn/problems/can-i-win/
public class Code01_CanIWinImpl extends Code01_CanIWin {

    public static void main(String[] args) {
        int a = 10;
        int b = 9;
        System.out.println(new Code01_CanIWinImpl().canMeWin2(a, b));
    }

    // choose < 20
    public boolean canIWin(int choose, int des) {
        if (des == 0) return true;
        if ((choose * (choose + 1) >> 1) < des) {
            return false;
        }
        return f((1 << choose) - 1, des, new HashMap<>());
    }

    public boolean canMeWin2(int choose, int des) {
        if (des == 0) return true;
        if (choose * (choose + 1) >> 1 < des) return false;
        return ff(choose, 0, des, new int[(1 << choose) - 1]);
    }

    // status 0的位置表示未拿
    boolean ff(int choose, int status, int rest, int[] dp) {
        if (rest <= 0) return false;
        if (dp[status] != 0) return dp[status] == 1;
        boolean ans = false;
        for (int i = 0; i < choose; i++) {
            if ((status & (1 << i)) == 0 && !ff(choose, status | (1 << i), rest - i - 1, dp)) {
                ans = true;
                break;
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }

    // status: 可以选择的位置为1
    // 返回true: 调用的先手赢
    // 因为有status可以退出rest, rest不是独立因素
    boolean f(int status, int rest, Map<Integer, Boolean> map) {
        if (map.containsKey(status)) return map.get(status);
        if (rest <= 0) {
            return false;
        }
        boolean ans = false;
        int c = 1;
        for (int i = 0; i < 32; i++) {
            if ((status & (c << i)) != 0) {
                if (!f(status - (c << i), rest - i - 1, map)) {
                    ans = true;
                    break;
                }
            }
        }
        map.put(status, ans);
        return ans;
    }
}
