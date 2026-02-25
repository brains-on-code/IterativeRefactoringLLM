package com.thealgorithms.searches;

public final class BinarySearch2dArray {

    private BinarySearch2dArray() {
    }

    static int[] binarySearch(int[][] matrix, int target) {
        int totalRows = matrix.length;
        int totalColumns = matrix[0].length;

        if (totalRows == 1) {
            return binarySearchInRow(matrix, target, 0, 0, totalColumns - 1);
        }

        int startRowIndex = 0;
        int endRowIndex = totalRows - 1;
        int midColumnIndex = totalColumns / 2;

        while (startRowIndex < endRowIndex - 1) {
            int midRowIndex = startRowIndex + (endRowIndex - startRowIndex) / 2;
            int midValue = matrix[midRowIndex][midColumnIndex];

            if (midValue == target) {
                return new int[] {midRowIndex, midColumnIndex};
            } else if (midValue < target) {
                startRowIndex = midRowIndex;
            } else {
                endRowIndex = midRowIndex;
            }
        }

        if (matrix[startRowIndex][midColumnIndex] == target) {
            return new int[] {startRowIndex, midColumnIndex};
        }

        if (matrix[endRowIndex][midColumnIndex] == target) {
            return new int[] {endRowIndex, midColumnIndex};
        }

        if (target <= matrix[startRowIndex][midColumnIndex - 1]) {
            return binarySearchInRow(matrix, target, startRowIndex, 0, midColumnIndex - 1);
        }

        if (target >= matrix[startRowIndex][midColumnIndex + 1]
                && target <= matrix[startRowIndex][totalColumns - 1]) {
            return binarySearchInRow(matrix, target, startRowIndex, midColumnIndex + 1, totalColumns - 1);
        }

        if (target <= matrix[endRowIndex][midColumnIndex - 1]) {
            return binarySearchInRow(matrix, target, endRowIndex, 0, midColumnIndex - 1);
        } else {
            return binarySearchInRow(matrix, target, endRowIndex, midColumnIndex + 1, totalColumns - 1);
        }
    }

    static int[] binarySearchInRow(int[][] matrix, int target, int rowIndex, int startColumnIndex, int endColumnIndex) {
        int leftColumnIndex = startColumnIndex;
        int rightColumnIndex = endColumnIndex;

        while (leftColumnIndex <= rightColumnIndex) {
            int midColumnIndex = leftColumnIndex + (rightColumnIndex - leftColumnIndex) / 2;
            int midValue = matrix[rowIndex][midColumnIndex];

            if (midValue == target) {
                return new int[] {rowIndex, midColumnIndex};
            } else if (midValue < target) {
                leftColumnIndex = midColumnIndex + 1;
            } else {
                rightColumnIndex = midColumnIndex - 1;
            }
        }

        return new int[] {-1, -1};
    }
}