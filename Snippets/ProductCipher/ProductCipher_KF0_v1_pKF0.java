package com.thealgorithms.ciphers;

import java.util.Scanner;

final class ProductCipher {

    private static final int SUBSTITUTION_SHIFT = 5;
    private static final char PADDING_CHAR = '/';

    private ProductCipher() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String inputText = readInputText(scanner);
            int key = readKey(scanner);

            String substitutedText = applySubstitution(inputText, SUBSTITUTION_SHIFT);
            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            String transposedText = applyTransposition(substitutedText, key);
            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedText);

            String decryptedText = decrypt(transposedText, key);
            System.out.println("Plaintext: ");
            System.out.println(decryptedText);
        }
    }

    private static String readInputText(Scanner scanner) {
        System.out.println("Enter the input to be encrypted: ");
        String input = scanner.nextLine();
        System.out.println();
        return input;
    }

    private static int readKey(Scanner scanner) {
        System.out.println("Enter a number: ");
        return scanner.nextInt();
    }

    private static String applySubstitution(String input, int shift) {
        StringBuilder result = new StringBuilder(input.length());
        for (char c : input.toCharArray()) {
            result.append((char) (c + shift));
        }
        return result.toString();
    }

    private static String reverseSubstitution(String input, int shift) {
        StringBuilder result = new StringBuilder(input.length());
        for (char c : input.toCharArray()) {
            result.append((char) (c - shift));
        }
        return result.toString();
    }

    private static String applyTransposition(String input, int key) {
        String paddedInput = padInput(input, key);
        int rows = paddedInput.length() / key;

        StringBuilder transposed = new StringBuilder(paddedInput.length());
        System.out.println();
        System.out.println("Transposition Matrix: ");

        for (int col = 0; col < key; col++) {
            for (int row = 0; row < rows; row++) {
                char c = paddedInput.charAt(col + (row * key));
                System.out.print(c);
                transposed.append(c);
            }
            System.out.println();
        }

        return transposed.toString();
    }

    private static String padInput(String input, int key) {
        int remainder = input.length() % key;
        if (remainder == 0) {
            return input;
        }

        int paddingNeeded = key - remainder;
        StringBuilder padded = new StringBuilder(input.length() + paddingNeeded);
        padded.append(input);
        for (int i = 0; i < paddingNeeded; i++) {
            padded.append(PADDING_CHAR);
        }
        return padded.toString();
    }

    private static String reverseTransposition(String input, int key) {
        int rows = input.length() / key;
        StringBuilder result = new StringBuilder(input.length());

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < key; col++) {
                char c = input.charAt(col + (row * key));
                result.append(c);
            }
        }

        return result.toString();
    }

    private static String decrypt(String encryptedText, int key) {
        int rows = encryptedText.length() / key;
        String transpositionPlaintext = reverseTransposition(encryptedText, rows);
        return reverseSubstitution(transpositionPlaintext, SUBSTITUTION_SHIFT);
    }
}