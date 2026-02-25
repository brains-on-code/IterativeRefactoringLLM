package com.thealgorithms.searches;

/**
 * This class provides a method to search for a target value in a 2D sorted
 * array using binary search on rows and columns.
 *
 * The 2D array must be strictly sorted in both rows and columns.
 */
public final class BinarySearch2dArray {

    private static final int[] NOT_FOUND = {-1, -1};

    private BinarySearch2dArray() {
        // Utility class; prevent instantiation.
    }

    /**
     * Performs a binary search on a 2D sorted array to find the target value.
     * The array must be sorted in ascending order in both rows and columns.
     *
     * @param arr    The 2D array to search in.
     * @param target The value to search for.
     * @return An array containing the row and column indices of the target,
     *         or [-1, -1] if the target is not found.
     */
    static int[] binarySearch(int[][] arr, int target) {
        if (isInvalidArray(arr)) {
            return NOT_FOUND;
        }

        int rowCount = arr.length;
        int colCount = arr[0].length;

        if (rowCount == 1) {
            return binarySearchRow(arr, target, 0, 0, colCount - 1);
        }

        int midCol = colCount / 2;
        int startRow = 0;
        int endRow = rowCount - 1;

        narrowToTwoRows(arr, target, midCol, startRow, endRow);

        // After narrowing, startRow and endRow are adjacent or equal
        // Check middle column in the two remaining rows.
        if (arr[startRow][midCol] == target) {
            return new int[] {startRow, midCol};
        }
        if (arr[endRow][midCol] == target) {
            return new int[] {endRow, midCol};
        }

        // Search in 4 possible quadrants.
        int[] result;

        // 1. Left part of startRow.
        if (midCol > 0 && target <= arr[startRow][midCol - 1]) {
            result = binarySearchRow(arr, target, startRow, 0, midCol - 1);
            if (result != NOT_FOUND) {
                return result;
            }
        }

        // 2. Right part of startRow.
        if (midCol + 1 < colCount
                && target >= arr[startRow][midCol + 1]
                && target <= arr[startRow][colCount - 1]) {
            result = binarySearchRow(arr, target, startRow, midCol + 1, colCount - 1);
            if (result != NOT_FOUND) {
                return result;
            }
        }

        // 3. Left part of endRow.
        if (midCol > 0 && target <= arr[endRow][midCol - 1]) {
            result = binarySearchRow(arr, target, endRow, 0, midCol - 1);
            if (result != NOT_FOUND) {
                return result;
            }
        }

        // 4. Right part of endRow.
        if (midCol + 1 < colCount) {
            return binarySearchRow(arr, target, endRow, midCol + 1, colCount - 1);
        }

        return NOT_FOUND;
    }

    private static void narrowToTwoRows(int[][] arr, int target, int midCol, int startRow, int endRow) {
        while (startRow < endRow - 1) {
            int midRow = startRow + (endRow - startRow) / 2;
            int midValue = arr[midRow][midCol];

            if (midValue == target) {
                // Found target; early exit by throwing to be caught by caller is not ideal,
                // but we keep logic simple and let caller handle direct checks.
                break;
            }

            if (midValue < target) {
                startRow = midRow;
            } else {
                endRow = midRow;
            }
        }
    }

    private static boolean isInvalidArray(int[][] arr) {
        return arr == null || arr.length == 0 || arr[0].length == 0;
    }

    /**
     * Performs a binary search on a specific row of the 2D array.
     *
     * @param arr      The 2D array to search in.
     * @param target   The value to search for.
     * @param row      The row index where the target will be searched.
     * @param colStart The starting column index for the search.
     * @param colEnd   The ending column index for the search.
     * @return An array containing the row and column indices of the target,
     *         or [-1, -1] if the target is not found.
     */
    private static int[] binarySearchRow(int[][] arr, int target, int row, int colStart, int colEnd) {
        int left = colStart;
        int right = colEnd;

        while (left <= right) {
            int midCol = left + (right - left) / 2;
            int midValue = arr[row][midCol];

            if (midValue == target) {
                return new int[] {row, midCol};
            }

            if (midValue < target) {
                left = midCol + 1;
            } else {
                right = midCol - 1;
            }
        }

        return NOT_FOUND;
    }
}