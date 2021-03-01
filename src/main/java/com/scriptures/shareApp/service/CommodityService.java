package com.scriptures.shareApp.service;

import com.scriptures.shareApp.controller.request.CommodityAddRequestBean;
import com.scriptures.shareApp.controller.request.CommodityGetFuzzyRequestBean;
import com.scriptures.shareApp.controller.request.CommodityUptReuquestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Commodity;
import com.scriptures.shareApp.util.ResponseEntity;

public interface CommodityService {

    ResponseEntity<String> commodityAdd(CommodityAddRequestBean bean);

    ResponseEntity<String> commodityUpt(CommodityUptReuquestBean bean);

    PageResponseBean<Commodity> getAll(int pageNum, int pageSize);

    PageResponseBean<Commodity> getFuzzy(CommodityGetFuzzyRequestBean bean);

    ResponseEntity<Commodity> getInfo(String id);

    ResponseEntity<Commodity> selectInfoByName(String commodityName);

    ResponseEntity<String> delete(String id, String handler);

    ResponseEntity<Object> deleteSome(String ids, String handler);

    PageResponseBean<Commodity> getFuzzyByKey(int pageNum, int pageSize, String key, String category, String startDay, String endDay);

    ResponseEntity<Object> updateStatus(String id, String handler, String status);

    ResponseEntity<Integer> getAllCounts();

    PageResponseBean getCommodityShare(int pageNum, int pageSize);

    PageResponseBean getCommodityShareByName(int pageNum, int pageSize, String commodityName);

    Double countCommission(String code, Double Deal);
}
