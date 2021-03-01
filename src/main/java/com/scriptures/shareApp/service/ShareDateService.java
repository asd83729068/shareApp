package com.scriptures.shareApp.service;

import java.util.List;

import com.scriptures.shareApp.controller.request.SharedataAddRequestBean;
import com.scriptures.shareApp.controller.request.SharedataUpdateRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Sharedata;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.vo.ArticleOrderVo;
import com.scriptures.shareApp.vo.ShareArticleDataVo;

public interface ShareDateService {

    public ResponseEntity<List<Sharedata>> getAll();

    public ResponseEntity<String> update(SharedataUpdateRequestBean bean);

    public ResponseEntity<String> add(SharedataAddRequestBean bean, String ipAddress);

    public PageResponseBean<Sharedata> pageGetAll(Integer pageNum, Integer pageSize);

    public PageResponseBean<ShareArticleDataVo> pageGetShareData(String typeId, Integer pageNum, Integer pageSize);

    public PageResponseBean<ShareArticleDataVo> pageGetShareDataByKey(String key, String typeId, Integer pageNum, Integer pageSize);

    ResponseEntity<List<ArticleOrderVo>> getArticleOrder(int limit);
}
