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
 * Demonstrates AES encryption and decryption in Java using AES/GCM/NoPadding.
 * The secret key and ciphertext are printed in hexadecimal form.
 */
public final class AESEncryption {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static Cipher aesCipher;

    private AESEncryption() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) throws Exception {
        String plainText = "Hello World";
        SecretKey secretKey = generateSecretKey();
        byte[] cipherText = encryptText(plainText, secretKey);
        String decryptedText = decryptText(cipherText, secretKey);

        System.out.println("Original Text: " + plainText);
        System.out.println("AES Key (Hex): " + bytesToHex(secretKey.getEncoded()));
        System.out.println("Encrypted Text (Hex): " + bytesToHex(cipherText));
        System.out.println("Decrypted Text: " + decryptedText);
    }

    /**
     * Generates a new 128-bit AES secret key.
     *
     * @return a newly generated AES {@link SecretKey}
     * @throws NoSuchAlgorithmException if AES algorithm is not available
     */
    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("AES");
        aesKeyGenerator.init(128);
        return aesKeyGenerator.generateKey();
    }

    /**
     * Encrypts the given plaintext using AES/GCM/NoPadding.
     *
     * @param plainText the text to encrypt
     * @param secretKey the AES key used for encryption
     * @return the encrypted bytes
     * @throws NoSuchPaddingException           if the padding scheme is not available
     * @throws NoSuchAlgorithmException         if the AES algorithm is not available
     * @throws InvalidKeyException              if the key is invalid
     * @throws BadPaddingException              if a padding error occurs
     * @throws IllegalBlockSizeException        if the block size is incorrect
     */
    public static byte[] encryptText(String plainText, SecretKey secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
                   InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
        aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return aesCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decrypts the given ciphertext using AES/GCM/NoPadding.
     *
     * @param cipherText the encrypted bytes
     * @param secretKey  the AES key used for decryption (must match encryption key)
     * @return the decrypted plaintext
     * @throws NoSuchPaddingException           if the padding scheme is not available
     * @throws NoSuchAlgorithmException         if the AES algorithm is not available
     * @throws InvalidKeyException              if the key is invalid
     * @throws BadPaddingException              if a padding error occurs
     * @throws IllegalBlockSizeException        if the block size is incorrect
     * @throws InvalidAlgorithmParameterException if the GCM parameters are invalid
     */
    public static String decryptText(byte[] cipherText, SecretKey secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
                   InvalidKeyException, IllegalBlockSizeException,
                   BadPaddingException, InvalidAlgorithmParameterException {

        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, aesCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] plainBytes = decryptionCipher.doFinal(cipherText);
        return new String(plainBytes, StandardCharsets.UTF_8);
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes the input byte array
     * @return a hexadecimal representation of the input
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];

        for (int index = 0; index < bytes.length; index++) {
            int value = bytes[index] & 0xFF;
            hexChars[index * 2] = HEX_ARRAY[value >>> 4];
            hexChars[index * 2 + 1] = HEX_ARRAY[value & 0x0F];
        }

        return new String(hexChars);
    }
}