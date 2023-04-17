package class39.me;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

// 牛牛的背包问题 + 输入输出
// https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281
public class Code02_SnacksWaysImpl2 {

    // 背包容量为w
    // 一共有n袋零食, 第i袋零食体积为v[i]
    // 总体积不超过背包容量的情况下，
    // 一共有多少种零食放法？(总体积为0也算一种放法)。
    public static void main(String[] args) throws IOException {
        int[] rr = new int[]{1, 2, 4};
        System.out.println(way(rr, 10));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int N = (int) in.nval;
            in.nextToken();
            int w = (int) in.nval;
            int[] arr = new int[N];
            for (int i = 0; i < N; i++) {
                in.nextToken();
                arr[i] = (int) in.nval;
            }
            out.println(way(arr, w));
            out.flush();
        }
    }


    public static int way(int[] arr, long w) {
        if (arr == null || arr.length == 0) return 0;
        int N = arr.length;
        if (N == 1) return arr[0] <= w ? 2 : 1;
        int mid = (N - 1) >> 1;
        int ans = 0;
        TreeMap<Long, Long> lMap = new TreeMap<>();
        ans += process(arr, 0, mid, 0, w, false, lMap);
        TreeMap<Long, Long> rMap = new TreeMap<>();
        ans += process(arr, mid + 1, N - 1, 0, w, false, rMap);
        long pre = 0;
        for (Map.Entry<Long, Long> entry : rMap.entrySet()) {
            pre += entry.getValue();
            entry.setValue(pre);
        }
        for (Map.Entry<Long, Long> entry : lMap.entrySet()) {
            Long floorKey = rMap.floorKey(w - entry.getKey());
            if (floorKey != null) {
                ans += entry.getValue() * rMap.get(floorKey);
            }
        }
        return ans + 1;
    }

    public static int process(int[] arr, int i, int end, long sum, long w, boolean choose, TreeMap<Long, Long> map) {
        if (i == end + 1) {
            if (choose) {
                map.put(sum, map.getOrDefault(sum, 0L) + 1);
            }
            return choose ? 1 : 0;
        }
        int p1 = process(arr, i + 1, end, sum, w, choose, map);
        int p2 = sum + arr[i] <= w ? process(arr, i + 1, end, sum + arr[i], w, true, map) : 0;
        return p1 + p2;
    }
}
