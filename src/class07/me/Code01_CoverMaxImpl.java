package class07.me;

import class07.Code01_CoverMax;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author lei
 */
public class Code01_CoverMaxImpl extends Code01_CoverMax {

    public static class CoverMax {
        
        
        // 暴力解法 time: O(lineEnd - lineStart)*N
        public int maxCover1(int[][] lines) {
            if (lines == null || lines.length < 1) {
                return 0;
            }
            // 1. 先找到所有线段的最小最大点
            int lineStart = lines[0][0];
            int lineEnd = lines[0][1];
            for (int i = 1; i < lines.length; i++) {
                lineStart = Math.min(lineStart, lines[i][0]);
                lineEnd = Math.max(lineEnd, lines[i][1]);
            }
            // 遍历每个点0.5位置,有多少线段包含。其最大的包含的数为解
            int ans = 0;
            for (double p = lineStart + 0.5; p < lineEnd; p++) {
                int count = 0;
                for (int[] line : lines) {
                    if (p > line[0] && p < line[1]) {
                        count++;
                    }
                }
                ans = Math.max(ans, count);
            }
            return ans;
        }
        
        
        public int maxCover2(int[][] lines) {
            if (lines == null || lines.length < 1) {
                return 0;
            }
            // 先按照线段开始位置由小到大排序
            // 申请最小堆, 遍历线段
            // 把堆中小于等于当前线段的值弹出, 把线段结束值放入最小堆中.此时堆的size即为每个已当前节点开始能贯穿的线段数量。
            // 遍历时堆size最大值为ans
            Arrays.sort(lines, Comparator.comparingInt(a -> a[0]));
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            int ans = 0;
            for (int[] line : lines) {
                while (!minHeap.isEmpty() && minHeap.peek() <= line[0]) {
                    minHeap.poll();
                }
                minHeap.offer(line[1]);
                ans = Math.max(ans, minHeap.size());
            }
            return ans;
        }
    }


    public static void main(String[] args) {

        Line l1 = new Line(4, 9);
        Line l2 = new Line(1, 4);
        Line l3 = new Line(7, 15);
        Line l4 = new Line(2, 4);
        Line l5 = new Line(4, 6);
        Line l6 = new Line(3, 7);

        // 底层堆结构，heap
        PriorityQueue<Line> heap = new PriorityQueue<>(new StartComparator());
        heap.add(l1);
        heap.add(l2);
        heap.add(l3);
        heap.add(l4);
        heap.add(l5);
        heap.add(l6);

        while (!heap.isEmpty()) {
            Line cur = heap.poll();
            System.out.println(cur.start + "," + cur.end);
        }

        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        CoverMax coverMax = new CoverMax();
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = coverMax.maxCover1(lines);
            int ans2 = coverMax.maxCover2(lines);
            int ans3 = maxCover3(lines);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }
}
