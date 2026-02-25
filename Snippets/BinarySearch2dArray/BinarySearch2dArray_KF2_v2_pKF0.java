package com.thealgorithms.searches;

public final class BinarySearch2dArray {

    private BinarySearch2dArray() {
        // Utility class; prevent instantiation
    }

    static int[] binarySearch(int[][] matrix, int target) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        if (rowCount == 1) {
            return binarySearchInRow(matrix, target, 0, 0, colCount - 1);
        }

        int startRow = 0;
        int endRow = rowCount - 1;
        int midCol = colCount / 2;

        while (startRow < endRow - 1) {
            int midRow = startRow + (endRow - startRow) / 2;
            int midValue = matrix[midRow][midCol];

            if (midValue == target) {
                return new int[] {midRow, midCol};
            }

            if (midValue < target) {
                startRow = midRow;
            } else {
                endRow = midRow;
            }
        }

        if (matrix[startRow][midCol] == target) {
            return new int[] {startRow, midCol};
        }

        if (matrix[endRow][midCol] == target) {
            return new int[] {endRow, midCol};
        }

        // Search in 1st quadrant
        if (target <= matrix[startRow][midCol - 1]) {
            return binarySearchInRow(matrix, target, startRow, 0, midCol - 1);
        }

        // Search in 2nd quadrant
        if (target >= matrix[startRow][midCol + 1] && target <= matrix[startRow][colCount - 1]) {
            return binarySearchInRow(matrix, target, startRow, midCol + 1, colCount - 1);
        }

        // Search in 3rd quadrant
        if (target <= matrix[endRow][midCol - 1]) {
            return binarySearchInRow(matrix, target, endRow, 0, midCol - 1);
        }

        // Search in 4th quadrant
        return binarySearchInRow(matrix, target, endRow, midCol + 1, colCount - 1);
    }

    static int[] binarySearchInRow(int[][] matrix, int target, int row, int colStart, int colEnd) {
        int left = colStart;
        int right = colEnd;

        while (left <= right) {
            int midIndex = left + (right - left) / 2;
            int midValue = matrix[row][midIndex];

            if (midValue == target) {
                return new int[] {row, midIndex};
            }

            if (midValue < target) {
                left = midIndex + 1;
            } else {
                right = midIndex - 1;
            }
        }

        return new int[] {-1, -1};
    }
}