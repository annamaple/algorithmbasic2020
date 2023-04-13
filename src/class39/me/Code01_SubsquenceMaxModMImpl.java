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
            int ans1 = max1(arr, m);
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
        int[] ans = new int[1];
        int sum = 0;
        f(arr, 0, sum, m, ans);
        return ans[0];
    }

    private static void f(int[] arr, int i, int sum, int m, int[] ans) {
        if (i == arr.length) {
            ans[0] = Math.max(ans[0], sum % m);
            return;
        }
        f(arr, i + 1, sum, m, ans);
        f(arr, i + 1, sum + arr[i], m, ans);
    }
}
