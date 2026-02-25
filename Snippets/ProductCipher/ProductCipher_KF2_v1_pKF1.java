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

            StringBuffer substitutedText = new StringBuffer();
            for (int index = 0; index < plaintextInput.length(); index++) {
                char currentChar = plaintextInput.charAt(index);
                substitutedText.append((char) (currentChar + SUBSTITUTION_SHIFT));
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            String transpositionInput = substitutedText.toString();
            int paddingRequired = transpositionInput.length() % key;
            if (paddingRequired != 0) {
                paddingRequired = key - paddingRequired;
                while (paddingRequired-- > 0) {
                    transpositionInput += PADDING_CHAR;
                }
            }

            StringBuffer transposedCipherText = new StringBuffer();
            System.out.println();
            System.out.println("Transposition Matrix: ");
            int numRows = transpositionInput.length() / key;
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

            int numColumns = transposedCipherText.length() / key;
            StringBuffer transposedPlaintext = new StringBuffer();
            for (int row = 0; row < numColumns; row++) {
                for (int column = 0; column < transposedCipherText.length() / numColumns; column++) {
                    char currentChar = transposedCipherText.charAt(row + (column * numColumns));
                    transposedPlaintext.append(currentChar);
                }
            }

            StringBuffer decryptedPlaintext = new StringBuffer();
            for (int index = 0; index < transposedPlaintext.length(); index++) {
                char currentChar = transposedPlaintext.charAt(index);
                decryptedPlaintext.append((char) (currentChar - SUBSTITUTION_SHIFT));
            }

            System.out.println("Plaintext: ");
            System.out.println(decryptedPlaintext);
        }
    }
}