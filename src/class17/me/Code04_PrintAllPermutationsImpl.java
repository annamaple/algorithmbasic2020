package class17.me;

import class17.Code04_PrintAllPermutations;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印字符串的全排列
 * <br> 全排列: 原字符串的字符全在，仅仅顺序不一样
 * 重复
 * 非重复
 */
public class Code04_PrintAllPermutationsImpl extends Code04_PrintAllPermutations {


    public static void main(String[] args) {
        String s = "122";
        System.out.println(new PrintAllPermutations().printAllPermutation(s));
        System.out.println(new PrintAllPermutations().printAllPermutationNotRepeat(s));
    }

    public static class PrintAllPermutations {

        // 全排列
        public List<String> printAllPermutation(String s) {
            List<String> ans = new ArrayList<>();
            if (s != null && s.length() > 0) {
                process(s.toCharArray(), 0, ans);
            }
            return ans;
        }

        void process(char[] str, int index, List<String> ans) {
            if (index == str.length) {
                ans.add(new String(str));
                return;
            }
            for (int i = index; i < str.length; i++) {
                swap(str, i, index);
                process(str, index + 1, ans);
                swap(str, i, index);
            }
        }

        // 全排列 去重复
        public List<String> printAllPermutationNotRepeat(String s) {
            List<String> ans = new ArrayList<>();
            if (s != null && s.length() > 0) {
                g(s.toCharArray(), 0, ans);
            }
            return ans;
        }

        void g(char[] str, int index, List<String> ans) {
            if (index == str.length) {
                ans.add(new String(str));
                return;
            }
            boolean[] visited = new boolean[256];
            for (int i = index; i < str.length; i++) {
                // 使用树组的时候时刻注意下标的含义
                if (!visited[str[i]]) {
                    visited[str[i]] = true;
                    swap(str, i, index);
                    g(str, index + 1, ans);
                    swap(str, i, index);
                }
            }
        }

        void swap(char[] arr, int i, int j) {
            char c = arr[i];
            arr[i] = arr[j];
            arr[j] = c;
        }
    }
}
