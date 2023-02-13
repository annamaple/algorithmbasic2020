package class09.me;

import class09.Code03_SmallerEqualBigger;

/**
 * @author lei
 */
public class Code03_SmallerEqualBiggerImpl extends Code03_SmallerEqualBigger {

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
        // head1 = listPartition1(head1, 4);
//        head1 = listPartition2(head1, 5);
        head1 = new SmallerEqualBigger().partition(head1, 1);
        printLinkedList(head1);
    }
    
    public static class SmallerEqualBigger {
        
        public Node partition(Node head, int pivot) {
            Node smallHead = null;
            Node smallTail = null;
            Node equalHead = null;
            Node equalTail = null;
            Node moreHead = null;
            Node moreTail = null;
            Node cur = head;
            Node next = null;
            while (cur != null) {
                next = cur.next;
                cur.next = null;
                if (cur.value < pivot) {
                    if (smallHead == null) {
                        smallHead = cur;
                        smallTail = cur;
                    } else {
                        smallTail.next = cur;
                        smallTail = smallTail.next;
                    }
                } else if (cur.value == pivot) {
                    if (equalHead == null) {
                        equalHead = cur;
                        equalTail = cur;
                    } else {
                        equalTail.next = cur;
                        equalTail = equalTail.next;
                    }
                } else {
                    if (moreHead == null) {
                        moreHead = cur;
                        moreTail = cur;
                    } else {
                        moreTail.next = cur;
                        moreTail = moreTail.next;
                    }
                }
                cur = next;
            }
            // 难点：链接链表
            // smallTail -> equalHead; equalTail -> moreHead
            if (equalTail != null) {
                equalTail.next = moreHead;
            } else {
                equalHead = moreHead;
            }
            if (smallHead != null) {
                smallTail.next = equalHead;
            } else {
                smallHead = equalHead;
            }
            return smallHead;
            
        }
    }
}
