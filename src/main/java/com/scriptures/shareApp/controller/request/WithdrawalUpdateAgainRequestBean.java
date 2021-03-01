package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class WithdrawalUpdateAgainRequestBean {
    @ApiModelProperty(value = "id", required = true)
    private String id;
    @ApiModelProperty(value = "提现金额")
    private Double withdrawal;
    @ApiModelProperty(value = "会员手机号")
    private String createBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(Double withdrawal) {
        this.withdrawal = withdrawal;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
