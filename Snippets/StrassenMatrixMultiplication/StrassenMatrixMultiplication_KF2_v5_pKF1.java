package com.thealgorithms.divideandconquer;

public class StrassenMatrixMultiplication {

    public int[][] multiply(int[][] leftMatrix, int[][] rightMatrix) {
        int size = leftMatrix.length;
        int[][] result = new int[size][size];

        if (size == 1) {
            result[0][0] = leftMatrix[0][0] * rightMatrix[0][0];
            return result;
        }

        int halfSize = size / 2;

        int[][] a11 = new int[halfSize][halfSize];
        int[][] a12 = new int[halfSize][halfSize];
        int[][] a21 = new int[halfSize][halfSize];
        int[][] a22 = new int[halfSize][halfSize];

        int[][] b11 = new int[halfSize][halfSize];
        int[][] b12 = new int[halfSize][halfSize];
        int[][] b21 = new int[halfSize][halfSize];
        int[][] b22 = new int[halfSize][halfSize];

        split(leftMatrix, a11, 0, 0);
        split(leftMatrix, a12, 0, halfSize);
        split(leftMatrix, a21, halfSize, 0);
        split(leftMatrix, a22, halfSize, halfSize);

        split(rightMatrix, b11, 0, 0);
        split(rightMatrix, b12, 0, halfSize);
        split(rightMatrix, b21, halfSize, 0);
        split(rightMatrix, b22, halfSize, halfSize);

        int[][] p1 = multiply(add(a11, a22), add(b11, b22));
        int[][] p2 = multiply(add(a21, a22), b11);
        int[][] p3 = multiply(a11, subtract(b12, b22));
        int[][] p4 = multiply(a22, subtract(b21, b11));
        int[][] p5 = multiply(add(a11, a12), b22);
        int[][] p6 = multiply(subtract(a21, a11), add(b11, b12));
        int[][] p7 = multiply(subtract(a12, a22), add(b21, b22));

        int[][] c11 = add(subtract(add(p1, p4), p5), p7);
        int[][] c12 = add(p3, p5);
        int[][] c21 = add(p2, p4);
        int[][] c22 = add(subtract(add(p1, p3), p2), p6);

        join(c11, result, 0, 0);
        join(c12, result, 0, halfSize);
        join(c21, result, halfSize, 0);
        join(c22, result, halfSize, halfSize);

        return result;
    }

    public int[][] subtract(int[][] leftMatrix, int[][] rightMatrix) {
        int size = leftMatrix.length;
        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                result[row][column] = leftMatrix[row][column] - rightMatrix[row][column];
            }
        }

        return result;
    }

    public int[][] add(int[][] leftMatrix, int[][] rightMatrix) {
        int size = leftMatrix.length;
        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                result[row][column] = leftMatrix[row][column] + rightMatrix[row][column];
            }
        }

        return result;
    }

    public void split(int[][] source, int[][] destination, int rowOffset, int columnOffset) {
        for (int destRow = 0, srcRow = rowOffset;
             destRow < destination.length;
             destRow++, srcRow++) {
            for (int destCol = 0, srcCol = columnOffset;
                 destCol < destination.length;
                 destCol++, srcCol++) {
                destination[destRow][destCol] = source[srcRow][srcCol];
            }
        }
    }

    public void join(int[][] source, int[][] destination, int rowOffset, int columnOffset) {
        for (int srcRow = 0, destRow = rowOffset;
             srcRow < source.length;
             srcRow++, destRow++) {
            for (int srcCol = 0, destCol = columnOffset;
                 srcCol < source.length;
                 srcCol++, destCol++) {
                destination[destRow][destCol] = source[srcRow][srcCol];
            }
        }
    }
}