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

        int startRow = 0;
        int endRow = rowCount - 1;
        int middleColumn = columnCount / 2;

        while (startRow < endRow - 1) {
            int middleRow = startRow + (endRow - startRow) / 2;

            if (matrix[middleRow][middleColumn] == target) {
                return new int[] {middleRow, middleColumn};
            } else if (matrix[middleRow][middleColumn] < target) {
                startRow = middleRow;
            } else {
                endRow = middleRow;
            }
        }

        if (matrix[startRow][middleColumn] == target) {
            return new int[] {startRow, middleColumn};
        }

        if (matrix[endRow][middleColumn] == target) {
            return new int[] {endRow, middleColumn};
        }

        if (target <= matrix[startRow][middleColumn - 1]) {
            return binarySearchInRow(matrix, target, startRow, 0, middleColumn - 1);
        }

        if (target >= matrix[startRow][middleColumn + 1] && target <= matrix[startRow][columnCount - 1]) {
            return binarySearchInRow(matrix, target, startRow, middleColumn + 1, columnCount - 1);
        }

        if (target <= matrix[endRow][middleColumn - 1]) {
            return binarySearchInRow(matrix, target, endRow, 0, middleColumn - 1);
        } else {
            return binarySearchInRow(matrix, target, endRow, middleColumn + 1, columnCount - 1);
        }
    }

    static int[] binarySearchInRow(int[][] matrix, int target, int rowIndex, int startColumn, int endColumn) {
        int left = startColumn;
        int right = endColumn;

        while (left <= right) {
            int middleColumn = left + (right - left) / 2;

            if (matrix[rowIndex][middleColumn] == target) {
                return new int[] {rowIndex, middleColumn};
            } else if (matrix[rowIndex][middleColumn] < target) {
                left = middleColumn + 1;
            } else {
                right = middleColumn - 1;
            }
        }

        return new int[] {-1, -1};
    }
}