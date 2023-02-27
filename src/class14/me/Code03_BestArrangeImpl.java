package class14.me;

import class14.Code03_BestArrange;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 会议室预定问题
 *
 * @author lei
 */
public class Code03_BestArrangeImpl extends Code03_BestArrange {

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
//            if (bestArrange1(programs) != bestArrange2(programs)) {
//                System.out.println("Oops!");
//            }
            if (bestArrange1(programs) != new BestArrange().bestArrange1(programs)) {
                System.out.println(bestArrange1(programs));
                System.out.println(new BestArrange().bestArrange2(programs));
                System.out.println("Oops!");
                return;
            }
            
        }
        System.out.println("finish!");
    }

    public static class BestArrange {

        // force recursion
        public int bestArrange1(Program[] programs) {
            if (programs == null || programs.length == 0) return 0;
            return process(programs, 0, 0);
        }
        
        // greedy
        public int bestArrange2(Program[] programs) {
            // 最早结束的
            int timeLine = 0;
            int done = 0;
            PriorityQueue<Program> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a.end));
            for (Program p : programs) {
                heap.offer(p);
            }
            Program cur;
            while (!heap.isEmpty()) {
                cur = heap.poll();
                if (cur.end >= timeLine) {
                    timeLine = cur.end;
                    done++;
                }
            }
            return done;
        }
        


        /**
         * 返回可预订的会议数量的最大值
         *
         * @param programs 剩下的项目
         * @param done     已定的项目
         * @param timeLine 当前时间节点
         * @return 预订的会议数量的最大值
         */
        int process(Program[] programs, int done, int timeLine) {
            if (programs.length == 0) {
                return done;
            }
            int max = done;
            for (Program program : programs) {
                if (program.start >= timeLine) {
                    max = Math.max(max, process(otherProgram(programs, program), done + 1, program.end));
                }
            }
            return max;
        }

        private Program[] otherProgram(Program[] programs, Program program) {
            Program[] res = new Program[programs.length - 1];
            int index = 0;
            for (Program p : programs) {
                if (p != program) {
                    res[index++] = p;
                }
            }
            return res;
        }


    }
}
