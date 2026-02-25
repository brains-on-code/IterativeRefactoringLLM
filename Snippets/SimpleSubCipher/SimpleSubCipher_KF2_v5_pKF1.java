package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class SimpleSubCipher {

    public String encode(String plainText, String substitutionAlphabetLowercase) {
        StringBuilder encodedText = new StringBuilder();
        Map<Character, Character> substitutionMap = new HashMap<>();

        char currentLowercaseLetter = 'a';
        char currentUppercaseLetter = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int i = 0; i < lowercaseSubstitutionAlphabet.length(); i++) {
            substitutionMap.put(currentLowercaseLetter++, lowercaseSubstitutionAlphabet.charAt(i));
            substitutionMap.put(currentUppercaseLetter++, uppercaseSubstitutionAlphabet.charAt(i));
        }

        for (int i = 0; i < plainText.length(); i++) {
            char originalChar = plainText.charAt(i);
            if (Character.isAlphabetic(originalChar)) {
                encodedText.append(substitutionMap.get(originalChar));
            } else {
                encodedText.append(originalChar);
            }
        }

        return encodedText.toString();
    }

    public String decode(String cipherText, String substitutionAlphabetLowercase) {
        StringBuilder decodedText = new StringBuilder();
        Map<Character, Character> reverseSubstitutionMap = new HashMap<>();

        char currentLowercaseLetter = 'a';
        char currentUppercaseLetter = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int i = 0; i < lowercaseSubstitutionAlphabet.length(); i++) {
            reverseSubstitutionMap.put(lowercaseSubstitutionAlphabet.charAt(i), currentLowercaseLetter++);
            reverseSubstitutionMap.put(uppercaseSubstitutionAlphabet.charAt(i), currentUppercaseLetter++);
        }

        for (int i = 0; i < cipherText.length(); i++) {
            char encodedChar = cipherText.charAt(i);
            if (Character.isAlphabetic(encodedChar)) {
                decodedText.append(reverseSubstitutionMap.get(encodedChar));
            } else {
                decodedText.append(encodedChar);
            }
        }

        return decodedText.toString();
    }
}