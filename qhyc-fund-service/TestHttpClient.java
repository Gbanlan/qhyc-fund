package com.taotao.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTimeUtils;

import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.HttpUtils;
import com.taotao.common.utils.SignUtils;

public class TestHttpClient {

	public static void main(String[] args) {
//		test();
		Map<String, String> param=new HashMap<String, String>();
		param.put("outerTradeNo","53453");
		param.put("amount", "0.01");
		param.put("username", "xiaoming");
		param.put("mchId", "600001");
		param.put("productName", "充值");
		param.put("userIp", "192.168.1.11");
		param.put("payType", "wechat");
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		param.put("orderTime", dateString);
		param.put("service", "T003");
		param.put("notifyUrl", "http://www.baidu.com");
		
		String md5salt="tAuNY4MfVjElSezSjNgbez0E7wNMG42ZhV1rZ91ZEop";
		param.put("sign", SignUtils.signWithMD5(param, md5salt));
		System.out.println(param.toString());
		try {
			String res = HttpUtils.postUrl("http://xpay.sibaisheng.com:13156/xpay/api/pay/yw/topay", param);
			
			System.out.println(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	static void test()
	{
		Map<String,String> params = new HashMap<String,String>();
		params.put("outerTradeNo", "123");
		params.put("amount", "0.1"); 
		params.put("username", "1300001");
		params.put("mchId", "600001");
		params.put("productName", "充值");
		params.put("userIp", "192.168.1.11");
		params.put("payType", "wechat");
		
		params.put("orderTime", "20170606145000");
		params.put("service", "T003");
		params.put("notifyUrl", "http://aaaaa");
		String salt = "tAuNY4MfVjElSezSjNgbez0E7wNMG42ZhV1rZ91ZEop";
		String sign = SignUtils.signWithMD5(params, salt);
		params.put("sign", sign);

		try {
			String res = HttpUtils.postUrl("http://xpay.sibaisheng.com:13156/xpay/api/pay/yw/topay", params);
			
			System.out.println(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
