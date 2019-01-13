package com.mfvanek.experiments.spring;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ForLecture {

    public static void main(String[] args) {
        final int n = 10;
        final int[] array = IntStream.range(0, n).toArray();
        System.out.println("Original array:");
        Arrays.stream(array).forEach(a -> System.out.print(a + " "));

        final String[] resultArray = myStupidAlgorithm(array);
        System.out.println("\n\nResult array:");
        Arrays.stream(resultArray).forEach(a -> System.out.print(a + " "));
    }

    private static String[] myStupidAlgorithm(final int[] input) {
        final String[] output = new String[input.length];
        for (int i = 0; i < input.length; ++i) {
            input[i] *= 10;
        }
        for (int i = 0; i < input.length; ++i) {
            output[i] = String.format("'%d'", input[i]);
        }
        return output;
    }
}
