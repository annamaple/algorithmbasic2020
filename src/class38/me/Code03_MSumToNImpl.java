package class38.me;

import class38.Code03_MSumToN;
import cn.hutool.core.util.StrUtil;

public class Code03_MSumToNImpl extends Code03_MSumToN {

    // 定义一种数：可以表示成若干（数量>1）连续正数和的数
    // 比如:
    // 5 = 2+3，5就是这样的数
    // 12 = 3+4+5，12就是这样的数
    // 1不是这样的数，因为要求数量大于1个、连续正数和
    // 2 = 1 + 1，2也不是，因为等号右边不是连续正数
    // 给定一个参数N，返回是不是可以表示成若干连续正数和的数

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            // System.out.println(StrUtil.format("{} : {}", i, force(i)));
            boolean ans1 = isMSum2(i), ans2 = isMSum(i);
            if (ans1 != ans2) {
                System.out.println(StrUtil.format("fuck: N = {},  ans1: {}, ans2: {}", i, ans1, ans2));
                return;
            }
        }
        System.out.println("nice");
    }

    public static boolean isMSum(int N) {
        return N > 2 && (N & (-N)) != N;
    }

    public static boolean force(int N) {
        int sum = 0;
        int l = 0;
        for (int i = 0; i < N; i++) {
            sum += i;
            while (sum > N) {
                sum -= l++;
            }
            if (sum == N) {
                return true;
            }
        }
        return false;
    }
}
