package class16.me;

import class16.Code03_TopologicalOrderBFS;

import java.util.*;
import java.util.stream.Collectors;

// 拓扑排序
public class Code03_TopologicalOrderBFSImpl extends Code03_TopologicalOrderBFS {


    // 拓扑排序隐藏含义：有向无环图
    // 方法1：入度法。每次去入度为0的节点，并把其后序节点的入度减1
    public static class Topological {
        public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
            // write your code here
            ArrayList<DirectedGraphNode> ans = new ArrayList<>();
            if (graph == null) {
                return ans;
            }
            // 初始化入度表
            Map<DirectedGraphNode, Integer> indegreMap = graph.stream().collect(Collectors.toMap(k -> k, k -> 0));
            graph.forEach(cur -> cur.neighbors.forEach(next -> indegreMap.put(next, indegreMap.get(next) + 1)));
            Queue<DirectedGraphNode> queue = new LinkedList<>();
            indegreMap.forEach((node, indegre) -> {
                if (indegre == 0) {
                    queue.offer(node);
                }
            });
            while (!queue.isEmpty()) {
                DirectedGraphNode cur = queue.poll();
                ans.add(cur);
                cur.neighbors.forEach(next -> {
                    int indegre = indegreMap.get(next) - 1;
                    indegreMap.put(next, indegre);
                    if (indegre == 0) {
                        queue.offer(next);
                    }
                });
            }
            return ans;
        }
    }


}
