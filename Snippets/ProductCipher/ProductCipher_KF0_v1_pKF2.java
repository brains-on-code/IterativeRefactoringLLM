package com.thealgorithms.ciphers;

import java.util.Scanner;

final class ProductCipher {

    private static final char PADDING_CHAR = '/';
    private static final int SUBSTITUTION_SHIFT = 5;

    private ProductCipher() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the input to be encrypted: ");
            String inputText = scanner.nextLine();

            System.out.println();
            System.out.println("Enter a number: ");
            int key = scanner.nextInt();

            String substitutedText = applySubstitutionCipher(inputText, SUBSTITUTION_SHIFT);
            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            String transposedText = applyTranspositionCipher(substitutedText, key);
            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedText);

            String transpositionDecrypted = reverseTranspositionCipher(transposedText, key);
            String decryptedText = applySubstitutionCipher(transpositionDecrypted, -SUBSTITUTION_SHIFT);

            System.out.println("Plaintext: ");
            System.out.println(decryptedText);
        }
    }

    private static String applySubstitutionCipher(String input, int shift) {
        StringBuilder output = new StringBuilder(input.length());
        for (int index = 0; index < input.length(); index++) {
            char currentChar = input.charAt(index);
            output.append((char) (currentChar + shift));
        }
        return output.toString();
    }

    private static String applyTranspositionCipher(String input, int key) {
        String paddedInput = padInputForTransposition(input, key);
        StringBuilder output = new StringBuilder(paddedInput.length());

        int rows = paddedInput.length() / key;
        System.out.println();
        System.out.println("Transposition Matrix: ");

        for (int column = 0; column < key; column++) {
            for (int row = 0; row < rows; row++) {
                char currentChar = paddedInput.charAt(column + (row * key));
                System.out.print(currentChar);
                output.append(currentChar);
            }
            System.out.println();
        }

        return output.toString();
    }

    private static String padInputForTransposition(String input, int key) {
        int remainder = input.length() % key;
        if (remainder == 0) {
            return input;
        }

        int paddingNeeded = key - remainder;
        StringBuilder paddedInput = new StringBuilder(input.length() + paddingNeeded);
        paddedInput.append(input);

        for (int count = 0; count < paddingNeeded; count++) {
            paddedInput.append(PADDING_CHAR);
        }

        return paddedInput.toString();
    }

    private static String reverseTranspositionCipher(String input, int key) {
        int rows = input.length() / key;
        StringBuilder plaintext = new StringBuilder(input.length());

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < input.length() / rows; column++) {
                char currentChar = input.charAt(row + (column * rows));
                plaintext.append(currentChar);
            }
        }

        return plaintext.toString();
    }
}