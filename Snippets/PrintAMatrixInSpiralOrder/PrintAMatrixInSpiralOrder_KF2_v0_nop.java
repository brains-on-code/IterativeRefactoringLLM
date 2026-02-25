package com.thealgorithms.matrix;

import java.util.ArrayList;
import java.util.List;

public class PrintAMatrixInSpiralOrder {


    public List<Integer> print(int[][] matrix, int row, int col) {

        int r = 0;
        int c = 0;
        int i;

        List<Integer> result = new ArrayList<>();

        while (r < row && c < col) {
            for (i = c; i < col; i++) {
                result.add(matrix[r][i]);
            }

            r++;

            for (i = r; i < row; i++) {
                result.add(matrix[i][col - 1]);
            }

            col--;

            if (r < row) {
                for (i = col - 1; i >= c; i--) {
                    result.add(matrix[row - 1][i]);
                }

                row--;
            }

            if (c < col) {
                for (i = row - 1; i >= r; i--) {
                    result.add(matrix[i][c]);
                }
                c++;
            }
        }
        return result;
    }
}
