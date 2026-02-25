package com.thealgorithms.ciphers;

import java.util.Scanner;

final class ProductCipher {

    private static final int SUBSTITUTION_SHIFT = 5;
    private static final char PADDING_CHAR = '/';

    private ProductCipher() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the input to be encrypted: ");
            String plaintext = scanner.nextLine();

            System.out.println();
            System.out.println("Enter a number: ");
            int keySize = scanner.nextInt();

            StringBuffer substitutedText = new StringBuffer();
            for (int index = 0; index < plaintext.length(); index++) {
                char originalChar = plaintext.charAt(index);
                substitutedText.append((char) (originalChar + SUBSTITUTION_SHIFT));
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            String transpositionInput = substitutedText.toString();
            int remainder = transpositionInput.length() % keySize;
            if (remainder != 0) {
                int paddingNeeded = keySize - remainder;
                while (paddingNeeded-- > 0) {
                    transpositionInput += PADDING_CHAR;
                }
            }

            StringBuffer transposedCipherText = new StringBuffer();
            System.out.println();
            System.out.println("Transposition Matrix: ");
            int rowCount = transpositionInput.length() / keySize;
            for (int columnIndex = 0; columnIndex < keySize; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    char matrixChar = transpositionInput.charAt(columnIndex + (rowIndex * keySize));
                    System.out.print(matrixChar);
                    transposedCipherText.append(matrixChar);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedCipherText);

            int columnCount = transposedCipherText.length() / keySize;
            StringBuffer transposedPlaintext = new StringBuffer();
            for (int rowIndex = 0; rowIndex < columnCount; rowIndex++) {
                for (int columnIndex = 0; columnIndex < transposedCipherText.length() / columnCount; columnIndex++) {
                    char matrixChar = transposedCipherText.charAt(rowIndex + (columnIndex * columnCount));
                    transposedPlaintext.append(matrixChar);
                }
            }

            StringBuffer decryptedPlaintext = new StringBuffer();
            for (int index = 0; index < transposedPlaintext.length(); index++) {
                char encryptedChar = transposedPlaintext.charAt(index);
                decryptedPlaintext.append((char) (encryptedChar - SUBSTITUTION_SHIFT));
            }

            System.out.println("Plaintext: ");
            System.out.println(decryptedPlaintext);
        }
    }
}