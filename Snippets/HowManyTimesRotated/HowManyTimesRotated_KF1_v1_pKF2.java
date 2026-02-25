package com.thealgorithms.searches;

import java.util.Scanner;

/**
 * Utility class to determine how many times a sorted array
 * has been rotated. The number of rotations is equal to the
 * index of the minimum element in the rotated sorted array.
 *
 * Example:
 *   Original sorted array: [2, 5, 6, 8, 11, 12, 15, 18]
 *   Rotated array:         [11, 12, 15, 18, 2, 5, 6, 8]
 *   Rotations: 4 (index of element 2)
 */
final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    public static void method1(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }

        System.out.println("The array has been rotated " + method2(arr) + " times");
        scanner.close();
    }

    /**
     * Finds the number of rotations in a rotated sorted array using binary search.
     * Assumes:
     *  - The array was originally sorted in ascending order.
     *  - The array has been rotated 0 or more times.
     *  - All elements are distinct.
     *
     * @param arr the rotated sorted array
     * @return the index of the minimum element (number of rotations)
     */
    public static int method2(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        int mid = 0;

        while (left <= right) {
            mid = left + (right - left) / 2;

            // Handle boundaries safely
            int prev = (mid - 1 + arr.length) % arr.length;
            int next = (mid + 1) % arr.length;

            // If current element is the minimum
            if (arr[mid] <= arr[prev] && arr[mid] <= arr[next]) {
                break;
            }

            // Decide which half to search:
            // If left part is sorted, minimum is in right part
            if (arr[left] <= arr[mid]) {
                left = mid + 1;
            } else { // Right part is sorted, minimum is in left part
                right = mid - 1;
            }
        }

        return mid;
    }
}