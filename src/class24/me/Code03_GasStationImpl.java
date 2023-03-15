package class24.me;

import class24.Code03_GasStation;

import java.util.Deque;
import java.util.LinkedList;


// 测试链接：https://leetcode.com/problems/gas-station
public class Code03_GasStationImpl extends Code03_GasStation {


    private static class GasStation {
        
        // 汽油问题
        // gas当前位置的汽油
        // cost当前位置到下一个位置的耗费
        // 返回从每个位置出发是否可以跑完一圈
        public int canCompleteCircuit(int[] gas, int[] cost) {
            if (gas == null || cost == null || gas.length == 0 || cost.length == 0) return -1;
            boolean[] stat = canCompleteCircuitA(gas, cost);
            for (int i = 0; i < stat.length; i++) {
                if (stat[i]) {
                    return i;
                }
            }
            return -1;
        }


        public boolean[] canCompleteCircuitA(int[] gas, int[] cost) {
            // gas [1, 1, 2]
            // cost[2, 1, 1]
            // 转换为前缀和
            int N = gas.length;
            int M = N << 1;
            // [-1, 0, 1, -1, 0, 1]
            int[] arr = new int[M];
            for (int i = 0; i < N; i++) {
                arr[i] = arr[i + N] = gas[i] - cost[i];
            }
            for (int i = 1; i < M; i++) {
                arr[i] = arr[i] + arr[i - 1];
            }
            Deque<Integer> minQ = new LinkedList<>();
            for (int i = 0; i < N; i++) {
                while (!minQ.isEmpty() && arr[minQ.peekLast()] >= arr[i]) {
                    minQ.pollLast();
                }
                minQ.offerLast(i);
            }
            boolean[] ans = new boolean[N];
            for (int r = N, l = 0, preSum = 0; r < M; r++, preSum = arr[l], l++) {
                if (arr[minQ.peekFirst()] - preSum >= 0) {
                    ans[l] = true;
                }
                // 窗口右移
                while (!minQ.isEmpty() && arr[minQ.peekLast()] >= arr[r]) {
                    minQ.pollLast();
                }
                minQ.offerLast(r);
                if (minQ.peekFirst() == l) {
                    minQ.pollFirst();
                }
            }
            return ans;
        }


    }
}
