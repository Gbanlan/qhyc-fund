package com.qhyc.fund.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qhyc.fund.entity.User;
import com.qhyc.fund.service.UserService;


@Controller
public class UserController {

	@Resource
	private UserService userService;
	
	@RequestMapping(value="/index/{id}")
	 public String goToIndexJsp(@PathVariable int id){
		User user=userService.findById(id);
		System.out.println(user.getName());
		return "home";
	 }
}
