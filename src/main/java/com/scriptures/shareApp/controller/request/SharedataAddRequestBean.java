package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class SharedataAddRequestBean {
	
	@ApiModelProperty(value = "分享Id")
	private String shareId;
	@ApiModelProperty(value = "类型（文章1、商品2）")
    private Integer type;
	@ApiModelProperty(value = "创建人")
    private String createBy;
	@ApiModelProperty(value = "备注")
    private String remarks;
	
	public String getShareId() {
		return shareId;
	}
	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
