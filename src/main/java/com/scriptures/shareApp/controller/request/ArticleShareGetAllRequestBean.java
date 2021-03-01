package com.scriptures.shareApp.controller.request;

public class ArticleShareGetAllRequestBean {

    private String id;

    private String articleTitle;

    private Integer shareTotalCounts;

    private Double shareTotalMoney;

    private Integer shareTotalClick;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Integer getShareTotalCounts() {
        return shareTotalCounts;
    }

    public void setShareTotalCounts(Integer shareTotalCounts) {
        this.shareTotalCounts = shareTotalCounts;
    }

    public Double getShareTotalMoney() {
        return shareTotalMoney;
    }

    public void setShareTotalMoney(Double shareTotalMoney) {
        this.shareTotalMoney = shareTotalMoney;
    }

    public Integer getShareTotalClick() {
        return shareTotalClick;
    }

    public void setShareTotalClick(Integer shareTotalClick) {
        this.shareTotalClick = shareTotalClick;
    }

}
