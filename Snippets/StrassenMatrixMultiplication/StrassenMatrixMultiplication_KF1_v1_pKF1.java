package com.thealgorithms.divideandconquer;

public class Class1 {

    public int[][] method1(int[][] matrixA, int[][] matrixB) {
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

            method4(matrixA, a11, 0, 0);
            method4(matrixA, a12, 0, newSize);
            method4(matrixA, a21, newSize, 0);
            method4(matrixA, a22, newSize, newSize);

            method4(matrixB, b11, 0, 0);
            method4(matrixB, b12, 0, newSize);
            method4(matrixB, b21, newSize, 0);
            method4(matrixB, b22, newSize, newSize);

            int[][] p1 = method1(method3(a11, a22), method3(b11, b22));
            int[][] p2 = method1(method3(a21, a22), b11);
            int[][] p3 = method1(a11, method2(b12, b22));
            int[][] p4 = method1(a22, method2(b21, b11));
            int[][] p5 = method1(method3(a11, a12), b22);
            int[][] p6 = method1(method2(a21, a11), method3(b11, b12));
            int[][] p7 = method1(method2(a12, a22), method3(b21, b22));

            int[][] c11 = method3(method2(method3(p1, p4), p7), p5);
            int[][] c12 = method3(p3, p5);
            int[][] c21 = method3(p2, p4);
            int[][] c22 = method3(method2(method3(p1, p3), p2), p6);

            method5(c11, result, 0, 0);
            method5(c12, result, 0, newSize);
            method5(c21, result, newSize, 0);
            method5(c22, result, newSize, newSize);
        }

        return result;
    }

    public int[][] method2(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;

        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                result[row][col] = matrixA[row][col] - matrixB[row][col];
            }
        }

        return result;
    }

    public int[][] method3(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;

        int[][] result = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                result[row][col] = matrixA[row][col] + matrixB[row][col];
            }
        }

        return result;
    }

    public void method4(int[][] source, int[][] destination, int rowOffset, int colOffset) {
        for (int destRow = 0, srcRow = rowOffset; destRow < destination.length; destRow++, srcRow++) {
            for (int destCol = 0, srcCol = colOffset; destCol < destination.length; destCol++, srcCol++) {
                destination[destRow][destCol] = source[srcRow][srcCol];
            }
        }
    }

    public void method5(int[][] source, int[][] destination, int rowOffset, int colOffset) {
        for (int srcRow = 0, destRow = rowOffset; srcRow < source.length; srcRow++, destRow++) {
            for (int srcCol = 0, destCol = colOffset; srcCol < source.length; srcCol++, destCol++) {
                destination[destRow][destCol] = source[srcRow][srcCol];
            }
        }
    }
}