package com.example.guoyurenli.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class AESUtil
{
	// AES要求16位字符做为key，输入的长度应该>=16
	public static String keyPadding(String key)
	{
		if(key.length()>=16) 
			return key.substring(0,16);
		
		// 补足16位
		for(int i=key.length(); i<16;i++)
			key += '0';
		return key;
	}
	 
	// 加密
	public static String encrypt(String plain, String key) throws Exception
	{	  
		key = keyPadding(key);
		byte[] key_data = key.getBytes();
		byte[] plain_data = plain.getBytes("UTF-8");
		byte[] output_data = encrypt(plain_data, key_data);
		
	    return Hex.encodeHexString(output_data, false);
	}
	
	// 解密
	public static String decrypt(String cipher, String key) throws Exception
	{	    
		key = keyPadding(key);
		byte[] key_data = key.getBytes();
		byte[] cipher_data = Hex.decodeHex(cipher);
		byte[] output_data = decrypt(cipher_data, key_data);
		
	    return new String(output_data, "UTF-8");
	}
	
	// 加密
	public static byte[] encrypt(byte[] plain, byte[] key) throws Exception
	{	    
	    Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");    
	    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
	    c.init(Cipher.ENCRYPT_MODE, keySpec);
	    
	    // 加密
	    byte [] output = c.doFinal(plain);
	    return output;
	}
	
	// 解密
	public static byte[] decrypt(byte[] cipher, byte[] key) throws Exception
	{	    
	    Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");    
	    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
	    c.init(Cipher.DECRYPT_MODE, keySpec);
	    
	    byte [] output = c.doFinal(cipher);	    
	    return output;
	}
	
}
