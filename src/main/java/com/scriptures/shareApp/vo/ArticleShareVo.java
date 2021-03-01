package com.scriptures.shareApp.vo;

public class ArticleShareVo {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String articleTitle;

    private Integer shareTotalCounts;// 分享次数

    private Double shareTotalMoney;//分享佣金

    private Integer shareValidClick;//有效点击数（有效分享次数）

    private Integer shareTotalClick;//所有点击数

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

    public Integer getShareValidClick() {
        return shareValidClick;
    }

    public void setShareValidClick(Integer shareValidClick) {
        this.shareValidClick = shareValidClick;
    }
}
