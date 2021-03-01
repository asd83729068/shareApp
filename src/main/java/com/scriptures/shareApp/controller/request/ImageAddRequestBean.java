package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class ImageAddRequestBean {
    @ApiModelProperty(value = "图片名字")
    private String imagename;
    @ApiModelProperty(value = "图片获得地址")
    private String imageurl;
    @ApiModelProperty(value = "创建人")
    private String createBy;
    @ApiModelProperty(value = "类型")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }


}
