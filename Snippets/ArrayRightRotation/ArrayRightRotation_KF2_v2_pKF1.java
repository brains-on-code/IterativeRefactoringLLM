package com.thealgorithms.others;

public final class ArrayRightRotation {

    private ArrayRightRotation() {
        // Utility class; prevent instantiation
    }

    public static int[] rotateRight(int[] inputArray, int rotationCount) {
        if (inputArray == null || inputArray.length == 0 || rotationCount < 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        int arrayLength = inputArray.length;
        int normalizedRotationCount = rotationCount % arrayLength;

        reverseRange(inputArray, 0, arrayLength - 1);
        reverseRange(inputArray, 0, normalizedRotationCount - 1);
        reverseRange(inputArray, normalizedRotationCount, arrayLength - 1);

        return inputArray;
    }

    private static void reverseRange(int[] array, int startIndex, int endIndex) {
        while (startIndex < endIndex) {
            int temporaryValue = array[startIndex];
            array[startIndex] = array[endIndex];
            array[endIndex] = temporaryValue;
            startIndex++;
            endIndex--;
        }
    }
}