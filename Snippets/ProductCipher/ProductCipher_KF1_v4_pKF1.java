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
            String transposedText = applyTransposition(paddedText, keySize);
            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedText);

            String reverseTransposedText = reverseTransposition(transposedText, keySize);
            String decryptedText = reverseSubstitution(reverseTransposedText);

            System.out.println("Plaintext: ");
            System.out.println(decryptedText);
        }
    }

    private static String applySubstitution(String input) {
        StringBuilder substitutedTextBuilder = new StringBuilder();
        for (int index = 0; index < input.length(); index++) {
            char originalCharacter = input.charAt(index);
            substitutedTextBuilder.append((char) (originalCharacter + SUBSTITUTION_SHIFT));
        }
        return substitutedTextBuilder.toString();
    }

    private static String padForTransposition(String input, int keySize) {
        int paddingRemainder = input.length() % keySize;
        if (paddingRemainder == 0) {
            return input;
        }

        int paddingCharactersToAdd = keySize - paddingRemainder;
        StringBuilder paddedTextBuilder = new StringBuilder(input);
        while (paddingCharactersToAdd-- > 0) {
            paddedTextBuilder.append(PADDING_CHARACTER);
        }
        return paddedTextBuilder.toString();
    }

    private static String applyTransposition(String paddedText, int keySize) {
        int rowCount = paddedText.length() / keySize;
        StringBuilder transposedTextBuilder = new StringBuilder();

        for (int columnIndex = 0; columnIndex < keySize; columnIndex++) {
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                char matrixCharacter = paddedText.charAt(columnIndex + (rowIndex * keySize));
                System.out.print(matrixCharacter);
                transposedTextBuilder.append(matrixCharacter);
            }
            System.out.println();
        }

        return transposedTextBuilder.toString();
    }

    private static String reverseTransposition(String transposedText, int keySize) {
        int rowCount = transposedText.length() / keySize;
        StringBuilder reverseTransposedTextBuilder = new StringBuilder();

        for (int columnIndex = 0; columnIndex < rowCount; columnIndex++) {
            for (int rowIndex = 0; rowIndex < transposedText.length() / rowCount; rowIndex++) {
                char matrixCharacter = transposedText.charAt(columnIndex + (rowIndex * rowCount));
                reverseTransposedTextBuilder.append(matrixCharacter);
            }
        }

        return reverseTransposedTextBuilder.toString();
    }

    private static String reverseSubstitution(String input) {
        StringBuilder decryptedTextBuilder = new StringBuilder();
        for (int index = 0; index < input.length(); index++) {
            char substitutedCharacter = input.charAt(index);
            decryptedTextBuilder.append((char) (substitutedCharacter - SUBSTITUTION_SHIFT));
        }
        return decryptedTextBuilder.toString();
    }
}