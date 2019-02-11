package com.mfvanek.experiments.spring;

import java.util.*;

// Try to use https://en.wikipedia.org/wiki/External_sorting
public class JaTest6 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int k = Integer.parseInt(scanner.nextLine(), 10);
            if (k > 0) {
                int[][] arrays = new int[k][];
                int total = 0;
                for (int i = 0; i < k; ++i) {
                    String[] tmp = scanner.nextLine().split(" ");
                    if (tmp.length > 0) {
                        int n = Integer.parseInt(tmp[0], 10);
                        total += n;
                        arrays[i] = new int[n];
                        for (int j = 1; j < tmp.length && j < n + 1; ++j) {
                            arrays[i][j - 1] = Integer.parseInt(tmp[j], 10);
                        }
                    }
                }

                int r = 0;
                int[] result = new int[total];
                for (int i = 0; i < k; ++i) {
                    for (int j = 0; j < arrays[i].length; ++j) {
                        result[r++] = arrays[i][j];
                    }
                }

                Arrays.sort(result);
                for (int i : result) {
                    System.out.print(i + " ");
                }
            }
        }
    }
}
