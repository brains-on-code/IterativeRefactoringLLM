package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class SimpleSubCipher {

    public String encode(String plainText, String substitutionAlphabetLowercase) {
        StringBuilder encodedTextBuilder = new StringBuilder();
        Map<Character, Character> substitutionMap = new HashMap<>();

        char lowercaseLetter = 'a';
        char uppercaseLetter = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int index = 0; index < lowercaseSubstitutionAlphabet.length(); index++) {
            substitutionMap.put(lowercaseLetter++, lowercaseSubstitutionAlphabet.charAt(index));
            substitutionMap.put(uppercaseLetter++, uppercaseSubstitutionAlphabet.charAt(index));
        }

        for (int index = 0; index < plainText.length(); index++) {
            char currentChar = plainText.charAt(index);
            if (Character.isAlphabetic(currentChar)) {
                encodedTextBuilder.append(substitutionMap.get(currentChar));
            } else {
                encodedTextBuilder.append(currentChar);
            }
        }

        return encodedTextBuilder.toString();
    }

    public String decode(String cipherText, String substitutionAlphabetLowercase) {
        StringBuilder decodedTextBuilder = new StringBuilder();
        Map<Character, Character> reverseSubstitutionMap = new HashMap<>();

        char lowercaseLetter = 'a';
        char uppercaseLetter = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int index = 0; index < lowercaseSubstitutionAlphabet.length(); index++) {
            reverseSubstitutionMap.put(lowercaseSubstitutionAlphabet.charAt(index), lowercaseLetter++);
            reverseSubstitutionMap.put(uppercaseSubstitutionAlphabet.charAt(index), uppercaseLetter++);
        }

        for (int index = 0; index < cipherText.length(); index++) {
            char currentChar = cipherText.charAt(index);
            if (Character.isAlphabetic(currentChar)) {
                decodedTextBuilder.append(reverseSubstitutionMap.get(currentChar));
            } else {
                decodedTextBuilder.append(currentChar);
            }
        }

        return decodedTextBuilder.toString();
    }
}