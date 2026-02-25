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
 * Demonstrates AES-GCM encryption and decryption.
 */
public final class Class1 {

    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
    private static Cipher encryptionCipher;

    private Class1() {
        // Utility class; prevent instantiation.
    }

    public static void main(String[] args) throws Exception {
        String originalText = "Hello World";
        SecretKey aesKey = generateAesKey();
        byte[] encryptedBytes = encrypt(originalText, aesKey);
        String decryptedText = decrypt(encryptedBytes, aesKey);

        System.out.println("Original Text: " + originalText);
        System.out.println("AES Key (Hex Form): " + toHex(aesKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form): " + toHex(encryptedBytes));
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public static SecretKey generateAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(String plaintext, SecretKey key)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException {

        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        return encryptionCipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(byte[] ciphertext, SecretKey key)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidAlgorithmParameterException {

        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(ciphertext);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static String toHex(byte[] bytes) {
        char[] hexCharsArray = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;
            hexCharsArray[i * 2] = HEX_CHARS[value >>> 4];
            hexCharsArray[i * 2 + 1] = HEX_CHARS[value & 0x0F];
        }
        return new String(hexCharsArray);
    }
}