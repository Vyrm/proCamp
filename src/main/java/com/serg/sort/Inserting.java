package com.serg.sort;

import java.util.Arrays;

public class Inserting {
    public static void main(String[] args) {
        int[] a = {12, 8, 13, 3, 1, 10, 7, 22};
        Integer c = new Integer(325);
        int[] b = sort(a, c);
        System.out.println(Arrays.toString(b));
    }

    public static int[] sort(int[] a, Integer c) {
        Integer q = new Integer(c);
        q = 1001;
        int[] b = a;
        for (int j = 0; j < a.length / 2; j++) {
            for (int i = 0; i < a.length - 1; i++) {
                int temp;
                if (b[i] > b[i + 1]) {
                    temp = b[i];
                    b[i] = b[i + 1];
                    b[i+1] = temp;
                }
            }
        }
        return b;
    }
}
