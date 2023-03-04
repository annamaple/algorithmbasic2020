package class16.me;

import class16.Code06_NetworkDelayTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Code06_NetworkDelayTimeImpl extends Code06_NetworkDelayTime {

    public static void main(String[] args) {
        int[][] times = {
                {2,1,1},{2,3,1},{3,4,1}
        };
        int n = 4;
        int k = 2;
        System.out.println(new Code06_NetworkDelayTimeImpl().networkDelayTime(times, n, k));
    }

    public int networkDelayTime(int[][] times, int n, int k) {
        if (times == null || n < 0 || k < 0) return -1;
        Map<Integer, List<int[]>> nodeMap = new HashMap<>();
        for (int[] time : times) {
            List<int[]> nexts = nodeMap.computeIfAbsent(time[0], t -> new ArrayList<>());
            nexts.add(time);
        }
        Heap1 heap = new Heap1(n);
        heap.addOrUpdateOrIgnore(k, 0);
        int ans = -1;
        int countNode = 0;
        while (!heap.isEmpty()) {
            //cur[0]:node cur[1]:distance
            int[] cur = heap.poll();
            countNode++;
            ans = Math.max(ans, cur[1]);
            List<int[]> nexts = nodeMap.get(cur[0]);
            if (nexts != null && nexts.size() > 0) {
                for (int[] next : nexts) {
                    // next:  0   1   2
                    //      from to weight
                    heap.addOrUpdateOrIgnore(next[1], cur[1] + next[2]);
                }
            }
        }
        return countNode == n ? ans : -1;
    }

    public static class Heap {
        // 由题意有 1 --> 2   2--->1 这种情况，需要记录已经使用的节点
        int[] used;
        Map<Integer, Integer> distanceMap;
        int[] data;
        Map<Integer, Integer> indexMap;
        int heapSize;

        public Heap(int limit) {
            this.data = new int[limit];
            // 节点编号 1 - n
            this.used = new int[limit + 1];
            this.distanceMap = new HashMap<>();
            this.indexMap = new HashMap<>();
            this.heapSize = 0;
        }

        public void offer(int node, int distance) {
            distanceMap.put(node, distance);
            data[heapSize] = node;
            indexMap.put(node, heapSize);
            heapSize++;
            heapInsert(heapSize - 1);
        }

        // arr[0]:node; arr[1]:distance
        public int[] poll() {
            int[] ans = new int[]{data[0], distanceMap.get(data[0])};
            swap(0, heapSize - 1);
            heapSize--;
            indexMap.remove(ans[0]);
            distanceMap.remove(ans[0]);
            heapify(0, heapSize);
            // 记录该节点已完成
            used[ans[0]] = 1;
            return ans;
        }

        public void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int less = left + 1 < heapSize && compare(left + 1, left) < 0 ? left + 1: left;
                less = compare(index, less) < 0 ? index : less;
                if (less == index) {
                    break;
                }
                swap(index, less);
                index = less;
                left = index * 2 + 1;
            }
        }

        public void addOrUpdateOrIgnore(int node, int distance) {
            if (used[node] == 1) {
                return;
            }
            if (!distanceMap.containsKey(node)) {
                offer(node, distance);
            } else {
                int oldDistance = distanceMap.get(node);
                if (distance < oldDistance) {
                    distanceMap.put(node, distance);
                    heapInsert(indexMap.get(node));
                }
            }
        }

        public void heapInsert(int index) {
            while (compare(index, (index - 1) / 2) < 0) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        int compare(int idx1, int idx2) {
            return distanceMap.get(data[idx1]) - distanceMap.get(data[idx2]);
        }

        void swap(int idx1, int idx2) {
            int node1 = data[idx1];
            int node2 = data[idx2];
            data[idx2] = node1;
            data[idx1] = node2;
            indexMap.put(node1, idx2);
            indexMap.put(node2, idx1);
        }
    }

    // 优化，由于全是数字可以改map结构为树组
    public static class Heap1 {
        // 由题意有 1 --> 2   2--->1 这种情况，需要记录已经使用的节点
        // 下标：node 值：是否使用
        boolean[] used;
        // 下标：node 值：distance
        int[] distanceMap;
        int[] data;
        // 下标：node 值：index
        int[] indexMap;
        int heapSize;

        public Heap1(int limit) {
            this.data = new int[limit];
            // 节点编号 1 - n
            this.used = new boolean[limit + 1];
            this.distanceMap = new int[limit + 1];
            this.indexMap = new int[limit + 1];
            for (int i = 0; i < limit + 1; i++) {
                distanceMap[i] = indexMap[i] = -1;
            }
            this.heapSize = 0;
        }

        public void offer(int node, int distance) {
            distanceMap[node] = distance;
            data[heapSize] = node;
            indexMap[node] = heapSize;
            heapSize++;
            heapInsert(heapSize - 1);
        }

        // arr[0]:node; arr[1]:distance
        public int[] poll() {
            int[] ans = new int[]{data[0], distanceMap[data[0]]};
            swap(0, heapSize - 1);
            heapSize--;
            // indexMap.remove(ans[0]);
            // distanceMap.remove(ans[0]);
            heapify(0, heapSize);
            // 记录该节点已完成
            used[ans[0]] = true;
            return ans;
        }

        public void heapify(int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int less = left + 1 < heapSize && compare(left + 1, left) < 0 ? left + 1: left;
                less = compare(index, less) < 0 ? index : less;
                if (less == index) {
                    break;
                }
                swap(index, less);
                index = less;
                left = index * 2 + 1;
            }
        }

        public void addOrUpdateOrIgnore(int node, int distance) {
            if (used[node]) {
                return;
            }
            if (distanceMap[node] == -1) {
                offer(node, distance);
            } else {
                int oldDistance = distanceMap[node];
                if (distance < oldDistance) {
                    distanceMap[node] = distance;
                    heapInsert(indexMap[node]);
                }
            }
        }

        public void heapInsert(int index) {
            while (compare(index, (index - 1) / 2) < 0) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        int compare(int idx1, int idx2) {
            return distanceMap[data[idx1]] - distanceMap[data[idx2]];
        }

        void swap(int idx1, int idx2) {
            int node1 = data[idx1];
            int node2 = data[idx2];
            data[idx2] = node1;
            data[idx1] = node2;
            indexMap[node1] = idx2;
            indexMap[node2] = idx1;
        }
    }
}









