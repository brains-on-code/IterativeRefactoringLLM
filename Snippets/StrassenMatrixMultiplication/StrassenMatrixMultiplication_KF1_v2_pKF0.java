package com.thealgorithms.divideandconquer;

public class Class1 {

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

        split(matrixA, a11, 0, 0);
        split(matrixA, a12, 0, halfSize);
        split(matrixA, a21, halfSize, 0);
        split(matrixA, a22, halfSize, halfSize);

        split(matrixB, b11, 0, 0);
        split(matrixB, b12, 0, halfSize);
        split(matrixB, b21, halfSize, 0);
        split(matrixB, b22, halfSize, halfSize);

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

    public void split(int[][] parent, int[][] child, int rowOffset, int colOffset) {
        int childSize = child.length;
        for (int row = 0; row < childSize; row++) {
            int parentRow = row + rowOffset;
            for (int col = 0; col < childSize; col++) {
                int parentCol = col + colOffset;
                child[row][col] = parent[parentRow][parentCol];
            }
        }
    }

    public void join(int[][] child, int[][] parent, int rowOffset, int colOffset) {
        int childSize = child.length;
        for (int row = 0; row < childSize; row++) {
            int parentRow = row + rowOffset;
            for (int col = 0; col < childSize; col++) {
                int parentCol = col + colOffset;
                parent[parentRow][parentCol] = child[row][col];
            }
        }
    }
}