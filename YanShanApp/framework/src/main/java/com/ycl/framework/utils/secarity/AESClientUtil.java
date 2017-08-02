package com.ycl.framework.utils.secarity;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//srcKey仅仅用于加密
public class AESClientUtil {

	private static String srcKey = "mint2012codeperf";

	private static byte[] ivbyte = { 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32,
			0x31, 0x38, 0x37, 0x36, 0x35, 0x34, 0x33, 0x32, 0x31 };

	/**
	 * 111111转换成70-66-A4-0F-42-77-69-CC-43-34-7A-A9-6B-72-93-1A
	 * {"loginname":"tfb00171","loginpwd":"70-66-A4-0F-42-77-69-CC-43-34-7A-A9
	 * 
	 * @param plainText
	 * @return
	 */
	public static String MD5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("UTF-16LE"));
			byte b[] = md.digest();
			String st = "";
			for (int i = 0; i < b.length; i++) {
				String hex = Integer.toHexString(b[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				st += hex.toUpperCase() + "-";
			}
			return st.substring(0, st.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

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
