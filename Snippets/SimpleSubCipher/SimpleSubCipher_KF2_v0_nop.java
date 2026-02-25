package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;


public class SimpleSubCipher {


    public String encode(String message, String cipherSmall) {
        StringBuilder encoded = new StringBuilder();

        Map<Character, Character> cipherMap = new HashMap<>();

        char beginSmallLetter = 'a';
        char beginCapitalLetter = 'A';

        cipherSmall = cipherSmall.toLowerCase();
        String cipherCapital = cipherSmall.toUpperCase();

        for (int i = 0; i < cipherSmall.length(); i++) {
            cipherMap.put(beginSmallLetter++, cipherSmall.charAt(i));
            cipherMap.put(beginCapitalLetter++, cipherCapital.charAt(i));
        }

        for (int i = 0; i < message.length(); i++) {
            if (Character.isAlphabetic(message.charAt(i))) {
                encoded.append(cipherMap.get(message.charAt(i)));
            } else {
                encoded.append(message.charAt(i));
            }
        }

        return encoded.toString();
    }


    public String decode(String encryptedMessage, String cipherSmall) {
        StringBuilder decoded = new StringBuilder();

        Map<Character, Character> cipherMap = new HashMap<>();

        char beginSmallLetter = 'a';
        char beginCapitalLetter = 'A';

        cipherSmall = cipherSmall.toLowerCase();
        String cipherCapital = cipherSmall.toUpperCase();

        for (int i = 0; i < cipherSmall.length(); i++) {
            cipherMap.put(cipherSmall.charAt(i), beginSmallLetter++);
            cipherMap.put(cipherCapital.charAt(i), beginCapitalLetter++);
        }

        for (int i = 0; i < encryptedMessage.length(); i++) {
            if (Character.isAlphabetic(encryptedMessage.charAt(i))) {
                decoded.append(cipherMap.get(encryptedMessage.charAt(i)));
            } else {
                decoded.append(encryptedMessage.charAt(i));
            }
        }

        return decoded.toString();
    }
}
