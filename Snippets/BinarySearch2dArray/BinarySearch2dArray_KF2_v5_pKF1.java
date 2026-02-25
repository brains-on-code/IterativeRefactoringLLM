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

        int topRow = 0;
        int bottomRow = rowCount - 1;
        int midColumn = columnCount / 2;

        while (topRow < bottomRow - 1) {
            int midRow = topRow + (bottomRow - topRow) / 2;
            int midValue = matrix[midRow][midColumn];

            if (midValue == target) {
                return new int[] {midRow, midColumn};
            } else if (midValue < target) {
                topRow = midRow;
            } else {
                bottomRow = midRow;
            }
        }

        if (matrix[topRow][midColumn] == target) {
            return new int[] {topRow, midColumn};
        }

        if (matrix[bottomRow][midColumn] == target) {
            return new int[] {bottomRow, midColumn};
        }

        if (target <= matrix[topRow][midColumn - 1]) {
            return binarySearchInRow(matrix, target, topRow, 0, midColumn - 1);
        }

        if (target >= matrix[topRow][midColumn + 1]
                && target <= matrix[topRow][columnCount - 1]) {
            return binarySearchInRow(matrix, target, topRow, midColumn + 1, columnCount - 1);
        }

        if (target <= matrix[bottomRow][midColumn - 1]) {
            return binarySearchInRow(matrix, target, bottomRow, 0, midColumn - 1);
        } else {
            return binarySearchInRow(matrix, target, bottomRow, midColumn + 1, columnCount - 1);
        }
    }

    static int[] binarySearchInRow(
            int[][] matrix,
            int target,
            int rowIndex,
            int startColumn,
            int endColumn
    ) {
        int leftColumn = startColumn;
        int rightColumn = endColumn;

        while (leftColumn <= rightColumn) {
            int midColumn = leftColumn + (rightColumn - leftColumn) / 2;
            int midValue = matrix[rowIndex][midColumn];

            if (midValue == target) {
                return new int[] {rowIndex, midColumn};
            } else if (midValue < target) {
                leftColumn = midColumn + 1;
            } else {
                rightColumn = midColumn - 1;
            }
        }

        return new int[] {-1, -1};
    }
}