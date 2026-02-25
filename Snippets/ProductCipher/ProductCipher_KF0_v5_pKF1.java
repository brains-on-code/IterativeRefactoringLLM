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

            StringBuilder substitutedText = new StringBuilder();
            for (int i = 0; i < plainText.length(); i++) {
                char originalChar = plainText.charAt(i);
                char shiftedChar = (char) (originalChar + SUBSTITUTION_SHIFT);
                substitutedText.append(shiftedChar);
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            String transpositionInput = substitutedText.toString();
            int remainder = transpositionInput.length() % keyLength;
            if (remainder != 0) {
                int paddingNeeded = keyLength - remainder;
                StringBuilder paddedInput = new StringBuilder(transpositionInput);
                for (int i = 0; i < paddingNeeded; i++) {
                    paddedInput.append(PADDING_CHARACTER);
                }
                transpositionInput = paddedInput.toString();
            }

            StringBuilder transposedCipherText = new StringBuilder();
            int rowCount = transpositionInput.length() / keyLength;

            System.out.println();
            System.out.println("Transposition Matrix: ");
            for (int column = 0; column < keyLength; column++) {
                for (int row = 0; row < rowCount; row++) {
                    int charIndex = column + (row * keyLength);
                    char matrixChar = transpositionInput.charAt(charIndex);
                    System.out.print(matrixChar);
                    transposedCipherText.append(matrixChar);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedCipherText);

            int decryptedRowCount = transposedCipherText.length() / keyLength;
            int decryptedColumnCount = transposedCipherText.length() / decryptedRowCount;
            StringBuilder transpositionDecryptedText = new StringBuilder();

            for (int column = 0; column < decryptedRowCount; column++) {
                for (int row = 0; row < decryptedColumnCount; row++) {
                    int charIndex = column + (row * decryptedRowCount);
                    char decryptedMatrixChar = transposedCipherText.charAt(charIndex);
                    transpositionDecryptedText.append(decryptedMatrixChar);
                }
            }

            StringBuilder recoveredPlainText = new StringBuilder();
            for (int i = 0; i < transpositionDecryptedText.length(); i++) {
                char decryptedChar = transpositionDecryptedText.charAt(i);
                if (decryptedChar == PADDING_CHARACTER) {
                    continue;
                }
                char originalChar = (char) (decryptedChar - SUBSTITUTION_SHIFT);
                recoveredPlainText.append(originalChar);
            }

            System.out.println("Plaintext: ");
            System.out.println(recoveredPlainText);
        }
    }
}