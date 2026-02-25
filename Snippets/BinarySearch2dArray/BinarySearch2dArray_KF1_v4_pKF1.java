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
        int totalRows = matrix.length;
        int totalColumns = matrix[0].length;

        if (totalRows == 1) {
            return binarySearchInRow(matrix, target, 0, 0, totalColumns - 1);
        }

        int topRow = 0;
        int bottomRow = totalRows - 1;
        int midColumn = totalColumns / 2;

        while (topRow < bottomRow - 1) {
            int midRow = topRow + (bottomRow - topRow) / 2;

            if (matrix[midRow][midColumn] == target) {
                return new int[] {midRow, midColumn};
            } else if (matrix[midRow][midColumn] < target) {
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

        // Search in 1st quadrant
        if (target <= matrix[topRow][midColumn - 1]) {
            return binarySearchInRow(matrix, target, topRow, 0, midColumn - 1);
        }

        // Search in 2nd quadrant
        if (target >= matrix[topRow][midColumn + 1] && target <= matrix[topRow][totalColumns - 1]) {
            return binarySearchInRow(matrix, target, topRow, midColumn + 1, totalColumns - 1);
        }

        // Search in 3rd quadrant
        if (target <= matrix[bottomRow][midColumn - 1]) {
            return binarySearchInRow(matrix, target, bottomRow, 0, midColumn - 1);
        }

        // Search in 4th quadrant
        return binarySearchInRow(matrix, target, bottomRow, midColumn + 1, totalColumns - 1);
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
            int midColumn = leftColumn + (rightColumn - leftColumn) / 2;

            if (matrix[rowIndex][midColumn] == target) {
                return new int[] {rowIndex, midColumn};
            } else if (matrix[rowIndex][midColumn] < target) {
                leftColumn = midColumn + 1;
            } else {
                rightColumn = midColumn - 1;
            }
        }

        return new int[] {-1, -1};
    }
}