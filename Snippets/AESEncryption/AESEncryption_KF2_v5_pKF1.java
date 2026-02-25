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

    private AESEncryption() {}

    private static final char[] HEX_ALPHABET = "0123456789ABCDEF".toCharArray();
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_GCM_NO_PADDING_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE_IN_BITS = 128;
    private static final int GCM_TAG_LENGTH_IN_BITS = 128;

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
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE_IN_BITS);
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(String plaintext, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                    NoSuchPaddingException,
                    InvalidKeyException,
                    IllegalBlockSizeException,
                    BadPaddingException {
        encryptionCipher = Cipher.getInstance(AES_GCM_NO_PADDING_TRANSFORMATION);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return encryptionCipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(byte[] ciphertext, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                    NoSuchPaddingException,
                    InvalidKeyException,
                    IllegalBlockSizeException,
                    BadPaddingException,
                    InvalidAlgorithmParameterException {
        Cipher decryptionCipher = Cipher.getInstance(AES_GCM_NO_PADDING_TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec =
                new GCMParameterSpec(GCM_TAG_LENGTH_IN_BITS, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(ciphertext);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int byteIndex = 0; byteIndex < bytes.length; byteIndex++) {
            int unsignedByte = bytes[byteIndex] & 0xFF;
            hexChars[byteIndex * 2] = HEX_ALPHABET[unsignedByte >>> 4];
            hexChars[byteIndex * 2 + 1] = HEX_ALPHABET[unsignedByte & 0x0F];
        }
        return new String(hexChars);
    }
}