package class28.me;

import class28.Code01_Manacher;

public class Code01_ManacherImpl extends Code01_Manacher {

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            int a1;
            String a2;
//            if ((a1 = manacher(str)) != (a2 = new Manacher().maxReverse(str)).length()) {
            if ((a1 = manacher(str)) != (a2 = new MaxReverseString().manacher(str)).length()) {
                System.out.println(str);
                System.out.println(a1);
                System.out.println(a2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish");
    }


    public static class Manacher {
        public String maxReverse(String s) {
            if (s == null || s.length() == 0) {
                return "";
            }
            char[] str = manachar(s);
            // 存放每个字符的回文半径
            int[] rArr = new int[str.length];
            // 最长回文中心点
            int mCenter = 0;
            // c最右回文字符串的中心点， r最右回文边界 + 1
            int c = -1, r = -1;
            for (int i = 0; i < str.length; i++) {
                // 数组元素下标i关于下标c的对称点下标为(2 * c - i)
                rArr[i] = r > i ? Math.min(r - i, rArr[2 * c - i]) : 1;
                while (i + rArr[i] < str.length && i - rArr[i] >= 0 && str[i + rArr[i]] == str[i - rArr[i]]) {
                    rArr[i]++;
                }
                if (i + rArr[i] > r) {
                    r = i + rArr[i];
                    c = i;
                }
                mCenter = rArr[i] > rArr[mCenter] ? i : mCenter;
            }
            // 处理结果
            char[] ans = new char[rArr[mCenter] - 1];
            int index = 0;
            for (int i = mCenter - rArr[mCenter] + 2; i < mCenter + rArr[mCenter] - 1; i += 2) {
                ans[index++] = str[i];
            }
            return new String(ans);
        }


        char[] manachar(String s) {
            char[] str = s.toCharArray();
            char[] res = new char[str.length * 2 + 1];
            int index = 0;
            res[index++] = '#';
            for (char c : str) {
                res[index++] = c;
                res[index++] = '#';
            }
            return res;
        }
    }


    public static class MaxReverseString {

        // 求最长回文： 依次扩O(n^2)
        public String maxReverseStr1(String s) {
            if (s == null || s.length() == 0) {
                return "";
            }
            // r: 回文半径的最大值 
            // c: r取得最大值时回文字符串中心点的位置
            int c = -1, r = -1;
            char[] str = manaChar(s);
            for (int i = 0; i < str.length; i++) {
                int curR = getMaxReverse(str, i);
                if (curR > r) {
                    r = curR;
                    c = i;
                }
            }
            char[] ans = new char[r - 1];
            int index = 0;
            for (int i = c - r + 2; i <= c + r - 1; i += 2) {
                ans[index++] = str[i];
            }
            return new String(ans);
        }

        public String manacher(String s) {
            if (s == null || s.length() == 0) return "";
            char[] str = manaChar(s);
            // 回文半径
            int[] rArr = new int[str.length];
            // 最长回文中心点
            int maxC = 0;
            // c能扩到的最右回文中心点
            // 能扩到的最右回文 + 1
            int c = -1, r = -1;
            for (int i = 0; i < str.length; i++) {
                rArr[i] = i < r ? Math.min(r - i, rArr[2 * c - i]) : 1;
                while (i + rArr[i] < str.length && i - rArr[i] >= 0 && str[i + rArr[i]] == str[i - rArr[i]]) {
                    rArr[i]++;
                }
                if (i + rArr[i] > r) {
                    r = rArr[i];
                    c = i;
                }
                maxC = rArr[i] > rArr[maxC] ? i : maxC;
            }
            char[] ans = new char[rArr[maxC] - 1];
            int index = 0;
            for (int i = maxC - rArr[maxC] + 2; i < maxC + rArr[maxC] - 1; i += 2) {
                ans[index++] = str[i];
            }
            return new String(ans);
        }

        // 返回指定位置index处最大回文半径
        int getMaxReverse(char[] str, int index) {
            int N = str.length;
            int r = 1;
            while (index + r < N && index - r >= 0 && str[index + r] == str[index - r]) {
                r++;
            }
            return r;
        }

        char[] manaChar(String s) {
            char[] str = s.toCharArray();
            char[] res = new char[s.length() * 2 + 1];
            res[0] = '#';
            int i = 1;
            for (char c : str) {
                res[i++] = c;
                res[i++] = '#';
            }
            return res;
        }
    }
}
