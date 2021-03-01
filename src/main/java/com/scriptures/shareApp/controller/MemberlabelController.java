package com.scriptures.shareApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptures.shareApp.controller.request.MemberlabelAddRequestBean;
import com.scriptures.shareApp.controller.request.MemberlabelUpdateRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Memberlabel;
import com.scriptures.shareApp.service.MemberlabelService;
import com.scriptures.shareApp.util.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "标签操作接口", produces = "application/json")
@RestController
@RequestMapping("/Memberlabel/")
public class MemberlabelController {

    @Autowired
    private MemberlabelService memberlabelService;

    @ApiOperation(value = "添加一个标签操作接口", notes = "添加一个标签")
    @PutMapping(value = "add.do")
    public ResponseEntity<String> add(@RequestBody MemberlabelAddRequestBean bean) {
        return memberlabelService.add(bean);
    }

    @ApiOperation(value = "更新一个标签操作接口", notes = "更新一个标签")
    @PostMapping(value = "update.do")
    public ResponseEntity<String> update(@RequestBody MemberlabelUpdateRequestBean bean) {
        return memberlabelService.update(bean);
    }

    @ApiOperation(value = "删除一个标签操作接口", notes = "删除一个标签")
    @DeleteMapping(value = "delete.do")
    public ResponseEntity<String> delete(@RequestParam String id, @RequestParam String updateBy) {
        return memberlabelService.delete(id, updateBy);
    }

    @ApiOperation(value = "显示所有普通标签", notes = "分页显示标签")
    @GetMapping(value = "pagegGetAll.do")
    public PageResponseBean<Memberlabel> pageGetAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return memberlabelService.pageGetAll(pageNum, pageSize);
    }

    @ApiOperation(value = "显示所有时间标签", notes = "分页显示标签")
    @GetMapping(value = "selectAllTime.do")
    public PageResponseBean<Memberlabel> selectAllTime(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return memberlabelService.selectAllTime(pageNum, pageSize);
    }

    @ApiOperation(value = "显示所有标签", notes = "分页显示标签")
    @GetMapping(value = "selectAllAll.do")
    public PageResponseBean<Memberlabel> selectAllAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return memberlabelService.selectAllAll(pageNum, pageSize);
    }

    @ApiOperation(value = "批量删除标签操作接口", notes = "批量删除标签")
    @DeleteMapping(value = "deleteSome.do")
    public ResponseEntity<String> deleteSome(@RequestParam String ids, @RequestParam String updateBy) {
        return memberlabelService.deleteSomeMemberlabel(ids, updateBy);
    }
}
