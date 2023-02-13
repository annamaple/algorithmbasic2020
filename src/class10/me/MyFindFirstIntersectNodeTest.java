package class10.me;

import class10.Code01_FindFirstIntersectNode;

/**
 * Cycle List Intersect
 *
 * @author lei
 */
public class MyFindFirstIntersectNodeTest extends Code01_FindFirstIntersectNode {

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);

        // 0->9->8->6->7->null
        ListNode head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).val);

        // 1->2->3->4->5->6->7->4...
        head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(6);
        head1.next.next.next.next.next.next = new ListNode(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).val);

        // 0->9->8->6->4->5->6..
        head2 = new ListNode(0);
        head2.next = new ListNode(9);
        head2.next.next = new ListNode(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).val);
    }
    
    public static ListNode getIntersectNode(ListNode headA, ListNode headB) {
        return new FindFirstIntersectNode().getIntersectNode(headA, headB);
    }

    public static class FindFirstIntersectNode {

        /**
         * 两个不知是否有环的链表，请求他们的相交节点
         *
         * @param headA 链接A的头结点
         * @param headB 链表B的头节点
         * @return 相交节点，不相交返回null
         */
        public ListNode getIntersectNode(ListNode headA, ListNode headB) {
            if (headA == null || headB == null) {
                return null;
            }
            ListNode firstCycleNodeA = getFirstInCycleNode(headA);
            ListNode firstCycleNodeB = getFirstInCycleNode(headB);
            if (firstCycleNodeA == null && firstCycleNodeB == null) {
                // 都无环
                return getNoCycleListIntersectNode(headA, headB);
            }
            if (firstCycleNodeA != null && firstCycleNodeB != null) {
                // 都有环
                // 入环节点是同一个, 相交节点一定不在环上
                if (firstCycleNodeA == firstCycleNodeB) {
                    // 入环节点和之后的节点断开, 此时headA, headB必无环，调用无环节点获取相交节点的方法
                    ListNode next = firstCycleNodeA.next;
                    firstCycleNodeA.next = null;
                    ListNode ans = getNoCycleListIntersectNode(headA, headB);
                    // 链表还原
                    firstCycleNodeA.next = next;
                    return ans;
                } else {
                    // 入环节点不相等, 要么在环上相交，要么不相交
                    ListNode curA = firstCycleNodeA.next;
                    while (curA != firstCycleNodeA) {
                        if (curA == firstCycleNodeB) {
                            // 在环上相交时, 有两个最近的相交节点，返回任意一个即可
                            return firstCycleNodeA;
                        }
                        curA = curA.next;
                    }
                    // 走完一圈没遇到 firstCycleNodeB，表示不会相交
                    return null;
                }
            }
            // 一个无环一个有环必无相交节点
            return null;
        }


        /**
         * 获取两个不相交链表
         */
        public ListNode getNoCycleListIntersectNode(ListNode headA, ListNode headB) {
            ListNode curA = headA;
            ListNode curB = headB;
            int n = 0;
            while (curA.next != null) {
                n++;
                curA = curA.next;
            }
            while (curB.next != null) {
                n--;
                curB = curB.next;
            }
            if (curA != curB) {
                return null;
            }
            // 重定向：使curA指向较长的链表的头节点，curB指向较短的链表的头节点
            curA = n >= 0 ? headA : headB;
            curB = curA == headA ? headB : headA;
            n = Math.abs(n);
            while (n != 0) {
                curA = curA.next;
                n--;
            }
            while (curA != curB) {
                curA = curA.next;
                curB = curB.next;
            }
            return curA;
        }

        /**
         * 求链表的第一个入环节点
         *
         * @param head 头结点
         * @return 第一个入环节点，链表无环返回null
         */
        public ListNode getFirstInCycleNode(ListNode head) {
            // 方法一：使用Set 略
            // 方法二：使用快慢指针
            if (head == null || head.next == null || head.next.next == null) {
                return null;
            }
            ListNode slow = head.next;
            ListNode fast = head.next.next;
            while (slow != fast) {
                if (fast.next == null || fast.next.next == null) {
                    return null;
                }
                slow = slow.next;
                fast = fast.next.next;
            }
            fast = head;
            while (slow != fast) {
                slow = slow.next;
                fast = fast.next;
            }
            return slow;
        }


        
    }

    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
