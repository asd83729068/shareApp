package com.scriptures.shareApp.controller.request;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class ArticleUptRequestBean {
    @NotBlank(message = "id不能为空")
    @ApiModelProperty(value = "文章id，必填", required = true)
    private String id;

    @ApiModelProperty(value = "文章封面图", required = true)
    private String cover;
    @ApiModelProperty(value = "文章作者", required = true)
    private String author;

    @NotBlank(message = "标题不能为空")
    @ApiModelProperty(value = "文章标题，必填", required = true)
    private String articleTitle;

    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty(value = "文章内容，必填", required = true)
    private String articleContent;

    @NotBlank(message = "提成金额不能为空")
    @ApiModelProperty(value = "提成金额，必填", required = true)
    private Double commission;

    @NotBlank(message = "更新人不能为空")
    @ApiModelProperty(value = "更新人，当前用户，必填", required = true)
    private String updateBy;

    @NotBlank(message = "分享状态不能为空")
    @ApiModelProperty(value = "分享状态，必填", required = true)
    private String remarks;
    @ApiModelProperty(value = "标签", required = true)
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
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
