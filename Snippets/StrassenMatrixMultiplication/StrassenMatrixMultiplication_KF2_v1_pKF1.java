package com.thealgorithms.divideandconquer;

public class StrassenMatrixMultiplication {

    public int[][] multiply(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] result = new int[size][size];

        if (size == 1) {
            result[0][0] = matrixA[0][0] * matrixB[0][0];
        } else {
            int newSize = size / 2;

            int[][] a11 = new int[newSize][newSize];
            int[][] a12 = new int[newSize][newSize];
            int[][] a21 = new int[newSize][newSize];
            int[][] a22 = new int[newSize][newSize];

            int[][] b11 = new int[newSize][newSize];
            int[][] b12 = new int[newSize][newSize];
            int[][] b21 = new int[newSize][newSize];
            int[][] b22 = new int[newSize][newSize];

            split(matrixA, a11, 0, 0);
            split(matrixA, a12, 0, newSize);
            split(matrixA, a21, newSize, 0);
            split(matrixA, a22, newSize, newSize);

            split(matrixB, b11, 0, 0);
            split(matrixB, b12, 0, newSize);
            split(matrixB, b21, newSize, 0);
            split(matrixB, b22, newSize, newSize);

            int[][] m1 = multiply(add(a11, a22), add(b11, b22));
            int[][] m2 = multiply(add(a21, a22), b11);
            int[][] m3 = multiply(a11, subtract(b12, b22));
            int[][] m4 = multiply(a22, subtract(b21, b11));
            int[][] m5 = multiply(add(a11, a12), b22);
            int[][] m6 = multiply(subtract(a21, a11), add(b11, b12));
            int[][] m7 = multiply(subtract(a12, a22), add(b21, b22));

            int[][] c11 = add(subtract(add(m1, m4), m5), m7);
            int[][] c12 = add(m3, m5);
            int[][] c21 = add(m2, m4);
            int[][] c22 = add(subtract(add(m1, m3), m2), m6);

            join(c11, result, 0, 0);
            join(c12, result, 0, newSize);
            join(c21, result, newSize, 0);
            join(c22, result, newSize, newSize);
        }

        return result;
    }

    public int[][] subtract(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                result[row][column] = matrixA[row][column] - matrixB[row][column];
            }
        }

        return result;
    }

    public int[][] add(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                result[row][column] = matrixA[row][column] + matrixB[row][column];
            }
        }

        return result;
    }

    public void split(int[][] parent, int[][] child, int rowOffset, int columnOffset) {
        for (int childRow = 0, parentRow = rowOffset; childRow < child.length; childRow++, parentRow++) {
            for (int childColumn = 0, parentColumn = columnOffset; childColumn < child.length; childColumn++, parentColumn++) {
                child[childRow][childColumn] = parent[parentRow][parentColumn];
            }
        }
    }

    public void join(int[][] child, int[][] parent, int rowOffset, int columnOffset) {
        for (int childRow = 0, parentRow = rowOffset; childRow < child.length; childRow++, parentRow++) {
            for (int childColumn = 0, parentColumn = columnOffset; childColumn < child.length; childColumn++, parentColumn++) {
                parent[parentRow][parentColumn] = child[childRow][childColumn];
            }
        }
    }
}