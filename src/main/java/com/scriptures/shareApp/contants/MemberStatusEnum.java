package com.scriptures.shareApp.contants;

public enum MemberStatusEnum {
    //会员账号状态
    NORMAL(0,"正常"),
    DELETE(1,"已删除"),
    STOP(2,"禁用")

    ;
    private int status;
    private String msg;

    MemberStatusEnum(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
