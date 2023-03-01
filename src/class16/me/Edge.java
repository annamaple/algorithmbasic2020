package class16.me;

public class Edge<T> {
    int weight;
    Node<T> from;
    Node<T> to;

    public Edge(int weight, Node<T> from, Node<T> to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
