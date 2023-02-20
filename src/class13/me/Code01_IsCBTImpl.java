package class13.me;

import class13.Code01_IsCBT;

/**
 * @author lei
 */
public class Code01_IsCBTImpl extends Code01_IsCBT {

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (new CompleteTree().isCBT(head) != isCompleteTree2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    public static class CompleteTree {

        // 是否完全二叉树的DP写法
        /*
            是的情况：
            1. 左满 右满: 高度相等
            2. 左满 右完全: 左高 = 右高 且
            3. 左右完全 右满: 左高 = 右高 + 1
            4. 左满 有满： 左高 = 右高 + 1
        */
        public boolean isCBT(TreeNode root) {
            return process(root).isCBT;
        }

        public Info process(TreeNode node) {
            if (node == null) return new Info(true, true, 0);
            Info leftInfo = process(node.left);
            Info rightInfo = process(node.right);
            int height = Math.max(leftInfo.height, rightInfo.height);
            boolean isFull = leftInfo.isFull && rightInfo.isFull && (leftInfo.height == rightInfo.height || leftInfo.height == rightInfo.height + 1);
            boolean isCBT = isFull;
            if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
                isCBT = true;
            }
            if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                isCBT = true;
            }
            return new Info(isFull, isCBT, height);
        }

        public static class Info {
            boolean isFull;
            boolean isCBT;
            int height;

            public Info(boolean isFull, boolean isCBT, int height) {
                this.isFull = isFull;
                this.isCBT = isCBT;
                this.height = height;
            }
        }
    }

}
