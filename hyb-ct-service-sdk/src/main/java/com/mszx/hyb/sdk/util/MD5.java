package com.mszx.hyb.sdk.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*************************************************
 * md5 类实现了RSA Data Security, Inc.在提交给IETF 的RFC1321中的MD5 message-digest 算法。
 *************************************************/
public class MD5 {


	public static String getMd5(String source) throws NoSuchAlgorithmException{
		MessageDigest s = MessageDigest.getInstance("MD5");
		s.update(source.getBytes());
		byte[] bytes = s.digest();
		return toHexString(bytes);
	}


	public static  String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	} 
	
	public static void main(String [] args){
		String str = "abc123";
		String md5 = MD5.getMD5Str(str);
		System.out.println(md5);
	}



	public static String toHexString(byte bytes[])
	{
		char buf[] = new char[bytes.length * 2];
		int radix = 16;
		int mask = radix - 1;
		for(int i = 0; i < bytes.length; i++)
		{
			buf[2 * i] = digits[bytes[i] >>> 4 & mask];
			buf[2 * i + 1] = digits[bytes[i] & mask];
		}

		return new String(buf);
	}

	public static byte[] hexStringToByteArray(String hex)
	{
		if(hex == null)
			return null;
		String bss[] = hex.split(" ");
		hex = "";
		for(int i = 0; i < bss.length; i++)
			hex = (new StringBuilder(String.valueOf(hex))).append(bss[i]).toString();

		int stringLength = hex.length();
		if(stringLength % 2 != 0)
			throw new IllegalArgumentException("Hex String must have even number of characters!");
		byte result[] = new byte[stringLength / 2];
		int j = 0;
		for(int i = 0; i < result.length; i++)
		{
			char hi = Character.toLowerCase(hex.charAt(j++));
			char lo = Character.toLowerCase(hex.charAt(j++));
			result[i] = (byte)(Character.digit(hi, 16) << 4 | Character.digit(lo, 16));
		}

		return result;
	}

	static final char digits[] = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z'
	};
}
