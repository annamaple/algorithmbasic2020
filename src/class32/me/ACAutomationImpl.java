package class32.me;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ACAutomationImpl {

    public static void main(String[] args) {
        String a1 = "ab";
        String a2 = "bc";
        String a3 = "abc";
        String a4 = "anna";
        String context = "anbcaabbabcannnannafdsfdsfs";
        ACAutomationImpl acAutomation = new ACAutomationImpl();
        acAutomation.insert(a1);
        acAutomation.insert(a2);
        acAutomation.insert(a3);
        acAutomation.insert(a4);
        acAutomation.build();
        System.out.println(acAutomation.contains(context));
    }

    private static class Node {
        String end;
        boolean endUsed;
        Node[] next;
        Node fail;

        public Node() {
            this.end = null;
            this.endUsed = false;
            // 26个小写字符
            this.next = new Node[26];
        }
    }

    Node root;

    public ACAutomationImpl() {
        root = new Node();
    }

    public void insert(String target) {
        if (target == null || target.length() == 0) {
            return;
        }
        char[] str = target.toCharArray();
        Node cur = root;
        for (char c : str) {
            if (cur.next[c - 'a'] == null) {
                cur.next[c - 'a'] = new Node();
            }
            cur = cur.next[c - 'a'];
        }
        cur.end = target;
    }

    // [|: a] [\: b] [——: c]
    // O —— O
    // | \
    // O  O —— O
    //  \
    //   O —— O
    public void build() {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        Node cur, cFail, next;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            // 当前节点把其子的fail节点连接好
            for (int i = 0; i < cur.next.length; i++) {
                if (cur.next[i] != null) {
                    cur.next[i].fail = root;
                    cFail = cur.fail;
                    while (cFail != null) {
                        if (cFail.next[i] != null) {
                            cur.next[i].fail = cFail.next[i];
                            break;
                        }
                        cFail = cFail.fail;
                    }
                    queue.add(cur.next[i]);
                }
            }
        }
    }

    public List<String> contains(String context) {
        List<String> ans = new LinkedList<>();
        if (context != null) {
            char[] str = context.toCharArray();
            Node cur = root, flow;
            for (char c : str) {
                while (cur.next[c - 'a'] == null && cur != root) {
                    cur = cur.fail;
                }
                cur = cur.next[c - 'a'] != null ? cur.next[c - 'a'] : root;
                flow = cur;
                while (flow != root && !flow.endUsed) {
                    if (flow.end != null && flow.end.length() > 0) {
                        ans.add(flow.end);
                        flow.endUsed = true;
                    }
                    flow = flow.fail;
                }
            }
        }
        return ans;
    }
}
