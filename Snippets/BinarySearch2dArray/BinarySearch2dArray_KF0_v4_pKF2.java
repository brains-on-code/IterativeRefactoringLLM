package com.thealgorithms.searches;

/**
 * Utility class for searching a target value in a 2D array that is sorted in
 * ascending order both row-wise and column-wise.
 *
 * Algorithm overview:
 * 1. Use binary search on the middle column to narrow down to two candidate rows.
 * 2. Perform binary search on the appropriate row segments.
 */
public final class BinarySearch2dArray {

    private BinarySearch2dArray() {
        // Utility class; prevent instantiation
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

        // If there is only one row, perform a standard binary search on that row
        if (rowCount == 1) {
            return binarySearch(arr, target, 0, 0, colCount - 1);
        }

        int startRow = 0;
        int endRow = rowCount - 1;
        int midCol = colCount / 2;

        // Use the middle column to narrow down to two candidate rows
        while (startRow < endRow - 1) {
            int midRow = startRow + (endRow - startRow) / 2;
            int midValue = arr[midRow][midCol];

            if (midValue == target) {
                return new int[] {midRow, midCol};
            } else if (midValue < target) {
                startRow = midRow;
            } else {
                endRow = midRow;
            }
        }

        // Check the middle column in the two remaining candidate rows
        if (arr[startRow][midCol] == target) {
            return new int[] {startRow, midCol};
        }
        if (arr[endRow][midCol] == target) {
            return new int[] {endRow, midCol};
        }

        // At this point, the target (if present) must lie in one of four segments:
        // 1) Left segment of startRow
        // 2) Right segment of startRow
        // 3) Left segment of endRow
        // 4) Right segment of endRow

        // 1) Left segment of startRow
        if (midCol - 1 >= 0 && target <= arr[startRow][midCol - 1]) {
            return binarySearch(arr, target, startRow, 0, midCol - 1);
        }

        // 2) Right segment of startRow
        if (midCol + 1 < colCount
                && target >= arr[startRow][midCol + 1]
                && target <= arr[startRow][colCount - 1]) {
            return binarySearch(arr, target, startRow, midCol + 1, colCount - 1);
        }

        // 3) Left segment of endRow
        if (midCol - 1 >= 0 && target <= arr[endRow][midCol - 1]) {
            return binarySearch(arr, target, endRow, 0, midCol - 1);
        }

        // 4) Right segment of endRow
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
            int midValue = arr[row][midIndex];

            if (midValue == target) {
                return new int[] {row, midIndex};
            } else if (midValue < target) {
                colStart = midIndex + 1;
            } else {
                colEnd = midIndex - 1;
            }
        }

        return new int[] {-1, -1};
    }
}