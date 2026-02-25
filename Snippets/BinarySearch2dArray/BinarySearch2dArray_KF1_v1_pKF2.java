package com.thealgorithms.searches;

/**
 * Utility class for searching in a 2D matrix.
 *
 * The matrix is assumed to be:
 * - Sorted in non-decreasing order row-wise.
 * - Sorted in non-decreasing order column-wise.
 *
 * The public API returns the position of the target as [row, column],
 * or [-1, -1] if the target is not found.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Searches for a target value in a 2D matrix.
     *
     * @param matrix the 2D matrix to search in
     * @param target the value to search for
     * @return an array of size 2: [rowIndex, columnIndex] if found, otherwise [-1, -1]
     */
    static int[] method2(int[][] matrix, int target) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        // If there is only one row, perform a simple binary search on that row
        if (rowCount == 1) {
            return method2(matrix, target, 0, 0, colCount - 1);
        }

        int startRow = 0;
        int endRow = rowCount - 1;
        int midCol = colCount / 2;

        // Narrow down to two candidate rows using the middle column
        while (startRow < endRow - 1) {
            int midRow = startRow + (endRow - startRow) / 2;

            if (matrix[midRow][midCol] == target) {
                return new int[] {midRow, midCol};
            } else if (matrix[midRow][midCol] < target) {
                startRow = midRow;
            } else {
                endRow = midRow;
            }
        }

        // Check the middle column in the two candidate rows
        if (matrix[startRow][midCol] == target) {
            return new int[] {startRow, midCol};
        }

        if (matrix[endRow][midCol] == target) {
            return new int[] {endRow, midCol};
        }

        // Search in 4 possible subarrays (quadrants) around the middle column

        // 1st quadrant: startRow, columns [0 .. midCol - 1]
        if (target <= matrix[startRow][midCol - 1]) {
            return method2(matrix, target, startRow, 0, midCol - 1);
        }

        // 2nd quadrant: startRow, columns [midCol + 1 .. colCount - 1]
        if (target >= matrix[startRow][midCol + 1] && target <= matrix[startRow][colCount - 1]) {
            return method2(matrix, target, startRow, midCol + 1, colCount - 1);
        }

        // 3rd quadrant: endRow, columns [0 .. midCol - 1]
        if (target <= matrix[endRow][midCol - 1]) {
            return method2(matrix, target, endRow, 0, midCol - 1);
        }

        // 4th quadrant: endRow, columns [midCol + 1 .. colCount - 1]
        return method2(matrix, target, endRow, midCol + 1, colCount - 1);
    }

    /**
     * Performs a binary search on a single row of the matrix.
     *
     * @param matrix the 2D matrix
     * @param target the value to search for
     * @param row    the row index to search in
     * @param left   the left column index (inclusive)
     * @param right  the right column index (inclusive)
     * @return an array of size 2: [rowIndex, columnIndex] if found, otherwise [-1, -1]
     */
    static int[] method2(int[][] matrix, int target, int row, int left, int right) {
        while (left <= right) {
            int midCol = left + (right - left) / 2;

            if (matrix[row][midCol] == target) {
                return new int[] {row, midCol};
            } else if (matrix[row][midCol] < target) {
                left = midCol + 1;
            } else {
                right = midCol - 1;
            }
        }

        return new int[] {-1, -1};
    }
}