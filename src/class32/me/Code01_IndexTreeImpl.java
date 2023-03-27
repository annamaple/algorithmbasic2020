package class32.me;

import class32.Code01_IndexTree;

public class Code01_IndexTreeImpl extends Code01_IndexTree {

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 2000000;
        IndexTree tree = new IndexTree(N);
        Right test = new Right(N);
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.add(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test finish");
    }

    public static class IndexTree {

        int N;
        int[] arr;

        public IndexTree(int size) {
            this.N = size;
            this.arr = new int[N + 1];
        }

        // 原位置1 ～ N的前缀和
        public int sum(int index) {
            int ret = 0;
            while (index > 0) {
                ret += arr[index];
                index -= index & (-index);
            }
            return ret;
        }

        // 给指定位置+d
        public void add(int index, int d) {
            while (index <= N) {
                arr[index] += d;
                index += index & (-index);
            }
        }
    }
}
