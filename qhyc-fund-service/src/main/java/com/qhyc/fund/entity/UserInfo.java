package com.qhyc.fund.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * 用户实体
* @ClassName: UserInfo 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author mz
* @date 2017年6月7日 上午10:28:09 
*
 */
public class UserInfo implements Serializable{
	

	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* 
	*/
	private static final long serialVersionUID = 1L;
	/**
	 * 手机号码(主键)
	 */
	private String phone;
	/**
	 * 姓名
	 */
	private String fullName;
	/**
	 * 身份证号
	 */
	private String identityCard;
	
	/**
	 * 银行卡号
	 */
	private String bankCard;
	
	/**
	 * 持卡人姓名
	 */
	private String cardholderName;
	
	/**
	 * 开户行
	 */
	private String  openingBank;
	/**
	 * 开户行地址
	 */
	private String  bankAddr;
	
	/**
	 * 导出状态
	 */
	private String exportStatus;
	
	/**
	 * 有效状态
	 */
	private String validStatus;
	
	/**
	 * 注册时间
	 */
	private Date registerDate;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getCardholderName() {
		return cardholderName;
	}

	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public String getBankAddr() {
		return bankAddr;
	}

	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}

	public String getExportStatus() {
		return exportStatus;
	}

	public void setExportStatus(String exportStatus) {
		this.exportStatus = exportStatus;
	}

	public String getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

     

}
