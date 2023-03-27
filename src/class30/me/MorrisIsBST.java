package class30.me;

import class12.Code02_IsBST;

// leetcode 98
public class MorrisIsBST extends Code02_IsBST {

    public static class Morris {

        public boolean isBST(Node head) {
            if (head == null) return true;
            Node cur = head, mostRight;
            Integer pre = null;
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
                if (pre != null && cur.value <= pre) {
                    return false;
                }
                pre = cur.value;
                cur = cur.right;
            }
            return true;
        }
    }


    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != new Morris().isBST(head)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finish!");
    }
}
