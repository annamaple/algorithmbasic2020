package class30.me;

import class30.Code01_MorrisTraversal;

import java.util.LinkedList;
import java.util.List;

public class Code01_MorrisTraversalImpl extends Code01_MorrisTraversal {

    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);

        System.out.println("-----pre-----");
        System.out.println(new Visit().visitPre(head));
        System.out.println(new Morris().morrisPre(head));
        System.out.println("-----i n-----");
        System.out.println(new Visit().visitIn(head));
        System.out.println(new Morris().morrisIn(head));
        System.out.println("-----sub-----");
        System.out.println(new Visit().visitSub(head));
        System.out.println(new Morris().morrisSub(head));
    }

    public static class Morris {

        List<Integer> morrisPre(Node head) {
            List<Integer> ans = new LinkedList<>();
            if (head != null) {
                Node cur = head, mostRight;
                while (cur != null) {
                    mostRight = cur.left;
                    if (mostRight != null) {
                        while (mostRight.right != null && mostRight.right != cur) {
                            mostRight = mostRight.right;
                        }
                        if (mostRight.right == null) {
                            ans.add(cur.value);
                            mostRight.right = cur;
                            cur = cur.left;
                            continue;
                        } else {
                            mostRight.right = null;
                        }
                    } else {
                        ans.add(cur.value);
                    }
                    cur = cur.right;
                }
            }
            return ans;
        }

        List<Integer> morrisIn(Node root) {
            List<Integer> ans = new LinkedList<>();
            if (root != null) {
                Node cur = root, mostRight;
                while (cur != null) {
                    mostRight = cur.left;
                    if (mostRight != null) {
                        while (mostRight.right != null && mostRight.right != cur) {
                            mostRight = mostRight.right;
                        }
                        if (mostRight.right == null) {
                            mostRight.right = cur;
                            cur = cur.left;
                            continue;
                        } else {
                            mostRight.right = null;
                        }
                    }
                    ans.add(cur.value);
                    cur = cur.right;
                }
            }
            return ans;
        }

        public List<Integer> morrisSub(Node head) {
            List<Integer> ans = new LinkedList<>();
            if (head != null) {
                Node mostRight, cur = head;
                while (cur != null) {
                    mostRight = cur.left;
                    if (mostRight != null) {
                        while (mostRight.right != null && mostRight.right != cur) {
                            mostRight = mostRight.right;
                        }
                        if (mostRight.right == null) {
                            mostRight.right = cur;
                            cur = cur.left;
                            continue;
                        } else {
                            mostRight.right = null;
                            addAns(cur.left, ans);
                        }
                    }
                    cur = cur.right;
                }
            }
            addAns(head, ans);
            return ans;
        }

        Node reverse(Node head) {
            Node next, pre = null;
            while (head != null) {
                next = head.right;
                head.right = pre;
                pre = head;
                head = next;
            }
            return pre;
        }

        void addAns(Node node, List<Integer> ans) {
            Node tail = reverse(node);
            Node cur = tail;
            while (cur != null) {
                ans.add(cur.value);
                cur = cur.right;
            }
            reverse(tail);
        }
    }


    public static class Visit {

        List<Integer> visitPre(Node root) {
            List<Integer> ans = new LinkedList<>();
            pre(root, ans);
            return ans;
        }

        void pre(Node node, List<Integer> ans) {
            ans.add(node.value);
            if (node.left != null) {
                pre(node.left, ans);
            }
            if (node.right != null) {
                pre(node.right, ans);
            }
        }

        List<Integer> visitIn(Node root) {
            List<Integer> ans = new LinkedList<>();
            in(root, ans);
            return ans;
        }

        void in(Node node, List<Integer> ans) {
            if (node.left != null) {
                in(node.left, ans);
            }
            ans.add(node.value);
            if (node.right != null) {
                in(node.right, ans);
            }
        }

        List<Integer> visitSub(Node root) {
            List<Integer> ans = new LinkedList<>();
            sub(root, ans);
            return ans;
        }

        void sub(Node node, List<Integer> ans) {
            if (node != null) {
                sub(node.left, ans);
                sub(node.right, ans);
                ans.add(node.value);
            }
        }
    }
}
