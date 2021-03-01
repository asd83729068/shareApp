package com.scriptures.shareApp.service;

import com.scriptures.shareApp.controller.request.CommoditycategoriesAddRequestBean;
import com.scriptures.shareApp.controller.request.CommoditycategoriesUptRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Commoditycategories;
import com.scriptures.shareApp.util.ResponseEntity;

public interface CommoditycategoriesService {

	ResponseEntity<Object> categoryAdd(CommoditycategoriesAddRequestBean bean);

	ResponseEntity<Object> categoryUpt(CommoditycategoriesUptRequestBean bean);

	ResponseEntity<String> delete(String id, String handler);

	PageResponseBean<Commoditycategories> getAll(int pageNum, int pageSize);

	ResponseEntity<Object> getInfo(String id);

	ResponseEntity<String> deleteSome(String ids, String handler);

}
