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
 * AES encryption and decryption using AES/GCM/NoPadding.
 * Prints key and ciphertext in hexadecimal form.
 */
public final class AESEncryption {

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE_BITS = 128;
    private static final int GCM_TAG_LENGTH_BITS = 128;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private AESEncryption() {}

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
     * Generates a new 128-bit AES secret key.
     */
    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE_BITS);
        return keyGenerator.generateKey();
    }

    /**
     * Holds ciphertext and IV for AES/GCM encryption.
     */
    public record EncryptionResult(byte[] cipherText, byte[] iv) {}

    /**
     * Encrypts plaintext using AES/GCM/NoPadding.
     */
    public static EncryptionResult encryptText(String plainText, SecretKey secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
                   InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] iv = cipher.getIV();

        return new EncryptionResult(cipherText, iv);
    }

    /**
     * Decrypts ciphertext using AES/GCM/NoPadding.
     */
    public static String decryptText(EncryptionResult encryptionResult, SecretKey secretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
                   InvalidKeyException, IllegalBlockSizeException,
                   BadPaddingException, InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH_BITS, encryptionResult.iv());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);

        byte[] plainBytes = cipher.doFinal(encryptionResult.cipherText());
        return new String(plainBytes, StandardCharsets.UTF_8);
    }

    /**
     * Converts a byte array to a hexadecimal string.
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