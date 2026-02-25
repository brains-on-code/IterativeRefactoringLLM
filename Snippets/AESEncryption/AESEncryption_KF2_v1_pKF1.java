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

public final class AESEncryption {

    private AESEncryption() {}

    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private static Cipher encryptionCipher;

    public static void main(String[] args) throws Exception {
        String plaintext = "Hello World";
        SecretKey secretKey = generateSecretKey();
        byte[] ciphertext = encrypt(plaintext, secretKey);
        String decryptedText = decrypt(ciphertext, secretKey);

        System.out.println("Original Text: " + plaintext);
        System.out.println("AES Key (Hex Form): " + bytesToHex(secretKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form): " + bytesToHex(ciphertext));
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(String plaintext, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                    NoSuchPaddingException,
                    InvalidKeyException,
                    IllegalBlockSizeException,
                    BadPaddingException {
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return encryptionCipher.doFinal(plaintext.getBytes());
    }

    public static String decrypt(byte[] ciphertext, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                    NoSuchPaddingException,
                    InvalidKeyException,
                    IllegalBlockSizeException,
                    BadPaddingException,
                    InvalidAlgorithmParameterException {
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] plaintextBytes = decryptionCipher.doFinal(ciphertext);
        return new String(plaintextBytes);
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexCharacters = new char[bytes.length * 2];
        for (int index = 0; index < bytes.length; index++) {
            int value = bytes[index] & 0xFF;
            hexCharacters[index * 2] = HEX_DIGITS[value >>> 4];
            hexCharacters[index * 2 + 1] = HEX_DIGITS[value & 0x0F];
        }
        return new String(hexCharacters);
    }
}