package com.scriptures.shareApp.controller.request;

import java.util.Date;

public class CommodityUptReuquestBean {
    private String id;

    private String cover;

    private String commodityName;

    private String commodityIntroduction;

    private Double commodityPrices;

    private String commodityLink;

    private String commodityCategory;

    private Double commission;

    private String updateBy;

    private String remarks;

    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityIntroduction() {
        return commodityIntroduction;
    }

    public void setCommodityIntroduction(String commodityIntroduction) {
        this.commodityIntroduction = commodityIntroduction;
    }

    public Double getCommodityPrices() {
        return commodityPrices;
    }

    public void setCommodityPrices(Double commodityPrices) {
        this.commodityPrices = commodityPrices;
    }

    public String getCommodityLink() {
        return commodityLink;
    }

    public void setCommodityLink(String commodityLink) {
        this.commodityLink = commodityLink;
    }

    public String getCommodityCategory() {
        return commodityCategory;
    }

    public void setCommodityCategory(String commodityCategory) {
        this.commodityCategory = commodityCategory;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


}
