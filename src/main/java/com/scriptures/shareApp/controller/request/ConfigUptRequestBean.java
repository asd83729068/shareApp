package com.scriptures.shareApp.controller.request;

import java.util.Date;

public class ConfigUptRequestBean {

    private Integer maxShare;

    private Integer intervalTime;

    //private Integer browseTime;

    private String updateBy;

    public Integer getMaxShare() {
        return maxShare;
    }

    public void setMaxShare(Integer maxShare) {
        this.maxShare = maxShare;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
