package com.qhyc.fund.service;

import com.qhyc.fund.entity.UserInfo;
import com.qhyc.fund.uitl.JsonResult;

public interface UserService {
	  /**
     * @Desciption 按照用户id查找该用户
     * @param id 用户id
     * @return 用户实体
     */
    public UserInfo findById(int phone);
    
    
    public JsonResult insertUserInfo(UserInfo userInfo);

}
