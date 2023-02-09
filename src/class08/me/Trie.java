package class08.me;


/**
 * 前缀树（trie，发音为 "try"）是一个树状的数据结构，用于高效地存储和检索一系列字符串的前缀。前缀树有许多应用，如自动补全和拼写检查。
 * <p>
 * 实现前缀树 Trie 类：
 * <p>
 * Trie()初始化前缀树对象。
 * void insert(String word)将字符串word插入前缀树中。
 * int countWordsEqualTo(String word)返回前缀树中字符串word的实例个数。
 * int countWordsStartingWith(String prefix)返回前缀树中以prefix为前缀的字符串个数。
 * void erase(String word)从前缀树中移除字符串word 。
 */
public class Trie {

    public Trie() {
        root = new Node();
    }

    private Node root;

    public void insert(String word) {
        if (word == null) {
            return;
        }
        Node cur = root;
        char[] chars = word.toCharArray();
        cur.pass++;
        int path;
        for (char c : chars) {
            path = c - 'a';
            if (cur.nexts[path] == null) {
                cur.nexts[path] = new Node();
            }
            cur = cur.nexts[path];
            cur.pass++;
        }
        cur.end++;
    }

    public int countWordsEqualTo(String word) {
        Node node = getPrefixNode(word);
        return node == null ? 0 : node.end;
    }

    public int countWordsStartingWith(String prefix) {
        Node node;
        return (node = getPrefixNode(prefix)) == null ? 0 : node.pass;
    }

    private Node getPrefixNode(String word) {
        if (word == null) {
            return null;
        }
        int path;
        Node cur = root;
        for (char c : word.toCharArray()) {
            path = c - 'a';
            if (cur.nexts[path] == null || cur.nexts[path].pass == 0) {
                return null;
            }
            cur = cur.nexts[path];
        }
        return cur;
    }

    public void erase(String word) {
        if (word == null) {
            return;
        }
        if (countWordsEqualTo(word) == 0) {
            return;
        }
        int path;
        Node cur = root;
        cur.pass--;
        for (char c : word.toCharArray()) {
            path = c - 'a';
            cur = cur.nexts[path];
            cur.pass--;
        }
        cur.end--;
    }


    private static class Node {
        int pass;
        int end;
        Node[] nexts = new Node[26];
    }

}