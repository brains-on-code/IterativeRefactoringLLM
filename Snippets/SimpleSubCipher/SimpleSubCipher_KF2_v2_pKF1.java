package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class SimpleSubCipher {

    public String encode(String plainText, String substitutionAlphabetLowercase) {
        StringBuilder cipherTextBuilder = new StringBuilder();
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
                cipherTextBuilder.append(substitutionMap.get(currentChar));
            } else {
                cipherTextBuilder.append(currentChar);
            }
        }

        return cipherTextBuilder.toString();
    }

    public String decode(String cipherText, String substitutionAlphabetLowercase) {
        StringBuilder plainTextBuilder = new StringBuilder();
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
                plainTextBuilder.append(reverseSubstitutionMap.get(currentChar));
            } else {
                plainTextBuilder.append(currentChar);
            }
        }

        return plainTextBuilder.toString();
    }
}