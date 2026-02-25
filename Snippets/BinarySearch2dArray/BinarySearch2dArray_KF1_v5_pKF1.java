package com.thealgorithms.searches;

/**
 * Utility class for searching a value in a row- and column-wise sorted matrix.
 */
public final class SortedMatrixSearch {

    private SortedMatrixSearch() {
    }

    /**
     * Searches for a target value in a matrix where each row and each column is sorted in ascending order.
     *
     * @param matrix the sorted 2D matrix
     * @param target the value to search for
     * @return an array of size 2 containing the row and column indices of the target if found,
     *         otherwise {-1, -1}
     */
    static int[] searchInSortedMatrix(int[][] matrix, int target) {
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

        // Search in 1st quadrant
        if (target <= matrix[startRow][middleColumn - 1]) {
            return binarySearchInRow(matrix, target, startRow, 0, middleColumn - 1);
        }

        // Search in 2nd quadrant
        if (target >= matrix[startRow][middleColumn + 1] && target <= matrix[startRow][columnCount - 1]) {
            return binarySearchInRow(matrix, target, startRow, middleColumn + 1, columnCount - 1);
        }

        // Search in 3rd quadrant
        if (target <= matrix[endRow][middleColumn - 1]) {
            return binarySearchInRow(matrix, target, endRow, 0, middleColumn - 1);
        }

        // Search in 4th quadrant
        return binarySearchInRow(matrix, target, endRow, middleColumn + 1, columnCount - 1);
    }

    /**
     * Performs a binary search for a target value within a specific row of the matrix.
     *
     * @param matrix      the 2D matrix
     * @param target      the value to search for
     * @param rowIndex    the index of the row to search in
     * @param leftColumn  the starting column index of the search range
     * @param rightColumn the ending column index of the search range
     * @return an array of size 2 containing the row and column indices of the target if found,
     *         otherwise {-1, -1}
     */
    static int[] binarySearchInRow(int[][] matrix, int target, int rowIndex, int leftColumn, int rightColumn) {
        while (leftColumn <= rightColumn) {
            int middleColumn = leftColumn + (rightColumn - leftColumn) / 2;

            if (matrix[rowIndex][middleColumn] == target) {
                return new int[] {rowIndex, middleColumn};
            } else if (matrix[rowIndex][middleColumn] < target) {
                leftColumn = middleColumn + 1;
            } else {
                rightColumn = middleColumn - 1;
            }
        }

        return new int[] {-1, -1};
    }
}