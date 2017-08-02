package com.ycl.framework.utils.secarity;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//srcKey仅仅用于解密
public class AESUtil {

	private static String srcKey = "yaoqianshu201212";

	private static byte[] ivbyte = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1,
			6, 8, 0xC, 0xD, 91 };

	// 加密
	public static String Encrypt(String sSrc) throws Exception {
		if (srcKey == null) {
			return null;
		}
		// 判断Key是否为16位
		if (srcKey.length() != 16) {
			return null;
		}
		byte[] raw = srcKey.getBytes("UTF-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec(ivbyte);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
		return Base64Encoder.encode(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	// 解密
	public static String Decrypt(String sSrc) throws Exception {
		// 判断Key是否正确
		if (srcKey == null) {
			return null;
		}
		// 判断Key是否为16位
		if (srcKey.length() != 16) {
			return null;
		}
		byte[] raw = srcKey.getBytes("UTF-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(ivbyte);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] encrypted1 = Base64Decoder.decodeToBytes(sSrc);// 先用base64解密
		try {
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "UTF-8");
			return originalString;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
	}
}
