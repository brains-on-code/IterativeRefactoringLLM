package com.thealgorithms.divideandconquer;

public class StrassenMatrixMultiplier {

    public int[][] multiply(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] result = new int[size][size];

        if (size == 1) {
            result[0][0] = matrixA[0][0] * matrixB[0][0];
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

        copySubMatrix(matrixA, a11, 0, 0);
        copySubMatrix(matrixA, a12, 0, halfSize);
        copySubMatrix(matrixA, a21, halfSize, 0);
        copySubMatrix(matrixA, a22, halfSize, halfSize);

        copySubMatrix(matrixB, b11, 0, 0);
        copySubMatrix(matrixB, b12, 0, halfSize);
        copySubMatrix(matrixB, b21, halfSize, 0);
        copySubMatrix(matrixB, b22, halfSize, halfSize);

        int[][] p1 = multiply(add(a11, a22), add(b11, b22));
        int[][] p2 = multiply(add(a21, a22), b11);
        int[][] p3 = multiply(a11, subtract(b12, b22));
        int[][] p4 = multiply(a22, subtract(b21, b11));
        int[][] p5 = multiply(add(a11, a12), b22);
        int[][] p6 = multiply(subtract(a21, a11), add(b11, b12));
        int[][] p7 = multiply(subtract(a12, a22), add(b21, b22));

        int[][] c11 = add(subtract(add(p1, p4), p7), p5);
        int[][] c12 = add(p3, p5);
        int[][] c21 = add(p2, p4);
        int[][] c22 = add(subtract(add(p1, p3), p2), p6);

        pasteSubMatrix(c11, result, 0, 0);
        pasteSubMatrix(c12, result, 0, halfSize);
        pasteSubMatrix(c21, result, halfSize, 0);
        pasteSubMatrix(c22, result, halfSize, halfSize);

        return result;
    }

    public int[][] subtract(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                result[row][col] = matrixA[row][col] - matrixB[row][col];
            }
        }

        return result;
    }

    public int[][] add(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                result[row][col] = matrixA[row][col] + matrixB[row][col];
            }
        }

        return result;
    }

    public void copySubMatrix(int[][] source, int[][] destination, int rowOffset, int colOffset) {
        for (int destRow = 0, srcRow = rowOffset; destRow < destination.length; destRow++, srcRow++) {
            for (int destCol = 0, srcCol = colOffset; destCol < destination.length; destCol++, srcCol++) {
                destination[destRow][destCol] = source[srcRow][srcCol];
            }
        }
    }

    public void pasteSubMatrix(int[][] source, int[][] destination, int rowOffset, int colOffset) {
        for (int srcRow = 0, destRow = rowOffset; srcRow < source.length; srcRow++, destRow++) {
            for (int srcCol = 0, destCol = colOffset; srcCol < source.length; srcCol++, destCol++) {
                destination[destRow][destCol] = source[srcRow][srcCol];
            }
        }
    }
}