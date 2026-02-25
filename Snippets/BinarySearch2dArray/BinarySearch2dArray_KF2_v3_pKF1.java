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
        int middleColumn = columnCount / 2;

        while (topRow < bottomRow - 1) {
            int middleRow = topRow + (bottomRow - topRow) / 2;
            int middleValue = matrix[middleRow][middleColumn];

            if (middleValue == target) {
                return new int[] {middleRow, middleColumn};
            } else if (middleValue < target) {
                topRow = middleRow;
            } else {
                bottomRow = middleRow;
            }
        }

        if (matrix[topRow][middleColumn] == target) {
            return new int[] {topRow, middleColumn};
        }

        if (matrix[bottomRow][middleColumn] == target) {
            return new int[] {bottomRow, middleColumn};
        }

        if (target <= matrix[topRow][middleColumn - 1]) {
            return binarySearchInRow(matrix, target, topRow, 0, middleColumn - 1);
        }

        if (target >= matrix[topRow][middleColumn + 1]
                && target <= matrix[topRow][columnCount - 1]) {
            return binarySearchInRow(matrix, target, topRow, middleColumn + 1, columnCount - 1);
        }

        if (target <= matrix[bottomRow][middleColumn - 1]) {
            return binarySearchInRow(matrix, target, bottomRow, 0, middleColumn - 1);
        } else {
            return binarySearchInRow(matrix, target, bottomRow, middleColumn + 1, columnCount - 1);
        }
    }

    static int[] binarySearchInRow(
            int[][] matrix,
            int target,
            int rowIndex,
            int startColumnIndex,
            int endColumnIndex
    ) {
        int leftColumn = startColumnIndex;
        int rightColumn = endColumnIndex;

        while (leftColumn <= rightColumn) {
            int middleColumn = leftColumn + (rightColumn - leftColumn) / 2;
            int middleValue = matrix[rowIndex][middleColumn];

            if (middleValue == target) {
                return new int[] {rowIndex, middleColumn};
            } else if (middleValue < target) {
                leftColumn = middleColumn + 1;
            } else {
                rightColumn = middleColumn - 1;
            }
        }

        return new int[] {-1, -1};
    }
}