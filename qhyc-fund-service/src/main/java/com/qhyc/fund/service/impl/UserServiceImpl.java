package com.qhyc.fund.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.qhyc.fund.entity.UserInfo;
import com.qhyc.fund.service.UserService;
import com.qhyc.fund.uitl.HttpUtils;
import com.qhyc.fund.uitl.JsonResult;
import com.qhyc.fund.uitl.PoiExcelExport;

@Service
public class UserServiceImpl extends BaseService<UserInfo> implements UserService {


	@Override
	public JsonResult insertUserInfo(UserInfo userInfo) {
		try {
			sqlSession.insert("user.insertUserInfo", userInfo);
		} catch (Exception e) {
			JsonResult.fail("服务器异常,请重试或联系管理员!");
		}
		return JsonResult.ok();
	}

	@Override
	public JsonResult IdCardVerification(UserInfo userInfo) {
		String key = "f68ef96ef4654abea17b93b048316c62";
		String realname = userInfo.getFullName();
		String idcard = userInfo.getIdentityCard();
		StringBuffer url = new StringBuffer();
		url.append("http://api.avatardata.cn/IdCardCertificate/Verify");
		url.append("?key=").append(key);
		;
		url.append("&realname=").append(realname);
		url.append("&idcard=").append(idcard);

		try {
			String res = HttpUtils.getUrl(url.toString());
			JSONObject jsonObject = JSONObject.parseObject(res);
			if ("Succes".equals(jsonObject.get("reason"))) {
				String code = (String) jsonObject.getJSONObject("result").getString("code");
				if ("1000".equals(code)) {
					return JsonResult.ok();
				} else {
					String message = (String) jsonObject.getJSONObject("result").getString("message");
					return JsonResult.fail(message);
				}
			} else {
				return JsonResult.fail(jsonObject.getString("reason"));
			}

		} catch (Exception e) {

		}
		return JsonResult.fail("服务器异常,请重试或联系管理员!");
	}

	@Override
	public JsonResult bankCardVerification(UserInfo userInfo) {
		String key = "fee54734521145bd96f223697618c11f";
		String realname = userInfo.getCardholderName();// 持卡人
		String cardnum = userInfo.getBankCard();// 银行卡
		StringBuffer url = new StringBuffer();
		url.append("http://api.avatardata.cn/BankCardCertificate/Verify");
		url.append("?key=").append(key);
		;
		url.append("&realname=").append(realname);
		url.append("&cardnum=").append(cardnum);

		try {
			String res = HttpUtils.getUrl(url.toString());
			JSONObject jsonObject = JSONObject.parseObject(res);
			if ("Succes".equals(jsonObject.get("reason"))) {
				String code = (String) jsonObject.getJSONObject("result").getString("code");
				if ("1000".equals(code)) {
					return JsonResult.ok();
				} else {
					String message = (String) jsonObject.getJSONObject("result").getString("message");
					return JsonResult.fail(message);
				}
			} else {
				return JsonResult.fail(jsonObject.getString("reason"));
			}

		} catch (Exception e) {

		}
		return JsonResult.fail("服务器异常,请重试或联系管理员!");
	}

	@Override
	public JsonResult updateUserInfo(UserInfo userInfo) {
		try {
			int result = sqlSession.update("user.updateUserInfo", userInfo);
			if (result > 0) {
				return JsonResult.ok();
			} else {
				return JsonResult.fail("确认注册失败！");
			}
		} catch (Exception e) {
			return JsonResult.fail("服务器异常,请重试或联系管理员!");
		}
	}

	@Override
	public JsonResult exportUserInfo(UserInfo userInfo) {
		List<UserInfo> userInfolist=sqlSession.selectList("user.exportUserInfo",userInfo);
		String fileDir="C://userInfo/excel/";//文件地址
		if(!userInfolist.isEmpty()){
	        // 生成文件名称
	        String fileName ="userInfo_" + new Date().getTime() + ".xls";
	        fileDir=fileDir+fileName;
	        //判断文件夹是否存在,如果不存在则创建
	        File outFile = new File(fileDir);
            if(!outFile.getParentFile().exists()){
                outFile.getParentFile().mkdirs();
            }
            if(outFile.exists()) {
                outFile.delete();
            }
			PoiExcelExport pee = new PoiExcelExport(fileDir,"sheet1");
	        String titleColumn[] = {"phone","fullName","IdentityCard","bankCard","cardholderName","openingBank","bankAddr"};
	        String titleName[] = {"手机号","姓名","身份证","银行卡","持卡人姓名","开户行","开户行地址"};
	        int titleSize[] = {15,13,20,20,13,13,35};
	        //其他设置 set方法可全不调用
	        String colFormula[] = new String[7];
	        //colFormula[4] = "D@*12";   //设置第5列的公式
	        pee.setColFormula(colFormula);
	        //pee.setAddress("A:D");  //自动筛选 
	        fileDir= pee.wirteExcel(titleColumn, titleName, titleSize, userInfolist);
	        //把导出状态设置为已经导出
	        userInfo.setExportStatus("Y");
	        sqlSession.update("user.updateUserInfo", userInfo);
		}else{
			 return JsonResult.fail("没有要导出的用户注册数据！");
		}
		return JsonResult.ok(fileDir);
	}

}
