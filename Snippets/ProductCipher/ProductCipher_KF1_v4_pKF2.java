package com.thealgorithms.ciphers;

import java.util.Scanner;

/**
 * Demonstrates a simple cipher that combines:
 * 1. A Caesar-style substitution (fixed character shift), and
 * 2. A columnar transposition.
 */
final class Class1 {

    /** Fixed shift used for the substitution step. */
    private static final int SHIFT = 5;

    /** Character used to pad the text so it fits evenly into the matrix. */
    private static final char PADDING_CHAR = '/';

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static void method1(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String inputText = readInputText(scanner);
            int columns = readColumnSize(scanner);

            String substitutedText = applySubstitution(inputText, SHIFT);
            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            String paddedText = padToColumnSize(substitutedText, columns, PADDING_CHAR);
            StringBuilder encryptedText = new StringBuilder();

            System.out.println();
            System.out.println("Transposition Matrix: ");
            performColumnarTransposition(paddedText, columns, encryptedText);

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(encryptedText);

            String transposedBack = reverseColumnarTransposition(encryptedText.toString(), columns);
            String plaintext = applySubstitution(transposedBack, -SHIFT);

            System.out.println("Plaintext: ");
            System.out.println(plaintext);
        }
    }

    private static String readInputText(Scanner scanner) {
        System.out.println("Enter the input to be encrypted: ");
        return scanner.nextLine();
    }

    private static int readColumnSize(Scanner scanner) {
        System.out.println();
        System.out.println("Enter a number (matrix column size): ");
        return scanner.nextInt();
    }

    /**
     * Shifts each character in {@code text} by {@code shift} Unicode code points.
     *
     * @param text  the input text
     * @param shift the number of Unicode code points to shift each character
     * @return the shifted text
     */
    private static String applySubstitution(String text, int shift) {
        StringBuilder result = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            result.append((char) (text.charAt(i) + shift));
        }
        return result.toString();
    }

    /**
     * Pads {@code text} with {@code paddingChar} so that its length
     * is a multiple of {@code columns}.
     *
     * @param text        the text to pad
     * @param columns     the desired column size
     * @param paddingChar the character used for padding
     * @return the padded text
     */
    private static String padToColumnSize(String text, int columns, char paddingChar) {
        int remainder = text.length() % columns;
        if (remainder == 0) {
            return text;
        }

        int paddingNeeded = columns - remainder;
        StringBuilder padded = new StringBuilder(text.length() + paddingNeeded);
        padded.append(text);
        for (int i = 0; i < paddingNeeded; i++) {
            padded.append(paddingChar);
        }
        return padded.toString();
    }

    /**
     * Performs a columnar transposition on {@code text}.
     * <p>
     * The text is interpreted as a matrix filled row by row. This method:
     * <ul>
     *     <li>Prints the matrix column by column, and</li>
     *     <li>Appends the characters in that same column-major order
     *         to {@code encryptedText}.</li>
     * </ul>
     *
     * @param text          the padded text to transpose
     * @param columns       the number of columns in the matrix
     * @param encryptedText the builder to collect the transposed characters
     */
    private static void performColumnarTransposition(String text, int columns, StringBuilder encryptedText) {
        int rows = text.length() / columns;
        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                char ch = text.charAt(col + (row * columns));
                System.out.print(ch);
                encryptedText.append(ch);
            }
            System.out.println();
        }
    }

    /**
     * Reconstructs the row-major order text from the column-major
     * {@code encryptedText} produced by {@link #performColumnarTransposition}.
     *
     * @param encryptedText the transposed text
     * @param columns       the number of columns used during transposition
     * @return the text restored to its pre-transposition order
     */
    private static String reverseColumnarTransposition(String encryptedText, int columns) {
        int rows = encryptedText.length() / columns;
        StringBuilder transposedBack = new StringBuilder(encryptedText.length());

        for (int col = 0; col < columns; col++) {
            for (int row = 0; row < rows; row++) {
                char ch = encryptedText.charAt(col + (row * columns));
                transposedBack.append(ch);
            }
        }
        return transposedBack.toString();
    }
}