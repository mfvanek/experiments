package com.mfvanek.experiments.spring;

import java.util.*;

public class JaTest3 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int[] arr = new int[n];
            int current = 0;
            int idx = 0;
            boolean first = true;
            for (int i = 0; i < n; ++i) {
                int item = scanner.nextInt();
                if (first) {
                    current = item;
                    arr[idx] = item;
                    ++idx;
                    first = false;
                } else {
                    if (item != current) {
                        current = item;
                        arr[idx] = item;
                        ++idx;
                    }
                }
            }

            for (int i = 0; i < idx; ++i) {
                System.out.println(arr[i]);
            }
        }
    }
}
