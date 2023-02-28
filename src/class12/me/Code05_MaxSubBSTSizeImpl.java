package class12.me;

import class12.Code05_MaxSubBSTSize;

/**
 * @author lei
 */
public class Code05_MaxSubBSTSizeImpl extends Code05_MaxSubBSTSize {


    // 为了验证
    // 对数器方法
    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
//            if (largestBSTSubtree(head) != right(head)) {
//                System.out.println("出错了！");
//            }
            if (new MaxSubBST().largestBSTSize(head) != right(head)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }


    public static class MaxSubBST {

        public int largestBSTSize(TreeNode root) {
            Info ans = process(root);
            return ans == null ? 0 : ans.maxSize;
        }

        Info process(TreeNode node) {
            if (node == null) {
                return new Info(true, 0, null, null, null);
            }
            Info leftInfo = process(node.left);
            Info rightInfo = process(node.right);
            boolean isBST = false;
            int maxSize;
            Integer max;
            Integer min;
            TreeNode maxBSTHead;
            boolean leftLess = leftInfo.max == null || leftInfo.max < node.val;
            boolean rightMore = rightInfo.min == null || rightInfo.min > node.val;
            if (leftInfo.isBST && rightInfo.isBST && leftLess && rightMore) {
                isBST = true;
                maxSize = leftInfo.maxSize + rightInfo.maxSize + 1;
                min = leftInfo.min == null ? node.val : leftInfo.min;
                max = rightInfo.max == null ? node.val : rightInfo.max;
                maxBSTHead = node;
            } else {
                Info info = leftInfo.maxSize >= rightInfo.maxSize ? leftInfo : rightInfo;
                maxSize = info.maxSize;
                min = info.min;
                max = info.max;
                maxBSTHead = info.maxBSTHead;
            }
            return new Info(isBST, maxSize, maxBSTHead, min, max);
        }


        public static class Info {
            boolean isBST;
            int maxSize;
            TreeNode maxBSTHead;
            Integer min;
            Integer max;

            public Info(boolean isBST, int maxSize, TreeNode maxBSTHead, Integer min, Integer max) {
                this.isBST = isBST;
                this.maxSize = maxSize;
                this.maxBSTHead = maxBSTHead;
                this.min = min;
                this.max = max;
            }
        }
    }
}
