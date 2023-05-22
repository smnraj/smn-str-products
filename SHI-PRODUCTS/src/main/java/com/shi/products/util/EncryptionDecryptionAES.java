/**
 * 
 */
package com.shi.products.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @author suman.raju
 *
 */
public class EncryptionDecryptionAES {
	private static final Logger log = LoggerFactory.getLogger(EncryptionDecryptionAES.class);

	private static final String ALGORITHM_SHA256 = "PBKDF2WithHmacSHA256";
	private static final String ALGORITHM_AES = "AES";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	// private static final String TRANSFORMATION = "AES/CBC/NoPadding";
	private static final String SECRET_KEY = "Star_Health";
	private static final String SALT = "StaR_HealtH";
	private static final byte[] iv = { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'k', 'e', 'y' };

	public static String encrypt(String stringToEncrypt) {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			SecretKeySpec secretKey = generateKey();

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			log.error("Error while encrypting: {}", e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt) {
		try {
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			SecretKeySpec secretKey = generateKey();

			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			log.error("Error while decrypting: {}", e.toString());
		}
		return null;
	}

	public static SecretKeySpec generateKey() throws Exception {
		SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM_SHA256);
		KeySpec keySpec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
		SecretKey key = factory.generateSecret(keySpec);
		SecretKeySpec secretKey = new SecretKeySpec(key.getEncoded(), ALGORITHM_AES);
		return secretKey;
	}

	/*
	 * public static void main(String[] args) { String stringToEncrypt = "SHI2023";
	 * 
	 * String encryptedString = EncryptionDecryptionAES.encrypt(stringToEncrypt);
	 * String decryptedString = EncryptionDecryptionAES.decrypt(encryptedString);
	 * 
	 * System.out.println("Encrypted String = " + encryptedString);
	 * System.out.println("Decrypted String = " + decryptedString); }
	 */

}
