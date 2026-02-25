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
        int topRow = 0;
        int bottomRow = rowCount - 1;
        int midColumn = columnCount / 2; // Middle column index for comparison.

        // Perform binary search on rows based on the middle column.
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

        // Check the middle column of topRow and bottomRow.
        if (matrix[topRow][midColumn] == target) {
            return new int[] {topRow, midColumn};
        }

        if (matrix[bottomRow][midColumn] == target) {
            return new int[] {bottomRow, midColumn};
        }

        // 1st quadrant: left side of topRow
        if (target <= matrix[topRow][midColumn - 1]) {
            return binarySearchInRow(matrix, target, topRow, 0, midColumn - 1);
        }

        // 2nd quadrant: right side of topRow
        if (target >= matrix[topRow][midColumn + 1] && target <= matrix[topRow][columnCount - 1]) {
            return binarySearchInRow(matrix, target, topRow, midColumn + 1, columnCount - 1);
        }

        // 3rd quadrant: left side of bottomRow
        if (target <= matrix[bottomRow][midColumn - 1]) {
            return binarySearchInRow(matrix, target, bottomRow, 0, midColumn - 1);
        }

        // 4th quadrant: right side of bottomRow
        return binarySearchInRow(matrix, target, bottomRow, midColumn + 1, columnCount - 1);
    }

    /**
     * Performs a binary search on a specific row of the 2D array.
     *
     * @param matrix      The 2D array to search in.
     * @param target      The value to search for.
     * @param rowIndex    The row index where the target will be searched.
     * @param startColumn The starting column index for the search.
     * @param endColumn   The ending column index for the search.
     * @return An array containing the row and column indices of the target, or [-1,
     *         -1] if the target is not found.
     */
    static int[] binarySearchInRow(int[][] matrix, int target, int rowIndex, int startColumn, int endColumn) {
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