package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

public class SimpleSubCipher {

    public String encode(String message, String substitutionAlphabetLowercase) {
        StringBuilder encodedMessage = new StringBuilder();
        Map<Character, Character> substitutionMap = new HashMap<>();

        char currentLowercaseLetter = 'a';
        char currentUppercaseLetter = 'A';

        String lowercaseSubstitution = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitution = lowercaseSubstitution.toUpperCase();

        for (int i = 0; i < lowercaseSubstitution.length(); i++) {
            substitutionMap.put(currentLowercaseLetter++, lowercaseSubstitution.charAt(i));
            substitutionMap.put(currentUppercaseLetter++, uppercaseSubstitution.charAt(i));
        }

        for (int i = 0; i < message.length(); i++) {
            char originalChar = message.charAt(i);
            if (Character.isAlphabetic(originalChar)) {
                encodedMessage.append(substitutionMap.get(originalChar));
            } else {
                encodedMessage.append(originalChar);
            }
        }

        return encodedMessage.toString();
    }

    public String decode(String encodedMessage, String substitutionAlphabetLowercase) {
        StringBuilder decodedMessage = new StringBuilder();
        Map<Character, Character> reverseSubstitutionMap = new HashMap<>();

        char currentLowercaseLetter = 'a';
        char currentUppercaseLetter = 'A';

        String lowercaseSubstitution = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitution = lowercaseSubstitution.toUpperCase();

        for (int i = 0; i < lowercaseSubstitution.length(); i++) {
            reverseSubstitutionMap.put(lowercaseSubstitution.charAt(i), currentLowercaseLetter++);
            reverseSubstitutionMap.put(uppercaseSubstitution.charAt(i), currentUppercaseLetter++);
        }

        for (int i = 0; i < encodedMessage.length(); i++) {
            char encodedChar = encodedMessage.charAt(i);
            if (Character.isAlphabetic(encodedChar)) {
                decodedMessage.append(reverseSubstitutionMap.get(encodedChar));
            } else {
                decodedMessage.append(encodedChar);
            }
        }

        return decodedMessage.toString();
    }
}