package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class ImageSelectInfo {
    @ApiModelProperty(value = "名称")
    private String imagename;
    @ApiModelProperty(value = "类型")
    private Integer type;

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
