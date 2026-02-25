package com.thealgorithms.searches;

/**
 * Utility class for searching a target value in a 2D array that is sorted in
 * ascending order both row-wise and column-wise.
 *
 * The algorithm:
 * 1. Uses binary search on the middle column to narrow down candidate rows.
 * 2. Then performs binary search on the appropriate row segments.
 */
public final class BinarySearch2dArray {

    private BinarySearch2dArray() {
        // Prevent instantiation
    }

    /**
     * Searches for a target value in a 2D sorted array.
     *
     * @param arr    the 2D array to search in (must be non-empty and sorted
     *               in ascending order in both rows and columns)
     * @param target the value to search for
     * @return an array {rowIndex, colIndex} of the target, or {-1, -1} if not found
     */
    static int[] binarySearch(int[][] arr, int target) {
        int rowCount = arr.length;
        int colCount = arr[0].length;

        // Single-row case: search that row directly
        if (rowCount == 1) {
            return binarySearch(arr, target, 0, 0, colCount - 1);
        }

        int startRow = 0;
        int endRow = rowCount - 1;
        int midCol = colCount / 2;

        // Narrow down to two candidate rows using the middle column
        while (startRow < endRow - 1) {
            int midRow = startRow + (endRow - startRow) / 2;

            if (arr[midRow][midCol] == target) {
                return new int[] {midRow, midCol};
            } else if (arr[midRow][midCol] < target) {
                startRow = midRow;
            } else {
                endRow = midRow;
            }
        }

        // Check middle column in the two remaining rows
        if (arr[startRow][midCol] == target) {
            return new int[] {startRow, midCol};
        }
        if (arr[endRow][midCol] == target) {
            return new int[] {endRow, midCol};
        }

        // Search in 4 possible segments:
        // 1. Left part of startRow
        if (target <= arr[startRow][midCol - 1]) {
            return binarySearch(arr, target, startRow, 0, midCol - 1);
        }

        // 2. Right part of startRow
        if (target >= arr[startRow][midCol + 1] && target <= arr[startRow][colCount - 1]) {
            return binarySearch(arr, target, startRow, midCol + 1, colCount - 1);
        }

        // 3. Left part of endRow
        if (target <= arr[endRow][midCol - 1]) {
            return binarySearch(arr, target, endRow, 0, midCol - 1);
        }

        // 4. Right part of endRow
        return binarySearch(arr, target, endRow, midCol + 1, colCount - 1);
    }

    /**
     * Binary search within a single row between the given column indices.
     *
     * @param arr      the 2D array
     * @param target   the value to search for
     * @param row      the row index to search in
     * @param colStart starting column index (inclusive)
     * @param colEnd   ending column index (inclusive)
     * @return an array {rowIndex, colIndex} of the target, or {-1, -1} if not found
     */
    static int[] binarySearch(int[][] arr, int target, int row, int colStart, int colEnd) {
        while (colStart <= colEnd) {
            int midIndex = colStart + (colEnd - colStart) / 2;

            if (arr[row][midIndex] == target) {
                return new int[] {row, midIndex};
            } else if (arr[row][midIndex] < target) {
                colStart = midIndex + 1;
            } else {
                colEnd = midIndex - 1;
            }
        }

        return new int[] {-1, -1};
    }
}