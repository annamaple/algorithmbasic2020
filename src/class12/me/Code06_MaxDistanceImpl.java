package class12.me;

import class12.Code06_MaxDistance;

public class Code06_MaxDistanceImpl extends Code06_MaxDistance {

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxDistance1(head) != new MaxDistance().maxDistance(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

    public static class MaxDistance {

        public int maxDistance(Node root) {
            return process(root).maxDis;
        }

        Info process(Node node) {
            if (node == null) return new Info(0, 0);
            Info leftInfo = process(node.left);
            Info rightInfo = process(node.right);
            int maxDis =  Math.max(Math.max(leftInfo.maxDis, rightInfo.maxDis), leftInfo.height + rightInfo.height + 1);
            int height = Math.max(leftInfo.height, rightInfo.height) + 1;
            return new Info(maxDis, height);
        }

        public static class Info {
            int maxDis;
            int height;

            public Info(int maxDis, int height) {
                this.maxDis = maxDis;
                this.height = height;
            }
        }
    }
}
