package com.thealgorithms.ciphers;

import java.util.Scanner;

final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    public static void method1(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the input to be encrypted: ");
            String inputText = scanner.nextLine();

            System.out.println();
            System.out.println("Enter a number (matrix column size): ");
            int columns = scanner.nextInt();

            // Step 1: Simple substitution (shift each character by +5)
            StringBuilder substitutedText = new StringBuilder();
            for (int i = 0; i < inputText.length(); i++) {
                char ch = inputText.charAt(i);
                substitutedText.append((char) (ch + 5));
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            // Step 2: Pad substituted text so its length is divisible by 'columns'
            String paddedText = substitutedText.toString();
            int remainder = paddedText.length() % columns;
            if (remainder != 0) {
                int paddingNeeded = columns - remainder;
                while (paddingNeeded-- > 0) {
                    paddedText += "/";
                }
            }

            // Step 3: Columnar transposition (encryption)
            StringBuilder encryptedText = new StringBuilder();
            int rows = paddedText.length() / columns;

            System.out.println();
            System.out.println("Transposition Matrix: ");
            for (int col = 0; col < columns; col++) {
                for (int row = 0; row < rows; row++) {
                    char ch = paddedText.charAt(col + (row * columns));
                    System.out.print(ch);
                    encryptedText.append(ch);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(encryptedText);

            // Step 4: Reverse columnar transposition (decryption)
            int decryptedRows = encryptedText.length() / columns;
            StringBuilder transposedBack = new StringBuilder();

            for (int col = 0; col < columns; col++) {
                for (int row = 0; row < decryptedRows; row++) {
                    char ch = encryptedText.charAt(col + (row * columns));
                    transposedBack.append(ch);
                }
            }

            // Step 5: Reverse substitution (shift each character by -5)
            StringBuilder plaintext = new StringBuilder();
            for (int i = 0; i < transposedBack.length(); i++) {
                char ch = transposedBack.charAt(i);
                plaintext.append((char) (ch - 5));
            }

            System.out.println("Plaintext: ");
            System.out.println(plaintext);
        }
    }
}