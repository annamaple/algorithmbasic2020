package class19.me;

import class19.Code03_StickersToSpellWord;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Code03_StickersToSpellWordImpl extends Code03_StickersToSpellWord {

    public static void main(String[] args) {
//        String[] stickers = {"with","example","science"};
        String[] stickers = {"notice","possible"};
        String target = "c";
        // ans = 3;
        System.out.println(minStickers2(stickers, target));
        System.out.println(new StickersTOSpellWord().minStickers(stickers, target));
    }

    public static class StickersTOSpellWord {

        public int minStickers(String[] stickers, String target) {
            if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
                return 0;
            }
            int[][] sCounts = new int[stickers.length][26];
            for (int i = 0; i < stickers.length; i++) {
                String s = stickers[i];
                int[] counts = sCounts[i];
                for (char c : s.toCharArray()) {
                    counts[c - 'a']++;
                }
            }
            return process(sCounts, target, new HashMap<>());
        }

        // 暴力递归
        // 从sCounts表示字符串数组转字符数组计数数组
        // target当前需要拼凑的目标
        // 返回最小的贴纸数量
        int process(int[][] sCounts, String target, Map<String, Integer> map) {
            // 傻缓存
            if (map.containsKey(target)) {
                return map.get(target);
            }
            if (Objects.equals(target, "")) {
                // 不需要贴纸
                map.put(target, 0);
                return 0;
            }
            int[] tCounts = stringToCharCounts(target);
            int min = Integer.MAX_VALUE;
            for (int[] counts : sCounts) {
                if (counts[target.toCharArray()[0] - 'a'] > 0) {
                    // 当前的贴纸存在目标字符串第一个字符
                    String rest = minus(counts, tCounts);
                    min = Math.min(min, process(sCounts, rest, map));
                }
            }
            int ans =  min == Integer.MAX_VALUE ? min : min + 1;
            map.put(target, ans);
            return ans;
        }

        // counts 字符计数器
        // 目标字符计数器
        public String minus(int[] counts, int[] tCounts) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                int num = tCounts[i] - counts[i];
                sb.append(String.valueOf((char) ('a' + i)).repeat(Math.max(0, num)));
            }
            return sb.toString();
        }

        public String charCountsToString(int[] counts) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < counts.length; i++) {
                if (counts[i] > 0) {
                    char c = (char) ('a' + i);
                    sb.append(String.valueOf(c).repeat(Math.max(0, counts[i])));
                }
            }
            return sb.toString();
        }

        public int[] stringToCharCounts(String s) {
            int[] counts = new int[26];
            for (char c : s.toCharArray()) {
                counts[c - 'a']++;
            }
            return counts;
        }
    }
}
