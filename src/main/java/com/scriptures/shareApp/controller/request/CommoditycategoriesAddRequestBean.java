package com.scriptures.shareApp.controller.request;

import java.util.Date;

public class CommoditycategoriesAddRequestBean {

    private String categoriesname;

    private Integer orderby;

    private String createBy;

	public String getCategoriesname() {
		return categoriesname;
	}

	public void setCategoriesname(String categoriesname) {
		this.categoriesname = categoriesname;
	}

	public Integer getOrderby() {
		return orderby;
	}

	public void setOrderby(Integer orderby) {
		this.orderby = orderby;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

    //private String remarks;
    
    
}
