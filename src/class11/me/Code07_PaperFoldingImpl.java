package class11.me;

import class11.Code07_PaperFolding;

/**
 * 折纸问题
 *
 * @author lei
 */
public class Code07_PaperFoldingImpl extends Code07_PaperFolding {

    public static void main(String[] args) {
        int n = 4;
        printAllFolds(n);
        new PaperFold().printAllFolds(n);
    }
    
    public static class PaperFold {
        
        public void printAllFolds(int n) {
            if (n < 1) return;
            process(n, 1, "凹");
        } 
        
        void process(int n, int times, String fold) {
            if (n < times) {
                return;
            }
            process(n, times + 1, "凹");
            System.out.print(fold + " ");
            process(n, times + 1, "凸");
        }
    }
}
