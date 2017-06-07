package com.qhyc.fund.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhyc.fund.entity.UserInfo;
import com.qhyc.fund.service.UserService;
import com.qhyc.fund.uitl.JsonResult;
import com.qhyc.fund.uitl.SmsSample;

/**
 * 
 * @ClassName: UserController
 * @Description: TODO(用户控制器)
 * @author mz
 * @date 2017年6月7日 上午11:34:21
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@RequestMapping(value = "/queryUserByPhone/{phone}")
	@ResponseBody
	public UserInfo goHome(@PathVariable int phone) {
		UserInfo userInfo = userService.findById(phone);
		System.out.println(userInfo.getFullName());
		return userInfo;
	}

	/**
     * 
    * @Title: registerUserInfo 
    * @Description: TODO(注册用户) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
	@RequestMapping("/registerUser")
	@ResponseBody
	public JsonResult registerUserInfo(@RequestParam UserInfo userInfo) {
		JsonResult result = userService.insertUserInfo(userInfo);
		return result;
	}

	@RequestMapping("/getValidationCode/{phone}")
	@ResponseBody
	public JsonResult getValidationCode(@PathVariable String phone) {
		String testUsername = "gaolei00"; //在短信宝注册的用户名
		String testPassword = "123000"; //在短信宝注册的密码
		String httpUrl = "http://api.smsbao.com/sms";
		StringBuffer httpArg = new StringBuffer();
		httpArg.append("u=").append(testUsername).append("&");
		httpArg.append("p=").append(SmsSample.md5(testPassword)).append("&");
		httpArg.append("m=").append(phone).append("&");
		StringBuilder testContent =new  StringBuilder();
		testContent.append("【闪电手】您的验证码为");
		testContent.append(SmsSample.getRandNum());
		testContent.append("，3分钟内有效，请尽快验证。如非本人操作，请忽略本条消息。");
		httpArg.append("c=").append(SmsSample.encodeUrlString(testContent.toString(), "UTF-8"));
		String result = SmsSample.request(httpUrl, httpArg.toString());
		return JsonResult.ok(result);
	}

}
