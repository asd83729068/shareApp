package com.scriptures.shareApp.vo;

public class CommodityOrderVo {


    private String code;

    private String commodityName;

   // private Integer shareCounts = 0;

    private Double dealMoney = 0.0;

    private Integer dealCount = 0;

    private Double awardAmount = 0.0;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

//    public Integer getShareCounts() {
//        return shareCounts;
//    }
//
//    public void setShareCounts(Integer shareCounts) {
//        this.shareCounts = shareCounts;
//    }

    public Double getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(Double dealMoney) {
        this.dealMoney = dealMoney;
    }

    public Integer getDealCount() {
        return dealCount;
    }

    public void setDealCount(Integer dealCount) {
        this.dealCount = dealCount;
    }

    public Double getAwardAmount() {
        return awardAmount;
    }

    public void setAwardAmount(Double awardAmount) {
        this.awardAmount = awardAmount;
    }
}
