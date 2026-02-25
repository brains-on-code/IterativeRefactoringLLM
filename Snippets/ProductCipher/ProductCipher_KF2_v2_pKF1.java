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
            int key = scanner.nextInt();

            StringBuffer substitutedText = new StringBuffer();
            for (int i = 0; i < plaintext.length(); i++) {
                char originalChar = plaintext.charAt(i);
                substitutedText.append((char) (originalChar + SUBSTITUTION_SHIFT));
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            String transpositionInput = substitutedText.toString();
            int remainder = transpositionInput.length() % key;
            if (remainder != 0) {
                int paddingCount = key - remainder;
                while (paddingCount-- > 0) {
                    transpositionInput += PADDING_CHAR;
                }
            }

            StringBuffer transposedCipherText = new StringBuffer();
            System.out.println();
            System.out.println("Transposition Matrix: ");
            int rowCount = transpositionInput.length() / key;
            for (int columnIndex = 0; columnIndex < key; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    char matrixChar = transpositionInput.charAt(columnIndex + (rowIndex * key));
                    System.out.print(matrixChar);
                    transposedCipherText.append(matrixChar);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedCipherText);

            int columnCount = transposedCipherText.length() / key;
            StringBuffer transposedPlaintext = new StringBuffer();
            for (int rowIndex = 0; rowIndex < columnCount; rowIndex++) {
                for (int columnIndex = 0; columnIndex < transposedCipherText.length() / columnCount; columnIndex++) {
                    char matrixChar = transposedCipherText.charAt(rowIndex + (columnIndex * columnCount));
                    transposedPlaintext.append(matrixChar);
                }
            }

            StringBuffer decryptedPlaintext = new StringBuffer();
            for (int i = 0; i < transposedPlaintext.length(); i++) {
                char encryptedChar = transposedPlaintext.charAt(i);
                decryptedPlaintext.append((char) (encryptedChar - SUBSTITUTION_SHIFT));
            }

            System.out.println("Plaintext: ");
            System.out.println(decryptedPlaintext);
        }
    }
}