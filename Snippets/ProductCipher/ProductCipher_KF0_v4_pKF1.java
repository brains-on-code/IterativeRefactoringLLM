package com.thealgorithms.ciphers;

import java.util.Scanner;

final class ProductCipher {

    private static final int SUBSTITUTION_SHIFT = 5;
    private static final char PADDING_CHARACTER = '/';

    private ProductCipher() {
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the input to be encrypted: ");
            String plainText = scanner.nextLine();

            System.out.println();
            System.out.println("Enter a number: ");
            int keyLength = scanner.nextInt();

            StringBuilder substitutedTextBuilder = new StringBuilder();
            for (int index = 0; index < plainText.length(); index++) {
                char currentCharacter = plainText.charAt(index);
                char substitutedCharacter = (char) (currentCharacter + SUBSTITUTION_SHIFT);
                substitutedTextBuilder.append(substitutedCharacter);
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedTextBuilder);

            String transpositionInput = substitutedTextBuilder.toString();
            int remainingCharacters = transpositionInput.length() % keyLength;
            if (remainingCharacters != 0) {
                int paddingCharactersNeeded = keyLength - remainingCharacters;
                StringBuilder paddedInputBuilder = new StringBuilder(transpositionInput);
                for (int index = 0; index < paddingCharactersNeeded; index++) {
                    paddedInputBuilder.append(PADDING_CHARACTER);
                }
                transpositionInput = paddedInputBuilder.toString();
            }

            StringBuilder transposedCipherTextBuilder = new StringBuilder();
            int rowCount = transpositionInput.length() / keyLength;

            System.out.println();
            System.out.println("Transposition Matrix: ");
            for (int columnIndex = 0; columnIndex < keyLength; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
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

            int decryptedRowCount = transposedCipherTextBuilder.length() / keyLength;
            int decryptedColumnCount = transposedCipherTextBuilder.length() / decryptedRowCount;
            StringBuilder transpositionDecryptedTextBuilder = new StringBuilder();

            for (int columnIndex = 0; columnIndex < decryptedRowCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < decryptedColumnCount; rowIndex++) {
                    int characterIndex = columnIndex + (rowIndex * decryptedRowCount);
                    char decryptedMatrixCharacter = transposedCipherTextBuilder.charAt(characterIndex);
                    transpositionDecryptedTextBuilder.append(decryptedMatrixCharacter);
                }
            }

            StringBuilder recoveredPlainTextBuilder = new StringBuilder();
            for (int index = 0; index < transpositionDecryptedTextBuilder.length(); index++) {
                char decryptedCharacter = transpositionDecryptedTextBuilder.charAt(index);
                if (decryptedCharacter == PADDING_CHARACTER) {
                    continue;
                }
                char originalCharacter = (char) (decryptedCharacter - SUBSTITUTION_SHIFT);
                recoveredPlainTextBuilder.append(originalCharacter);
            }

            System.out.println("Plaintext: ");
            System.out.println(recoveredPlainTextBuilder);
        }
    }
}