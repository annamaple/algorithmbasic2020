package class08.me;

import java.util.Arrays;

/**
 * @author lei
 */
public class Main {

    public static void main(String[] args) {
        int[] arr = new int[]{4, 2, 1, 5, 7};
        Arrays.stream(arr).sorted().forEach(System.out::println);
    }
}
