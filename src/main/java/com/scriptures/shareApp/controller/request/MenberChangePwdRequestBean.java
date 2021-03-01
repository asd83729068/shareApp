package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class MenberChangePwdRequestBean {
	@ApiModelProperty(value = "id",required=true)
	private String id;
	@ApiModelProperty(value = "旧密码")
	private String oldPassword;
	@ApiModelProperty(value = "新密码")
	private String newPassword;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
