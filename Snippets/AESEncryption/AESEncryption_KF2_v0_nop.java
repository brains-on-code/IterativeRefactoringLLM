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
    private AESEncryption() {
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    private static Cipher aesCipher;


    public static void main(String[] args) throws Exception {
        String plainText = "Hello World";
        SecretKey secKey = getSecretEncryptionKey();
        byte[] cipherText = encryptText(plainText, secKey);
        String decryptedText = decryptText(cipherText, secKey);

        System.out.println("Original Text:" + plainText);
        System.out.println("AES Key (Hex Form):" + bytesToHex(secKey.getEncoded()));
        System.out.println("Encrypted Text (Hex Form):" + bytesToHex(cipherText));
        System.out.println("Descrypted Text:" + decryptedText);
    }


    public static SecretKey getSecretEncryptionKey() throws NoSuchAlgorithmException {
        KeyGenerator aesKeyGenerator = KeyGenerator.getInstance("AES");
        aesKeyGenerator.init(128);
        return aesKeyGenerator.generateKey();
    }


    public static byte[] encryptText(String plainText, SecretKey secKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        aesCipher = Cipher.getInstance("AES/GCM/NoPadding");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        return aesCipher.doFinal(plainText.getBytes());
    }


    public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, aesCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, secKey, gcmParameterSpec);
        byte[] bytePlainText = decryptionCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }


    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
