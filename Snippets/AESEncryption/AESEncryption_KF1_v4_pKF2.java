package com.thealgorithms.ciphers;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 * AES-GCM encryption and decryption example.
 */
public final class AesGcmExample {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_GCM_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE_BITS = 128;
    private static final int GCM_TAG_LENGTH_BITS = 128;

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * Cipher used for encryption; its IV is reused for decryption.
     * Note: This is for demonstration only. Avoid static mutable state and IV reuse in real applications.
     */
    private static Cipher encryptionCipher;

    private AesGcmExample() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) throws Exception {
        String originalText = "Hello World";
        SecretKey key = generateAesKey();
        byte[] encryptedBytes = encrypt(originalText, key);
        String decryptedText = decrypt(encryptedBytes, key);

        System.out.println("Original Text: " + originalText);
        System.out.println("AES Key (Hex): " + toHex(key.getEncoded()));
        System.out.println("Encrypted Text (Hex): " + toHex(encryptedBytes));
        System.out.println("Decrypted Text: " + decryptedText);
    }

    /**
     * Generates a 128-bit AES key.
     *
     * @return AES secret key
     * @throws NoSuchAlgorithmException if AES is not available
     */
    public static SecretKey generateAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE_BITS);
        return keyGenerator.generateKey();
    }

    /**
     * Encrypts plaintext using AES-GCM.
     *
     * @param plaintext text to encrypt
     * @param key       AES key
     * @return ciphertext bytes
     * @throws NoSuchAlgorithmException  if AES is not available
     * @throws NoSuchPaddingException    if padding is not available
     * @throws InvalidKeyException       if the key is invalid
     * @throws IllegalBlockSizeException if the block size is invalid
     * @throws BadPaddingException       if padding is incorrect
     */
    public static byte[] encrypt(String plaintext, SecretKey key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
                   InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        encryptionCipher = Cipher.getInstance(AES_GCM_TRANSFORMATION);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptionCipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decrypts ciphertext using AES-GCM and the IV from the encryption cipher.
     *
     * @param ciphertext bytes to decrypt
     * @param key        AES key
     * @return decrypted text
     * @throws NoSuchAlgorithmException           if AES is not available
     * @throws NoSuchPaddingException             if padding is not available
     * @throws InvalidKeyException                if the key is invalid
     * @throws IllegalBlockSizeException          if the block size is invalid
     * @throws BadPaddingException                if padding is incorrect
     * @throws InvalidAlgorithmParameterException if GCM parameters are invalid
     */
    public static String decrypt(byte[] ciphertext, SecretKey key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
                   InvalidKeyException, IllegalBlockSizeException,
                   BadPaddingException, InvalidAlgorithmParameterException {

        Cipher decryptionCipher = Cipher.getInstance(AES_GCM_TRANSFORMATION);
        GCMParameterSpec gcmSpec =
                new GCMParameterSpec(GCM_TAG_LENGTH_BITS, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(ciphertext);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * Converts bytes to a hexadecimal string.
     *
     * @param bytes input bytes
     * @return hex string
     */
    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int index = 0; index < bytes.length; index++) {
            int value = bytes[index] & 0xFF;
            hexChars[index * 2] = HEX_ARRAY[value >>> 4];
            hexChars[index * 2 + 1] = HEX_ARRAY[value & 0x0F];
        }
        return new String(hexChars);
    }
}