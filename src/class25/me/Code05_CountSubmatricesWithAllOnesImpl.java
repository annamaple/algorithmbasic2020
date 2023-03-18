package class25.me;

import class25.Code05_CountSubmatricesWithAllOnes;

import java.util.Stack;

public class Code05_CountSubmatricesWithAllOnesImpl extends Code05_CountSubmatricesWithAllOnes {

    public static void main(String[] args) {
        // mat = [[1,0,1],[1,1,0],[1,1,0]]
        // ans = 13
        int[][] mat = {
                {1, 0, 1}, {1, 1, 0}, {1, 1, 0}
        };
        System.out.println(new CountSubmatricesWithAllOnes().numSubmat(mat));
        System.out.println(new A().numSubmat(mat));
    }

    private static class A {

        public int numSubmat(int[][] mat) {
            if (mat == null) return 0;
            int M = mat.length;
            int N = mat[0].length;
            int[] height = new int[N];
            int ans = 0;
            for (int i = 0; i < M; i++ ) {
                for (int j = 0; j < N; j++) {
                    height[j] = mat[i][j] == 0 ? 0 : height[j] + 1;
                }
                ans += getBottomNum(height);
            }
            return ans;
        }

        int getBottomNum(int[] arr) {
            int N = arr.length;
            Stack<Integer> stack = new Stack<>();
            int cur, width, maxHigh;
            int res = 0;
            for (int i = 0; i < N; i++) {
                while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                    cur = stack.pop();
                    if (arr[cur] > arr[i]) {
                        width = stack.isEmpty() ? i : i - stack.peek() - 1;
                        maxHigh = Math.max(arr[i], stack.isEmpty() ? 0 : arr[stack.peek()]);
                        res += width * (width + 1) / 2 * (arr[cur] - maxHigh);
                    }
                }
                stack.push(i);
            }
            while(!stack.isEmpty()) {
                cur = stack.pop();
                width = stack.isEmpty() ? N : N - stack.peek() - 1;
                maxHigh = stack.isEmpty() ? 0 : arr[stack.peek()];
                res += width * (width + 1) / 2 * (arr[cur] - maxHigh);
            }
            return res;
        }
    }


    public static class CountSubmatricesWithAllOnes {

        public int numSubmat(int[][] mat) {
            if (mat == null || mat.length == 0) {
                return 0;
            }
            int M = mat.length;
            int N = mat[0].length;
            int[] height = new int[N];
            int ans = 0;
            for (int[] ints : mat) {
                for (int j = 0; j < N; j++) {
                    height[j] = ints[j] == 0 ? 0 : height[j] + 1;
                }
                ans += getCurBottomMat(height);
            }
            return ans;
        }


        // 获取以当前行为底的"1"的子矩阵数量
        int getCurBottomMat(int[] arr) {
            int N = arr.length;
            int[] stack = new int[N];
            int size = 0;
            int res = 0;
            for (int i = 0; i < N; i++) {
                while (size != 0 && arr[stack[size - 1]] >= arr[i]) {
                    int cur = stack[--size];
                    if (arr[cur] > arr[i]) {
                        int wight = size == 0 ? i : i - stack[size - 1] - 1;
                        int maxHigh = Math.max(arr[i], size == 0 ? 0 : arr[stack[size - 1]]);
                        res += wight * (wight + 1) / 2 * (arr[cur] - maxHigh);
                    }
                }
                stack[size++] = i;
            }
            while (size != 0) {
                int cur = stack[--size];
                int wight = size == 0 ? N : N - stack[size - 1] - 1;
                int maxHigh = size == 0 ? 0 : arr[stack[size - 1]];
                res += wight * (wight + 1) / 2 * (arr[cur] - maxHigh);
            }
            return res;
        }

    }

}
