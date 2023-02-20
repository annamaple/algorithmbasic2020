package class12.me;

import class12.Code01_IsCBT;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_IsCBTImpl extends Code01_IsCBT {

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != new IsCBT().isCbt(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    public static class IsCBT {
        // 层次遍历：记录是否再次遇到过左右孩子不双全的节点且不是叶子节点
        public boolean isCbt(Node head) {
            if (head == null) return true;
            Queue<Node> queue = new LinkedList<>();
            queue.offer(head);
            Node cur;
            // 标记是否遇到过左右不双全的节点
            boolean isLeaf = false;
            while (!queue.isEmpty()) {
                cur = queue.poll();
                if ((isLeaf && (cur.right != null || cur.left != null)) ||
                        (cur.left == null && cur.right != null)) {
                    return false;
                }
                if (cur.left == null || cur.right == null) {
                    isLeaf = true;
                }
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
            return true;
        }
    }
}
