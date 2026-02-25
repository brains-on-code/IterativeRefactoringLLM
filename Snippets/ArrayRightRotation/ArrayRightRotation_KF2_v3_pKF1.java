package com.thealgorithms.others;

public final class ArrayRightRotation {

    private ArrayRightRotation() {
        // Utility class; prevent instantiation
    }

    public static int[] rotateRight(int[] array, int rotationCount) {
        if (array == null || array.length == 0 || rotationCount < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int length = array.length;
        int effectiveRotations = rotationCount % length;

        reverseSubarray(array, 0, length - 1);
        reverseSubarray(array, 0, effectiveRotations - 1);
        reverseSubarray(array, effectiveRotations, length - 1);

        return array;
    }

    private static void reverseSubarray(int[] array, int leftIndex, int rightIndex) {
        while (leftIndex < rightIndex) {
            int temp = array[leftIndex];
            array[leftIndex] = array[rightIndex];
            array[rightIndex] = temp;
            leftIndex++;
            rightIndex--;
        }
    }
}