package class37.me;

import class37.Code02_SlidingWindowMedian;

import java.util.Arrays;

public class Code02_SlidingWindowMedianImpl extends Code02_SlidingWindowMedian {

    // https://leetcode.cn/problems/sliding-window-median/
    // 滑动窗口中位数
    // 中位数是有序序列最中间的那个数。如果序列的长度是偶数，则没有最中间的数；此时中位数是最中间的两个数的平均数。

    static int[] run(char[] str, int i, int x, int y, int dir) {
        if (i == str.length) {
            return new int[]{x, y, dir};
        }
        if (str[i] == 'G') {
            y += dir == 3 ? 1 : dir == 1 ? -1 : 0;
            x += dir == 0 ? 1 : dir == 2 ? -1 : 0;
        } else if (str[i] == 'R') {
            dir = dir + 1 == 4 ? 0 : dir + 1;
        } else {
            dir = dir == 0 ? 3 : dir - 1;
        }
        return run(str, i + 1, x, y, dir);
    }

    static public boolean isRobotBounded(String instructions) {
        if (instructions == null) {
            return true;
        }
        int[] info = run(instructions.toCharArray(), 0, 0, 0, 3);
        boolean flag = info[0] == 0 && info[1] == 0;
        return !flag;
    }

    public static void main(String[] args) {
        String s = "GGLLGG";
        System.out.println(isRobotBounded(s));



//        int[] arr = {1, 4, 2, 3};
        int[] arr = {1,3,-1,-3,5,3,6,7};
        int k = 3;
        double[] ans = medianSlidingWindow1(arr, k);
        System.out.println(Arrays.toString(ans));
    }

    // 有序表
    public static double[] medianSlidingWindow1(int[] nums, int k) {
        if (nums == null || k < 0 || nums.length < k) return null;
        SBTree<Integer> map = new SBTree<>();
        int N = nums.length;
        double[] ans = new double[N - k + 1];
        boolean flag = (k & 1) == 1;
        int l = 0, r = 0, i = 0;
        while (r < k - 1) {
            map.put(nums[r++]);
        }
        while (r < N) {
            map.put(nums[r++]);
            if (flag) {
                ans[i++] = (double) map.findRankK(k / 2 + 1);
            } else {
                int a = map.findRankK(k / 2);
                int b = map.findRankK(k / 2 + 1);
                ans[i++] = a / 2.0 + b / 2.0;
            }
            map.remove(nums[l++]);
        }
        return ans;
    }

    public static class SBTree<K extends Comparable<K>> {

        Node<K> root;

        // 查询第k名
        public K findRankK(int rank) {
            Node<K> cur = root;
            int rankL, rankR, pre = 0;
            while (cur != null) {
                rankL = pre + all(cur.l) + 1;
                rankR = pre + all(cur) - all(cur.r);
                if (rank < rankL) {
                    cur = cur.l;
                } else if (rank > rankR) {
                    pre = rankR;
                    cur = cur.r;
                } else {
                    return cur.k;
                }
            }
            return null;
        }

        public void put(K k) {
            this.root = add(this.root, k, contains(k));
        }

        public void remove(K k) {
            Node<K> node = findLessAndEqualK(k);
            if (node != null && node.k.compareTo(k) == 0) {
                this.root = del(this.root, k, all(node) - all(node.l) - all(node.r) == 1);
            }
        }

        boolean contains(K k) {
            Node<K> node = findLessAndEqualK(k);
            return node != null && node.k.compareTo(k) == 0;
        }

        Node<K> findLessAndEqualK(K k) {
            Node<K> cur = this.root, res = null;
            while (cur != null) {
                if (k.compareTo(cur.k) == 0) {
                    return cur;
                } else if (k.compareTo(cur.k) < 0) {
                    res = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return res;
        }

        Node<K> del(Node<K> cur, K k, boolean removeNode) {
            if (removeNode) {
                cur.size--;
            }
            cur.all--;
            if (k.compareTo(cur.k) < 0) {
                cur.l = del(cur.l, k, removeNode);
            } else if (k.compareTo(cur.k) > 0) {
                cur.r = del(cur.r, k, removeNode);
            } else {
                if (removeNode) {
                    if (cur.l == null || cur.r == null) {
                        cur = cur.l == null ? cur.r : cur.l;
                    } else {
                        Node<K> des = cur.r;
                        while (des.l != null) {
                            des = des.l;
                        }
                        int same = all(des) - all(des.l) - all(des.r);
                        cur.r = del(cur.r, des.k, true);
                        des.l = cur.l;
                        des.r = cur.r;
                        pushup(des);
                        des.all = same + all(des.l) + all(des.r);
                        cur = des;
                    }
                }
            }
            return cur;
        }

        Node<K> add(Node<K> cur, K k, boolean contains) {
            if (cur == null) {
                return new Node<>(k);
            }
            if (k.compareTo(cur.k) == 0) {
                cur.all++;
                return cur;
            }
            if (!contains) {
                cur.size++;
            }
            cur.all++;
            if (k.compareTo(cur.k) < 0) {
                cur.l = add(cur.l, k, contains);
            } else {
                cur.r = add(cur.r, k, contains);
            }
            return maintain(cur);
        }

        Node<K> rightRotate(Node<K> cur) {
            int same = all(cur) - all(cur.l) - all(cur.r);
            Node<K> l = cur.l;
            cur.l = l.r;
            l.r = cur;
            pushup(cur);
            pushup(l);
            l.all = cur.all;
            cur.all = all(cur.l) + all(cur.r) + same;
            return l;
        }

        Node<K> leftRotate(Node<K> cur) {
            int same = all(cur) - all(cur.l) - all(cur.r);
            Node<K> r = cur.r;
            cur.r = r.l;
            r.l = cur;
            pushup(cur);
            pushup(r);
            r.all = cur.all;
            cur.all = all(cur.l) + all(cur.r) + same;
            return r;
        }

        Node<K> maintain(Node<K> cur) {
            if (cur == null) return null;
            int l = size(cur.l);
            int ll = l == 0 ? 0 : size(cur.l.l);
            int lr = l == 0 ? 0 : size(cur.l.r);
            int r = size(cur.r);
            int rr = r == 0 ? 0 : size(cur.r.r);
            int rl = r == 0 ? 0 : size(cur.r.l);
            boolean flag = ll > r || lr > r || rr > l || rl > l;
            if (ll > r) {
                cur = rightRotate(cur);
            } else if (lr > r) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
            } else if (rr > l) {
                cur = leftRotate(cur);
            } else if (rl > l) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
            }
            if (flag) {
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        int size(Node<K> node) {
            return node == null ? 0 : node.size;
        }

        int all(Node<K> node) {
            return node == null ? 0 : node.all;
        }

        void pushup(Node<K> node) {
            if (node != null) {
                node.size = size(node.l) + size(node.r) + 1;
            }
        }
    }

    public static class Node<K extends Comparable<K>> {
        K k;
        int size;
        int all;
        Node<K> l, r;

        public Node(K k) {
            this.k = k;
            this.size = 1;
            this.all = 1;
        }
    }
}
