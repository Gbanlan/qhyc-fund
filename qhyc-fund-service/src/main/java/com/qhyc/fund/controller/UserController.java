package com.qhyc.fund.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qhyc.fund.entity.User;
import com.qhyc.fund.service.UserService;


@Controller
@RequestMapping("/user/")
public class UserController {

	@Resource
	private UserService userService;
	
	@RequestMapping(value="goHome/{id}")
	 public @ResponseBody User goHome(@PathVariable int id){
		User user=userService.findById(id);
		System.out.println(user.getName());
		return user;
	 }
}
