package com.qhyc.fund.service;

import com.qhyc.fund.entity.UserInfo;
import com.qhyc.fund.uitl.JsonResult;

/**
* @ClassName: UserService 
* @Description: TODO(用户注册接口) 
* @author mz
* @date 2017年6月7日 下午9:38:08 
*
 */
public interface UserService {
	  /**
     * @Desciption 按照用户id查找该用户
     * @param id 用户id
     * @return 用户实体
     */
    public UserInfo findById(String phone);
    
    /**
    * @Title: insertUserInfo 
    * @Description: TODO(注册用户入库) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
    public JsonResult insertUserInfo(UserInfo userInfo);
    
    /**
     * 
    * @Title: idAuthentication 
    * @Description: TODO(身份证实名验证) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
    public JsonResult IdCardVerification(UserInfo userInfo);
    
    /**
     * 银行卡实名认证
    * @Title: bankCardVerification 
    * @Description: TODO(这里用一句话描述这个方法的作用) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
    public JsonResult bankCardVerification(UserInfo userInfo);

}
