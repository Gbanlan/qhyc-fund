package com.qhyc.fund.uitl;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SmsSample {

	public static void main(String[] args) {

		String testUsername = "gaolei00"; //在短信宝注册的用户名
		String testPassword = "123000"; //在短信宝注册的密码
		String testPhone = "17711604191";
		String httpUrl = "http://api.smsbao.com/sms";

		StringBuffer httpArg = new StringBuffer();
		httpArg.append("u=").append(testUsername).append("&");
		httpArg.append("p=").append(md5(testPassword)).append("&");
		httpArg.append("m=").append(testPhone).append("&");
		StringBuilder testContent =new  StringBuilder();
		testContent.append("【闪电手】您的验证码为");
		testContent.append(getRandNum());
		testContent.append("，3分钟内有效，请尽快验证。如非本人操作，请忽略本条消息。");
		httpArg.append("c=").append(encodeUrlString(testContent.toString(), "UTF-8"));

		String result = request(httpUrl, httpArg.toString());
		System.out.println(result);
	}
	
	public static int getRandNum() {
		int min=1;
		int max=999999;
	    int randNum = min + (int)(Math.random() * ((max - min) + 1));
	    return randNum;
	}

	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = reader.readLine();
			if (strRead != null) {
				sbf.append(strRead);
				while ((strRead = reader.readLine()) != null) {
					sbf.append("\n");
					sbf.append(strRead);
				}
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String md5(String plainText) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	public static String encodeUrlString(String str, String charset) {
		String strret = null;
		if (str == null)
			return str;
		try {
			strret = java.net.URLEncoder.encode(str, charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return strret;
	}
}
