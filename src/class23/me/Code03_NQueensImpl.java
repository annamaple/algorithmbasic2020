package class23.me;

import class23.Code03_NQueens;
import cn.hutool.core.util.StrUtil;

public class Code03_NQueensImpl extends Code03_NQueens {

    public static void main(String[] args) {
        int n = 10;
        System.out.println(StrUtil.format(" 1 ^ 1 = {}", 1 ^ 1));
        
        long start = System.currentTimeMillis();
        System.out.println(num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
        System.out.println("0-------------");
        System.out.println(new NQueen().ways(n));
        System.out.println(new NQueen().way2(n));

    }


    // 给定一个n*n的格子，在数组中放置n个皇后，使每个皇后横竖斜都不在一条直线上，返回放置的方式数
    public static class NQueen {

        public int ways(int n) {
            if (n <= 0) return 0;
            return process(n, 0, new int[n]);
        }

        // n * n的格子，从row行开始到末尾航，每行填一个皇后，返回一共多少方式
        // n n * n的格子
        // row: 规定每行填一个，当前来到了第n行
        // path: 存放已填皇后的位置信息 下标对应第几行，值对应第几列
        int process(int n, int row, int[] path) {
            if (row == n) {
                return 1;
            }
            int way = 0;
            for (int j = 0; j < n; j++) {
                if (isValid(path, row, j)) {
                    path[row] = j;
                    way += process(n, row + 1, path);
                }
            }
            return way;
        }

        public int way2(int n) {
            if (n <= 0 || n > 32) return 0;
            int limit = n == 32 ? -1 : (1 << n) - 1;
            return f(limit, 0, 0, 0);
        }

        // 位运算记录皇后的限制状态，当colLimit限制状态和limit相等时, 即为一种方式
        private int f(int baseLimit, int colLimit, int leftLimit, int rightLimit) {
            if (baseLimit == colLimit) return 1;
            //0100
            //1000
            //0010
            //1110
            //1111
            // 可放置的位置用1表示
            //0001
            // 此处使用亦或需要注意，数在 <<时抹除掉无用位置的1 
            int pos = baseLimit ^ (colLimit | leftLimit | rightLimit);
            // int po = baseLimit & (~(colLimit | leftLimit | rightLimit));
            int rightOne;
            int way = 0;
            while (pos != 0) {
                rightOne = pos & (-pos);
                way += f(baseLimit, 
                        colLimit | rightOne,
                        ((leftLimit | rightOne) << 1) & baseLimit, 
                        (rightLimit | rightOne) >>> 1);
                pos = pos - rightOne;
            }
            return way;
        }


        // 判断(i, j)位置是否合法 
        // 合法：满足与path的元素不同列,不共斜线
        boolean isValid(int[] path, int i, int j) {
            for (int row = 0; row < i; row++) {
                if (path[row] == j || Math.abs(i - row) == Math.abs(j - path[row])) {
                    return false;
                }
            }
            return true;
        }
    }
}
