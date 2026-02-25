package com.thealgorithms.others;

public final class ArrayRightRotation {

    private ArrayRightRotation() {
        // Utility class; prevent instantiation
    }

    public static int[] rotateRight(int[] array, int rotationCount) {
        validateInput(array, rotationCount);

        int length = array.length;
        int effectiveRotations = rotationCount % length;

        if (effectiveRotations == 0) {
            return array;
        }

        reverse(array, 0, length - 1);
        reverse(array, 0, effectiveRotations - 1);
        reverse(array, effectiveRotations, length - 1);

        return array;
    }

    private static void validateInput(int[] array, int rotationCount) {
        if (array == null || array.length == 0 || rotationCount < 0) {
            throw new IllegalArgumentException("Array must be non-empty and rotation count must be non-negative.");
        }
    }

    private static void reverse(int[] array, int startIndex, int endIndex) {
        while (startIndex < endIndex) {
            int temp = array[startIndex];
            array[startIndex] = array[endIndex];
            array[endIndex] = temp;
            startIndex++;
            endIndex--;
        }
    }
}