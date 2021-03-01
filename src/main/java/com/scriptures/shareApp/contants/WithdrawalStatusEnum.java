package com.scriptures.shareApp.contants;

public enum  WithdrawalStatusEnum {

    //商城订单状态
    PENDING("0","待处理"),
    SUCCESS("1","已打款"),
    ILLEGAL("2","违规")

            ;
    private String status;
    private String msg;

    WithdrawalStatusEnum(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
