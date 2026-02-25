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

        char lowercasePlainLetter = 'a';
        char uppercasePlainLetter = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int index = 0; index < lowercaseSubstitutionAlphabet.length(); index++) {
            encryptionMap.put(lowercasePlainLetter++, lowercaseSubstitutionAlphabet.charAt(index));
            encryptionMap.put(uppercasePlainLetter++, uppercaseSubstitutionAlphabet.charAt(index));
        }

        for (int index = 0; index < plaintext.length(); index++) {
            char currentCharacter = plaintext.charAt(index);
            if (Character.isAlphabetic(currentCharacter)) {
                ciphertext.append(encryptionMap.get(currentCharacter));
            } else {
                ciphertext.append(currentCharacter);
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

        char lowercasePlainLetter = 'a';
        char uppercasePlainLetter = 'A';

        String lowercaseSubstitutionAlphabet = substitutionAlphabetLowercase.toLowerCase();
        String uppercaseSubstitutionAlphabet = lowercaseSubstitutionAlphabet.toUpperCase();

        for (int index = 0; index < lowercaseSubstitutionAlphabet.length(); index++) {
            decryptionMap.put(lowercaseSubstitutionAlphabet.charAt(index), lowercasePlainLetter++);
            decryptionMap.put(uppercaseSubstitutionAlphabet.charAt(index), uppercasePlainLetter++);
        }

        for (int index = 0; index < ciphertext.length(); index++) {
            char currentCharacter = ciphertext.charAt(index);
            if (Character.isAlphabetic(currentCharacter)) {
                plaintext.append(decryptionMap.get(currentCharacter));
            } else {
                plaintext.append(currentCharacter);
            }
        }

        return plaintext.toString();
    }
}