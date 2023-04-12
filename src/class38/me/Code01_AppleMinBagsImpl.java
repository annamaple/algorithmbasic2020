package class38.me;

import class38.Code01_AppleMinBags;
import cn.hutool.core.util.StrUtil;

public class Code01_AppleMinBagsImpl extends Code01_AppleMinBags {


    /*
        小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。   
        1）能装下6个苹果的袋子  
        2）能装下8个苹果的袋子  
        小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。  
        给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1   
     */
    // N个苹果多少个袋子
    public static int force(int N) {
        if (N <= 8) {
            return N == 6 || N == 8 ? 1 : -1;
        }
        int p1 = force(N - 6);
        int p2 = force(N - 8);
        if (p1 == -1 && p2 == -1) {
            return -1;
        }
        if (p1 == -1 || p2 == -1) {
            return Math.max(p1, p2) + 1;
        }
        return Math.min(p1, p2) + 1;
    }

    public static int minN(int N) {
        if (N < 12) {
            return N == 6 || N == 8 ? 1 : -1;
        }
        return (N & 1) == 0 ? (N + 7) / 8 : -1;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            // System.out.println(StrUtil.format("{} : {}", i, force(i)));
            int a1 = force(i);
            int a2 = minN(i);
            if (a1 != a2) {
                System.out.println(StrUtil.format("N : {}, a1 : {}, a2 : {}, ", i, a2, a2));
                return;
            }
        }
        System.out.println("nice");
    }
}
