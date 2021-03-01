package com.scriptures.shareApp.service;

import java.util.List;

import com.scriptures.shareApp.controller.request.ImageAddRequestBean;
import com.scriptures.shareApp.controller.request.ImageUpdateRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Image;
import com.scriptures.shareApp.util.ResponseEntity;

public interface ImageService {

    ResponseEntity<Image> getOne(String id);

    ResponseEntity<List<Image>> getAll();

    ResponseEntity<String> delete(String id, String updateBy);

    ResponseEntity<String> update(ImageUpdateRequestBean bean);

    ResponseEntity<String> add(ImageAddRequestBean bean);

    PageResponseBean<Image> pageImages(Integer pageNum, Integer pageSize);

    ResponseEntity<String> deleteSomeImages(String ids, String updateBy);

    ResponseEntity<Object> selectInfo(String imagename, Integer type);
}
