package com.thealgorithms.ciphers;

import java.util.Scanner;

final class SubstitutionTranspositionCipher {

    private SubstitutionTranspositionCipher() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the input to be encrypted: ");
            String inputText = scanner.nextLine();
            System.out.println(" ");
            System.out.println("Enter a number: ");
            int key = scanner.nextInt();

            // Substitution step
            StringBuffer substitutedText = new StringBuffer();
            for (int i = 0; i < inputText.length(); i++) {
                char currentChar = inputText.charAt(i);
                substitutedText.append((char) (currentChar + 5));
            }
            System.out.println(" ");
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            // Padding for transposition
            String paddedText = substitutedText.toString();
            int paddingNeeded = paddedText.length() % key;
            if (paddingNeeded != 0) {
                paddingNeeded = key - paddingNeeded;
                while (paddingNeeded-- > 0) {
                    paddedText += "/";
                }
            }

            StringBuffer transposedText = new StringBuffer();
            System.out.println(" ");
            System.out.println("Transposition Matrix: ");
            int rows = paddedText.length() / key;
            for (int col = 0; col < key; col++) {
                for (int row = 0; row < rows; row++) {
                    char matrixChar = paddedText.charAt(col + (row * key));
                    System.out.print(matrixChar);
                    transposedText.append(matrixChar);
                }
                System.out.println();
            }
            System.out.println(" ");
            System.out.println("Final encrypted text: ");
            System.out.println(transposedText);

            // Reverse transposition
            int columns = transposedText.length() / key;
            StringBuffer reverseTransposedText = new StringBuffer();
            for (int col = 0; col < columns; col++) {
                for (int row = 0; row < transposedText.length() / columns; row++) {
                    char matrixChar = transposedText.charAt(col + (row * columns));
                    reverseTransposedText.append(matrixChar);
                }
            }

            // Reverse substitution
            StringBuffer decryptedText = new StringBuffer();
            for (int i = 0; i < reverseTransposedText.length(); i++) {
                char currentChar = reverseTransposedText.charAt(i);
                decryptedText.append((char) (currentChar - 5));
            }

            System.out.println("Plaintext: ");
            System.out.println(decryptedText);
        }
    }
}