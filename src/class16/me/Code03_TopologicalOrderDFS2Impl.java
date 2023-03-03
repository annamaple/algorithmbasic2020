package class16.me;

import class16.Code03_TopologicalOrderDFS2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author lei
 */
public class Code03_TopologicalOrderDFS2Impl extends Code03_TopologicalOrderDFS2 {
    
    
    // 点次法：找到每一个点所到之处的所有点的数量，按照数量从到大到小的顺序排列节点的顺序就是拓扑排序
    // 点次法本质是深度搜索
    public static class TopologicalOrderDFS2 {

        public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
            if (graph == null) return null;
            // Pair k: Node, v: 所到之处的点数量
            Map<DirectedGraphNode, Pair<DirectedGraphNode, Long>> map = new HashMap<>();
            for (DirectedGraphNode node : graph) {
                df(node, map);
            }
            return map.values().stream()
                    .sorted((a, b) -> Objects.equals(b.getValue(), a.getValue()) ? 0 : (b.getValue() > a.getValue() ? 1 : -1))
                    .map(Pair::getKey).collect(Collectors.toCollection(ArrayList::new));
        }

        // 深度搜索图节点所到之处的所有点的数量
        private long df(DirectedGraphNode node, Map<DirectedGraphNode, Pair<DirectedGraphNode, Long>> map) {
            if (map.containsKey(node)) {
                return map.get(node).getValue();
            }
            long nodes = 1;
            for (DirectedGraphNode next : node.neighbors) {
                nodes += df(next, map);
            }
            map.put(node, new Pair<>(node, nodes));
            return nodes;
        }
        
        private static class Pair<K, V> {
            K key;
            V value;

            public Pair(K key, V value) {
                this.key = key;
                this.value = value;
            }

            public K getKey() {
                return key;
            }

            public V getValue() {
                return value;
            }
        }
    }
}
