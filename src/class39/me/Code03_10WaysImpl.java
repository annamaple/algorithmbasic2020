package class39.me;

import class39.Code03_10Ways;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

// N个0, N1, 自由组合, 一共2N个数, 任何前缀上0的数量不要比1少, 这样的达标结果有多少?
public class Code03_10WaysImpl extends Code03_10Ways {

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int i = 0; i < 10; i++) {
            long ans1 = way2(i);
            long ans2 = ways1(i);
            if (ans1 != ans2) {
                System.out.println(i);
                System.out.println(StrUtil.format("my: {}, ans: {}", ans1, ans2));
                System.out.println("Oops!");
                way2(i);
                return;
            }
        }
        System.out.println("test finish");
    }

    public static long way2(int N) {
        // (C2n n) - (C2n n - 1)
        if (N < 0) return 0;
        if (N < 2) return 1;
        long a = C(2L * N, N);
        long b = C(2L * N, N - 1);
        return a - b;

    }

    // 求(Cn a)
    public static long C(long n, long a) {
        if (n == a) return 1;
        long c1 = 1, c2 = 1;
        for (int i = 0; i < a; i++) {
            c1 *= n--;
        }
        for (int i = 2; i <= a; i++) {
            c2 *= i;
        }
        return c1 / c2;
    }

    // 暴力： 枚举处所有可能的排列：选出符合的
    public static long way1(int N) {
        List<List<Integer>> ans = new ArrayList<>();
        f(N, N, new ArrayList<>(N << 1), ans);
        long count = 0;
        // 记录任何时候0的个数
        for (List<Integer> path : ans) {
            long status = 0;
            for (int num : path) {
                status += num == 0 ? 1 : -1;
                if (status < 0) {
                    break;
                }
            }
            count += status == 0 ? 1 : 0;
        }
        return count;
    }

    public static void f(int zero, int one, List<Integer> path, List<List<Integer>> ans) {
        if (zero == 0 && one == 0) {
            ans.add(new ArrayList<>(path));
            return;
        }
        if (zero != 0) {
            path.add(0);
            f(zero - 1, one, path, ans);
            path.remove(path.size() - 1);
        }
        if (one != 0) {
            path.add(1);
            f(zero, one - 1, path, ans);
            path.remove(path.size() - 1);
        }
    }

}
