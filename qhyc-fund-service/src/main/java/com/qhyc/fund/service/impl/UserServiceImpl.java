package com.qhyc.fund.service.impl;

import org.springframework.stereotype.Service;

import com.qhyc.fund.entity.UserInfo;
import com.qhyc.fund.service.UserService;
import com.qhyc.fund.uitl.JsonResult;


@Service
public class UserServiceImpl extends BaseService<UserInfo> implements UserService {

	@Override
	public UserInfo findById(int id) {
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


}
