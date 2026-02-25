package com.thealgorithms.searches;

import java.util.Scanner;

/*
    Problem Statement:
    Given an array, find out how many times it has to been rotated
    from its initial sorted position.
    Input-Output:
    Eg. [11,12,15,18,2,5,6,8]
    It has been rotated: 4 times
    (One rotation means putting the first element to the end)
    Note: The array cannot contain duplicates

    Logic:
    The position of the minimum element will give the number of times the array has been rotated
    from its initial sorted position.
    Eg. For [2,5,6,8,11,12,15,18], 1 rotation gives [5,6,8,11,12,15,18,2], 2 rotations
   [6,8,11,12,15,18,2,5] and so on. Finding the minimum element will take O(N) time but, we can  use
   Binary Search to find the minimum element, we can reduce the complexity to O(log N). If we look
   at the rotated array, to identify the minimum element (say a[i]), we observe that
   a[i-1]>a[i]<a[i+1].

    Some other test cases:
    1. [1,2,3,4] Number of rotations: 0 or 4(Both valid)
    2. [15,17,2,3,5] Number of rotations: 3
 */
final class HowManyTimesRotated {
    private HowManyTimesRotated() {}

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int arrayLength = scanner.nextInt();
        int[] array = new int[arrayLength];

        for (int index = 0; index < arrayLength; index++) {
            array[index] = scanner.nextInt();
        }

        System.out.println("The array has been rotated " + countRotations(array) + " times");
        scanner.close();
    }

    public static int countRotations(int[] array) {
        int left = 0;
        int right = array.length - 1;
        int middle = 0;

        while (left <= right) {
            middle = left + (right - left) / 2;

            if (array[middle] < array[middle - 1] && array[middle] < array[middle + 1]) {
                break;
            } else if (array[middle] > array[middle - 1] && array[middle] < array[middle + 1]) {
                right = middle + 1;
            } else if (array[middle] > array[middle - 1] && array[middle] > array[middle + 1]) {
                left = middle - 1;
            }
        }

        return middle;
    }
}