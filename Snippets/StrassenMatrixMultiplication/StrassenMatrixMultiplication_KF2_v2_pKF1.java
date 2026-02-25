package com.thealgorithms.divideandconquer;

public class StrassenMatrixMultiplication {

    public int[][] multiply(int[][] leftMatrix, int[][] rightMatrix) {
        int size = leftMatrix.length;
        int[][] productMatrix = new int[size][size];

        if (size == 1) {
            productMatrix[0][0] = leftMatrix[0][0] * rightMatrix[0][0];
        } else {
            int halfSize = size / 2;

            int[][] aTopLeft = new int[halfSize][halfSize];
            int[][] aTopRight = new int[halfSize][halfSize];
            int[][] aBottomLeft = new int[halfSize][halfSize];
            int[][] aBottomRight = new int[halfSize][halfSize];

            int[][] bTopLeft = new int[halfSize][halfSize];
            int[][] bTopRight = new int[halfSize][halfSize];
            int[][] bBottomLeft = new int[halfSize][halfSize];
            int[][] bBottomRight = new int[halfSize][halfSize];

            split(leftMatrix, aTopLeft, 0, 0);
            split(leftMatrix, aTopRight, 0, halfSize);
            split(leftMatrix, aBottomLeft, halfSize, 0);
            split(leftMatrix, aBottomRight, halfSize, halfSize);

            split(rightMatrix, bTopLeft, 0, 0);
            split(rightMatrix, bTopRight, 0, halfSize);
            split(rightMatrix, bBottomLeft, halfSize, 0);
            split(rightMatrix, bBottomRight, halfSize, halfSize);

            int[][] p1 = multiply(add(aTopLeft, aBottomRight), add(bTopLeft, bBottomRight));
            int[][] p2 = multiply(add(aBottomLeft, aBottomRight), bTopLeft);
            int[][] p3 = multiply(aTopLeft, subtract(bTopRight, bBottomRight));
            int[][] p4 = multiply(aBottomRight, subtract(bBottomLeft, bTopLeft));
            int[][] p5 = multiply(add(aTopLeft, aTopRight), bBottomRight);
            int[][] p6 = multiply(subtract(aBottomLeft, aTopLeft), add(bTopLeft, bTopRight));
            int[][] p7 = multiply(subtract(aTopRight, aBottomRight), add(bBottomLeft, bBottomRight));

            int[][] cTopLeft = add(subtract(add(p1, p4), p5), p7);
            int[][] cTopRight = add(p3, p5);
            int[][] cBottomLeft = add(p2, p4);
            int[][] cBottomRight = add(subtract(add(p1, p3), p2), p6);

            join(cTopLeft, productMatrix, 0, 0);
            join(cTopRight, productMatrix, 0, halfSize);
            join(cBottomLeft, productMatrix, halfSize, 0);
            join(cBottomRight, productMatrix, halfSize, halfSize);
        }

        return productMatrix;
    }

    public int[][] subtract(int[][] leftMatrix, int[][] rightMatrix) {
        int size = leftMatrix.length;
        int[][] resultMatrix = new int[size][size];

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex < size; columnIndex++) {
                resultMatrix[rowIndex][columnIndex] =
                    leftMatrix[rowIndex][columnIndex] - rightMatrix[rowIndex][columnIndex];
            }
        }

        return resultMatrix;
    }

    public int[][] add(int[][] leftMatrix, int[][] rightMatrix) {
        int size = leftMatrix.length;
        int[][] resultMatrix = new int[size][size];

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex < size; columnIndex++) {
                resultMatrix[rowIndex][columnIndex] =
                    leftMatrix[rowIndex][columnIndex] + rightMatrix[rowIndex][columnIndex];
            }
        }

        return resultMatrix;
    }

    public void split(int[][] sourceMatrix, int[][] targetSubmatrix, int rowOffset, int columnOffset) {
        for (int subRow = 0, sourceRow = rowOffset; subRow < targetSubmatrix.length; subRow++, sourceRow++) {
            for (int subColumn = 0, sourceColumn = columnOffset;
                 subColumn < targetSubmatrix.length;
                 subColumn++, sourceColumn++) {
                targetSubmatrix[subRow][subColumn] = sourceMatrix[sourceRow][sourceColumn];
            }
        }
    }

    public void join(int[][] sourceSubmatrix, int[][] targetMatrix, int rowOffset, int columnOffset) {
        for (int subRow = 0, targetRow = rowOffset; subRow < sourceSubmatrix.length; subRow++, targetRow++) {
            for (int subColumn = 0, targetColumn = columnOffset;
                 subColumn < sourceSubmatrix.length;
                 subColumn++, targetColumn++) {
                targetMatrix[targetRow][targetColumn] = sourceSubmatrix[subRow][subColumn];
            }
        }
    }
}