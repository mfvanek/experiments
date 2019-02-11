package com.mfvanek.experiments.spring;

import java.util.*;

public class JaTest6 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int k = Integer.parseInt(scanner.nextLine(), 10);
            int[][] arrays = new int[k][];
            for (int i = 0; i < k; ++i) {
                String[] tmp = scanner.nextLine().split(" ");
                if (tmp.length > 0) {
                    int n = Integer.parseInt(tmp[0], 10);
                    arrays[i] = new int[n];
                    for (int j = 1; j < tmp.length && j < n + 1; ++j) {
                        arrays[i][j - 1] = Integer.parseInt(tmp[j], 10);
                    }
                }
            }

//            for (int i = 0; i < k; ++i) {
//                System.out.println(Arrays.toString(arrays[i]));
//            }
        }
    }
}
