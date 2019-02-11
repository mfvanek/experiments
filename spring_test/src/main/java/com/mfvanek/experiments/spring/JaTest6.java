package com.mfvanek.experiments.spring;

import java.util.*;

public class JaTest6 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int k = Integer.parseInt(scanner.nextLine(), 10);
            if (k > 0) {
                int[][] arrays = new int[k][];
                //int total = 0;
                for (int i = 0; i < k; ++i) {
                    String[] tmp = scanner.nextLine().split(" ");
                    if (tmp.length > 0) {
                        int n = Integer.parseInt(tmp[0], 10);
                        //total += n;
                        arrays[i] = new int[n];
                        for (int j = 1; j < tmp.length && j < n + 1; ++j) {
                            arrays[i][j - 1] = Integer.parseInt(tmp[j], 10);
                        }
                    }
                }

                int[] result = arrays[0];
                for (int i = 1; i < k; ++i) {
                    result = merge(result, arrays[i]);
                }

                for (int i : result) {
                    System.out.print(i + " ");
                }
            }
        }
    }

    private static int[] merge(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];

        int i = 0, j = 0, r = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                res[r++] = a[i++];
            } else {
                res[r++] = b[j++];
            }
        }

        while (i < a.length) {
            res[r++] = a[i++];
        }

        while (j < b.length) {
            res[r++] = b[j++];
        }

        return res;
    }
}
