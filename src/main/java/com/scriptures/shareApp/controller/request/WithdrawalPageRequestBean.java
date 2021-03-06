package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class WithdrawalPageRequestBean {
	@ApiModelProperty(value = "页数")
	private Integer pageNum;
	@ApiModelProperty(value = "行数")
	private Integer pageSize;
	@ApiModelProperty(value = "状态")
    private String status;
	@ApiModelProperty(value = "支付宝账号")
    private String alipayAccount;
	@ApiModelProperty(value = "支付宝名字")
    private String alipayName;
	@ApiModelProperty(value = "会员发起人ID")
    private String createBy;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
}
