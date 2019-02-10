package com.mfvanek.experiments.spring;

import java.util.*;

public class JaTest5 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String first = scanner.nextLine();
            String second = scanner.nextLine();
            //System.out.println(check(first, second));
            char[] a1 = first.toCharArray();
            char[] a2 = second.toCharArray();
            Arrays.sort(a1);
            Arrays.sort(a2);
            System.out.println(Arrays.equals(a1, a2) ? 1 : 0);
        }
    }

//    private static int check(String first, String second) {
//        if (first.length() != second.length()) {
//            return 0;
//        } else if (first.equals(second)) {
//            return 1;
//        }
//        long ma = first.chars().distinct().filter(x -> second.chars().anyMatch(y -> y == x)).count();
//        return distincts == 0L ? 1 : 0;
//    }
}
