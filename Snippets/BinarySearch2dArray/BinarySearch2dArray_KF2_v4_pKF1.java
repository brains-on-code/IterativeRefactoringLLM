package com.thealgorithms.searches;

public final class BinarySearch2dArray {

    private BinarySearch2dArray() {
    }

    static int[] binarySearch(int[][] matrix, int target) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        if (rowCount == 1) {
            return binarySearchInRow(matrix, target, 0, 0, columnCount - 1);
        }

        int topRowIndex = 0;
        int bottomRowIndex = rowCount - 1;
        int midColumnIndex = columnCount / 2;

        while (topRowIndex < bottomRowIndex - 1) {
            int midRowIndex = topRowIndex + (bottomRowIndex - topRowIndex) / 2;
            int midValue = matrix[midRowIndex][midColumnIndex];

            if (midValue == target) {
                return new int[] {midRowIndex, midColumnIndex};
            } else if (midValue < target) {
                topRowIndex = midRowIndex;
            } else {
                bottomRowIndex = midRowIndex;
            }
        }

        if (matrix[topRowIndex][midColumnIndex] == target) {
            return new int[] {topRowIndex, midColumnIndex};
        }

        if (matrix[bottomRowIndex][midColumnIndex] == target) {
            return new int[] {bottomRowIndex, midColumnIndex};
        }

        if (target <= matrix[topRowIndex][midColumnIndex - 1]) {
            return binarySearchInRow(matrix, target, topRowIndex, 0, midColumnIndex - 1);
        }

        if (target >= matrix[topRowIndex][midColumnIndex + 1]
                && target <= matrix[topRowIndex][columnCount - 1]) {
            return binarySearchInRow(matrix, target, topRowIndex, midColumnIndex + 1, columnCount - 1);
        }

        if (target <= matrix[bottomRowIndex][midColumnIndex - 1]) {
            return binarySearchInRow(matrix, target, bottomRowIndex, 0, midColumnIndex - 1);
        } else {
            return binarySearchInRow(matrix, target, bottomRowIndex, midColumnIndex + 1, columnCount - 1);
        }
    }

    static int[] binarySearchInRow(
            int[][] matrix,
            int target,
            int rowIndex,
            int startColumnIndex,
            int endColumnIndex
    ) {
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