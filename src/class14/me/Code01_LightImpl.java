package class14.me;

import class14.Code01_Light;

import java.util.HashSet;
import java.util.Set;

public class Code01_LightImpl extends Code01_Light {

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        int a = new Light().lightNum3("x..x....xx.x.xx");
        System.out.println(a);

        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
//            int ans1 = minLight1(test);
//            int ans2 = minLight2(test);
            int ans1 = new Light().lightNum1(test);
            int ans2 = new Light().lightNum2(test);
            int ans3 = new Light().lightNum4(test);
//            int ans3 = minLight3(test);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }

    public static class Light {

        // 暴力递归
        public int lightNum1(String road) {
            if (road == null || road.length() == 0) return 0;
            return process(road.toCharArray(), 0, new HashSet<>());
        }

        int process(char[] str, int index, Set<Integer> lightLocation) {
            if (str.length == index) {
                // 判断当前方案是否满足要求
                for (int i = 0; i < str.length; i++) {
                    if (str[i] == '.') {
                        // 表示居民区没有被照亮，不满足要求
                        if (!lightLocation.contains(i - 1) && !lightLocation.contains(i) && !lightLocation.contains(i + 1)) {
                            return Integer.MAX_VALUE;
                        }
                    }
                }
                return lightLocation.size();
            }
            // 当前位置可以装灯可以不装
            int no = process(str, index + 1, lightLocation);
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                // 点才能放灯
                lightLocation.add(index);
                yes = process(str, index + 1, lightLocation);
            }
            lightLocation.remove(index);
            return Math.min(no, yes);
        }

        int lightNum2(String road) {
            if (road == null || road.length() == 0) return 0;
            int i = 0;
            int light = 0;
            char[] str = road.toCharArray();
            while (i < road.length()) {
                if(str[i] != '.') {
                    i++;
                } else {
                    light++;
                    if (i + 1 == str.length) {
                        break;
                    } else {
                        if (str[i + 1] != '.') {
                            // .X
                            i += 2;
                        } else {
                            // ..
                            i += 3;
                        }
                    }
                }
            }
            return light;
        }

        // 更简洁的解法
        // 两个X之间，数一下.的数量，然后除以3，向上取整
        // 把灯数累加
        int lightNum3(String road) {
            if (road == null || road.length() == 0) return 0;
            char[] str = road.toCharArray();
            int index = 0;
            int dianNum = 0;
            int light = 0;
            while (index < str.length) {
                if (str[index] == '.') {
                    while (index < str.length && str[index] == '.') {
                        index++;
                        dianNum++;
                    }
                    index++;
                    light += dianNum / 3;
                    light = dianNum % 3 == 0 ? light : light + 1;
                    dianNum = 0;
                } else {
                    index++;
                }
            }
            return light;
        }

        // 同3，代码更简洁
        int lightNum4(String road) {
            if (road == null || road.length() == 0) return 0;
            int dianNum = 0;
            int light = 0;
            char[] str = road.toCharArray();
            for (char c: str) {
                if (c == '.') {
                    dianNum++;
                } else {
                    light += (dianNum + 2) / 3;
                    dianNum = 0;
                }
            }
            light += (dianNum + 2) / 3;
            return light;
        }
    }
}
