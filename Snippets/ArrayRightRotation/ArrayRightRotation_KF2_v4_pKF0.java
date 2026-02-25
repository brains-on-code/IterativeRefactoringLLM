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
        if (array == null) {
            throw new IllegalArgumentException("Array must not be null.");
        }
        if (array.length == 0) {
            throw new IllegalArgumentException("Array must not be empty.");
        }
        if (rotationCount < 0) {
            throw new IllegalArgumentException("Rotation count must be non-negative.");
        }
    }

    private static void reverse(int[] array, int startIndex, int endIndex) {
        while (startIndex < endIndex) {
            swap(array, startIndex, endIndex);
            startIndex++;
            endIndex--;
        }
    }

    private static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}