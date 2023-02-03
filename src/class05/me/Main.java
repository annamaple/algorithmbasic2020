package class05.me;

import class05.me.Code02_PartitionAndQuickSortImpl.NetherlandsFlag;
import class05.me.Code02_PartitionAndQuickSortImpl.QuickSortByNetherlandsFlag;

import java.util.Arrays;

/**
 * @author lei
 */
public class Main {

    public static void main(String[] args) {
        NetherlandsFlag netherlandsFlag = new NetherlandsFlag();
        int[] arr = {3, 1, 2, 3, 5, 4, 10, 2, 3, -1, -2};
        int num = 3;
        System.out.println(netherlandsFlag.netherlandsSimple(arr, num));
        Arrays.sort(arr);
        int index = arr.length - 1;
        while (index >=0 && arr[index] > num) {
            index--;
        }
        System.out.println(index);

        System.out.println(Arrays.toString(netherlandsFlag.netherlandsStand(arr, num)));
        int[] ans = new int[2];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == num) {
                ans[0] = i;
                break;
            }
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] == num) {
                ans[1] = i;
                break;
            }
        }
        System.out.println(Arrays.toString(ans));

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(netherlandsFlag.netherlandsFlagStand(arr, 0, arr.length - 1)));
        System.out.println(Arrays.toString(arr));

        QuickSortByNetherlandsFlag quickSort = new QuickSortByNetherlandsFlag();
        int times = 500_000;
        int maxSize = 100;
        int maxValue = 100;
        boolean success = true;
        while (times-- >= 0) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            quickSort.sort(arr1);
            quickSort.mergeSort(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("fuck");
                System.out.println("arr1: " + Arrays.toString(arr1));
                System.out.println("arr2: " + Arrays.toString(arr2));
                success = false;
                break;
            }
        }
        if (success) {
            System.out.println("nice");
        }
    }



    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
