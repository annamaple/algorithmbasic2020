package class30.me;

import class30.Code02_MinDepth;

public class Code02_MinDepthImpl extends Code02_MinDepth {

    public static void main(String[] args) {
        //       1
        //     2  3
        //    4 5   6
        //   7

        TreeNode node = new TreeNode(1);
        node.left = new TreeNode(2);
        node.right = new TreeNode(3);
        node.left.left = new TreeNode(4);
        node.left.right = new TreeNode(5);
        node.right.right = new TreeNode(6);
        node.left.left.left = new TreeNode(7);
        System.out.println(new MinDepth().minDepth1(node));
        System.out.println(new MinDepth().minDepth2(node));
        System.out.println(minDepth1(node));
    }


    public static class MinDepth {

        public int minDepth1(TreeNode head) {
            return process(head);
        }

        int process(TreeNode node) {
//            if (node == null) {
//                return 0;
//            }
            if (node.left == null && node.right == null) {
                return 1;
            }
            int p1 = Integer.MAX_VALUE;
            if (node.left != null) {
                p1 = process(node.left);
            }
            int p2 = Integer.MAX_VALUE;
            if (node.right != null) {
                p2 = process(node.right);
            }
            return 1 + Math.min(p1, p2);
        }


        public int minDepth2(TreeNode head) {
            if (head == null) return 0;
            int curLevel = 0, mostRightCount, minLevel = Integer.MAX_VALUE;
            TreeNode cur = head, mostRight;
            while (cur != null) {
                mostRight = cur.left;
                if (mostRight != null) {
                    mostRightCount = 1;
                    while (mostRight.right != null && mostRight.right != cur) {
                        mostRight = mostRight.right;
                        mostRightCount++;
                    }
                    if (mostRight.right == null) {
                        curLevel++;
                        mostRight.right = cur;
                        cur = cur.left;
                        continue;
                    } else {
                        if (mostRight.left == null) {
                            minLevel = Math.min(minLevel, curLevel);
                        }
                        curLevel -= mostRightCount;
                        mostRight.right = null;
                    }
                } else {
                    curLevel++;
                }
                cur = cur.right;
            }
            // 最右节点不在计算内，需要加上。同后序
            curLevel = 1;
            cur = head;
            while (cur.right != null) {
                cur = cur.right;
                curLevel++;
            }
            if (cur.left == null) {
                return Math.min(minLevel, curLevel);
            }
            return minLevel;
        }
    }

}
