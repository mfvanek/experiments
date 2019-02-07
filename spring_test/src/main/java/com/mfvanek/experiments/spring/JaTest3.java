package com.mfvanek.experiments.spring;

import java.io.*;

public class JaTest3 {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int n = Integer.parseInt(reader.readLine(), 10);
            //int[] arr = new int[n];
            int current = 0;
            //int idx = 0;
            boolean first = true;
            for (int i = 0; i < n; ++i) {
                int item = Integer.parseInt(reader.readLine(), 10);
                if (first) {
                    current = item;
                    //arr[idx] = item;
                    System.out.println(item);
                    //++idx;
                    first = false;
                } else {
                    if (item != current) {
                        current = item;
                        //arr[idx] = item;
                        System.out.println(item);
                        //++idx;
                    }
                }
            }
//            for (int i = 0; i < idx; ++i) {
//                System.out.println(arr[i]);
//            }
        } catch (IOException e) {
        }
    }
}
