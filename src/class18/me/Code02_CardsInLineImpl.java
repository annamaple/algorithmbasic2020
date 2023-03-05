package class18.me;

import class18.Code02_CardsInLine;

public class Code02_CardsInLineImpl extends Code02_CardsInLine {

    public static void main(String[] args) {
        int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        // force recursion
        System.out.println(new CardsInLine().win1(arr));
        // caddy cache
        System.out.println(new CardsInLine().win2(arr));
        // dp
        System.out.println(new CardsInLine().win3(arr));
    }


    /*
     * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
     * 玩家A和玩家B依次拿走每张纸牌
     * 规定玩家A先拿，玩家B后拿
     * 但是每个玩家每次只能拿走最左或最右的纸牌
     * 玩家A和玩家B都绝顶聪明
     * 请返回最后获胜者的分数
     */
    public static class CardsInLine {

        public int win1(int[] arr) {
            return Math.max(f(arr, 0, arr.length - 1), g(arr, 0, arr.length - 1));
        }

        public int win2(int[] arr) {
            int n = arr.length;
            int[][] fArr = new int[n][n];
            int[][] gArr = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    fArr[i][j] = -1;
                    gArr[i][j] = -1;
                }
            }
            int f = f(arr, 0, n - 1, fArr, gArr);
            int g = g(arr, 0, n - 1, fArr, gArr);
            return Math.max(f, g);
        }

        public int win3(int[] arr) {
            int n = arr.length;
            int[][] fArr = new int[n][n];
            int[][] gArr = new int[n][n];
            // base case
            for (int i = 0; i < n; i++) {
                fArr[i][i] = arr[i];
                // gArr[i][i] = 0;
            }
            // 位置关系
            for (int R = 1; R < n; R++) {
                for (int L = R - 1; L >= 0; L--) {
                    fArr[L][R] = Math.max(arr[L] + gArr[L + 1][R], arr[R] + gArr[L][R - 1]);
                    gArr[L][R] = Math.min(fArr[L + 1][R], fArr[L][R - 1]);
                }
            }
            return Math.max(fArr[0][n - 1], gArr[0][n - 1]);
        }

        int f(int[] arr, int left, int right, int[][] fArr, int[][] gArr) {
            if (fArr[left][right] != -1) {
                return fArr[left][right];
            }
            int ans;
            if (left == right) {
                ans = arr[left];
            } else {
                int p1 = arr[left] + g(arr, left + 1, right, fArr, gArr);
                int p2 = arr[right] + g(arr, left, right - 1, fArr, gArr);
                ans = Math.max(p1, p2);
            }
            fArr[left][right] = ans;
            return ans;
        }

        int g(int[] arr, int left, int right, int[][] fArr, int[][] gArr) {
            if (gArr[left][right] != -1) {
                return gArr[left][right];
            }
            int ans;
            if (left == right) {
                ans = 0;
            } else {
                int p1 = f(arr, left + 1, right, fArr, gArr);
                int p2 = f(arr, left, right - 1, fArr, gArr);
                ans = Math.min(p1, p2);
            }
            gArr[left][right] = ans;
            return ans;
        }


        // 先手函数
        int f(int[] arr, int left, int right) {
            if (left == right) {
                return arr[left];
            }
            int p1 = arr[left] + g(arr, left + 1, right);
            int p2 = arr[right] + g(arr, left, right - 1);
            return Math.max(p1, p2);
        }

        // 后手函数
        int g(int[] arr, int left, int right) {
            if (left == right) {
                // 后手函数，只剩下一张时，被先手拿了，后手拿不到返回0
                return 0;
            }
            // 先手选择之后, 后手的最大分数
            int p1 = f(arr, left + 1, right);
            int p2 = f(arr, left, right - 1);
            // 后手是被动的，只能接受先手的决策
            // 先手拿左边或右边时计算后手的最大分数，先手肯定会选择后手最大分数小的选择
            return Math.min(p1, p2);
        }
    }
}
