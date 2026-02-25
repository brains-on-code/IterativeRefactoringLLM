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

    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private static Cipher encryptionCipher;

    public static void main(String[] args) throws Exception {
        String originalPlaintext = "Hello World";
        SecretKey aesSecretKey = generateAesKey();
        byte[] encryptedBytes = encrypt(originalPlaintext, aesSecretKey);
        String decryptedPlaintext = decrypt(encryptedBytes, aesSecretKey);

        System.out.println("Original Text: " + originalPlaintext);
        System.out.println("AES Key (Hex Form): " + bytesToHex(aesSecretKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form): " + bytesToHex(encryptedBytes));
        System.out.println("Decrypted Text: " + decryptedPlaintext);
    }

    public static SecretKey generateAesKey() throws NoSuchAlgorithmException {
        KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("AES");
        aesKeyGenerator.init(128);
        return aesKeyGenerator.generateKey();
    }

    public static byte[] encrypt(String plaintext, SecretKey aesSecretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    IllegalBlockSizeException, BadPaddingException {
        encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, aesSecretKey);
        return encryptionCipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    public static String decrypt(byte[] ciphertext, SecretKey aesSecretKey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
                    IllegalBlockSizeException, BadPaddingException,
                    InvalidAlgorithmParameterException {
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, encryptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, aesSecretKey, gcmParameterSpec);
        byte[] decryptedBytes = decryptionCipher.doFinal(ciphertext);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

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