package class15.me;

import class15.Code02_NumberOfIslands;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author lei
 */
public class Code02_NumberOfIslandsImpl extends Code02_NumberOfIslands {

    // 为了测试
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + new NumberOfIslands().numIslands1(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行结果: " + new NumberOfIslands().numIslands2(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + new NumberOfIslands().numIslands3(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

        System.out.println();

        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

    }

    public static class NumberOfIslands {

        // 感染法
        public int numIslands1(char[][] board) {
            if (board == null || board.length == 0) return 0;
            int ans = 0;
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == '1') {
                        infect(board, i, j);
                        ans++;
                    }
                }
            }
            return ans;
        }

        // unionFind
        // 如何区分不同位置的1，使用内存地址
        public int numIslands2(char[][] board) {
            if (board == null || board.length == 0) return 0;
            Object[][] boards = new Object[board.length][board[0].length];
            List<Object> list = new LinkedList<>();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == '1') {
                        boards[i][j] = new Object();
                        list.add(boards[i][j]);
                    }
                }
            }
            UnionFindM unionFind = new UnionFindM(list);
            for (int i = 1; i < boards[0].length; i++) {
                if (boards[i][0] != null && boards[i - 1][0] != null) {
                    unionFind.union(boards[i][0], boards[i - 1][0]);
                }
            }
            for (int j = 1; j < boards.length; j++) {
                if (boards[0][j] != null && boards[0][j - 1] != null) {
                    unionFind.union(boards[0][j], boards[0][j - 1]);
                }
            }
            for (int i = 1; i < board.length; i++) {
                for (int j = 1; j < board[i].length; j++) {
                    if (boards[i][j] != null) {
                        if (boards[i - 1][j] != null) {
                            unionFind.union(boards[i][j], boards[i - 1][j]);
                        }
                        if (boards[i][j - 1] != null) {
                            unionFind.union(boards[i][j], boards[i][j - 1]);
                        }
                    }
                }
            }
            return unionFind.sets;
        }


        // 并查集数组实现
        public int numIslands3(char[][] board) {
            if (board == null || board.length == 0) return 0;
            UnionFindN uf = new UnionFindN(board);
            // 第一层
            for (int i = 1; i < board.length; i++) {
                if (board[i][0] == '1' && board[i - 1][0] == '1') {
                    uf.union(i, 0, i - 1, 0);
                }
            }
            // 第一列
            for (int j = 1; j < board[0].length; j++) {
                if (board[0][j] == '1' && board[0][j - 1] == '1') {
                    uf.union(0, j, 0, j - 1);
                }
            }
            // other
            for (int i = 1; i < board.length; i++) {
                for (int j = 1; j < board[i].length; j++) {
                    if (board[i][j] == '1') {
                        if (board[i - 1][j] == '1') {
                            uf.union(i, j, i - 1, j);
                        }
                        if (board[i][j - 1] == '1') {
                            uf.union(i, j, i, j - 1);
                        }
                    }
                }
            }
            return uf.sets;
        }


        public void infect(char[][] board, int i, int j) {
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != '1') {
                return;
            }
            board[i][j] = '#';
            infect(board, i + 1, j);
            infect(board, i - 1, j);
            infect(board, i, j - 1);
            infect(board, i, j + 1);
        }


        // 1 1 0
        // 0 0 1
        // 1 1 1
        // 使用Map结果UnionFind
        // 技巧，如何区分不同的1
        public static class UnionFindM {
            Map<Object, Object> parentMap;
            Map<Object, Integer> sizeMap;
            int sets;

            public UnionFindM(List<Object> list) {
                this.sets = list.size();
                this.parentMap = new HashMap<>(list.size());
                this.sizeMap = new HashMap<>(list.size());
                for (Object o : list) {
                    parentMap.put(o, o);
                    sizeMap.put(o, 1);
                }
            }

            public Object findHead(Object cur) {
                Stack<Object> path = new Stack<>();
                while (parentMap.get(cur) != cur) {
                    path.push(cur);
                    cur = parentMap.get(cur);
                }
                while (!path.isEmpty()) {
                    parentMap.put(path.pop(), cur);
                }
                return cur;
            }

            public void union(Object a, Object b) {
                Object headA = findHead(a);
                Object headB = findHead(b);
                if (headA != headB) {
                    Object more = sizeMap.get(headA) >= sizeMap.get(headB) ? headA : headB;
                    Object less = more == headA ? headB : headA;
                    parentMap.put(less, more);
                    sizeMap.put(more, sizeMap.get(more) + sizeMap.get(less));
                    sizeMap.remove(less);
                    sets--;
                }
            }
        }

        // 并查集 数组
        public static class UnionFindN {
            int[] parent;
            int[] size;
            int col;
            int[] help;
            int sets;

            public UnionFindN(char[][] board) {
                this.col = board[0].length;
                int N = board.length * col;
                this.parent = new int[N];
                this.size = new int[N];
                this.help = new int[N];
                this.sets = 0;
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < col; j++) {
                        if (board[i][j] == '1') {
                            int index = index(i, j);
                            parent[index] = index;
                            size[index] = 1;
                            sets++;
                        }
                    }
                }
            }

            public int findHead(int index) {
                int p = 0;
                while (index != parent[index]) {
                    help[p++] = index;
                    index = parent[index];
                }
                while (--p >= 0) {
                    parent[help[p]] = index;
                }
                return index;
            }

            public void union(int i1, int j1, int i2, int j2) {
                union(index(i1, j1), index(i2, j2));
            }

            public void union(int a, int b) {
                int headA = findHead(a);
                int headB = findHead(b);
                if (headA != headB) {
                    int sizeA = size[headA];
                    int sizeB = size[headB];
                    int more = sizeA >= sizeB ? headA : headB;
                    int less = more == headA ? headB : headA;
                    parent[less] = more;
                    size[more] += size[less];
                    size[less] = 0;
                    sets--;
                }
            }

            int index(int i, int j) {
                return i * col + j;
            }
        }
    }
}
