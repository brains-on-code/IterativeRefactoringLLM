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
 * This example program shows how AES encryption and decryption can be done in
 * Java. Please note that secret key and encrypted text is unreadable binary and
 * hence in the following program we display it in hexadecimal format of the
 * underlying bytes.
 */
public final class AESEncryption {

    private AESEncryption() {}

    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private static Cipher encryptionCipher;

    /**
     * 1. Generate a plain text for encryption
     * 2. Get a secret key (printed in hexadecimal form). In actual use this must
     *    be encrypted and kept safe. The same key is required for decryption.
     */
    public static void main(String[] args) throws Exception {
        String originalPlainText = "Hello World";
        SecretKey secretKey = generateSecretKey();
        byte[] encryptedBytes = encrypt(originalPlainText, secretKey);
        String decryptedPlainText = decrypt(encryptedBytes, secretKey);

        System.out.println("Original Text: " + originalPlainText);
        System.out.println("AES Key (Hex Form): " + bytesToHex(secretKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form): " + bytesToHex(encryptedBytes));
        System.out.println("Decrypted Text: " + decryptedPlainText);
    }

    /**
     * Generates an AES encryption key. In your actual programs, this should be
     * safely stored.
     *
     * @return SecretKey used for encryption and decryption
     * @throws NoSuchAlgorithmException if AES algorithm is not available
     */
    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("AES");
        aesKeyGenerator.init(128); // The AES key size in number of bits
        return aesKeyGenerator.generateKey();
    }

    /**
     * Encrypts the given plain text using AES and the provided secret key.
     *
     * @param plainText the text to encrypt
     * @param secretKey the AES secret key
     * @return encrypted bytes
     */
    public static byte[] encrypt(String plainText, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException {
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return encryptionCipher.doFinal(plainText.getBytes());
    }

    /**
     * Decrypts the given cipher text using AES and the provided secret key.
     *
     * @param cipherText the encrypted bytes
     * @param secretKey  the AES secret key used for encryption
     * @return decrypted plain text
     */
    public static String decrypt(byte[] cipherText, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                   NoSuchPaddingException,
                   InvalidKeyException,
                   IllegalBlockSizeException,
                   BadPaddingException,
                   InvalidAlgorithmParameterException {
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec =
                new GCMParameterSpec(128, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(cipherText);
        return new String(decryptedBytes);
    }

    /**
     * Convert a binary byte array into readable hex form.
     *
     * @param bytes the byte array to convert
     * @return hex string representation
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexCharacters = new char[bytes.length * 2];
        for (int index = 0; index < bytes.length; index++) {
            int unsignedByte = bytes[index] & 0xFF;
            hexCharacters[index * 2] = HEX_DIGITS[unsignedByte >>> 4];
            hexCharacters[index * 2 + 1] = HEX_DIGITS[unsignedByte & 0x0F];
        }
        return new String(hexCharacters);
    }
}