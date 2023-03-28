package class29.me;

import class29.Code03_ReservoirSampling;

import java.util.Arrays;

public class Code03_ReservoirSamplingImpl extends Code03_ReservoirSampling {


    public static void main(String[] args) {
        int len = 1000;
        int[] res = new int[len];
        int times = 100_0000;
        while (--times >= 0) {
            Box box = new Box(10);
            for (int i = 0; i < len; i++) {
                box.add(i);
            }
            for (int num : box.choices()) {
                res[num]++;
            }
        }
        Arrays.stream(res).forEach(System.out::println);
    }


    // 蓄水池算法
    public static class Box {

        int[] bag;
        int N;
        int count;

        public Box(int capacity) {
            N = capacity;
            bag = new int[N];
            count = 0;
        }

        // 等概率返回 1~num
        private int rand(int num) {
            return (int) (Math.random() * num) + 1;
        }

        public void add(int num) {
            count++;
            if (count <= N) {
                bag[count - 1] = num;
            } else if (rand(count) <= N) {
                // 第i个数，N/i概率留下
                // 等概率淘汰已有的
                bag[rand(N) - 1] = num;
            }
        }

        public int[] choices() {
            int[] res = new int[N];
            System.arraycopy(bag, 0, res, 0, N);
            return res;
        }
    }
}
