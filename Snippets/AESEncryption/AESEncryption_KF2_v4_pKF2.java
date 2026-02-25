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

public final class AESEncryption {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE_BITS = 128;
    private static final int GCM_TAG_LENGTH_BITS = 128;
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    private static Cipher encryptionCipher;

    private AESEncryption() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) throws Exception {
        String plainText = "Hello World";
        SecretKey secretKey = generateSecretKey();

        byte[] cipherText = encrypt(plainText, secretKey);
        String decryptedText = decrypt(cipherText, secretKey);

        System.out.println("Original Text: " + plainText);
        System.out.println("AES Key (Hex): " + bytesToHex(secretKey.getEncoded()));
        System.out.println("Encrypted Text (Hex): " + bytesToHex(cipherText));
        System.out.println("Decrypted Text: " + decryptedText);
    }

    /**
     * Generates a new AES secret key.
     *
     * @return a newly generated {@link SecretKey} for AES.
     * @throws NoSuchAlgorithmException if AES algorithm is not available.
     */
    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE_BITS);
        return keyGenerator.generateKey();
    }

    /**
     * Encrypts the given plaintext using AES-GCM.
     *
     * @param plainText the text to encrypt.
     * @param secretKey the AES key used for encryption.
     * @return the encrypted bytes.
     * @throws NoSuchAlgorithmException    if AES algorithm is not available.
     * @throws NoSuchPaddingException      if the padding scheme is not available.
     * @throws InvalidKeyException         if the key is invalid.
     * @throws IllegalBlockSizeException   if the block size is invalid.
     * @throws BadPaddingException         if padding is incorrect.
     */
    public static byte[] encrypt(String plainText, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException {

        encryptionCipher = Cipher.getInstance(AES_TRANSFORMATION);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        return encryptionCipher.doFinal(plainTextBytes);
    }

    /**
     * Decrypts the given ciphertext using AES-GCM.
     *
     * @param cipherText the encrypted bytes.
     * @param secretKey  the AES key used for decryption.
     * @return the decrypted plaintext.
     * @throws NoSuchAlgorithmException           if AES algorithm is not available.
     * @throws NoSuchPaddingException             if the padding scheme is not available.
     * @throws InvalidKeyException                if the key is invalid.
     * @throws IllegalBlockSizeException          if the block size is invalid.
     * @throws BadPaddingException                if padding is incorrect.
     * @throws InvalidAlgorithmParameterException if the GCM parameters are invalid.
     */
    public static String decrypt(byte[] cipherText, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidAlgorithmParameterException {

        Cipher decryptionCipher = Cipher.getInstance(AES_TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec =
                new GCMParameterSpec(GCM_TAG_LENGTH_BITS, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] plainTextBytes = decryptionCipher.doFinal(cipherText);
        return new String(plainTextBytes, StandardCharsets.UTF_8);
    }

    /**
     * Converts a byte array to its hexadecimal string representation.
     *
     * @param bytes the byte array to convert.
     * @return a hexadecimal string representation of the input bytes.
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int index = 0; index < bytes.length; index++) {
            int value = bytes[index] & 0xFF;
            hexChars[index * 2] = HEX_DIGITS[value >>> 4];
            hexChars[index * 2 + 1] = HEX_DIGITS[value & 0x0F];
        }

        return new String(hexChars);
    }
}