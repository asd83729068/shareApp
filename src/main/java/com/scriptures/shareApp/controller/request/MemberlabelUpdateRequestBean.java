package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class MemberlabelUpdateRequestBean {
	
	@ApiModelProperty(value = "标签id",required=true)
	private String id;
	@ApiModelProperty(value = "标签名字")
	private String labelname;
	@ApiModelProperty(value = "创建人")
    private String updateBy;
	@ApiModelProperty(value = "备注")
    private String remarks;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabelname() {
		return labelname;
	}
	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
