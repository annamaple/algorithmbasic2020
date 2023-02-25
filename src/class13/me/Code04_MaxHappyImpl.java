package class13.me;

import class13.Code04_MaxHappy;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工最大快乐值问题
 */
public class Code04_MaxHappyImpl extends Code04_MaxHappy {

    // Employee Node
    /*public static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }

    }*/

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNexts = 7;
        int maxHappy = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = genarateBoss(maxLevel, maxNexts, maxHappy);
//            if (maxHappy1(boss) != maxHappy2(boss)) {
//                System.out.println("Oops!");
//            }
            if (new MaxHappy().maxHappyWithOutInfo(boss) != new MaxHappy().maxHappy(boss)) {
                System.out.println("Fuck");
            }
        }
        System.out.println("finish!");
    }

    public static class MaxHappy {


        public int maxHappyWithOutInfo(Employee boss) {
            if (boss == null) return 0;
            return process(boss, false);
        }

        //  在已知直接上级是来还是不来的情况下，递归的求出当前节点的最大快乐值
        int process(Employee employee, boolean up) {
            if (up) {
                // 直接上级来
                int happy = 0;
                for (Employee e: employee.nexts) {
                    happy += process(e, false);
                }
                return happy;
            } else {
                // 直接上级不来, 本级可以来可以不来
                int happy1 = 0;
                int happy2 = employee.happy;
                for (Employee e: employee.nexts) {
                    happy1 += process(e, false);
                    happy2 += process(e, true);
                }
                return Math.max(happy1, happy2);
            }
        }

        public int maxHappy(Employee boss) {
            if (boss == null) return 0;
            Info ans = process(boss);
            return Math.max(ans.includeMeHappy, ans.excludeMeHappy);
        }

        Info process(Employee e) {
            if (e.nexts == null || e.nexts.isEmpty()) {
                return new Info(e.happy, 0);
            }
            List<Info> infoList = new ArrayList<>();
            for (Employee employee : e.nexts) {
                infoList.add(process(employee));
            }
            int includeMeHappy = e.happy;
            int excludeMeHappy = 0;
            for (Info info : infoList) {
                includeMeHappy += info.excludeMeHappy;
                excludeMeHappy += Math.max(info.includeMeHappy, info.excludeMeHappy);
            }
            return new Info(includeMeHappy, excludeMeHappy);
        }

        public static class Info {
            // 包括自身时的最大值
            int includeMeHappy;
            // 不包括自身的最大值
            int excludeMeHappy;

            public Info(int includeMeHappy, int excludeMeHappy) {
                this.includeMeHappy = includeMeHappy;
                this.excludeMeHappy = excludeMeHappy;
            }
        }
    }

}
