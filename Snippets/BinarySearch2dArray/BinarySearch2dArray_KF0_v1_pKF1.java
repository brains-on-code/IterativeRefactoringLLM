package com.thealgorithms.searches;

/**
 * This class provides a method to search for a target value in a 2D sorted
 * array.
 * The search is performed using a combination of binary search on rows and
 * columns.
 * The 2D array must be strictly sorted in both rows and columns.
 *
 * The algorithm works by:
 * 1. Performing a binary search on the middle column of the 2D array.
 * 2. Depending on the value found, it eliminates rows above or below the middle
 *    element.
 * 3. After finding or eliminating rows, it further applies binary search in the
 *    relevant columns.
 */
public final class BinarySearch2dArray {

    private BinarySearch2dArray() {
    }

    /**
     * Performs a binary search on a 2D sorted array to find the target value.
     * The array must be sorted in ascending order in both rows and columns.
     *
     * @param matrix The 2D array to search in.
     * @param target The value to search for.
     * @return An array containing the row and column indices of the target, or [-1,
     *         -1] if the target is not found.
     */
    static int[] binarySearch(int[][] matrix, int target) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        // Edge case: If there's only one row, search that row directly.
        if (rowCount == 1) {
            return binarySearchInRow(matrix, target, 0, 0, columnCount - 1);
        }

        // Set initial boundaries for binary search on rows.
        int startRow = 0;
        int endRow = rowCount - 1;
        int middleColumn = columnCount / 2; // Middle column index for comparison.

        // Perform binary search on rows based on the middle column.
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

        // Check the middle column of startRow and endRow.
        if (matrix[startRow][middleColumn] == target) {
            return new int[] {startRow, middleColumn};
        }

        if (matrix[endRow][middleColumn] == target) {
            return new int[] {endRow, middleColumn};
        }

        // 1st quadrant: left side of startRow
        if (target <= matrix[startRow][middleColumn - 1]) {
            return binarySearchInRow(matrix, target, startRow, 0, middleColumn - 1);
        }

        // 2nd quadrant: right side of startRow
        if (target >= matrix[startRow][middleColumn + 1] && target <= matrix[startRow][columnCount - 1]) {
            return binarySearchInRow(matrix, target, startRow, middleColumn + 1, columnCount - 1);
        }

        // 3rd quadrant: left side of endRow
        if (target <= matrix[endRow][middleColumn - 1]) {
            return binarySearchInRow(matrix, target, endRow, 0, middleColumn - 1);
        }

        // 4th quadrant: right side of endRow
        return binarySearchInRow(matrix, target, endRow, middleColumn + 1, columnCount - 1);
    }

    /**
     * Performs a binary search on a specific row of the 2D array.
     *
     * @param matrix        The 2D array to search in.
     * @param target        The value to search for.
     * @param rowIndex      The row index where the target will be searched.
     * @param startColumn   The starting column index for the search.
     * @param endColumn     The ending column index for the search.
     * @return An array containing the row and column indices of the target, or [-1,
     *         -1] if the target is not found.
     */
    static int[] binarySearchInRow(int[][] matrix, int target, int rowIndex, int startColumn, int endColumn) {
        while (startColumn <= endColumn) {
            int middleColumn = startColumn + (endColumn - startColumn) / 2;

            if (matrix[rowIndex][middleColumn] == target) {
                return new int[] {rowIndex, middleColumn};
            } else if (matrix[rowIndex][middleColumn] < target) {
                startColumn = middleColumn + 1;
            } else {
                endColumn = middleColumn - 1;
            }
        }

        return new int[] {-1, -1};
    }
}