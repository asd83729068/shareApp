package com.scriptures.shareApp.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class ImageUpdateRequestBean {

	@ApiModelProperty(value = "图片id")
	private String id;
	@ApiModelProperty(value = "图片名字")
	private String imagename;
	@ApiModelProperty(value = "图片获得地址")
    private String imageurl;
	@ApiModelProperty(value = "更新人")
    private String updateBy;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	
}
