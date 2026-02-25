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
            System.out.println("Enter a number: ");
            int key = scanner.nextInt();

            // Substitution step
            StringBuilder substitutedText = new StringBuilder();
            for (int i = 0; i < inputText.length(); i++) {
                char currentChar = inputText.charAt(i);
                substitutedText.append((char) (currentChar + 5));
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            // Padding for transposition
            String paddedText = substitutedText.toString();
            int remainder = paddedText.length() % key;
            if (remainder != 0) {
                int paddingNeeded = key - remainder;
                StringBuilder paddedBuilder = new StringBuilder(paddedText);
                for (int i = 0; i < paddingNeeded; i++) {
                    paddedBuilder.append('/');
                }
                paddedText = paddedBuilder.toString();
            }

            // Transposition matrix and encrypted text
            StringBuilder encryptedText = new StringBuilder();
            int rows = paddedText.length() / key;

            System.out.println();
            System.out.println("Transposition Matrix: ");
            for (int col = 0; col < key; col++) {
                for (int row = 0; row < rows; row++) {
                    char currentChar = paddedText.charAt(col + (row * key));
                    System.out.print(currentChar);
                    encryptedText.append(currentChar);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(encryptedText);

            // Reverse transposition
            int columns = encryptedText.length() / key;
            StringBuilder transposedBack = new StringBuilder();
            for (int col = 0; col < columns; col++) {
                for (int row = 0; row < key; row++) {
                    char currentChar = encryptedText.charAt(col + (row * columns));
                    transposedBack.append(currentChar);
                }
            }

            // Reverse substitution
            StringBuilder decryptedText = new StringBuilder();
            for (int i = 0; i < transposedBack.length(); i++) {
                char currentChar = transposedBack.charAt(i);
                decryptedText.append((char) (currentChar - 5));
            }

            System.out.println("Plaintext: ");
            System.out.println(decryptedText);
        }
    }
}