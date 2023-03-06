package class20.me;

import class20.Code03_Coffee;


public class Code03_CoffeeImpl extends Code03_Coffee {

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
        
        public int drink(int[] arr, int n, int a, int b) {
            return -1;
        }
    }
}
