package class17.me;

import class17.Code03_PrintAllSubsquences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Code03_PrintAllSubsquencesImpl extends Code03_PrintAllSubsquences {

    public static void main(String[] args) {
        String s = "1323";
        System.out.println("repeat:");
        System.out.println(new PrintAllStrSubsequence().printArrSubsequence(s));
        System.out.println("no repeat");
        System.out.println(new PrintAllStrSubsequence().printArrSubsequenceNoRepeat(s));
    }

    public static class PrintAllStrSubsequence {

        // 返回字符串全部子序列
        public List<String> printArrSubsequence(String str) {
            List<String> ans = new ArrayList<>();
            if (str != null && str.length() > 0) {
                process(str.toCharArray(), 0, ans, "");
            }
            ans.sort((a, b) -> a.length() == b.length() ? String.CASE_INSENSITIVE_ORDER.compare(a, b) : a.length() - b.length());
            return ans;
        }

        // 当前来到了字符树组index位置，
        // [..., index)已做决策，结果为path
        // [index, ....]需要做决策
        void process(char[] str, int index, List<String> ans, String path) {
            if (index == str.length) {
                ans.add(path);
                return;
            }
            // 不要str[index]
            process(str, index + 1, ans, path);
            // 要str[index]
            process(str, index + 1, ans, path + str[index]);
        }


        // 返回字符串全部子序列（不重复）
        public List<String> printArrSubsequenceNoRepeat(String str) {
            Set<String> ans = new HashSet<>();
            if (str != null && str.length() > 0) {
                process(str.toCharArray(), 0, ans, "");
            }
            List<String> res = new ArrayList<>(ans);
            res.sort((a, b) -> a.length() == b.length() ? String.CASE_INSENSITIVE_ORDER.compare(a, b) : a.length() - b.length());
            return res;
        }

        void process(char[] str, int index, Set<String> ans, String path) {
            if (index == str.length) {
                ans.add(path);
                return;
            }
            process(str, index + 1, ans, path);
            process(str, index + 1, ans, path + str[index]);
        }
    }
}
