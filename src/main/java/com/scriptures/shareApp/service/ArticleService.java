package com.scriptures.shareApp.service;

import com.scriptures.shareApp.controller.request.*;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Article;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.vo.ArticleShareVo;

public interface ArticleService {

    PageResponseBean getListAll(int pageNum, int pageSize);

    PageResponseBean getListFuzzy(ArticleGetFuzzyReuqustBean bean);

    ResponseEntity<Object> ArticleAdd(ArticleAddRequestBean bean);

    ResponseEntity<Object> ArticleUpdate(ArticleUptRequestBean bean);

    ResponseEntity<Object> ArticleStatusUpt(ArticleStatusUptRequestBean bean);

    ResponseEntity<Object> ArticleDelete(String id, String updateBy);

    ResponseEntity<Object> ArticleDelSome(String ids, String handler);

    ResponseEntity<Article> getArticleInfo(String id);

    ResponseEntity<Article> getArticleByTitle(String articleTitle);

    PageResponseBean getListFuzzyByKey(String key, String startDay, String endDay, int pageNum, int pageSize);

    ResponseEntity<Integer> getAllCounts();

    PageResponseBean getArticleShare(int pageNum, int pageSize);

    PageResponseBean getArticleByTitle(String title, int pageNum, int pageSize);

}
