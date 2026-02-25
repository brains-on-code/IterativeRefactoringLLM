package com.thealgorithms.divideandconquer;

// Java Program to Implement Strassen Algorithm for Matrix Multiplication

/*
 * Uses the divide and conquer approach to multiply two matrices.
 * Time Complexity: O(n^2.8074) better than the O(n^3) of the standard matrix multiplication
 * algorithm. Space Complexity: O(n^2)
 *
 * This Matrix multiplication can be performed only on square matrices
 * where n is a power of 2. Order of both of the matrices are n × n.
 *
 * Reference:
 * https://www.tutorialspoint.com/design_and_analysis_of_algorithms/design_and_analysis_of_algorithms_strassens_matrix_multiplication.htm#:~:text=Strassen's%20Matrix%20multiplication%20can%20be,matrices%20are%20n%20%C3%97%20n.
 * https://www.geeksforgeeks.org/strassens-matrix-multiplication/
 */

public class StrassenMatrixMultiplication {

    // Function to multiply matrices using Strassen's algorithm
    public int[][] multiply(int[][] leftMatrix, int[][] rightMatrix) {
        int size = leftMatrix.length;

        int[][] productMatrix = new int[size][size];

        if (size == 1) {
            productMatrix[0][0] = leftMatrix[0][0] * rightMatrix[0][0];
        } else {
            int halfSize = size / 2;

            // Submatrices of leftMatrix
            int[][] leftTopLeft = new int[halfSize][halfSize];
            int[][] leftTopRight = new int[halfSize][halfSize];
            int[][] leftBottomLeft = new int[halfSize][halfSize];
            int[][] leftBottomRight = new int[halfSize][halfSize];

            // Submatrices of rightMatrix
            int[][] rightTopLeft = new int[halfSize][halfSize];
            int[][] rightTopRight = new int[halfSize][halfSize];
            int[][] rightBottomLeft = new int[halfSize][halfSize];
            int[][] rightBottomRight = new int[halfSize][halfSize];

            // Split leftMatrix into 4 parts
            splitMatrix(leftMatrix, leftTopLeft, 0, 0);
            splitMatrix(leftMatrix, leftTopRight, 0, halfSize);
            splitMatrix(leftMatrix, leftBottomLeft, halfSize, 0);
            splitMatrix(leftMatrix, leftBottomRight, halfSize, halfSize);

            // Split rightMatrix into 4 parts
            splitMatrix(rightMatrix, rightTopLeft, 0, 0);
            splitMatrix(rightMatrix, rightTopRight, 0, halfSize);
            splitMatrix(rightMatrix, rightBottomLeft, halfSize, 0);
            splitMatrix(rightMatrix, rightBottomRight, halfSize, halfSize);

            // Strassen's 7 products
            int[][] product1 = multiply(addMatrices(leftTopLeft, leftBottomRight), addMatrices(rightTopLeft, rightBottomRight));
            int[][] product2 = multiply(addMatrices(leftBottomLeft, leftBottomRight), rightTopLeft);
            int[][] product3 = multiply(leftTopLeft, subtractMatrices(rightTopRight, rightBottomRight));
            int[][] product4 = multiply(leftBottomRight, subtractMatrices(rightBottomLeft, rightTopLeft));
            int[][] product5 = multiply(addMatrices(leftTopLeft, leftTopRight), rightBottomRight);
            int[][] product6 = multiply(subtractMatrices(leftBottomLeft, leftTopLeft), addMatrices(rightTopLeft, rightTopRight));
            int[][] product7 = multiply(subtractMatrices(leftTopRight, leftBottomRight), addMatrices(rightBottomLeft, rightBottomRight));

            // Result submatrices
            int[][] resultTopLeft = addMatrices(subtractMatrices(addMatrices(product1, product4), product5), product7);
            int[][] resultTopRight = addMatrices(product3, product5);
            int[][] resultBottomLeft = addMatrices(product2, product4);
            int[][] resultBottomRight = addMatrices(subtractMatrices(addMatrices(product1, product3), product2), product6);

            // Join result submatrices into the final product matrix
            joinMatrix(resultTopLeft, productMatrix, 0, 0);
            joinMatrix(resultTopRight, productMatrix, 0, halfSize);
            joinMatrix(resultBottomLeft, productMatrix, halfSize, 0);
            joinMatrix(resultBottomRight, productMatrix, halfSize, halfSize);
        }

        return productMatrix;
    }

    // Function to subtract two matrices
    public int[][] subtractMatrices(int[][] leftMatrix, int[][] rightMatrix) {
        int size = leftMatrix.length;
        int[][] resultMatrix = new int[size][size];

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex < size; columnIndex++) {
                resultMatrix[rowIndex][columnIndex] = leftMatrix[rowIndex][columnIndex] - rightMatrix[rowIndex][columnIndex];
            }
        }

        return resultMatrix;
    }

    // Function to add two matrices
    public int[][] addMatrices(int[][] leftMatrix, int[][] rightMatrix) {
        int size = leftMatrix.length;
        int[][] resultMatrix = new int[size][size];

        for (int rowIndex = 0; rowIndex < size; rowIndex++) {
            for (int columnIndex = 0; columnIndex < size; columnIndex++) {
                resultMatrix[rowIndex][columnIndex] = leftMatrix[rowIndex][columnIndex] + rightMatrix[rowIndex][columnIndex];
            }
        }

        return resultMatrix;
    }

    // Function to split parent matrix into child matrix
    public void splitMatrix(int[][] sourceMatrix, int[][] targetSubmatrix, int rowOffset, int columnOffset) {
        for (int targetRow = 0, sourceRow = rowOffset; targetRow < targetSubmatrix.length; targetRow++, sourceRow++) {
            for (int targetColumn = 0, sourceColumn = columnOffset; targetColumn < targetSubmatrix.length; targetColumn++, sourceColumn++) {
                targetSubmatrix[targetRow][targetColumn] = sourceMatrix[sourceRow][sourceColumn];
            }
        }
    }

    // Function to join child matrix into parent matrix
    public void joinMatrix(int[][] sourceSubmatrix, int[][] targetMatrix, int rowOffset, int columnOffset) {
        for (int sourceRow = 0, targetRow = rowOffset; sourceRow < sourceSubmatrix.length; sourceRow++, targetRow++) {
            for (int sourceColumn = 0, targetColumn = columnOffset; sourceColumn < sourceSubmatrix.length; sourceColumn++, targetColumn++) {
                targetMatrix[targetRow][targetColumn] = sourceSubmatrix[sourceRow][sourceColumn];
            }
        }
    }
}