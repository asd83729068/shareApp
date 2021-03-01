package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class MemberSearchRequestBean {
	@ApiModelProperty(value = "手机号")
	private String phone;
	@ApiModelProperty(value = "真实姓名")
	private String truename;
	@ApiModelProperty(value = "昵称")
	private String nickname;
	@ApiModelProperty(value = "支付宝账号")
	private String alipayAccount;
	@ApiModelProperty(value = "支付宝姓名")
	private String alipayName;
	@ApiModelProperty(value = "标签")
	private String label;
	@ApiModelProperty(value = "地址")
	private String address;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public String getAlipayName() {
		return alipayName;
	}

	public void setAlipayName(String alipayName) {
		this.alipayName = alipayName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
