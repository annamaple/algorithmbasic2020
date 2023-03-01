package class16.me;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    T val;
    int in;
    int out;
    List<Edge<T>> edges;
    List<Node<T>> nexts;

    public Node(T val) {
        this.val = val;
        this.in = 0;
        this.out = 0;
        this.edges = new ArrayList<>();
        this.nexts = new ArrayList<>();
    }
}
