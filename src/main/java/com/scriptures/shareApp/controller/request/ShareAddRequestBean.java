package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class ShareAddRequestBean {

	@ApiModelProperty(value = "类型（文章1、商品2）")
    private Integer type;
	@ApiModelProperty(value = "文章/商品ID")
    private String typeId;
	@ApiModelProperty(value = "会员ID")
    private String memberId;
	@ApiModelProperty(value = "分享方式")
    private String shareWay;
	@ApiModelProperty(value = "奖励金额")
    private Double reward;
	@ApiModelProperty(value = "备注")
    private String remarks;
	@ApiModelProperty(value = "创建人")
    private String createBy;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
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
	public Double getReward() {
		return reward;
	}
	public void setReward(Double reward) {
		this.reward = reward;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
}
