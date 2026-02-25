package com.thealgorithms.ciphers;

import java.util.Scanner;

final class ProductCipher {

    private static final int SUBSTITUTION_SHIFT = 5;
    private static final char PADDING_CHARACTER = '/';

    private ProductCipher() {
    }

    public static void main(String[] args) {
        try (Scanner inputScanner = new Scanner(System.in)) {
            System.out.println("Enter the input to be encrypted: ");
            String plainText = inputScanner.nextLine();

            System.out.println();
            System.out.println("Enter a number: ");
            int keyLength = inputScanner.nextInt();

            StringBuffer substitutedText = new StringBuffer();
            for (int charIndex = 0; charIndex < plainText.length(); charIndex++) {
                char originalCharacter = plainText.charAt(charIndex);
                substitutedText.append((char) (originalCharacter + SUBSTITUTION_SHIFT));
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            String transpositionInput = substitutedText.toString();
            int remainingCharacters = transpositionInput.length() % keyLength;
            if (remainingCharacters != 0) {
                int paddingCharactersRequired = keyLength - remainingCharacters;
                while (paddingCharactersRequired-- > 0) {
                    transpositionInput += PADDING_CHARACTER;
                }
            }

            StringBuffer transposedCipherText = new StringBuffer();
            System.out.println();
            System.out.println("Transposition Matrix: ");
            int rowCount = transpositionInput.length() / keyLength;
            for (int columnIndex = 0; columnIndex < keyLength; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    char matrixCharacter = transpositionInput.charAt(columnIndex + (rowIndex * keyLength));
                    System.out.print(matrixCharacter);
                    transposedCipherText.append(matrixCharacter);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedCipherText);

            int columnCount = transposedCipherText.length() / keyLength;
            StringBuffer transposedPlainText = new StringBuffer();
            for (int rowIndex = 0; rowIndex < columnCount; rowIndex++) {
                for (int columnIndex = 0; columnIndex < transposedCipherText.length() / columnCount; columnIndex++) {
                    char matrixCharacter = transposedCipherText.charAt(rowIndex + (columnIndex * columnCount));
                    transposedPlainText.append(matrixCharacter);
                }
            }

            StringBuffer decryptedPlainText = new StringBuffer();
            for (int charIndex = 0; charIndex < transposedPlainText.length(); charIndex++) {
                char encryptedCharacter = transposedPlainText.charAt(charIndex);
                decryptedPlainText.append((char) (encryptedCharacter - SUBSTITUTION_SHIFT));
            }

            System.out.println("Plaintext: ");
            System.out.println(decryptedPlainText);
        }
    }
}