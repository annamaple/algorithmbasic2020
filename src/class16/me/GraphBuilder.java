package class16.me;

public class GraphBuilder {

    // matrix: 所有的边
    // N * 3的矩阵
    // [1, 2, 3]  weight, form的值 to的值
    public static Graph<Integer> build(int[][] matrix) {
        Graph<Integer> graph = new Graph<>();
        for (int[] side : matrix) {
            int weight = side[0];
            int from = side[1];
            int to = side[2];
            Node<Integer> fromNode = graph.nodes.computeIfAbsent(from, Node::new);
            Node<Integer> toNode = graph.nodes.computeIfAbsent(to, Node::new);
            Edge<Integer> edge = new Edge<>(weight, fromNode, toNode);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            fromNode.edges.add(edge);
            toNode.in++;
            graph.edges.add(edge);
        }
        return graph;
    }
}
