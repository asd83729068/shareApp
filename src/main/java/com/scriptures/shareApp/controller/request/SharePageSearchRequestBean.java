package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class SharePageSearchRequestBean {
	@ApiModelProperty(value = "页数")
	private Integer pageNum;
	@ApiModelProperty(value = "行数")
	private Integer pageSize;
	@ApiModelProperty(value = "类型（文章1、商品2）")
    private String type;
	@ApiModelProperty(value = "会员ID")
    private String memberId;
	@ApiModelProperty(value = "分享方式")
    private String shareWay;
	
	
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getShareWay() {
		return shareWay;
	}
	public void setShareWay(String shareWay) {
		this.shareWay = shareWay;
	}
	
	
}
