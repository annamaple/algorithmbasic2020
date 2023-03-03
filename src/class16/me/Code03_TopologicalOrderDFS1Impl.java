package class16.me;

import class16.Code03_TopologicalOrderDFS1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lei
 */
public class Code03_TopologicalOrderDFS1Impl extends Code03_TopologicalOrderDFS1 {
    
    // 最大深度法：递归找到每个节点的最大深度。 拓扑排序为最大深度节点依次递减
    
    public static class TopologicalOrder {

        public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
            if (graph == null) return null;
            Map<DirectedGraphNode, Info> map = new HashMap<>();
            for (DirectedGraphNode node : graph) {
                df(node, map);
            }
            return map.values().stream()
                    .sorted((a, b) -> b.deep - a.deep)
                    .map(Info::getNode)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        // 深度查找每个节点的最大深度, 并放在map中
        private static Info df(DirectedGraphNode node, Map<DirectedGraphNode, Info> map) {
            if (map.containsKey(node)) {
                return map.get(node);
            }
            int deep = 0;
            for (DirectedGraphNode next : node.neighbors) {
                deep = Math.max(deep, df(next, map).deep);
            }
            Info ans = new Info(node, deep + 1);
            map.put(node, ans);
            return ans;
        }


        public static class Info {
            DirectedGraphNode node;
            int deep;

            public Info(DirectedGraphNode node, int deep) {
                this.node = node;
                this.deep = deep;
            }

            public DirectedGraphNode getNode() {
                return node;
            }

            public int getDeep() {
                return deep;
            }
        }
    }
    
}
