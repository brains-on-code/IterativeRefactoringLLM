package com.thealgorithms.ciphers;

import java.util.Scanner;

final class ProductCipher {

    private static final char PADDING_CHAR = '/';
    private static final int SHIFT_VALUE = 5;

    private ProductCipher() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String plaintext = readPlaintext(scanner);
            int key = readKey(scanner);

            String substituted = applySubstitutionCipher(plaintext, SHIFT_VALUE);
            System.out.println();
            System.out.println("Substituted text:");
            System.out.println(substituted);

            String paddedForTransposition = padForTransposition(substituted, key, PADDING_CHAR);
            String transposed = applyColumnarTransposition(paddedForTransposition, key);

            System.out.println();
            System.out.println("Final encrypted text:");
            System.out.println(transposed);

            String decrypted = decryptProductCipher(transposed, key, SHIFT_VALUE);
            System.out.println("Plaintext:");
            System.out.println(decrypted);
        }
    }

    private static String readPlaintext(Scanner scanner) {
        System.out.println("Enter the input to be encrypted:");
        return scanner.nextLine();
    }

    private static int readKey(Scanner scanner) {
        System.out.println();
        System.out.println("Enter a number:");
        return scanner.nextInt();
    }

    private static String applySubstitutionCipher(String input, int shift) {
        StringBuilder result = new StringBuilder(input.length());
        for (int index = 0; index < input.length(); index++) {
            char currentChar = input.charAt(index);
            result.append((char) (currentChar + shift));
        }
        return result.toString();
    }

    private static String padForTransposition(String input, int key, char paddingChar) {
        int remainder = input.length() % key;
        if (remainder == 0) {
            return input;
        }

        int paddingNeeded = key - remainder;
        StringBuilder paddedInput = new StringBuilder(input.length() + paddingNeeded);
        paddedInput.append(input);
        for (int i = 0; i < paddingNeeded; i++) {
            paddedInput.append(paddingChar);
        }
        return paddedInput.toString();
    }

    private static String applyColumnarTransposition(String input, int key) {
        int rows = input.length() / key;
        StringBuilder transposed = new StringBuilder(input.length());

        System.out.println();
        System.out.println("Transposition Matrix:");
        for (int column = 0; column < key; column++) {
            for (int row = 0; row < rows; row++) {
                char currentChar = input.charAt(column + (row * key));
                System.out.print(currentChar);
                transposed.append(currentChar);
            }
            System.out.println();
        }
        return transposed.toString();
    }

    private static String decryptProductCipher(String encryptedText, int key, int shift) {
        String afterTransposition = reverseColumnarTransposition(encryptedText, key);
        return reverseSubstitutionCipher(afterTransposition, shift);
    }

    private static String reverseColumnarTransposition(String input, int key) {
        int rows = input.length() / key;
        StringBuilder result = new StringBuilder(input.length());

        for (int column = 0; column < rows; column++) {
            for (int row = 0; row < key; row++) {
                char currentChar = input.charAt(column + (row * rows));
                result.append(currentChar);
            }
        }
        return result.toString();
    }

    private static String reverseSubstitutionCipher(String input, int shift) {
        StringBuilder result = new StringBuilder(input.length());
        for (int index = 0; index < input.length(); index++) {
            char currentChar = input.charAt(index);
            result.append((char) (currentChar - shift));
        }
        return result.toString();
    }
}