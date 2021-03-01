package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class WithdrawalAddRequestBean {
	
	@ApiModelProperty(value = "提现金额")
    private Double withdrawal;
	@ApiModelProperty(value = "状态")
    private Integer status;
	@ApiModelProperty(value = "支付宝账号")
    private String alipayAccount;
	@ApiModelProperty(value = "支付宝名字")
    private String alipayName;
	@ApiModelProperty(value = "会员手机号")
    private String createBy;
	@ApiModelProperty(value = "会员昵称")
    private String remarks;
	
	public Double getWithdrawal() {
		return withdrawal;
	}
	public void setWithdrawal(Double withdrawal) {
		this.withdrawal = withdrawal;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
