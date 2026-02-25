package com.thealgorithms.dynamicprogramming;


public final class WineProblem {
    private WineProblem() {
    }


    public static int wpRecursion(int[] arr, int si, int ei) {
        int n = arr.length;
        int year = (n - (ei - si + 1)) + 1;
        if (si == ei) {
            return arr[si] * year;
        }

        int start = wpRecursion(arr, si + 1, ei) + arr[si] * year;
        int end = wpRecursion(arr, si, ei - 1) + arr[ei] * year;

        return Math.max(start, end);
    }


    public static int wptd(int[] arr, int si, int ei, int[][] strg) {
        int n = arr.length;
        int year = (n - (ei - si + 1)) + 1;
        if (si == ei) {
            return arr[si] * year;
        }

        if (strg[si][ei] != 0) {
            return strg[si][ei];
        }
        int start = wptd(arr, si + 1, ei, strg) + arr[si] * year;
        int end = wptd(arr, si, ei - 1, strg) + arr[ei] * year;

        int ans = Math.max(start, end);
        strg[si][ei] = ans;

        return ans;
    }


    public static int wpbu(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Input array cannot be null or empty.");
        }
        int n = arr.length;
        int[][] strg = new int[n][n];

        for (int slide = 0; slide <= n - 1; slide++) {
            for (int si = 0; si <= n - slide - 1; si++) {
                int ei = si + slide;
                int year = (n - (ei - si + 1)) + 1;
                if (si == ei) {
                    strg[si][ei] = arr[si] * year;
                } else {
                    int start = strg[si + 1][ei] + arr[si] * year;
                    int end = strg[si][ei - 1] + arr[ei] * year;

                    strg[si][ei] = Math.max(start, end);
                }
            }
        }
        return strg[0][n - 1];
    }
}
