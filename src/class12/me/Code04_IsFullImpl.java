package class12.me;

import class12.Code04_IsFull;

public class Code04_IsFullImpl extends Code04_IsFull {

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != new IsFull().isFull(head)) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");
    }

    public static class IsFull {

        public boolean isFull(Node head) {
            if (head == null) return true;
            Info info = process(head);
            return Math.pow(2, info.height) - 1 == info.size;
        }

        Info process(Node node) {
            if (node == null) {
                return new Info(0, 0);
            }
            Info leftInfo = process(node.left);
            Info rightInfo = process(node.right);
            int height = Math.max(leftInfo.height, rightInfo.height) + 1;
            int size = leftInfo.size + rightInfo.size + 1;
            return new Info(height, size);
        }

        public static class Info {
            int height;
            int size;

            public Info(int height, int size) {
                this.height = height;
                this.size = size;
            }
        }
    }
}
