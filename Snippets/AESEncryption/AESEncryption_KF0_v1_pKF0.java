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
 * Example program showing AES-GCM encryption and decryption in Java.
 * Secret keys and ciphertext are displayed in hexadecimal form.
 */
public final class AESEncryption {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE_BITS = 128;
    private static final int GCM_TAG_LENGTH_BITS = 128;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private AESEncryption() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) throws Exception {
        String plainText = "Hello World";

        SecretKey secretKey = generateSecretKey();
        EncryptionResult encryptionResult = encrypt(plainText, secretKey);
        String decryptedText = decrypt(encryptionResult, secretKey);

        System.out.println("Original Text: " + plainText);
        System.out.println("AES Key (Hex Form): " + bytesToHex(secretKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form): " + bytesToHex(encryptionResult.cipherText()));
        System.out.println("Decrypted Text: " + decryptedText);
    }

    /**
     * Generates a new AES secret key.
     */
    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE_BITS);
        return keyGenerator.generateKey();
    }

    /**
     * Encrypts the given plaintext using AES-GCM.
     *
     * @param plainText the text to encrypt
     * @param secretKey the AES key
     * @return an EncryptionResult containing ciphertext and IV
     */
    public static EncryptionResult encrypt(String plainText, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException {

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] iv = cipher.getIV();

        return new EncryptionResult(cipherText, iv);
    }

    /**
     * Decrypts the given ciphertext using AES-GCM.
     *
     * @param encryptionResult the ciphertext and IV
     * @param secretKey        the AES key
     * @return the decrypted plaintext
     */
    public static String decrypt(EncryptionResult encryptionResult, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH_BITS, encryptionResult.iv());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);

        byte[] plainTextBytes = cipher.doFinal(encryptionResult.cipherText());
        return new String(plainTextBytes, StandardCharsets.UTF_8);
    }

    /**
     * Converts a byte array into a hexadecimal string.
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int index = 0; index < bytes.length; index++) {
            int value = bytes[index] & 0xFF;
            int hexIndex = index * 2;
            hexChars[hexIndex] = HEX_ARRAY[value >>> 4];
            hexChars[hexIndex + 1] = HEX_ARRAY[value & 0x0F];
        }

        return new String(hexChars);
    }

    /**
     * Holds the result of an encryption operation: ciphertext and IV.
     */
    public record EncryptionResult(byte[] cipherText, byte[] iv) {}
}