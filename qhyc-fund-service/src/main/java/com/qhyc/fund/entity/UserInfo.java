package com.qhyc.fund.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 用户实体类
* @ClassName: User 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author mz
* @date 2017年6月1日 下午8:34:02 
*
 */
public class User implements Serializable{
	
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* 
	*/
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String name;
	
	private String pwd;
	
	private int age;
	
	private BigDecimal balance;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	

}
