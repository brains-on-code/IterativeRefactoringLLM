package com.thealgorithms.ciphers;

import java.util.Scanner;

final class Class1 {

    private static final int SUBSTITUTION_SHIFT = 5;
    private static final char PADDING_CHAR = '/';

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static void method1(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String inputText = readInputText(scanner);
            int key = readKey(scanner);

            String substitutedText = applySubstitution(inputText, SUBSTITUTION_SHIFT);
            printSection("Substituted text:", substitutedText);

            String paddedText = padForTransposition(substitutedText, key, PADDING_CHAR);
            String encryptedText = applyTransposition(paddedText, key);
            printSection("Final encrypted text:", encryptedText);

            String transposedBack = reverseTransposition(encryptedText, key);
            String decryptedText = applySubstitution(transposedBack, -SUBSTITUTION_SHIFT);
            printSection("Plaintext:", decryptedText);
        }
    }

    private static String readInputText(Scanner scanner) {
        System.out.println("Enter the input to be encrypted: ");
        return scanner.nextLine();
    }

    private static int readKey(Scanner scanner) {
        System.out.println();
        System.out.println("Enter a number: ");
        return scanner.nextInt();
    }

    private static String applySubstitution(String text, int shift) {
        StringBuilder result = new StringBuilder(text.length());
        for (int index = 0; index < text.length(); index++) {
            char currentChar = text.charAt(index);
            result.append((char) (currentChar + shift));
        }
        return result.toString();
    }

    private static String padForTransposition(String text, int key, char paddingChar) {
        int remainder = text.length() % key;
        if (remainder == 0) {
            return text;
        }

        int paddingNeeded = key - remainder;
        StringBuilder paddedBuilder = new StringBuilder(text.length() + paddingNeeded);
        paddedBuilder.append(text);
        for (int i = 0; i < paddingNeeded; i++) {
            paddedBuilder.append(paddingChar);
        }
        return paddedBuilder.toString();
    }

    private static String applyTransposition(String text, int key) {
        int rows = text.length() / key;
        StringBuilder encryptedText = new StringBuilder(text.length());

        System.out.println();
        System.out.println("Transposition Matrix: ");
        for (int column = 0; column < key; column++) {
            for (int row = 0; row < rows; row++) {
                int index = column + (row * key);
                char currentChar = text.charAt(index);
                System.out.print(currentChar);
                encryptedText.append(currentChar);
            }
            System.out.println();
        }

        return encryptedText.toString();
    }

    private static String reverseTransposition(String encryptedText, int key) {
        int columns = encryptedText.length() / key;
        StringBuilder transposedBack = new StringBuilder(encryptedText.length());

        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < key; row++) {
                int index = column + (row * columns);
                char currentChar = encryptedText.charAt(index);
                transposedBack.append(currentChar);
            }
        }

        return transposedBack.toString();
    }

    private static void printSection(String title, String content) {
        System.out.println();
        System.out.println(title);
        System.out.println(content);
    }
}