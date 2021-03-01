package com.scriptures.shareApp.controller;

import java.util.List;

import com.scriptures.shareApp.controller.request.ImageSelectInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptures.shareApp.controller.request.ImageAddRequestBean;
import com.scriptures.shareApp.controller.request.ImageUpdateRequestBean;
import com.scriptures.shareApp.controller.request.MemberAddRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Image;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.service.ImageService;
import com.scriptures.shareApp.util.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "图片操作接口", produces = "application/json")
@RestController
@RequestMapping("/Image/")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ApiOperation(value = "查找一个图片操作接口", notes = "查找一个图片")
    @GetMapping(value = "getOne.do")
    public ResponseEntity<Image> getOne(@RequestParam String id) {
        return imageService.getOne(id);
    }

    @ApiOperation(value = "查找所有图片操作接口", notes = "查找所有图片")
    @GetMapping(value = "getAll.do")
    public ResponseEntity<List<Image>> getAll() {
        return imageService.getAll();
    }


    @ApiOperation(value = "添加一个图片操作接口", notes = "添加一个图片")
    @PutMapping(value = "add.do")
    public ResponseEntity<String> add(@RequestBody ImageAddRequestBean bean) {
        return imageService.add(bean);
    }

    @ApiOperation(value = "删除一个图片操作接口", notes = "删除一个图片")
    @DeleteMapping(value = "delete.do")
    public ResponseEntity<String> delete(@RequestParam String id, @RequestParam String updateBy) {
        return imageService.delete(id, updateBy);
    }

    @ApiOperation(value = "更新一个图片操作接口", notes = "更新一个图片")
    @PostMapping(value = "update.do")
    public ResponseEntity<String> update(@RequestBody ImageUpdateRequestBean bean) {
        return imageService.update(bean);
    }

    @ApiOperation(value = "分页显示图片操作接口", notes = "分页显示图片")
    @PostMapping(value = "pageGetAll.do")
    public PageResponseBean<Image> pageGetAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return imageService.pageImages(pageNum, pageSize);
    }

    @ApiOperation(value = "批量删除图片操作接口", notes = "批量删除图片")
    @DeleteMapping(value = "deleteSome.do")
    public ResponseEntity<String> deleteSome(@RequestParam String ids, @RequestParam String updateBy) {
        return imageService.deleteSomeImages(ids, updateBy);
    }

    @ApiOperation(value = "跳转到详情", notes = "跳转到详情")
    @PostMapping(value = "selectInfo.do")
    public ResponseEntity<Object> selectInfo(@RequestBody ImageSelectInfo bean) {
        return imageService.selectInfo(bean.getImagename(), bean.getType());
    }
}
