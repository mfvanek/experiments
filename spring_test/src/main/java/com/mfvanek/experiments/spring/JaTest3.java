package com.mfvanek.experiments.spring;

import java.io.*;

public class JaTest3 {

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int n = Integer.parseInt(reader.readLine());
            // TODO dynamic array is needed here
            int[] array = new int[n];
            int current = 0;
            int idx = 0;
            int item;
            boolean first = true;
            for (int i = 0; i < n; ++i) {
                item = Integer.parseInt(reader.readLine());
                if (first) {
                    current = item;
                    array[idx] = item;
                    ++idx;
                    first = false;
                } else {
                    if (current != item) {
                        current = item;
                        array[idx] = item;
                        ++idx;
                    }
                }
            }
            for (int i = 0; i < idx; ++i) {
                System.out.println(array[i]);
            }
        }
    }
}
