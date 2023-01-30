package class03.me;

import class03.Code02_DeleteGivenValue;

/**
 * 题目保证链表中节点的值互不相同, head!=null
 *
 * @author lei
 */
public class Code02_DeleteGivenValueImpl extends Code02_DeleteGivenValue {

    public static Node removeGivenValueNoRepeat(Node head, int num) {
        // 首先解决head的值即为删除的值
        if (head.value == num) return head.next;
        Node cur = head;
        Node pre = null;
        while (cur != null && cur.value != num) {
            pre = cur;
            cur = cur.next;
        }
        // 1 ) cur == null
        // 2 ) cur != null
        if (cur != null) {
            pre.next = cur.next;
        }
        return head;
    }
    
    public static Node removeGivenValue(Node head, int num) {
        // 先找到返回的头部节点
        while (head != null && head.value == num) {
            head = head.next;
        }
        Node pre = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value == num) {
                pre.next = cur.next;
            } else {
                // 注意这一步
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }
}
