package com.scriptures.shareApp.service;

import com.scriptures.shareApp.controller.request.AdminAddRequestBean;
import com.scriptures.shareApp.controller.request.AdminGetFuzzyRequestBean;
import com.scriptures.shareApp.controller.request.AdminLoginRequestBean;
import com.scriptures.shareApp.controller.request.AdminUptRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Admin;
import com.scriptures.shareApp.util.ResponseEntity;

public interface AdminService {

	ResponseEntity<Admin> adminLogin(AdminLoginRequestBean bean);

	ResponseEntity<Object> addAdmin(AdminAddRequestBean bean);

	ResponseEntity<Object> adminDelete(String id, String handler);

	PageResponseBean getAllList(int pageNum, int pageSize);

	PageResponseBean getFuzzy_S(AdminGetFuzzyRequestBean bean);

	ResponseEntity<Object> getAdminInfo(String id);

	ResponseEntity<Object> updatePwd(String id, String oldPwd, String newPwd);

	ResponseEntity<Object> update(AdminUptRequestBean bean);

	PageResponseBean getFuzzy(String key, int pageNum, int pageSize);

	ResponseEntity<Object> adminDelSome(String ids, String handler);

	ResponseEntity<Object> updateStatus(String id, int status);




}
