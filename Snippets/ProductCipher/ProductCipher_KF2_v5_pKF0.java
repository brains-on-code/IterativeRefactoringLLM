package com.thealgorithms.ciphers;

import java.util.Scanner;

final class ProductCipher {

    private static final int SHIFT = 5;
    private static final char PADDING_CHAR = '/';

    private ProductCipher() {
        // Utility class
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String inputText = readInputText(scanner);
            int key = readKey(scanner);

            String substitutedText = applySubstitution(inputText, SHIFT);
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
        return shiftCharacters(input, shift);
    }

    private static String applyReverseSubstitution(String input, int shift) {
        return shiftCharacters(input, -shift);
    }

    private static String shiftCharacters(String input, int shift) {
        StringBuilder output = new StringBuilder(input.length());
        for (char character : input.toCharArray()) {
            output.append((char) (character + shift));
        }
        return output.toString();
    }

    private static String applyTransposition(String input, int key) {
        String paddedInput = padInput(input, key);
        int rows = paddedInput.length() / key;

        StringBuilder output = new StringBuilder(paddedInput.length());
        System.out.println();
        System.out.println("Transposition Matrix: ");

        for (int column = 0; column < key; column++) {
            for (int row = 0; row < rows; row++) {
                int index = column + (row * key);
                char character = paddedInput.charAt(index);
                System.out.print(character);
                output.append(character);
            }
            System.out.println();
        }

        return output.toString();
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

    private static String decrypt(String transposedText, int key) {
        int rows = transposedText.length() / key;
        int columns = key;
        StringBuilder matrixPlaintext = new StringBuilder(transposedText.length());

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int index = column * rows + row;
                char character = transposedText.charAt(index);
                matrixPlaintext.append(character);
            }
        }

        String result = applyReverseSubstitution(matrixPlaintext.toString(), SHIFT);
        return result.replace(String.valueOf(PADDING_CHAR), "");
    }
}