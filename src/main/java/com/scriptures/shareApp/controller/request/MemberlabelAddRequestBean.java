package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class MemberlabelAddRequestBean {
	
	@ApiModelProperty(value = "标签名字")
	private String labelname;
	@ApiModelProperty(value = "创建人")
    private String createBy;
	@ApiModelProperty(value = "备注")
    private String remarks;
	
	public String getLabelname() {
		return labelname;
	}
	public void setLabelname(String labelname) {
		this.labelname = labelname;
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
