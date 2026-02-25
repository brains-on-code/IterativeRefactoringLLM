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
            printSection("Substituted text:", substitutedText);

            String transposedText = applyTransposition(substitutedText, key);
            printSection("Final encrypted text:", transposedText);

            String decryptedText = decrypt(transposedText, key);
            printSection("Plaintext:", decryptedText);
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

    private static void printSection(String title, String content) {
        System.out.println();
        System.out.println(title);
        System.out.println(content);
    }

    private static String applySubstitution(String input, int shift) {
        StringBuilder result = new StringBuilder(input.length());
        for (char character : input.toCharArray()) {
            result.append((char) (character + shift));
        }
        return result.toString();
    }

    private static String reverseSubstitution(String input, int shift) {
        StringBuilder result = new StringBuilder(input.length());
        for (char character : input.toCharArray()) {
            result.append((char) (character - shift));
        }
        return result.toString();
    }

    private static String applyTransposition(String input, int key) {
        String paddedInput = padInput(input, key);
        int rows = paddedInput.length() / key;

        StringBuilder transposed = new StringBuilder(paddedInput.length());
        printTranspositionMatrixHeader();

        for (int column = 0; column < key; column++) {
            for (int row = 0; row < rows; row++) {
                int index = getIndex(row, column, key);
                char character = paddedInput.charAt(index);
                System.out.print(character);
                transposed.append(character);
            }
            System.out.println();
        }

        return transposed.toString();
    }

    private static void printTranspositionMatrixHeader() {
        System.out.println();
        System.out.println("Transposition Matrix: ");
    }

    private static int getIndex(int row, int column, int key) {
        return column + (row * key);
    }

    private static String padInput(String input, int key) {
        int remainder = input.length() % key;
        if (remainder == 0) {
            return input;
        }

        int paddingNeeded = key - remainder;
        StringBuilder padded = new StringBuilder(input.length() + paddingNeeded)
            .append(input);

        for (int i = 0; i < paddingNeeded; i++) {
            padded.append(PADDING_CHAR);
        }
        return padded.toString();
    }

    private static String reverseTransposition(String input, int key) {
        int rows = input.length() / key;
        StringBuilder result = new StringBuilder(input.length());

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < key; column++) {
                int index = getIndex(row, column, key);
                result.append(input.charAt(index));
            }
        }

        return result.toString();
    }

    private static String decrypt(String encryptedText, int key) {
        String transpositionPlaintext = reverseTransposition(encryptedText, key);
        return reverseSubstitution(transpositionPlaintext, SUBSTITUTION_SHIFT);
    }
}