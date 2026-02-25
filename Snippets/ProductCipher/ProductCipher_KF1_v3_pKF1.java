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
            StringBuilder substitutedTextBuilder = new StringBuilder();
            for (int charIndex = 0; charIndex < plainText.length(); charIndex++) {
                char originalCharacter = plainText.charAt(charIndex);
                substitutedTextBuilder.append((char) (originalCharacter + SUBSTITUTION_SHIFT));
            }

            String substitutedText = substitutedTextBuilder.toString();

            System.out.println();
            System.out.println("Substituted text: ");
            System.out.println(substitutedText);

            // Padding for transposition
            String paddedText = substitutedText;
            int paddingRemainder = paddedText.length() % keySize;
            if (paddingRemainder != 0) {
                int paddingCharactersToAdd = keySize - paddingRemainder;
                StringBuilder paddedTextBuilder = new StringBuilder(paddedText);
                while (paddingCharactersToAdd-- > 0) {
                    paddedTextBuilder.append(PADDING_CHARACTER);
                }
                paddedText = paddedTextBuilder.toString();
            }

            StringBuilder transposedTextBuilder = new StringBuilder();
            System.out.println();
            System.out.println("Transposition Matrix: ");
            int rowCount = paddedText.length() / keySize;
            for (int columnIndex = 0; columnIndex < keySize; columnIndex++) {
                for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                    char matrixCharacter = paddedText.charAt(columnIndex + (rowIndex * keySize));
                    System.out.print(matrixCharacter);
                    transposedTextBuilder.append(matrixCharacter);
                }
                System.out.println();
            }

            String transposedText = transposedTextBuilder.toString();

            System.out.println();
            System.out.println("Final encrypted text: ");
            System.out.println(transposedText);

            // Reverse transposition
            int reverseRowCount = transposedText.length() / keySize;
            StringBuilder reverseTransposedTextBuilder = new StringBuilder();
            for (int columnIndex = 0; columnIndex < reverseRowCount; columnIndex++) {
                for (int rowIndex = 0; rowIndex < transposedText.length() / reverseRowCount; rowIndex++) {
                    char matrixCharacter = transposedText.charAt(columnIndex + (rowIndex * reverseRowCount));
                    reverseTransposedTextBuilder.append(matrixCharacter);
                }
            }

            String reverseTransposedText = reverseTransposedTextBuilder.toString();

            // Reverse substitution
            StringBuilder decryptedTextBuilder = new StringBuilder();
            for (int charIndex = 0; charIndex < reverseTransposedText.length(); charIndex++) {
                char substitutedCharacter = reverseTransposedText.charAt(charIndex);
                decryptedTextBuilder.append((char) (substitutedCharacter - SUBSTITUTION_SHIFT));
            }

            String decryptedText = decryptedTextBuilder.toString();

            System.out.println("Plaintext: ");
            System.out.println(decryptedText);
        }
    }
}