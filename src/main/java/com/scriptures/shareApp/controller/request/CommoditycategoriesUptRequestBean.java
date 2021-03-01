package com.scriptures.shareApp.controller.request;

import java.util.Date;

public class CommoditycategoriesUptRequestBean {
	private String id;

    private String categoriesname;

    private Integer orderby;

    private String updateBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

   // private String remarks;

   

}
