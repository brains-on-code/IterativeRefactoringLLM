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
 * Demonstrates AES encryption and decryption using AES/GCM/NoPadding.
 * Prints key and ciphertext in hexadecimal form.
 */
public final class AESEncryption {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE_BITS = 128;
    private static final int GCM_TAG_LENGTH_BITS = 128;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private AESEncryption() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) throws Exception {
        String plainText = "Hello World";

        SecretKey secretKey = generateSecretKey();
        EncryptionResult encryptionResult = encryptText(plainText, secretKey);
        String decryptedText = decryptText(encryptionResult, secretKey);

        System.out.println("Original Text: " + plainText);
        System.out.println("AES Key (Hex): " + bytesToHex(secretKey.getEncoded()));
        System.out.println("Encrypted Text (Hex): " + bytesToHex(encryptionResult.cipherText()));
        System.out.println("Decrypted Text: " + decryptedText);
    }

    /**
     * Generates a new AES secret key.
     *
     * @return a 128-bit AES {@link SecretKey}
     * @throws NoSuchAlgorithmException if AES algorithm is not available
     */
    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE_BITS);
        return keyGenerator.generateKey();
    }

    /**
     * Container for AES/GCM encryption output.
     *
     * @param cipherText the encrypted bytes
     * @param iv the initialization vector used during encryption
     */
    public record EncryptionResult(byte[] cipherText, byte[] iv) {}

    /**
     * Encrypts the given plaintext using AES/GCM/NoPadding.
     *
     * @param plainText the text to encrypt
     * @param secretKey the AES key used for encryption
     * @return an {@link EncryptionResult} containing ciphertext and IV
     * @throws NoSuchAlgorithmException if AES algorithm is not available
     * @throws NoSuchPaddingException if the padding scheme is not available
     * @throws InvalidKeyException if the provided key is invalid
     * @throws IllegalBlockSizeException if the block size is incorrect
     * @throws BadPaddingException if a padding error occurs
     */
    public static EncryptionResult encryptText(String plainText, SecretKey secretKey)
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
     * Decrypts the given ciphertext using AES/GCM/NoPadding.
     *
     * @param encryptionResult the ciphertext and IV to decrypt
     * @param secretKey the AES key used for decryption
     * @return the decrypted plaintext as a {@link String}
     * @throws NoSuchAlgorithmException if AES algorithm is not available
     * @throws NoSuchPaddingException if the padding scheme is not available
     * @throws InvalidKeyException if the provided key is invalid
     * @throws IllegalBlockSizeException if the block size is incorrect
     * @throws BadPaddingException if a padding error occurs
     * @throws InvalidAlgorithmParameterException if the GCM parameters are invalid
     */
    public static String decryptText(EncryptionResult encryptionResult, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH_BITS, encryptionResult.iv());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);

        byte[] plainBytes = cipher.doFinal(encryptionResult.cipherText());
        return new String(plainBytes, StandardCharsets.UTF_8);
    }

    /**
     * Converts a byte array to its hexadecimal string representation.
     *
     * @param bytes the byte array to convert
     * @return a hexadecimal string representing the input bytes
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