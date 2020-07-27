package com.eim.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * AES�ԳƼ���
 *
 * @author chendh
 */
public class EncryptUtil {

    private final static String ALGORITHM = "AES";// "AES";

    private final static String DEFAULT_KEY = "AES_ENCRYP_KEY";

    /**
     * 加密
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String content) throws Exception {
        return encrypt(content, DEFAULT_KEY);
    }

    /**
     * 加密
     *
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String content, String key) throws Exception {
        return encrypt(content.getBytes(), key.getBytes());
    }

    /**
     * 解密
     *
     * @param contentBytes
     * @param keyBytes
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] contentBytes, byte[] keyBytes)
            throws Exception {

        byte[] encryptedText = null;

        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(keyBytes);
        //kgen.init(128, new SecureRandom(keyBytes));
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();

        Key key = new SecretKeySpec(enCodeFormat, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, key);

        encryptedText = cipher.doFinal(contentBytes);

        return encryptedText;
    }

    /**
     * 解密
     *
     * @param contentBytes
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] contentBytes)
            throws Exception {
        return decrypt(contentBytes, DEFAULT_KEY);
    }

    /**
     * 解密
     *
     * @param contentBytes
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] contentBytes, String key)
            throws Exception {
        return decrypt(contentBytes, key.getBytes());
    }

    /**
     * 解密
     *
     * @param contentBytes
     * @param keyBytes
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] contentBytes, byte[] keyBytes)
            throws Exception {
        byte[] originBytes = null;

        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(keyBytes);
        //kgen.init(128, new SecureRandom(keyBytes));
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();

        Key key = new SecretKeySpec(enCodeFormat, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        originBytes = cipher.doFinal(contentBytes);

        return originBytes;
    }

    // public static byte[] generateKey(String key) {
    // return key.getBytes();
    // }

//	public static void main(String[] args) throws Exception {
//		String msg = "123456";
//		System.out.println("������:" + msg);
//
//		byte[] encontent = EncrypAES.encrypt(msg, "admin");
//
//		msg = CommonUtil.toHexString(encontent);
//
//		System.out.println("���ܺ�:" + msg);
//
//		byte[] decontent = EncrypAES.decrypt(CommonUtil.toHexByte(msg), "admin");
//
//		System.out.println("���ܺ�:" + new String(decontent));
//
//	}
}
