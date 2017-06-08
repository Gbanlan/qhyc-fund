package com.qhyc.fund.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qhyc.fund.entity.UserInfo;
import com.qhyc.fund.service.UserService;
import com.qhyc.fund.uitl.HttpUtils;
import com.qhyc.fund.uitl.JsonResult;


@Service
public class UserServiceImpl extends BaseService<UserInfo> implements UserService {

	@Override
	public UserInfo findById(String id) {
		return sqlSession.selectOne("user.findUserById", id);
	}

	@Override
	public JsonResult insertUserInfo(UserInfo userInfo) {
		try {
			sqlSession.insert("user.insertUserInfo", userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.fail(e.getMessage());
		}
		return JsonResult.ok();
	}

	@Override
	public JsonResult IdCardVerification(UserInfo userInfo) {
		String key="f68ef96ef4654abea17b93b048316c62";
		String  realname=userInfo.getFullName();
		String idcard =userInfo.getIdentityCard();
        StringBuffer url=new StringBuffer();
        url.append("http://api.avatardata.cn/IdCardCertificate/Verify");
        url.append("?key=").append(key);;
        url.append("&realname=").append(realname);
        url.append("&idcard=").append(idcard);
        
		try {
			String res = HttpUtils.getUrl(url.toString());
			JSONObject jsonObject = JSONObject.parseObject(res);
			if("Succes".equals(jsonObject.get("reason"))){
				String code=(String)jsonObject.getJSONObject("result").getString("code");
				if("1000".equals(code)){
					return JsonResult.ok();
				}else{
					String message=(String)jsonObject.getJSONObject("result").getString("message");
					return JsonResult.fail(message);
				}
			}else{
				return JsonResult.fail(jsonObject.getString("reason"));
			}
			
		} catch (Exception e) {
			
		}
		return JsonResult.fail("服务器异常,请重试或联系管理员!");
	}

	@Override
	public JsonResult bankCardVerification(UserInfo userInfo) {
		String key="fee54734521145bd96f223697618c11f";
		String  realname=userInfo.getCardholderName();//持卡人
		String cardnum =userInfo.getBankCard();//银行卡
        StringBuffer url=new StringBuffer();
        url.append("http://api.avatardata.cn/BankCardCertificate/Verify");
        url.append("?key=").append(key);;
        url.append("&realname=").append(realname);
        url.append("&cardnum=").append(cardnum);
        
		try {
			String res = HttpUtils.getUrl(url.toString());
			JSONObject jsonObject = JSONObject.parseObject(res);
			if("Succes".equals(jsonObject.get("reason"))){
				String code=(String)jsonObject.getJSONObject("result").getString("code");
				if("1000".equals(code)){
					return JsonResult.ok();
				}else{
					String message=(String)jsonObject.getJSONObject("result").getString("message");
					return JsonResult.fail(message);
				}
			}else{
				return JsonResult.fail(jsonObject.getString("reason"));
			}
			
		} catch (Exception e) {
			
		}
		return JsonResult.fail("服务器异常,请重试或联系管理员!");
	}


}
