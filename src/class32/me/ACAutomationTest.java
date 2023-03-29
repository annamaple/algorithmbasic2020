package class32.me;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ACAutomationTest {

    public static void main(String[] args) {
        String s = "AesUtils纵有红颜百生千劫";
        String[] targets = {
                "11",
                "ls",
                "Ae",
                "百胜",
                "百生"
        };
        ACAutomation ac = new ACAutomation();
        for (String target : targets) {
            ac.add(target);
        }
        ac.build();
        System.out.println(ac.check(s));
    }

    public static class ACAutomation {

        private static class Node {
            boolean endUse;
            String end;
            Map<Character, Node> nexts = new HashMap<>();
            Node fail;
        }

        private Node root;

        public ACAutomation() {
            this.root = new Node();
        }

        public void add(String target) {
            char[] str = target.toCharArray();
            Node cur = root;
            for (char c : str) {
                if (cur.nexts.get(c) == null) {
                    cur.nexts.put(c, new Node());
                }
                cur = cur.nexts.get(c);
            }
            cur.end = target;
        }

        public void build() {
            Node cur = root, cFill, ch;
            Queue<Node> queue = new LinkedList<>();
            queue.add(cur);
            while (!queue.isEmpty()) {
                cur = queue.poll();
                for (char c : cur.nexts.keySet()) {
                    ch = cur.nexts.get(c);
                    cFill = cur.fail;
                    ch.fail = root;
                    while (cFill != null) {
                        if (cFill.nexts.get(c) != null) {
                            ch.fail = cFill.nexts.get(c);
                            break;
                        }
                        cFill = cFill.fail;
                    }
                    queue.offer(ch);
                }
            }
        }

        public List<String> check(String context) {
            List<String> ans = new LinkedList<>();
            char[] str = context.toCharArray();
            Node cur = root, flow;
            for (char c : str) {
                while (cur.nexts.get(c) == null && cur.fail != null) {
                    cur = cur.fail;
                }
                cur = cur.nexts.get(c) != null ? cur.nexts.get(c) : root;
                flow = cur;
                while (flow != root && !flow.endUse) {
                    if (flow.end != null) {
                        ans.add(flow.end);
                    }
                    flow = flow.fail;
                }
            }
            return ans;
        }
    }

}
