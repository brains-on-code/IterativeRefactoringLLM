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
            String plaintextInput = scanner.nextLine();

            System.out.println();
            System.out.println("Enter a number: ");
            int key = scanner.nextInt();

            // Substitution encryption
            StringBuilder substitutedText = new StringBuilder();
            for (int index = 0; index < plaintextInput.length(); index++) {
                char currentChar = plaintextInput.charAt(index);
                substitutedText.append((char) (currentChar + SUBSTITUTION_SHIFT));
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            // Transposition encryption
            String transpositionInput = substitutedText.toString();
            int paddingRequired = transpositionInput.length() % key;
            if (paddingRequired != 0) {
                paddingRequired = key - paddingRequired;
                while (paddingRequired-- > 0) {
                    transpositionInput += PADDING_CHAR;
                }
            }

            StringBuilder transposedCipherText = new StringBuilder();
            int numRows = transpositionInput.length() / key;

            System.out.println();
            System.out.println("Transposition Matrix: ");
            for (int column = 0; column < key; column++) {
                for (int row = 0; row < numRows; row++) {
                    char currentChar = transpositionInput.charAt(column + (row * key));
                    System.out.print(currentChar);
                    transposedCipherText.append(currentChar);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedCipherText);

            // Transposition decryption
            int decryptedNumRows = transposedCipherText.length() / key;
            StringBuilder transpositionDecryptedText = new StringBuilder();
            for (int column = 0; column < decryptedNumRows; column++) {
                for (int row = 0; row < transposedCipherText.length() / decryptedNumRows; row++) {
                    char currentChar = transposedCipherText.charAt(column + (row * decryptedNumRows));
                    transpositionDecryptedText.append(currentChar);
                }
            }

            // Substitution decryption
            StringBuilder finalPlaintext = new StringBuilder();
            for (int index = 0; index < transpositionDecryptedText.length(); index++) {
                char currentChar = transpositionDecryptedText.charAt(index);
                if (currentChar == PADDING_CHAR) {
                    continue;
                }
                finalPlaintext.append((char) (currentChar - SUBSTITUTION_SHIFT));
            }

            System.out.println("Plaintext: ");
            System.out.println(finalPlaintext);
        }
    }
}