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
        int topRowIndex = 0;
        int bottomRowIndex = rowCount - 1;
        int middleColumnIndex = columnCount / 2; // Middle column index for comparison.

        // Perform binary search on rows based on the middle column.
        while (topRowIndex < bottomRowIndex - 1) {
            int middleRowIndex = topRowIndex + (bottomRowIndex - topRowIndex) / 2;
            int middleValue = matrix[middleRowIndex][middleColumnIndex];

            if (middleValue == target) {
                return new int[] {middleRowIndex, middleColumnIndex};
            } else if (middleValue < target) {
                topRowIndex = middleRowIndex;
            } else {
                bottomRowIndex = middleRowIndex;
            }
        }

        // Check the middle column of topRowIndex and bottomRowIndex.
        if (matrix[topRowIndex][middleColumnIndex] == target) {
            return new int[] {topRowIndex, middleColumnIndex};
        }

        if (matrix[bottomRowIndex][middleColumnIndex] == target) {
            return new int[] {bottomRowIndex, middleColumnIndex};
        }

        // 1st quadrant: left side of topRowIndex
        if (target <= matrix[topRowIndex][middleColumnIndex - 1]) {
            return binarySearchInRow(matrix, target, topRowIndex, 0, middleColumnIndex - 1);
        }

        // 2nd quadrant: right side of topRowIndex
        if (target >= matrix[topRowIndex][middleColumnIndex + 1] && target <= matrix[topRowIndex][columnCount - 1]) {
            return binarySearchInRow(matrix, target, topRowIndex, middleColumnIndex + 1, columnCount - 1);
        }

        // 3rd quadrant: left side of bottomRowIndex
        if (target <= matrix[bottomRowIndex][middleColumnIndex - 1]) {
            return binarySearchInRow(matrix, target, bottomRowIndex, 0, middleColumnIndex - 1);
        }

        // 4th quadrant: right side of bottomRowIndex
        return binarySearchInRow(matrix, target, bottomRowIndex, middleColumnIndex + 1, columnCount - 1);
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
        int leftColumnIndex = startColumn;
        int rightColumnIndex = endColumn;

        while (leftColumnIndex <= rightColumnIndex) {
            int middleColumnIndex = leftColumnIndex + (rightColumnIndex - leftColumnIndex) / 2;
            int middleValue = matrix[rowIndex][middleColumnIndex];

            if (middleValue == target) {
                return new int[] {rowIndex, middleColumnIndex};
            } else if (middleValue < target) {
                leftColumnIndex = middleColumnIndex + 1;
            } else {
                rightColumnIndex = middleColumnIndex - 1;
            }
        }

        return new int[] {-1, -1};
    }
}