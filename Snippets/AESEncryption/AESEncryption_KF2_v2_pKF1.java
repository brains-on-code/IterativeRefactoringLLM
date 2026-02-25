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

    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int AES_KEY_SIZE_BITS = 128;
    private static final int GCM_TAG_LENGTH_BITS = 128;

    private static Cipher encryptionCipher;

    public static void main(String[] args) throws Exception {
        String originalPlaintext = "Hello World";
        SecretKey aesKey = generateSecretKey();
        byte[] encryptedBytes = encrypt(originalPlaintext, aesKey);
        String decryptedPlaintext = decrypt(encryptedBytes, aesKey);

        System.out.println("Original Text: " + originalPlaintext);
        System.out.println("AES Key (Hex Form): " + bytesToHex(aesKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form): " + bytesToHex(encryptedBytes));
        System.out.println("Decrypted Text: " + decryptedPlaintext);
    }

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(AES_KEY_SIZE_BITS);
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(String plaintext, SecretKey secretKey)
            throws NoSuchAlgorithmException,
                    NoSuchPaddingException,
                    InvalidKeyException,
                    IllegalBlockSizeException,
                    BadPaddingException {
        encryptionCipher = Cipher.getInstance(AES_TRANSFORMATION);
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
        Cipher decryptionCipher = Cipher.getInstance(AES_TRANSFORMATION);
        GCMParameterSpec gcmParameterSpec =
                new GCMParameterSpec(GCM_TAG_LENGTH_BITS, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(ciphertext);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static String bytesToHex(byte[] inputBytes) {
        char[] hexCharacters = new char[inputBytes.length * 2];
        for (int index = 0; index < inputBytes.length; index++) {
            int unsignedByte = inputBytes[index] & 0xFF;
            hexCharacters[index * 2] = HEX_DIGITS[unsignedByte >>> 4];
            hexCharacters[index * 2 + 1] = HEX_DIGITS[unsignedByte & 0x0F];
        }
        return new String(hexCharacters);
    }
}