package class38.me;

import class38.Code02_EatGrass;
import cn.hutool.core.util.StrUtil;

public class Code02_EatGrassImpl extends Code02_EatGrass {
    
    /*  牛羊吃N份草谁赢
        给定一个正整数N，表示有N份青草统一堆放在仓库里有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草不管是牛还是羊，每一轮能吃的草量必须是：  
        1，4，16，64…(4的某次方)  
        谁最先把草吃完，谁获胜假设牛和羊都绝顶聪明，都想赢，都会做出理性的决定根据唯一的参数N，返回谁会赢  
     */

    // N份草先后轮流吃，谁赢。返回值固定1(先手赢),2(后手赢)
    public static int woWin(int N) {
        // 到谁的时候草已经为0，谁输
        if (N <= 0) {
            return 2;
        }
        int eat = 1;
        while (eat <= N) {
            if (woWin(N - eat) == 2) {
                return 1;
            }
            eat <<= 2;
        }
        return 2;
    }

    public static int woWin2(int N) {
        int mod = N % 5;
        return mod == 0 || mod == 2 ? 2 : 1;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println(StrUtil.format("{} : {}", i, woWin(i) == 1 ? "牛" : "羊"));
        }
    }
}
