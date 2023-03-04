package class17.me;

import class17.Code02_Hanoi;

public class Code02_HanoiImpl extends Code02_Hanoi {

    public static void main(String[] args) {
        System.out.println(new Hanoi().hanoi(3));
    }

    // 汉罗塔问题
    public static class Hanoi {

        // n为层数
        public int hanoi(int n) {
            return process(n, "left", "right", "mid");
        }

        // 整体思维
        int process(int n, String from, String to, String other) {
            if (n == 1) {
                System.out.println("move " + n + " from " + from + " to " + to);
                return 1;
            }
            int p1 = process(n - 1, from, other, to);
            System.out.println("move " + n + " from " + from + " to " + to);
            int p2 = process(n - 1, other, to, from);
            return p1 + p2 + 1;
        }
    }
}
