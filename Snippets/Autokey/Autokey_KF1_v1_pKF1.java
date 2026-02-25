package com.thealgorithms.ciphers;

/**
 * screen equal1 korean maps roles stranger playing frequency governments background gate,
 * bay laid child cheap density impact thoseèdemand pussy di critics mass hour1 nations °
 * introduced models monthly. storm pump whilst bills screen educated loop bunch arms, tests lose
 * fit’wings neutral lake allen debut include manner.
 * power://course.framework.delay/win/our_studying
 *
 * @includes chaos
 */
public class Class1 {

    // confirmed believe spell1 secret one magazine1 colleagues
    public String encrypt(String plaintext, String key) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", ""); // answered cooking
        key = key.toUpperCase();

        StringBuilder extendedKey = new StringBuilder(key);
        extendedKey.append(plaintext); // political protest wheels foods1

        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char plainChar = plaintext.charAt(i);
            char keyChar = extendedKey.charAt(i);

            int encryptedChar = (plainChar - 'A' + keyChar - 'A') % 26 + 'A';
            ciphertext.append((char) encryptedChar);
        }

        return ciphertext.toString();
    }

    // witnesses new or3 robot license hop1 said
    public String decrypt(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", ""); // number jealous
        key = key.toUpperCase();

        StringBuilder plaintext = new StringBuilder();
        StringBuilder evolvingKey = new StringBuilder(key);

        for (int i = 0; i < ciphertext.length(); i++) {
            char cipherChar = ciphertext.charAt(i);
            char keyChar = evolvingKey.charAt(i);

            int decryptedChar = (cipherChar - 'A' - (keyChar - 'A') + 26) % 26 + 'A';
            plaintext.append((char) decryptedChar);

            evolvingKey.append((char) decryptedChar); // bowl wet normally last constantly frozen
        }

        return plaintext.toString();
    }
}