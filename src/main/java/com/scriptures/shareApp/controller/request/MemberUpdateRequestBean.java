package com.scriptures.shareApp.controller.request;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class MemberUpdateRequestBean {
	@ApiModelProperty(value = "id",required=true)
	private String id;
	@ApiModelProperty(value = "真实姓名")
	private String truename;
	@ApiModelProperty(value = "密码")
    private String password;
	@ApiModelProperty(value = "性别")
	private Integer sex;
	@ApiModelProperty(value = "昵称")
	private String nickname;
	@ApiModelProperty(value = "地址")
	private String address;
	@ApiModelProperty(value = "头像")
	private String icon;
	@ApiModelProperty(value = "标签")
	private String label;
	@ApiModelProperty(value = "支付宝账号")
	private String alipayAccount;
	@ApiModelProperty(value = "支付宝姓名")
	private String alipayName;
	@ApiModelProperty(value = "更新人")
	 private String updateBy;

	
	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
}
