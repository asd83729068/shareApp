package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class SharedataUpdateRequestBean {
	
	@ApiModelProperty(value = "Id",required=true)
	private String id;
	@ApiModelProperty(value = "是否成交")
	private Integer issuccess;
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getIssuccess() {
		return issuccess;
	}
	public void setIssuccess(Integer issuccess) {
		this.issuccess = issuccess;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	
}
