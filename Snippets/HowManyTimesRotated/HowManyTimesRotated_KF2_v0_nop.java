package com.thealgorithms.searches;

import java.util.Scanner;


final class HowManyTimesRotated {
    private HowManyTimesRotated() {
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        System.out.println("The array has been rotated " + rotated(a) + " times");
        sc.close();
    }

    public static int rotated(int[] a) {
        int low = 0;
        int high = a.length - 1;
        int mid = 0;

        while (low <= high) {
            mid = low + (high - low) / 2;

            if (a[mid] < a[mid - 1] && a[mid] < a[mid + 1]) {
                break;
            } else if (a[mid] > a[mid - 1] && a[mid] < a[mid + 1]) {
                high = mid + 1;
            } else if (a[mid] > a[mid - 1] && a[mid] > a[mid + 1]) {
                low = mid - 1;
            }
        }

        return mid;
    }
}
