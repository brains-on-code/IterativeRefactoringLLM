package com.thealgorithms.searches;


public final class BinarySearch2dArray {

    private BinarySearch2dArray() {
    }


    static int[] binarySearch(int[][] arr, int target) {
        int rowCount = arr.length;
        int colCount = arr[0].length;

        if (rowCount == 1) {
            return binarySearch(arr, target, 0, 0, colCount);
        }

        int startRow = 0;
        int endRow = rowCount - 1;
        int midCol = colCount / 2;

        while (startRow < endRow - 1) {
            int midRow = startRow + (endRow - startRow) / 2;

            if (arr[midRow][midCol] == target) {
                return new int[] {midRow, midCol};
            }
            else if (arr[midRow][midCol] < target) {
                startRow = midRow;
            }
            else {
                endRow = midRow;
            }
        }

        if (arr[startRow][midCol] == target) {
            return new int[] {startRow, midCol};
        }

        if (arr[endRow][midCol] == target) {
            return new int[] {endRow, midCol};
        }

        if (target <= arr[startRow][midCol - 1]) {
            return binarySearch(arr, target, startRow, 0, midCol - 1);
        }

        if (target >= arr[startRow][midCol + 1] && target <= arr[startRow][colCount - 1]) {
            return binarySearch(arr, target, startRow, midCol + 1, colCount - 1);
        }

        if (target <= arr[endRow][midCol - 1]) {
            return binarySearch(arr, target, endRow, 0, midCol - 1);
        } else {
            return binarySearch(arr, target, endRow, midCol + 1, colCount - 1);
        }
    }


    static int[] binarySearch(int[][] arr, int target, int row, int colStart, int colEnd) {
        while (colStart <= colEnd) {
            int midIndex = colStart + (colEnd - colStart) / 2;

            if (arr[row][midIndex] == target) {
                return new int[] {row, midIndex};
            }
            else if (arr[row][midIndex] < target) {
                colStart = midIndex + 1;
            }
            else {
                colEnd = midIndex - 1;
            }
        }

        return new int[] {-1, -1};
    }
}
