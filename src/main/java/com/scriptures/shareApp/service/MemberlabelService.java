package com.scriptures.shareApp.service;

import com.scriptures.shareApp.controller.request.*;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Memberlabel;
import com.scriptures.shareApp.util.ResponseEntity;

public interface MemberlabelService {

    PageResponseBean<Memberlabel> pageGetAll(Integer pageNum, Integer pageSize);

    PageResponseBean<Memberlabel> selectAllTime(Integer pageNum, Integer pageSize);

    PageResponseBean<Memberlabel> selectAllAll(Integer pageNum, Integer pageSize);

    ResponseEntity<String> delete(String id, String updateBy);

    ResponseEntity<String> update(MemberlabelUpdateRequestBean bean);

    ResponseEntity<String> add(MemberlabelAddRequestBean bean);

    ResponseEntity<String> deleteSomeMemberlabel(String ids, String updateBy);
}
