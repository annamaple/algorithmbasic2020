package class14.me;

import class14.Code04_IPO;

import java.util.PriorityQueue;

/**
 * @author lei
 */
public class Code04_IPOImpl extends Code04_IPO {

    public static void main(String[] args) {

        // 2
        // 0
        // [1,2,3]
        // [0,1,1]
        int K = 2;
        int W = 0;
        int[] Profits = {1, 2, 3};
        int[] Capital = {0, 1, 1};
        System.out.println(new IPO().findMaximizedCapital1(K, W, Profits, Capital));
        System.out.println(new IPO().findMaximizedCapital2(K, W, Profits, Capital));
        System.out.println(findMaximizedCapital(K, W, Profits, Capital));
    }


    public static class IPO {

        public static class PC {
            int profit;
            int capital;

            public PC(int profit, int capital) {
                this.profit = profit;
                this.capital = capital;
            }
        }

        // force recursion
        // 最多K个项目
        // W是初始资金
        // Profits[] 收益 Capital[] 启动资本
        // 返回最终最大的资金
        public int findMaximizedCapital1(int K, int W, int[] Profits, int[] Capital) {
            if (Capital == null || Profits == null || K <= 0 || W < 0) return 0;
            return process(Profits, Capital, K, W);
        }

        // greedy
        public int findMaximizedCapital2(int K, int W, int[] Profits, int[] Capital) {
            // nature mind
            PriorityQueue<PC> heap = new PriorityQueue<>((a, b) -> a.capital - b.capital);
            PriorityQueue<PC> canDoHeap = new PriorityQueue<>((a, b) -> b.profit - a.profit);
            int N = Profits.length;
            for (int i = 0; i < N; i++) {
                heap.offer(new PC(Profits[i], Capital[i]));
            }
            int max = W;
            while (K > 0) {
                while (!heap.isEmpty() && heap.peek().capital <= max) {
                    canDoHeap.offer(heap.poll());
                }
                PC pc = canDoHeap.poll();
                max += pc.profit;
                K--;
            }
            return max;
        }


        // Profits[] Capital[] 剩下还可以做得项目
        // K 还可以做多少个项目
        // W 目前拥有的资金
        // 返回最终最大的资金
        int process(int[] Profits, int[] Capital, int K, int W) {
            if (K == 0 || Profits.length == 0) {
                return W;
            }
            int N = Profits.length;
            int max = W;
            for (int i = 0; i < N; i++) {
                if (Capital[i] <= W) {
                    max = Math.max(max, process(otherData(Profits, i), otherData(Capital, i), K - 1, W + Profits[i]));
                }
            }
            return max;
        }

        // 返回过滤掉data数组index位置的元素的新数组
        int[] otherData(int[] data, int index) {
            int[] res = new int[data.length - 1];
            int p = 0;
            for (int i = 0; i < data.length; i++) {
                if (i != index) {
                    res[p++] = data[i];
                }
            }
            return res;
        }
    }
}
