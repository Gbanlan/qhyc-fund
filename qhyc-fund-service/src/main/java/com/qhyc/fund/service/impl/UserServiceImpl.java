package com.qhyc.fund.service.impl;

import org.springframework.stereotype.Service;

import com.qhyc.fund.entity.User;
import com.qhyc.fund.service.UserService;


@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

	@Override
	public User findById(int id) {
		return sqlSession.selectOne("user.findUserById", id);
	}

}
