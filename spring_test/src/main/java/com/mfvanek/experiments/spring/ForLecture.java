package com.mfvanek.experiments.spring;

import java.util.Arrays;

public class ForLecture {

    public static void main(String[] args) {
        //final int n = 10;
        // final int[] array = IntStream.range(0, n).toArray();
        // final int[] array = {11, 2, 0, -2, 5, 1, -434, 0, 12, 4};
        final int[] array = {4, 3, 2, 1};
        System.out.println("Original array:");
        Arrays.stream(array).forEach(a -> System.out.print(a + " "));
        System.out.println("\n\n");

//        final String[] resultArray = myStupidAlgorithm(array);
//        System.out.println("\n\nResult array:");
//        Arrays.stream(resultArray).forEach(a -> System.out.print(a + " "));

        System.out.println("insertionSort");
        int[] sorted = insertionSort(array, true);
        //System.out.println("\n\nSorted array:");
        //Arrays.stream(sorted).forEach(a -> System.out.print(a + " "));
        System.out.println("\n\n");

        System.out.println("bubbleSort");
        int[] sorted1 = bubbleSort(array, true);
        //System.out.println("\n\nSorted array:");
        //Arrays.stream(sorted1).forEach(a -> System.out.print(a + " "));
    }

    private static String[] myStupidAlgorithm(final int[] input) {
        final int[] src = input.clone();
        final String[] output = new String[src.length];
        for (int i = 0; i < src.length; ++i) {
            src[i] *= 10;
        }
        for (int i = 0; i < src.length; ++i) {
            output[i] = String.format("'%d'", src[i]);
        }
        return output;
    }

    private static int[] insertionSort(int[] src, boolean show) {
        int[] dest = src.clone();
        for (int i = 1; i < src.length; ++i) {
            for (int j = 0; j < i; ++j) {
                if (dest[j] > dest[i]) {
                    swap(dest, j, i);
                }
                if (show) {
                    Arrays.stream(dest).forEach(a -> System.out.print(a + " "));
                    System.out.println();
                }
            }
        }
        return dest;
    }

    private static void swap(int[] arr, int idx1, int idx2) {
        int tmp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = tmp;
    }

    private static int[] bubbleSort(int[] src, boolean show) {
        int[] dest = src.clone();
        final int n = dest.length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n - i - 1; ++j) {
                if (dest[j] > dest[j + 1]) {
                    swap(dest, j, j + 1);
                }
                if (show) {
                    Arrays.stream(dest).forEach(a -> System.out.print(a + " "));
                    System.out.println();
                }
            }
        }
        return dest;
    }
}
