package com.mfvanek.experiments.spring;

import java.io.*;

public class JaTest3 {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int n = Integer.parseInt(reader.readLine());
            int current = 0;
            boolean first = true;
            int item = 0;
            for (int i = 0; i < n; ++i) {
                item = Integer.parseInt(reader.readLine());
                if (first) {
                    current = item;
                    System.out.println(item);
                    first = false;
                } else {
                    if (item != current) {
                        current = item;
                        System.out.println(item);
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
