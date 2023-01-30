package class03.me;

import class03.Code01_ReverseList;

import java.util.List;

/**
 * @author lei
 */
public class Code01_ReverseListImpl extends Code01_ReverseList {

    //  head
    //   a    ->   b    ->  c  ->  null
    //   c    ->   b    ->  a  ->  null
    public static Node reverseLinkedList(Node head) {
        Node cur = head;
        Node next = null;
        Node pre = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    // head
    // null   <   a   <->  b   <->   c  ->  null
    // null   <-  c   <->  b   <->   a  ->  null  
    public static DoubleNode reverseDoubleList(DoubleNode head) {
        DoubleNode cur = head;
        DoubleNode next = null;
        DoubleNode pre = null;
        while (cur != null) {
            next = cur.next;
            cur.next = cur.last;
            cur.last = next;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        DoubleNode node1 = generateRandomDoubleList(10, 10);
        boolean result = checkDoubleListReverse(getDoubleListOriginOrder(node1), reverseDoubleList(node1));
        System.out.println(result ? "success" : "fuck");
//        while (cur != null) {
//            System.out.print(" " + cur.value);
//            cur = cur.next;
//        }


//        int len = 50;
//        int value = 100;
//        int testTime = 100000;
//        System.out.println("test begin!");
//        for (int i = 0; i < testTime; i++) {
//            Node node1 = generateRandomLinkedList(len, value);
//            List<Integer> list1 = getLinkedListOriginOrder(node1);
//            node1 = reverseLinkedList(node1);
//            if (!checkLinkedListReverse(list1, node1)) {
//                System.out.println("Oops1!");
//            }
//
//            Node node2 = generateRandomLinkedList(len, value);
//            List<Integer> list2 = getLinkedListOriginOrder(node2);
//            node2 = testReverseLinkedList(node2);
//            if (!checkLinkedListReverse(list2, node2)) {
//                System.out.println("Oops2!");
//            }
//
//            DoubleNode node3 = generateRandomDoubleList(len, value);
//            List<Integer> list3 = getDoubleListOriginOrder(node3);
//            node3 = reverseDoubleList(node3);
//            if (!checkDoubleListReverse(list3, node3)) {
//                System.out.println("Oops3!");
//            }
//
//            DoubleNode node4 = generateRandomDoubleList(len, value);
//            List<Integer> list4 = getDoubleListOriginOrder(node4);
//            node4 = reverseDoubleList(node4);
//            if (!checkDoubleListReverse(list4, node4)) {
//                System.out.println("Oops4!");
//            }
//
//        }
//        System.out.println("test finish!");
    }
}
