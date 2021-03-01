package com.scriptures.shareApp.vo;

public class CommodityShareVo {

    private String id;

    private String code;

    private String commodityName;

    private Integer shareCounts = 0;

    private Double dealMoney = 0.0;

    private Integer dealCount = 0;

    private Double awardAmount = 0.0;

    private Integer clicks;

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getShareCounts() {
        return shareCounts;
    }

    public void setShareCounts(Integer shareCounts) {
        this.shareCounts = shareCounts;
    }

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
