package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * The simple substitution cipher is a cipher that has been in use for many
 * hundreds of years (an excellent history is given in Simon Singhs 'the Code
 * Book'). It basically consists of substituting every plaintext character for a
 * different ciphertext character. It differs from the Caesar cipher in that the
 * cipher alphabet is not simply the alphabet shifted, it is completely jumbled.
 */
public class SimpleSubCipher {

    /**
     * Encrypt text by replacing each element with its opposite character.
     *
     * @param plaintext the message to encrypt
     * @param substitutionAlphabetLowercase the substitution alphabet in lowercase
     * @return encrypted message
     */
    public String encode(String plaintext, String substitutionAlphabetLowercase) {
        StringBuilder ciphertext = new StringBuilder();
        Map<Character, Character> encryptionMap = new HashMap<>();

        char lowercasePlainChar = 'a';
        char uppercasePlainChar = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int i = 0; i < lowercaseSubstitutionAlphabet.length(); i++) {
            encryptionMap.put(lowercasePlainChar++, lowercaseSubstitutionAlphabet.charAt(i));
            encryptionMap.put(uppercasePlainChar++, uppercaseSubstitutionAlphabet.charAt(i));
        }

        for (int i = 0; i < plaintext.length(); i++) {
            char currentChar = plaintext.charAt(i);
            if (Character.isAlphabetic(currentChar)) {
                ciphertext.append(encryptionMap.get(currentChar));
            } else {
                ciphertext.append(currentChar);
            }
        }

        return ciphertext.toString();
    }

    /**
     * Decrypt message by replacing each element with its opposite character in
     * cipher.
     *
     * @param ciphertext the encrypted message
     * @param substitutionAlphabetLowercase the substitution alphabet in lowercase
     * @return decrypted message
     */
    public String decode(String ciphertext, String substitutionAlphabetLowercase) {
        StringBuilder plaintext = new StringBuilder();
        Map<Character, Character> decryptionMap = new HashMap<>();

        char lowercasePlainChar = 'a';
        char uppercasePlainChar = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int i = 0; i < lowercaseSubstitutionAlphabet.length(); i++) {
            decryptionMap.put(lowercaseSubstitutionAlphabet.charAt(i), lowercasePlainChar++);
            decryptionMap.put(uppercaseSubstitutionAlphabet.charAt(i), uppercasePlainChar++);
        }

        for (int i = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);
            if (Character.isAlphabetic(currentChar)) {
                plaintext.append(decryptionMap.get(currentChar));
            } else {
                plaintext.append(currentChar);
            }
        }

        return plaintext.toString();
    }
}