package class20.me;

import class20.Code03_Coffee;

import java.util.PriorityQueue;


public class Code03_CoffeeImpl extends Code03_Coffee {

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            // int ans2 = minTime1(arr, n, a, b);
            int ans2 = new Coffee().drink(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops");
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        System.out.println("测试结束");

    }

    /*  
        数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
        现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
        认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
        每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
        洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
        四个参数：arr, n, a, b
        假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
     */

    public static class Coffee {

        public static class CoffeeMachine {
            int startTime;
            int unitTime;

            public CoffeeMachine(int startTime, int unitTime) {
                this.startTime = startTime;
                this.unitTime = unitTime;
            }
        }

        public int drink(int[] arr, int n, int a, int b) {
            // 记录员工喝完咖啡的时间即咖啡杯能够洗或挥发开始时间
            int[] cup = drinkOverMinTime(arr, n);
            // 保证咖啡杯都干净的最小时间
            // return minCleanTime(cup, a, b);
            return minCleanTimeDp(cup, a, b);
        }

        // cup: 咖啡杯可以进行洗或挥发的开始时间
        // a: 洗完一个杯子时间
        // b: 杯子挥发的时间
        int minCleanTime(int[] cup, int a, int b) {
//            return process(cup, a, b, 0, 0, 0);
            return p(cup, a, b, 0, 0);
        }
        
        int minCleanTimeDp(int[] cup, int a, int b) {
            // 清洗机可以开始清洗的时间
            int maxClean = 0;
            for (int c : cup) {
                maxClean = Math.max(maxClean, c) + a;
            }
            int[][] dp = new int[cup.length + 1][maxClean + 1];
            // int[cup.length + 1][...] = 0;
            for (int index = cup.length - 1; index >= 0 ; index--) {
                for (int washTime = 0; washTime <= maxClean; washTime++) {
                    // 洗
                    int selfWash = Math.max(cup[index], washTime) + a;
                    if (selfWash > maxClean) {
                        // 当selfWash大于 maxClean表示此事的策略比全部用机器洗用时更长，可以忽略
                        break;
                    }
                    int restClean = dp[index + 1][selfWash];
                    int p1 = Math.max(selfWash, restClean);
                    // 挥发
                    int selfVolatilize = cup[index] + b;
                    restClean = dp[index + 1][washTime];
                    int p2 = Math.max(selfVolatilize, restClean);
                    dp[index][washTime] = Math.min(p1, p2);
                }
            }
            return dp[0][0];
        }

        // 由左：优秀递归含义 ：当前做完决策，做完的值不传而是把当前和剩下区分开，baseCase为0为没有杯子时的wash时间
        // 清除washTime含义：清洗机可以开始清洗的时间
        int p(int[] cup, int a, int b, int index, int washTime) {
            if (index == cup.length) {
                return 0;
            }
            // 洗
            int selfWash = Math.max(cup[index], washTime) + a;
            int restClean = p(cup, a, b, index + 1, selfWash);
            int p1 = Math.max(selfWash, restClean);
            // 挥发 : 自己挥发时间，剩下的进行清洁时间。最大值为总体结束时间    
            int selfVolatilize = cup[index] + b;
            restClean = p(cup, a, b, index + 1, washTime);
            int p2 = Math.max(selfVolatilize, restClean);
            return Math.min(p1, p2);
        }
        
        // 由我复杂的递归含义（反面教材）：当前做完决定，把当前做完的值传递给后面，baseCase为答案
        // 当前来到了index号杯子
        // 从左到右模型
        // a: 洗完一个杯子时间
        // b: 杯子挥发时间
        // washTime: 清洗机洗完时间
        // volatilizeTime: 杯子挥发完时间
        int process(int[] cup, int a, int b, int index, int washTime, int volatilizeTime) {
            if (index == cup.length) {
                return Math.max(washTime, volatilizeTime);
            }
            // 洗
            int p1 = process(cup, a, b, index + 1, Math.max(washTime, cup[index]) + a, volatilizeTime);
            // 挥发
            int p2 = process(cup, a, b, index + 1, washTime, Math.max(cup[index] + b, volatilizeTime));
            return Math.min(p1, p2);
        }

        // 所有人喝完咖啡的最小时间
        int[] drinkOverMinTime(int[] arr, int n) {
            int[] cup = new int[n];
            PriorityQueue<CoffeeMachine> minHeap = new PriorityQueue<>(
                    (ma, mb) -> (ma.startTime + ma.unitTime) - (mb.startTime + mb.unitTime));
            for (int num : arr) {
                minHeap.offer(new CoffeeMachine(0, num));
            }
            for (int i = 0; i < n; i++) {
                CoffeeMachine machine = minHeap.poll();
                cup[i] = machine.startTime + machine.unitTime;
                machine.startTime = cup[i];
                minHeap.offer(machine);
            }
            return cup;
        }
    }
}
