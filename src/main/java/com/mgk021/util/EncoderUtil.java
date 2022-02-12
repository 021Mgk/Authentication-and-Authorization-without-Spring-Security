package com.mgk021.util;



import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.*;
import java.util.HashMap;


public class EncoderUtil implements Serializable {

    private static String secretKey ="AbCdEfGhIGkLmNop123456789!!@@##" ;

    public static String getMD5(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getSHA256(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getSHA512(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(text.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String getAESEncrypt( String cleartext) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(secretKey.getBytes());
        kgen.init(128, sr);
        SecretKey skey = kgen.generateKey();
        byte[] rawKey = skey.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
        if (encrypted == null)
            return "";
        StringBuffer result = new StringBuffer(2 * encrypted.length);
        String HEX = "0123456789ABCDEF";
        for (int i = 0; i < encrypted.length; i++) {
            result.append(HEX.charAt((encrypted[i] >> 4) & 0x0f)).append(HEX.charAt(encrypted[i] & 0x0f));
        }
        return result.toString();
    }

    public static String getAESDecrypt( String encrypted) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(secretKey.getBytes());
        kgen.init(128, sr);
        SecretKey skey = kgen.generateKey();
        byte[] rawKey = skey.getEncoded();
        int len = encrypted.length() / 2;
        byte[] enc = new byte[len];
        for (int i = 0; i < len; i++) {
            enc[i] = Integer.valueOf(encrypted.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(enc);
        return new String(decrypted);
    }

    public static HashMap getRSAKeys() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair kp = keyGen.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        HashMap keys = new HashMap();
        keys.put("PublicKey", publicKey);
        keys.put("PrivateKey", privateKey);
        return keys;
    }

    public static byte[] getRSAEncrypt(PublicKey publicKey, String clearText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(clearText.getBytes());
    }

    public static String getRSADecrypt(PrivateKey privateKey, byte[] encrypt) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(encrypt));
    }
}
