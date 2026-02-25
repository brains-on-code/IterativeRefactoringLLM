package com.thealgorithms.divideandconquer;

public class StrassenMatrixMultiplier {

    public int[][] multiply(int[][] leftMatrix, int[][] rightMatrix) {
        int matrixSize = leftMatrix.length;
        int[][] productMatrix = new int[matrixSize][matrixSize];

        if (matrixSize == 1) {
            productMatrix[0][0] = leftMatrix[0][0] * rightMatrix[0][0];
            return productMatrix;
        }

        int halfSize = matrixSize / 2;

        int[][] leftTopLeft = new int[halfSize][halfSize];
        int[][] leftTopRight = new int[halfSize][halfSize];
        int[][] leftBottomLeft = new int[halfSize][halfSize];
        int[][] leftBottomRight = new int[halfSize][halfSize];

        int[][] rightTopLeft = new int[halfSize][halfSize];
        int[][] rightTopRight = new int[halfSize][halfSize];
        int[][] rightBottomLeft = new int[halfSize][halfSize];
        int[][] rightBottomRight = new int[halfSize][halfSize];

        copySubMatrix(leftMatrix, leftTopLeft, 0, 0);
        copySubMatrix(leftMatrix, leftTopRight, 0, halfSize);
        copySubMatrix(leftMatrix, leftBottomLeft, halfSize, 0);
        copySubMatrix(leftMatrix, leftBottomRight, halfSize, halfSize);

        copySubMatrix(rightMatrix, rightTopLeft, 0, 0);
        copySubMatrix(rightMatrix, rightTopRight, 0, halfSize);
        copySubMatrix(rightMatrix, rightBottomLeft, halfSize, 0);
        copySubMatrix(rightMatrix, rightBottomRight, halfSize, halfSize);

        int[][] p1 = multiply(add(leftTopLeft, leftBottomRight), add(rightTopLeft, rightBottomRight));
        int[][] p2 = multiply(add(leftBottomLeft, leftBottomRight), rightTopLeft);
        int[][] p3 = multiply(leftTopLeft, subtract(rightTopRight, rightBottomRight));
        int[][] p4 = multiply(leftBottomRight, subtract(rightBottomLeft, rightTopLeft));
        int[][] p5 = multiply(add(leftTopLeft, leftTopRight), rightBottomRight);
        int[][] p6 = multiply(subtract(leftBottomLeft, leftTopLeft), add(rightTopLeft, rightTopRight));
        int[][] p7 = multiply(subtract(leftTopRight, leftBottomRight), add(rightBottomLeft, rightBottomRight));

        int[][] resultTopLeft = add(subtract(add(p1, p4), p7), p5);
        int[][] resultTopRight = add(p3, p5);
        int[][] resultBottomLeft = add(p2, p4);
        int[][] resultBottomRight = add(subtract(add(p1, p3), p2), p6);

        pasteSubMatrix(resultTopLeft, productMatrix, 0, 0);
        pasteSubMatrix(resultTopRight, productMatrix, 0, halfSize);
        pasteSubMatrix(resultBottomLeft, productMatrix, halfSize, 0);
        pasteSubMatrix(resultBottomRight, productMatrix, halfSize, halfSize);

        return productMatrix;
    }

    public int[][] subtract(int[][] minuendMatrix, int[][] subtrahendMatrix) {
        int matrixSize = minuendMatrix.length;
        int[][] differenceMatrix = new int[matrixSize][matrixSize];

        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                differenceMatrix[rowIndex][columnIndex] =
                    minuendMatrix[rowIndex][columnIndex] - subtrahendMatrix[rowIndex][columnIndex];
            }
        }

        return differenceMatrix;
    }

    public int[][] add(int[][] firstMatrix, int[][] secondMatrix) {
        int matrixSize = firstMatrix.length;
        int[][] sumMatrix = new int[matrixSize][matrixSize];

        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                sumMatrix[rowIndex][columnIndex] =
                    firstMatrix[rowIndex][columnIndex] + secondMatrix[rowIndex][columnIndex];
            }
        }

        return sumMatrix;
    }

    public void copySubMatrix(int[][] sourceMatrix, int[][] targetSubMatrix, int rowOffset, int columnOffset) {
        for (int targetRow = 0, sourceRow = rowOffset;
             targetRow < targetSubMatrix.length;
             targetRow++, sourceRow++) {

            for (int targetColumn = 0, sourceColumn = columnOffset;
                 targetColumn < targetSubMatrix.length;
                 targetColumn++, sourceColumn++) {

                targetSubMatrix[targetRow][targetColumn] = sourceMatrix[sourceRow][sourceColumn];
            }
        }
    }

    public void pasteSubMatrix(int[][] sourceSubMatrix, int[][] targetMatrix, int rowOffset, int columnOffset) {
        for (int sourceRow = 0, targetRow = rowOffset;
             sourceRow < sourceSubMatrix.length;
             sourceRow++, targetRow++) {

            for (int sourceColumn = 0, targetColumn = columnOffset;
                 sourceColumn < sourceSubMatrix.length;
                 sourceColumn++, targetColumn++) {

                targetMatrix[targetRow][targetColumn] = sourceSubMatrix[sourceRow][sourceColumn];
            }
        }
    }
}