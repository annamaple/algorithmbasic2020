package class37.me;

import class37.Code01_CountofRangeSum;

public class Code01_CountofRangeSumImpl extends Code01_CountofRangeSum {


    // 求数组arr的子数组累加和在range范围（闭区间）的个数range [low, high]

    // 思路与转换：累加和 => 前缀和数组sum + 已i结尾的子数组中，有哪些前缀和在range范围中
    // i > j, 若sum[i] - sum[j] in [low, high], 则arr中[j + 1, j]区间和in[low, high]

    public static void main(String[] args) {
        int len = 6;
        int varible = 50;
        for (int i = 0; i < 10000; i++) {
            int[] test = generateArray(len, varible);
            int lower = (int) (Math.random() * varible) - (int) (Math.random() * varible);
            int upper = lower + (int) (Math.random() * varible);
            int ans1 = countRangeSum1(test, lower, upper);
            int ans2 = new CountOfRangeSumByMergeSort().countOfRangeSum(test, lower, upper);
            if (ans1 != ans2) {
                printArray(test);
                System.out.println(lower);
                System.out.println(upper);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("fuck");
                return;
            }
        }
        System.out.println("nice");

    }

    public static class CountOfRangeSumBySortedMap {

        private static class SBTreeMap<K extends Comparable<K>, V> {

            private SBNode<K, V> rightRotate(SBNode<K, V> cur) {
                SBNode<K, V> l = cur.l;
                cur.l = l.r;
                l.r = cur;
                // TODO Lei: 维护size
                return l;
            }


            private static class SBNode<K extends Comparable<K>, V> {
                K k;
                V v;
                int size;
                SBNode<K, V> l, r;

                public SBNode(K k, V v) {
                    this.k = k;
                    this.v = v;
                    this.size = 1;
                }
            }
        }
    }

    public static class CountOfRangeSumByMergeSort {
        // 利用归并merge过程两边有序，使比较不回退，降低时间复杂度
        // 方法一：归并排序
        public int countOfRangeSum(int[] arr, int low, int high) {
            if (arr == null) return 0;
            int N = arr.length;
            long[] sum = new long[N];
            sum[0] = arr[0];
            for (int i = 1; i < N; i++) {
                sum[i] = sum[i - 1] + arr[i];
            }
            return process(sum, 0, N - 1, low, high);
        }

        private int process(long[] arr, int left, int right, int low, int high) {
            if (left == right) {
                return arr[left] >= low && arr[left] <= high ? 1 : 0;
            }
            int mid = left + ((right - left) >> 1);
            int p1 = process(arr, left, mid, low, high);
            int p2 = process(arr, mid + 1, right, low, high);
            int p3 = merge(arr, left, mid, right, low, high);
            return p1 + p2 + p3;
        }

        private int merge(long[] sum, int left, int mid, int right, int low, int high) {
            int l1 = left, l2 = left, r = mid + 1;
            long min, max;
            // j < i; sum[i] - sum[j] in [low, high]
            // sum[j] in [sum[i] - high, sum[i] - low]
            // sum[j] 递增  [sum[i] - high, sum[i] - low] 递增， 不回退， 窗口
            int ans = 0;
            while (r <= right) {
                min = sum[r] - high;
                max = sum[r] - low;
                while (l1 <= mid && sum[l1] < min) {
                    l1++;
                }
                while (l2 <= mid && sum[l2] <= max) {
                    l2++;
                }
                ans += l2 - l1;
                r++;
            }
            // merge
            int p1 = left;
            int p2 = mid + 1;
            long[] help = new long[right - left + 1];
            int i = 0;
            while (p1 <= mid && p2 <= right) {
                help[i++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
            }
            while (p1 <= mid) {
                help[i++] = sum[p1++];
            }
            while (p2 <= right) {
                help[i++] = sum[p2++];
            }
            while (--i >= 0) {
                sum[left + i] = help[i];
            }
            return ans;
        }

    }


}
