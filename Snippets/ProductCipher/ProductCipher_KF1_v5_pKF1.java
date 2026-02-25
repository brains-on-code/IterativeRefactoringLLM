package com.thealgorithms.ciphers;

import java.util.Scanner;

final class SubstitutionTranspositionCipher {

    private static final int SUBSTITUTION_SHIFT = 5;
    private static final char PADDING_CHARACTER = '/';

    private SubstitutionTranspositionCipher() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the input to be encrypted: ");
            String plainText = scanner.nextLine();

            System.out.println();
            System.out.println("Enter a number: ");
            int keySize = scanner.nextInt();

            String substitutedText = applySubstitution(plainText);
            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            String paddedText = padForTransposition(substitutedText, keySize);

            System.out.println();
            System.out.println("Transposition Matrix: ");
            String cipherText = applyTransposition(paddedText, keySize);
            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(cipherText);

            String untransposedText = reverseTransposition(cipherText, keySize);
            String decryptedText = reverseSubstitution(untransposedText);

            System.out.println("Plaintext: ");
            System.out.println(decryptedText);
        }
    }

    private static String applySubstitution(String plainText) {
        StringBuilder substitutedTextBuilder = new StringBuilder();
        for (int index = 0; index < plainText.length(); index++) {
            char originalCharacter = plainText.charAt(index);
            char shiftedCharacter = (char) (originalCharacter + SUBSTITUTION_SHIFT);
            substitutedTextBuilder.append(shiftedCharacter);
        }
        return substitutedTextBuilder.toString();
    }

    private static String padForTransposition(String text, int keySize) {
        int remainder = text.length() % keySize;
        if (remainder == 0) {
            return text;
        }

        int paddingCount = keySize - remainder;
        StringBuilder paddedTextBuilder = new StringBuilder(text);
        while (paddingCount-- > 0) {
            paddedTextBuilder.append(PADDING_CHARACTER);
        }
        return paddedTextBuilder.toString();
    }

    private static String applyTransposition(String paddedText, int keySize) {
        int rowCount = paddedText.length() / keySize;
        StringBuilder transposedTextBuilder = new StringBuilder();

        for (int columnIndex = 0; columnIndex < keySize; columnIndex++) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                int charIndex = columnIndex + (rowIndex * keySize);
                char matrixCharacter = paddedText.charAt(charIndex);
                System.out.print(matrixCharacter);
                transposedTextBuilder.append(matrixCharacter);
            }
            System.out.println();
        }

        return transposedTextBuilder.toString();
    }

    private static String reverseTransposition(String transposedText, int keySize) {
        int rowCount = transposedText.length() / keySize;
        int columnCount = keySize;
        StringBuilder originalOrderBuilder = new StringBuilder();

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                int charIndex = rowIndex + (columnIndex * rowCount);
                char matrixCharacter = transposedText.charAt(charIndex);
                originalOrderBuilder.append(matrixCharacter);
            }
        }

        return originalOrderBuilder.toString();
    }

    private static String reverseSubstitution(String substitutedText) {
        StringBuilder decryptedTextBuilder = new StringBuilder();
        for (int index = 0; index < substitutedText.length(); index++) {
            char substitutedCharacter = substitutedText.charAt(index);
            char originalCharacter = (char) (substitutedCharacter - SUBSTITUTION_SHIFT);
            decryptedTextBuilder.append(originalCharacter);
        }
        return decryptedTextBuilder.toString();
    }
}