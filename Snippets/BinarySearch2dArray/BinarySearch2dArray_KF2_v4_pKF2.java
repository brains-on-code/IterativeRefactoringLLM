package com.thealgorithms.searches;

public final class BinarySearch2dArray {

    private BinarySearch2dArray() {
        // Prevent instantiation
    }

    /**
     * Performs binary search on a 2D matrix where:
     * <ul>
     *   <li>Each row is sorted in ascending order</li>
     *   <li>The first element of each row is greater than the last element of the previous row</li>
     * </ul>
     *
     * @param matrix the 2D sorted matrix
     * @param target the value to search for
     * @return an array of size 2: [rowIndex, colIndex], or [-1, -1] if not found
     */
    static int[] binarySearch(int[][] matrix, int target) {
        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        // Single-row matrix: search directly in that row
        if (rowCount == 1) {
            return binarySearchInRow(matrix, target, 0, 0, colCount - 1);
        }

        int startRow = 0;
        int endRow = rowCount - 1;
        int midCol = colCount / 2;

        // Reduce to two candidate rows using the middle column
        while (startRow < endRow - 1) {
            int midRow = startRow + (endRow - startRow) / 2;
            int midValue = matrix[midRow][midCol];

            if (midValue == target) {
                return new int[] {midRow, midCol};
            } else if (midValue < target) {
                startRow = midRow;
            } else {
                endRow = midRow;
            }
        }

        // Check middle column of the remaining two rows
        if (matrix[startRow][midCol] == target) {
            return new int[] {startRow, midCol};
        }
        if (matrix[endRow][midCol] == target) {
            return new int[] {endRow, midCol};
        }

        // Search in the four possible quadrants:

        // Top-left: startRow, columns [0 .. midCol - 1]
        if (target <= matrix[startRow][midCol - 1]) {
            return binarySearchInRow(matrix, target, startRow, 0, midCol - 1);
        }

        // Top-right: startRow, columns [midCol + 1 .. colCount - 1]
        if (target >= matrix[startRow][midCol + 1] && target <= matrix[startRow][colCount - 1]) {
            return binarySearchInRow(matrix, target, startRow, midCol + 1, colCount - 1);
        }

        // Bottom-left: endRow, columns [0 .. midCol - 1]
        if (target <= matrix[endRow][midCol - 1]) {
            return binarySearchInRow(matrix, target, endRow, 0, midCol - 1);
        }

        // Bottom-right: endRow, columns [midCol + 1 .. colCount - 1]
        return binarySearchInRow(matrix, target, endRow, midCol + 1, colCount - 1);
    }

    /**
     * Performs binary search on a single row of the matrix.
     *
     * @param matrix   the 2D matrix
     * @param target   the value to search for
     * @param row      the row index to search in
     * @param colStart starting column index (inclusive)
     * @param colEnd   ending column index (inclusive)
     * @return an array of size 2: [rowIndex, colIndex], or [-1, -1] if not found
     */
    static int[] binarySearchInRow(int[][] matrix, int target, int row, int colStart, int colEnd) {
        while (colStart <= colEnd) {
            int midCol = colStart + (colEnd - colStart) / 2;
            int midValue = matrix[row][midCol];

            if (midValue == target) {
                return new int[] {row, midCol};
            } else if (midValue < target) {
                colStart = midCol + 1;
            } else {
                colEnd = midCol - 1;
            }
        }

        return new int[] {-1, -1};
    }
}