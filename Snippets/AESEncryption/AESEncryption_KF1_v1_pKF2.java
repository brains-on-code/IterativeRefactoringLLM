package com.thealgorithms.ciphers;

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
 * Demonstrates AES-GCM encryption and decryption.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation.
    }

    /** Hexadecimal characters used for byte-to-hex conversion. */
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * Cipher instance used for encryption; its IV is reused for decryption.
     * Note: In production code, avoid static mutable state and reuse of IVs.
     */
    private static Cipher encryptionCipher;

    /**
     * Example entry point demonstrating AES-GCM encryption and decryption.
     *
     * @param args command-line arguments (unused)
     */
    public static void method1(String[] args) throws Exception {
        String originalText = "Hello World";
        SecretKey key = method2();
        byte[] encryptedBytes = method3(originalText, key);
        String decryptedText = method4(encryptedBytes, key);

        System.out.println("Original Text:" + originalText);
        System.out.println("AES Key (Hex Form):" + method5(key.getEncoded()));
        System.out.println("Encrypted Text (Hex Form):" + method5(encryptedBytes));
        System.out.println("Descrypted Text:" + decryptedText);
    }

    /**
     * Generates a 128-bit AES secret key.
     *
     * @return generated AES SecretKey
     * @throws NoSuchAlgorithmException if AES algorithm is not available
     */
    public static SecretKey method2() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    /**
     * Encrypts the given plaintext using AES-GCM with the provided key.
     *
     * @param plaintext the text to encrypt
     * @param key       the AES secret key
     * @return encrypted bytes
     * @throws NoSuchAlgorithmException  if AES algorithm is not available
     * @throws NoSuchPaddingException    if padding scheme is not available
     * @throws InvalidKeyException       if the key is invalid
     * @throws IllegalBlockSizeException if the block size is invalid
     * @throws BadPaddingException       if padding is incorrect
     */
    public static byte[] method3(String plaintext, SecretKey key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
                   InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptionCipher.doFinal(plaintext.getBytes());
    }

    /**
     * Decrypts the given ciphertext using AES-GCM with the provided key.
     * Uses the IV from the encryption cipher.
     *
     * @param ciphertext the bytes to decrypt
     * @param key        the AES secret key
     * @return decrypted plaintext
     * @throws NoSuchAlgorithmException         if AES algorithm is not available
     * @throws NoSuchPaddingException           if padding scheme is not available
     * @throws InvalidKeyException              if the key is invalid
     * @throws IllegalBlockSizeException        if the block size is invalid
     * @throws BadPaddingException              if padding is incorrect
     * @throws InvalidAlgorithmParameterException if the GCM parameters are invalid
     */
    public static String method4(byte[] ciphertext, SecretKey key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
                   InvalidKeyException, IllegalBlockSizeException,
                   BadPaddingException, InvalidAlgorithmParameterException {

        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(ciphertext);
        return new String(decryptedBytes);
    }

    /**
     * Converts a byte array to its hexadecimal string representation.
     *
     * @param bytes the byte array to convert
     * @return hex string representation of the input bytes
     */
    public static String method5(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int index = 0; index < bytes.length; index++) {
            int value = bytes[index] & 0xFF;
            hexChars[index * 2] = HEX_ARRAY[value >>> 4];
            hexChars[index * 2 + 1] = HEX_ARRAY[value & 0x0F];
        }
        return new String(hexChars);
    }
}