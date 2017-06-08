package com.qhyc.fund.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhyc.fund.entity.UserInfo;
import com.qhyc.fund.service.UserService;
import com.qhyc.fund.uitl.JsonResult;
import com.qhyc.fund.uitl.MailUtil;
import com.qhyc.fund.uitl.OutputFileUtils;
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

	/**
     * 
    * @Title: registerUserInfo 
    * @Description: TODO(注册用户) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
	@RequestMapping(value ="/registerUser",method= RequestMethod.GET)
	@ResponseBody
	public JsonResult registerUserInfo(HttpServletRequest request, HttpServletResponse response)
	{
	    //封装UserInfo实体
		UserInfo userInfo = packingUserInfo(request);
		JsonResult result = userService.insertUserInfo(userInfo);
		return result;
	}
	
	private UserInfo packingUserInfo(HttpServletRequest request) {
		String phone=request.getParameter("phone");
		String fullName=request.getParameter("fullName");
		String identityCard=request.getParameter("identityCard");
		String bankCard=request.getParameter("bankCard");
		String cardholderName=request.getParameter("cardholderName");
		String openingBank=request.getParameter("openingBank");
		String bankAddr=request.getParameter("bankAddr");
		UserInfo userInfo=new UserInfo();
		userInfo.setPhone(phone);
		userInfo.setFullName(fullName);
		userInfo.setIdentityCard(identityCard);
		userInfo.setBankCard(bankCard);
		userInfo.setCardholderName(cardholderName);
		userInfo.setOpeningBank(openingBank);
		userInfo.setBankAddr(bankAddr);
		userInfo.setExportStatus("N");
		userInfo.setValidStatus("N");
		return userInfo;
	}
	
	@RequestMapping(value ="/confirmRegisterUser",method= RequestMethod.GET)
	@ResponseBody
	public JsonResult confirmRegisterUser(HttpServletRequest request, HttpServletResponse response)
	{
		String phone=request.getParameter("phone");
		String fullName=request.getParameter("fullName");
		if(StringUtils.isEmpty(phone)){
			JsonResult.fail("用户手机号不能为空");
		}
	    //封装UserInfo实体
		UserInfo userInfo=new UserInfo();
		userInfo.setPhone(phone);
		userInfo.setValidStatus("Y");//修改有效状态为生效
		JsonResult result = userService.updateUserInfo(userInfo);
		if(result.getStatus()==200){
			//发送成功邮件
			try {
				MailUtil.sendEmail(fullName, phone);
			} catch (Exception e) {
				JsonResult.fail("确认注册邮件通知发送失败！");
			}
		}
		return result;
	}

	@RequestMapping(value="/getValidaCode/{phone}",method= RequestMethod.GET)
	@ResponseBody
	public JsonResult getValidaCode(@PathVariable String phone,HttpServletRequest request, HttpServletResponse response) {
		String testUsername = "gaolei00"; //在短信宝注册的用户名
		String testPassword = "123000"; //在短信宝注册的密码
		String httpUrl = "http://api.smsbao.com/sms";
		StringBuffer httpArg = new StringBuffer();
		httpArg.append("u=").append(testUsername).append("&");
		httpArg.append("p=").append(SmsSample.md5(testPassword)).append("&");
		httpArg.append("m=").append(phone).append("&");
		StringBuilder testContent =new  StringBuilder();
		testContent.append("【闪电手】您的验证码为");
		//产生随机  验证码
		int validaCode=SmsSample.getRandNum();
		testContent.append(validaCode);
		testContent.append("，3分钟内有效，请尽快验证。如非本人操作，请忽略本条消息。");
		httpArg.append("c=").append(SmsSample.encodeUrlString(testContent.toString(), "UTF-8"));
		String result = SmsSample.request(httpUrl, httpArg.toString());
		if("0".equals(result)){
			request.getSession().setAttribute(""+phone,validaCode);
			request.getSession().setMaxInactiveInterval(30*60);//以秒为单位，即在没有活动30分钟后，session将失效
		}
		return JsonResult.ok(result);
	}
	
	
	
	@RequestMapping(value="/checkValidaCode/{validaCode}/{phone}",method= RequestMethod.GET)
	@ResponseBody
	public JsonResult checkValidaCode(@PathVariable String validaCode,@PathVariable String phone,HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isEmpty(validaCode)){
			JsonResult.fail("验证码为空，校验失败！");
		}else{
			//从Session中取出数组
			Integer  sVCode= (Integer)request.getSession().getAttribute(phone); 
			if(null!=sVCode){
				return JsonResult.fail("验证手机号码超时！");
			}
			else if(validaCode.equals(sVCode)){
				return JsonResult.ok();
			}else{
				return JsonResult.fail("手机验证码和系统不匹配，验证失败！");
			}
		}
		return JsonResult.fail("服务器异常,请重试或联系管理员!");
	}
	
	
	/**
	 * 
	* @Title: checkValidaCode 
	* @Description: TODO(实名校验身份证信息) 
	* @param @param fullName
	* @param @param identityCard
	* @param @return    设定文件 
	* @return JsonResult    返回类型 
	* @throws
	 */
	@RequestMapping(value="/IdCardVerification/{fullName}/{identityCard}",method= RequestMethod.GET)
	@ResponseBody
	public JsonResult IdCardVerification(@PathVariable String fullName,@PathVariable String identityCard) {
		UserInfo userInfo=new UserInfo();
		userInfo.setFullName(fullName);
		userInfo.setIdentityCard(identityCard);
		JsonResult result=userService.IdCardVerification(userInfo);
		return result;
	}
	
	/**
	 * 
	* @Title: checkValidaCode 
	* @Description: TODO(实名校验银行卡信息) 
	* @param @param fullName
	* @param @param identityCard
	* @param @return    设定文件 
	* @return JsonResult    返回类型 
	* @throws
	 */
	@RequestMapping(value="/bankCardVerification/{cardholderName}/{bankCard}",method= RequestMethod.GET)
	@ResponseBody
	public JsonResult bankCardVerification(@PathVariable String cardholderName,@PathVariable String bankCard) {
		UserInfo userInfo=new UserInfo();
		userInfo.setCardholderName(cardholderName);
		userInfo.setBankCard(bankCard);
		JsonResult result=userService.bankCardVerification(userInfo);
		return result;
	}
	
	@RequestMapping(value="/exportUserInfo",method= RequestMethod.GET)
	@ResponseBody
	public JsonResult exportUserInfo() {
		UserInfo userInfo=new UserInfo();
		userInfo.setExportStatus("N");//导出未导出的数据
		JsonResult result=userService.exportUserInfo(userInfo);
		return result;
	}
	
	@RequestMapping("/downloadUserInfoExcel")
    public void downloadUserInfoExcel(HttpServletRequest req, HttpServletResponse res) {
        String fileUrl=req.getParameter("fileUrl");//文件名称
        try {
            File pdfFile=new File(fileUrl);
            // 下载文件名称
            String downFileName = "用户注册数据";
            downFileName=downFileName + "_" + new Date().getTime() + ".xls";
            res.reset();
            res.setCharacterEncoding("UTF-8");
            // 定义输出类型
            res.setContentType("application/vnd.ms-doc");
            res.setHeader("content-disposition", "attachment;filename=\"" + new String(downFileName.getBytes("GBK"), "ISO-8859-1") + "\";size=" + pdfFile.length());                
            OutputFileUtils.outputFile(pdfFile,res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
	

	
	

}
