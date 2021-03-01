package com.scriptures.shareApp.contants;

public enum OrderStatusEnum {

    //商城订单状态
    PAID("2","已付款"),
    REFUNDED("7","已退款"),
    COMLETE("5","已完成"),
    CLOSE("8","已关闭"),
    //"3","已配货"

    //分享后台订单状态
    unfreeze("0","解冻"),
    freeze("1","冻结"),
    invalid("2","失效")
    ;
    private String status;
    private String msg;

    OrderStatusEnum(String status, String msg) {
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
