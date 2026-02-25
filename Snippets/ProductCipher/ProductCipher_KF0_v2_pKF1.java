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
            String plaintext = inputScanner.nextLine();

            System.out.println();
            System.out.println("Enter a number: ");
            int keyLength = inputScanner.nextInt();

            StringBuilder substitutedTextBuilder = new StringBuilder();
            for (int index = 0; index < plaintext.length(); index++) {
                char originalCharacter = plaintext.charAt(index);
                char shiftedCharacter = (char) (originalCharacter + SUBSTITUTION_SHIFT);
                substitutedTextBuilder.append(shiftedCharacter);
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedTextBuilder);

            String transpositionInput = substitutedTextBuilder.toString();
            int paddingCharactersToAdd = transpositionInput.length() % keyLength;
            if (paddingCharactersToAdd != 0) {
                paddingCharactersToAdd = keyLength - paddingCharactersToAdd;
                while (paddingCharactersToAdd-- > 0) {
                    transpositionInput += PADDING_CHARACTER;
                }
            }

            StringBuilder transposedCipherTextBuilder = new StringBuilder();
            int numberOfRows = transpositionInput.length() / keyLength;

            System.out.println();
            System.out.println("Transposition Matrix: ");
            for (int columnIndex = 0; columnIndex < keyLength; columnIndex++) {
                for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
                    int characterIndex = columnIndex + (rowIndex * keyLength);
                    char matrixCharacter = transpositionInput.charAt(characterIndex);
                    System.out.print(matrixCharacter);
                    transposedCipherTextBuilder.append(matrixCharacter);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedCipherTextBuilder);

            int decryptedNumberOfRows = transposedCipherTextBuilder.length() / keyLength;
            StringBuilder transpositionDecryptedTextBuilder = new StringBuilder();
            int decryptedNumberOfColumns = transposedCipherTextBuilder.length() / decryptedNumberOfRows;

            for (int columnIndex = 0; columnIndex < decryptedNumberOfRows; columnIndex++) {
                for (int rowIndex = 0; rowIndex < decryptedNumberOfColumns; rowIndex++) {
                    int characterIndex = columnIndex + (rowIndex * decryptedNumberOfRows);
                    char decryptedMatrixCharacter = transposedCipherTextBuilder.charAt(characterIndex);
                    transpositionDecryptedTextBuilder.append(decryptedMatrixCharacter);
                }
            }

            StringBuilder finalPlaintextBuilder = new StringBuilder();
            for (int index = 0; index < transpositionDecryptedTextBuilder.length(); index++) {
                char decryptedCharacter = transpositionDecryptedTextBuilder.charAt(index);
                if (decryptedCharacter == PADDING_CHARACTER) {
                    continue;
                }
                char originalCharacter = (char) (decryptedCharacter - SUBSTITUTION_SHIFT);
                finalPlaintextBuilder.append(originalCharacter);
            }

            System.out.println("Plaintext: ");
            System.out.println(finalPlaintextBuilder);
        }
    }
}