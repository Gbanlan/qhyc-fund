package com.qhyc.fund.service;

import com.qhyc.fund.entity.User;

public interface UserService {
	  /**
     * @Desciption 按照用户id查找该用户
     * @param id 用户id
     * @return 用户实体
     */
    public User findById(int id);

}
