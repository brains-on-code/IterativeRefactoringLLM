package com.thealgorithms.divideandconquer;

public class StrassenMatrixMultiplier {

    public int[][] multiply(int[][] leftMatrix, int[][] rightMatrix) {
        int matrixSize = leftMatrix.length;
        int[][] productMatrix = new int[matrixSize][matrixSize];

        if (matrixSize == 1) {
            productMatrix[0][0] = leftMatrix[0][0] * rightMatrix[0][0];
            return productMatrix;
        }

        int subMatrixSize = matrixSize / 2;

        int[][] leftTop = new int[subMatrixSize][subMatrixSize];
        int[][] rightTop = new int[subMatrixSize][subMatrixSize];
        int[][] leftBottom = new int[subMatrixSize][subMatrixSize];
        int[][] rightBottom = new int[subMatrixSize][subMatrixSize];

        int[][] leftTopRight = new int[subMatrixSize][subMatrixSize];
        int[][] rightTopRight = new int[subMatrixSize][subMatrixSize];
        int[][] leftBottomRight = new int[subMatrixSize][subMatrixSize];
        int[][] rightBottomRight = new int[subMatrixSize][subMatrixSize];

        copySubMatrix(leftMatrix, leftTop, 0, 0);
        copySubMatrix(leftMatrix, leftTopRight, 0, subMatrixSize);
        copySubMatrix(leftMatrix, leftBottom, subMatrixSize, 0);
        copySubMatrix(leftMatrix, leftBottomRight, subMatrixSize, subMatrixSize);

        copySubMatrix(rightMatrix, rightTop, 0, 0);
        copySubMatrix(rightMatrix, rightTopRight, 0, subMatrixSize);
        copySubMatrix(rightMatrix, rightBottom, subMatrixSize, 0);
        copySubMatrix(rightMatrix, rightBottomRight, subMatrixSize, subMatrixSize);

        int[][] p1 = multiply(add(leftTop, leftBottomRight), add(rightTop, rightBottomRight));
        int[][] p2 = multiply(add(leftBottom, leftBottomRight), rightTop);
        int[][] p3 = multiply(leftTop, subtract(rightTopRight, rightBottomRight));
        int[][] p4 = multiply(leftBottomRight, subtract(rightBottom, rightTop));
        int[][] p5 = multiply(add(leftTop, leftTopRight), rightBottomRight);
        int[][] p6 = multiply(subtract(leftBottom, leftTop), add(rightTop, rightTopRight));
        int[][] p7 = multiply(subtract(leftTopRight, leftBottomRight), add(rightBottom, rightBottomRight));

        int[][] resultTopLeft = add(subtract(add(p1, p4), p7), p5);
        int[][] resultTopRight = add(p3, p5);
        int[][] resultBottomLeft = add(p2, p4);
        int[][] resultBottomRight = add(subtract(add(p1, p3), p2), p6);

        pasteSubMatrix(resultTopLeft, productMatrix, 0, 0);
        pasteSubMatrix(resultTopRight, productMatrix, 0, subMatrixSize);
        pasteSubMatrix(resultBottomLeft, productMatrix, subMatrixSize, 0);
        pasteSubMatrix(resultBottomRight, productMatrix, subMatrixSize, subMatrixSize);

        return productMatrix;
    }

    public int[][] subtract(int[][] leftMatrix, int[][] rightMatrix) {
        int matrixSize = leftMatrix.length;
        int[][] resultMatrix = new int[matrixSize][matrixSize];

        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                resultMatrix[rowIndex][columnIndex] =
                    leftMatrix[rowIndex][columnIndex] - rightMatrix[rowIndex][columnIndex];
            }
        }

        return resultMatrix;
    }

    public int[][] add(int[][] leftMatrix, int[][] rightMatrix) {
        int matrixSize = leftMatrix.length;
        int[][] resultMatrix = new int[matrixSize][matrixSize];

        for (int rowIndex = 0; rowIndex < matrixSize; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrixSize; columnIndex++) {
                resultMatrix[rowIndex][columnIndex] =
                    leftMatrix[rowIndex][columnIndex] + rightMatrix[rowIndex][columnIndex];
            }
        }

        return resultMatrix;
    }

    public void copySubMatrix(int[][] sourceMatrix, int[][] targetSubMatrix, int rowOffset, int columnOffset) {
        for (int targetRowIndex = 0, sourceRowIndex = rowOffset;
             targetRowIndex < targetSubMatrix.length;
             targetRowIndex++, sourceRowIndex++) {

            for (int targetColumnIndex = 0, sourceColumnIndex = columnOffset;
                 targetColumnIndex < targetSubMatrix.length;
                 targetColumnIndex++, sourceColumnIndex++) {

                targetSubMatrix[targetRowIndex][targetColumnIndex] =
                    sourceMatrix[sourceRowIndex][sourceColumnIndex];
            }
        }
    }

    public void pasteSubMatrix(int[][] sourceSubMatrix, int[][] targetMatrix, int rowOffset, int columnOffset) {
        for (int sourceRowIndex = 0, targetRowIndex = rowOffset;
             sourceRowIndex < sourceSubMatrix.length;
             sourceRowIndex++, targetRowIndex++) {

            for (int sourceColumnIndex = 0, targetColumnIndex = columnOffset;
                 sourceColumnIndex < sourceSubMatrix.length;
                 sourceColumnIndex++, targetColumnIndex++) {

                targetMatrix[targetRowIndex][targetColumnIndex] =
                    sourceSubMatrix[sourceRowIndex][sourceColumnIndex];
            }
        }
    }
}