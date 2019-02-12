package com.mfvanek.experiments.spring;

import java.io.*;
import java.util.*;

// Try to use https://en.wikipedia.org/wiki/External_sorting
public class JaTest6 {

    private static int counter = 0;

    public static void main(String[] args) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            counter = 0;
            int k = Integer.parseInt(reader.readLine(), 10);
            if (k > 0) {
                String[] files = new String[k];
                for (int i = 0; i < k; ++i) {
                    String[] tmp = reader.readLine().split(" ");
                    if (tmp.length > 1) {
                        int n = Integer.parseInt(tmp[0], 10);
                        if (n > 0) {
                            final String fileName = makeFileName();
                            files[i] = fileName;
                            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                                for (String value : tmp) {
                                    writer.println(value);
                                }
                            }
                        }
                    }
                }

//                for (String i : files) {
//                    System.out.println(i);
//                }

                System.out.println(merge(files[0], files[1]));
            }
        }
    }

    private static String makeFileName() {
        return String.format("output_%d.txt", counter++);
    }

    private static String merge(String firstFile, String secondFile) throws IOException {
        final String fileName = makeFileName();
        try (BufferedReader first = new BufferedReader(new FileReader(firstFile));
             BufferedReader second = new BufferedReader(new FileReader(secondFile));
             PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            int n1 = Integer.parseInt(first.readLine(), 10);
            int n2 = Integer.parseInt(second.readLine(), 10);
            int n = n1 + n2;
            writer.println(n);

            int i = 0, j = 0;
            int a = Integer.parseInt(first.readLine(), 10);
            int b = Integer.parseInt(second.readLine(), 10);
            while (i < n1 && j < n2) {
                if (a < b) {
                    writer.println(a);
                    ++i;
                    if (i < n1) {
                        a = Integer.parseInt(first.readLine(), 10);
                    }
                } else {
                    writer.println(b);
                    ++j;
                    if (j < n2) {
                        b = Integer.parseInt(second.readLine(), 10);
                    }
                }
            }

            while (i < n1) {
                writer.println(a);
                ++i;
                if (i < n1) {
                    a = Integer.parseInt(first.readLine(), 10);
                }
            }

            while (j < n2) {
                writer.println(b);
                ++j;
                if (j < n2) {
                    b = Integer.parseInt(second.readLine(), 10);
                }
            }
        }
        return fileName;
    }
}
