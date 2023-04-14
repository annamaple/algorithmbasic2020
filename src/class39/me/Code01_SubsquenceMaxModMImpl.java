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
        return f(arr, m, 0, 0);
    }

    // 从左到右尝试所有子序列的可能, 返回mod M最大值
    private static int f(int[] arr, int m, int i, int sum) {
        if (i == arr.length) {
            return sum % m;
        }
        int p1 = f(arr, m, i + 1, sum);
        int p2 = f(arr, m, i + 1, sum + arr[i]);
        return Math.max(p1, p2);
    }
}
