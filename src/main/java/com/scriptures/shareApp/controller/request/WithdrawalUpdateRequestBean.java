package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class WithdrawalUpdateRequestBean {
	
	@ApiModelProperty(value = "id",required=true)
	private String id;
	@ApiModelProperty(value = "状态")
    private Integer status;
	@ApiModelProperty(value = "更新人名字")
	private String updateBy;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
}
