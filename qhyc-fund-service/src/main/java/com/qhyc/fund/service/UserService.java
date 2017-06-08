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
    * @Title: insertUserInfo 
    * @Description: TODO(注册用户入库) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
    public JsonResult insertUserInfo(UserInfo userInfo);
    
    /**
    * @Title: idAuthentication 
    * @Description: TODO(身份证实名验证) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
    public JsonResult IdCardVerification(UserInfo userInfo);
    
    /**
    * @Title: bankCardVerification 
    * @Description: TODO(银行卡实名认证) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
    public JsonResult bankCardVerification(UserInfo userInfo);
    
    /**
     * 
    * @Title: updateUserInfo 
    * @Description: TODO(确认注册更新) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
    public JsonResult updateUserInfo(UserInfo userInfo);
    
    /**
    * @Title: exportUserInfo 
    * @Description: TODO(导出Excel数据) 
    * @param @param userInfo
    * @param @return    设定文件 
    * @return JsonResult    返回类型 
    * @throws
     */
    public JsonResult exportUserInfo(UserInfo userInfo);

}
