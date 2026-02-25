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
    private static final int AES_KEY_SIZE = 128;
    private static final int GCM_TAG_LENGTH = 128;
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static Cipher encryptionCipher;

    private AESEncryption() {
        // Prevent instantiation of utility class
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

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE);
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(String plainText, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException {

        encryptionCipher = Cipher.getInstance(AES_TRANSFORMATION);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return encryptionCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(byte[] cipherText, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidAlgorithmParameterException {

        Cipher decryptionCipher = Cipher.getInstance(AES_TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec =
                new GCMParameterSpec(GCM_TAG_LENGTH, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);

        byte[] plainTextBytes = decryptionCipher.doFinal(cipherText);
        return new String(plainTextBytes, StandardCharsets.UTF_8);
    }

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