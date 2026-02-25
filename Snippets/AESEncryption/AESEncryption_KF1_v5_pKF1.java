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

public final class AesGcmExample {

    private AesGcmExample() {}

    private static final char[] HEX_ALPHABET = "0123456789ABCDEF".toCharArray();
    private static Cipher encryptionCipher;

    public static void main(String[] args) throws Exception {
        String plaintext = "Hello World";
        SecretKey aesKey = generateAesKey();
        byte[] ciphertext = encrypt(plaintext, aesKey);
        String decryptedText = decrypt(ciphertext, aesKey);

        System.out.println("Original Text: " + plaintext);
        System.out.println("AES Key (Hex Form): " + bytesToHex(aesKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form): " + bytesToHex(ciphertext));
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public static SecretKey generateAesKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static byte[] encrypt(String plaintext, SecretKey aesKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    IllegalBlockSizeException, BadPaddingException {
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, aesKey);
        return encryptionCipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(byte[] ciphertext, SecretKey aesKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    IllegalBlockSizeException, BadPaddingException,
                    InvalidAlgorithmParameterException {
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(ciphertext);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static String bytesToHex(byte[] inputBytes) {
        char[] hexChars = new char[inputBytes.length * 2];
        for (int i = 0; i < inputBytes.length; i++) {
            int unsignedByte = inputBytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ALPHABET[unsignedByte >>> 4];
            hexChars[i * 2 + 1] = HEX_ALPHABET[unsignedByte & 0x0F];
        }
        return new String(hexChars);
    }
}