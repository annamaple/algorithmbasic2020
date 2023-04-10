package class40.me;

import class40.Code03_LongestLessSumSubArrayLength;

import java.util.HashMap;
import java.util.Map;

public class Code03_LongestLessSumSubArrayLengthImpl extends Code03_LongestLessSumSubArrayLength {

    public static void main(String[] args) {
        int[] arr = {1, 1, 1};
        int k = 2;
        System.out.println(new Code03_LongestLessSumSubArrayLengthImpl().subarraySum(arr, k));
    }
    
    // TODO Lei: 数组三连第三问

    public int subarraySum(int[] arr, int k) {
        if (arr == null) return 0;
        // K 前缀和, V: 前缀和个数
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int ans = 0, sum = 0;
        for (int j : arr) {
            sum += j;
            ans += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return ans;
    }
}
