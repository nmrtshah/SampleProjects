/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.util.eservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Niral R. Malaviya
 */
public class AESEncDec {

    private static final int SALT_LENGTH = 16;
    private static final int AUTH_KEY_LENGTH = 8;
    private static final int ITERATIONS = 28752;
    private static final int BUFFER_SIZE = 1024;

    private static byte[] generateSalt(int length) {
        Random r = new SecureRandom();
        byte[] salt = new byte[length];
        r.nextBytes(salt);
        return salt;
    }

    private static Keys keygen(int keyLength, char[] password, byte[] salt) {
        SecretKeyFactory factory;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException impossible) {
            return null;
        }
        KeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, keyLength + AUTH_KEY_LENGTH * 8);
        SecretKey tmp = null;
        try {
            tmp = factory.generateSecret(spec);
        } catch (InvalidKeySpecException impossible) {
        }
        byte[] fullKey = tmp.getEncoded();
        SecretKey authKey = new SecretKeySpec(Arrays.copyOfRange(fullKey, 0, AUTH_KEY_LENGTH), "AES");
        SecretKey encKey = new SecretKeySpec(Arrays.copyOfRange(fullKey, AUTH_KEY_LENGTH, fullKey.length), "AES");
        return new Keys(encKey, authKey);
    }

    public static void encrypt(int keyLength, char[] password, InputStream input, OutputStream output)
            throws InvalidKeyLengthException, StrongEncryptionNotAvailableException, IOException {
        if (keyLength != 128 && keyLength != 192 && keyLength != 256) {
            throw new InvalidKeyLengthException(keyLength);
        }
        byte[] salt = generateSalt(SALT_LENGTH);
        Keys keys = keygen(keyLength, password, salt);
        Cipher encrypt = null;
        try {
            encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encrypt.init(Cipher.ENCRYPT_MODE, keys.encryption);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException impossible) {
        } catch (InvalidKeyException e) {
            throw new StrongEncryptionNotAvailableException(keyLength);
        }
        byte[] iv = null;
        try {
            iv = encrypt.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        } catch (InvalidParameterSpecException impossible) {
        }
        output.write(keyLength / 8);
        output.write(salt);
        output.write(keys.authentication.getEncoded());
        output.write(iv);
        byte[] buffer = new byte[BUFFER_SIZE];
        int numRead;
        byte[] encrypted = null;
        while ((numRead = input.read(buffer)) > 0) {
            encrypted = encrypt.update(buffer, 0, numRead);
            if (encrypted != null) {
                output.write(encrypted);
            }
        }
        try {
            encrypted = encrypt.doFinal();
        } catch (IllegalBlockSizeException | BadPaddingException impossible) {
        }
        if (encrypted != null) {
            output.write(encrypted);
        }
    }

    public static int decrypt(char[] password, InputStream input, OutputStream output)
            throws InvalidPasswordException, InvalidAESStreamException, IOException,
            StrongEncryptionNotAvailableException {
        int keyLength = input.read() * 8;
        System.out.println("keylength :" + keyLength);
        if (keyLength != 128 && keyLength != 192 && keyLength != 256) {
            throw new InvalidAESStreamException();
        }
        byte[] salt = new byte[SALT_LENGTH];
        input.read(salt);
        Keys keys = keygen(keyLength, password, salt);
        byte[] authRead = new byte[AUTH_KEY_LENGTH];
        input.read(authRead);
        if (!Arrays.equals(keys.authentication.getEncoded(), authRead)) {
            throw new InvalidPasswordException();
        }

        byte[] iv = new byte[16];
        input.read(iv);
        Cipher decrypt = null;
        try {
            decrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decrypt.init(Cipher.DECRYPT_MODE, keys.encryption, new IvParameterSpec(iv));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException impossible) {
        } catch (InvalidKeyException e) {
            throw new StrongEncryptionNotAvailableException(keyLength);
        }

        byte[] buffer = new byte[BUFFER_SIZE];
        int numRead;
        byte[] decrypted;
        while ((numRead = input.read(buffer)) > 0) {
            decrypted = decrypt.update(buffer, 0, numRead);
            if (decrypted != null) {
                output.write(decrypted);
            }
        }
        try {
            decrypted = decrypt.doFinal();
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new InvalidAESStreamException(e);
        }
        if (decrypted != null) {
            output.write(decrypted);
        }

        return keyLength;
    }

    private static class Keys {

        public final SecretKey encryption, authentication;

        public Keys(SecretKey encryption, SecretKey authentication) {
            this.encryption = encryption;
            this.authentication = authentication;
        }
    }

    public static class InvalidPasswordException extends Exception {
    }

    public static class InvalidKeyLengthException extends Exception {

        InvalidKeyLengthException(int length) {
            super("Invalid AES key length: " + length);
        }
    }

    public static class StrongEncryptionNotAvailableException extends Exception {

        public StrongEncryptionNotAvailableException(int keySize) {
            super(keySize + "-bit AES encryption is not available on this Java platform.");
        }
    }

    public static class InvalidAESStreamException extends Exception {

        public InvalidAESStreamException() {
            super();
        }

        ;
		public InvalidAESStreamException(Exception e) {
            super(e);
        }
    }
}
