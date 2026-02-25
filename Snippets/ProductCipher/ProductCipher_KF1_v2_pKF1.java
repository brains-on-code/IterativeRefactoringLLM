package com.thealgorithms.ciphers;

import java.util.Scanner;

final class SubstitutionTranspositionCipher {

    private static final int SUBSTITUTION_SHIFT = 5;
    private static final char PADDING_CHARACTER = '/';

    private SubstitutionTranspositionCipher() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the input to be encrypted: ");
            String plainText = scanner.nextLine();

            System.out.println();
            System.out.println("Enter a number: ");
            int keySize = scanner.nextInt();

            // Substitution step
            StringBuilder substitutedText = new StringBuilder();
            for (int index = 0; index < plainText.length(); index++) {
                char originalCharacter = plainText.charAt(index);
                substitutedText.append((char) (originalCharacter + SUBSTITUTION_SHIFT));
            }

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            // Padding for transposition
            String paddedText = substitutedText.toString();
            int remainder = paddedText.length() % keySize;
            if (remainder != 0) {
                int paddingCount = keySize - remainder;
                StringBuilder paddedBuilder = new StringBuilder(paddedText);
                while (paddingCount-- > 0) {
                    paddedBuilder.append(PADDING_CHARACTER);
                }
                paddedText = paddedBuilder.toString();
            }

            StringBuilder transposedText = new StringBuilder();
            System.out.println();
            System.out.println("Transposition Matrix: ");
            int rowCount = paddedText.length() / keySize;
            for (int columnIndex = 0; columnIndex < keySize; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    char matrixCharacter = paddedText.charAt(columnIndex + (rowIndex * keySize));
                    System.out.print(matrixCharacter);
                    transposedText.append(matrixCharacter);
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedText);

            // Reverse transposition
            int columnCount = transposedText.length() / keySize;
            StringBuilder reverseTransposedText = new StringBuilder();
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < transposedText.length() / columnCount; rowIndex++) {
                    char matrixCharacter = transposedText.charAt(columnIndex + (rowIndex * columnCount));
                    reverseTransposedText.append(matrixCharacter);
                }
            }

            // Reverse substitution
            StringBuilder decryptedText = new StringBuilder();
            for (int index = 0; index < reverseTransposedText.length(); index++) {
                char substitutedCharacter = reverseTransposedText.charAt(index);
                decryptedText.append((char) (substitutedCharacter - SUBSTITUTION_SHIFT));
            }

            System.out.println("Plaintext: ");
            System.out.println(decryptedText);
        }
    }
}