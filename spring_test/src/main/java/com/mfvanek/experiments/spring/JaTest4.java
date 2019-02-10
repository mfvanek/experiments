package com.mfvanek.experiments.spring;

import java.util.*;

/**
 * Дано целое число n. Требуется вывести все правильные скобочные последовательности длины 2 ⋅ n, упорядоченные лексикографически (см. https://ru.wikipedia.org/wiki/Лексикографический_порядок).
 *
 * В задаче используются только круглые скобки.
 *
 * Желательно получить решение, которое работает за время, пропорциональное общему количеству правильных скобочных последовательностей в ответе, и при этом использует объём памяти, пропорциональный n.
 *
 * Формат ввода
 * Единственная строка входного файла содержит целое число n, 0 ≤ n ≤ 11
 *
 * Формат вывода
 * Выходной файл содержит сгенерированные правильные скобочные последовательности, упорядоченные лексикографически.
 */
public class JaTest4 {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = Integer.parseInt(scanner.nextLine());
            if (n > 0) {
                gen(0, 0, n, "");
            }
        }
    }

    private static void gen(int leftBraces, int rightBraces, int n, String str) {
        if (leftBraces == n && rightBraces == n) {
            System.out.println(str);
        } else {
            if (leftBraces < n) {
                gen(leftBraces + 1, rightBraces, n, str + "(");
            }
            if (rightBraces < leftBraces) {
                gen(leftBraces, rightBraces + 1, n, str + ")");
            }
        }
    }
}
