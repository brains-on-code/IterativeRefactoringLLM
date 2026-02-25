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
        StringBuilder ciphertextBuilder = new StringBuilder();
        Map<Character, Character> encryptionMap = new HashMap<>();

        char currentLowercasePlainChar = 'a';
        char currentUppercasePlainChar = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int index = 0; index < lowercaseSubstitutionAlphabet.length(); index++) {
            encryptionMap.put(currentLowercasePlainChar++, lowercaseSubstitutionAlphabet.charAt(index));
            encryptionMap.put(currentUppercasePlainChar++, uppercaseSubstitutionAlphabet.charAt(index));
        }

        for (int index = 0; index < plaintext.length(); index++) {
            char currentChar = plaintext.charAt(index);
            if (Character.isAlphabetic(currentChar)) {
                ciphertextBuilder.append(encryptionMap.get(currentChar));
            } else {
                ciphertextBuilder.append(currentChar);
            }
        }

        return ciphertextBuilder.toString();
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
        StringBuilder plaintextBuilder = new StringBuilder();
        Map<Character, Character> decryptionMap = new HashMap<>();

        char currentLowercasePlainChar = 'a';
        char currentUppercasePlainChar = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int index = 0; index < lowercaseSubstitutionAlphabet.length(); index++) {
            decryptionMap.put(lowercaseSubstitutionAlphabet.charAt(index), currentLowercasePlainChar++);
            decryptionMap.put(uppercaseSubstitutionAlphabet.charAt(index), currentUppercasePlainChar++);
        }

        for (int index = 0; index < ciphertext.length(); index++) {
            char currentChar = ciphertext.charAt(index);
            if (Character.isAlphabetic(currentChar)) {
                plaintextBuilder.append(decryptionMap.get(currentChar));
            } else {
                plaintextBuilder.append(currentChar);
            }
        }

        return plaintextBuilder.toString();
    }
}