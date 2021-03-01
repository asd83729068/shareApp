package com.scriptures.shareApp.controller.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class ArticleStatusUptRequestBean {
	@NotBlank(message = "id不能为空")
	@ApiModelProperty(value = "文章id，必填", required = true)
	private String id;

	@NotBlank(message = "分享状态不能为空")
	@ApiModelProperty(value = "分享状态，必填", required = true)
	private String remarks;
	
	@NotBlank(message = "更新人不能为空")
	@ApiModelProperty(value = "更新人，当前用户，必填", required = true)
	private String updateBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	
	
}
