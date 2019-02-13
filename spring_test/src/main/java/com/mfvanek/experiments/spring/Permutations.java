package com.mfvanek.experiments.spring;

import java.util.ArrayList;
import java.util.List;

public class Permutations {

    /*
a -> a
ab -> ab ba
abc -> abc
    */
    public static void main(String[] args) {

        String str = "abc";
        // permute(str);
        permute2(str);
    }

    private static void permute2(String str) {
        List<String> res = permute3(str);
        res.forEach(System.out::println);
    }

    private static List<String> permute3(String str) {
        final List<String> res = new ArrayList<>();

        if (str == null) {
            return res;
        }

        if (str.length() == 0) {
            res.add("");
            return res;
        }

        char first = str.charAt(0);
        List<String> words = permute3(str.substring(1));
        for (String word : words) {
            for (int i = 0; i <= word.length(); ++i) {
                String perm = insertCharAt(word, first, i);
                res.add(perm);
            }
        }

        return res;
    }

    private static String insertCharAt(String word, char first, int i) {
        if (i == 0) {
            return first + word;
        }

        if (i == word.length()) {
            return word + first;
        }

        return word.substring(0, i) + first + word.substring(i);
    }

    private static void permute(String str) {
        char[] arr = str.toCharArray();
        permute(arr, 0, str.length());
    }

    private static void permute(char[] arr, int start, int end) {
        if (start == end) {
            System.out.println(arr);
        }

        for (int i = start; i < end; ++i) {
            swap(arr, start, i);
            System.out.println(" - " + start + " -- " + String.valueOf(arr));
            permute(arr, start + 1, end);
            swap(arr, start, i);
        }
    }

    private static void swap(char[] arr, int s, int i) {
        char tmp = arr[s];
        arr[s] = arr[i];
        arr[i] = tmp;
    }
}
