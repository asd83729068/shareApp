package com.scriptures.shareApp.controller.request;

import java.util.Date;

public class CommodityGetFuzzyRequestBean {
	
	private String commodityName;

	//private String commodityIntroduction;

	//private Double commodityPrices;

	private String commodityCategory;

	private String remarks;
	
	private int pageNum;
	
	private int pageSize;
	

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityCategory() {
		return commodityCategory;
	}

	public void setCommodityCategory(String commodityCategory) {
		this.commodityCategory = commodityCategory;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	

}
