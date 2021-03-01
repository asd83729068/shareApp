package com.scriptures.shareApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.controller.request.MemberAddRequestBean;
import com.scriptures.shareApp.controller.request.MemberUpdateRequestBean;
import com.scriptures.shareApp.controller.request.MemberlabelAddRequestBean;
import com.scriptures.shareApp.controller.request.MemberlabelUpdateRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.entity.Memberlabel;
import com.scriptures.shareApp.dao.mapper.MemberlabelMapper;
import com.scriptures.shareApp.service.MemberlabelService;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;

import javax.annotation.Resource;

@Service
public class MemberlabelServiceImpl implements MemberlabelService {

    @Resource
    private MemberlabelMapper memberlabelMapper;

    @Override
    public PageResponseBean<Memberlabel> pageGetAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Memberlabel> memberlabels = (List<Memberlabel>) memberlabelMapper.selectAll();
        PageInfo<Memberlabel> pageInfo = new PageInfo<>(memberlabels);

        PageResponseBean<Memberlabel> page = new PageResponseBean<Memberlabel>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(1);
        return page;
    }

    @Override
    public PageResponseBean<Memberlabel> selectAllTime(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Memberlabel> memberlabels = (List<Memberlabel>) memberlabelMapper.selectAllTime();
        PageInfo<Memberlabel> pageInfo = new PageInfo<>(memberlabels);

        PageResponseBean<Memberlabel> page = new PageResponseBean<Memberlabel>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(1);
        return page;
    }

    @Override
    public PageResponseBean<Memberlabel> selectAllAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Memberlabel> memberlabels = (List<Memberlabel>) memberlabelMapper.selectAllAll();
        PageInfo<Memberlabel> pageInfo = new PageInfo<>(memberlabels);

        PageResponseBean<Memberlabel> page = new PageResponseBean<Memberlabel>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(1);
        return page;
    }

    @Override
    public ResponseEntity<String> delete(String id, String updateBy) {
        if (id == null || updateBy == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Memberlabel memberlabel = memberlabelMapper.selectById(id);
        if (memberlabel == null) {
            return ResponseEntityUtil.fail("该标签不存在或者已被删除！");
        }
        memberlabel.setDelFlag(1);
        memberlabel.setUpdateBy(updateBy);
        memberlabel.setUpdateDate(new Date());
        int keyCount = memberlabelMapper.updateByPrimaryKeySelective(memberlabel);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("删除成功！");
        }
        return ResponseEntityUtil.fail("删除失败！");
    }

    @Override
    public ResponseEntity<String> update(MemberlabelUpdateRequestBean bean) {
        if (bean == null || StringUtil.isEmpty(bean.getLabelname())) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        if (!bean.getLabelname().matches("^[A-Za-z0-9\u4e00-\u9fa5]+$")) {
            return ResponseEntityUtil.fail("标签名只能为汉子，字母，数字，下划线,不能含有特殊字符");
        }
        if (checkLabelName(bean.getLabelname()) && StringUtil.isEmpty(bean.getRemarks())) {
            return ResponseEntityUtil.fail("该标签名已存在！");
        }
        Memberlabel memberlabel = new Memberlabel();
        memberlabel.setId(bean.getId());
        memberlabel.setLabelname(bean.getLabelname());
        memberlabel.setUpdateBy(bean.getUpdateBy());
        memberlabel.setUpdateDate(new Date());
        memberlabel.setRemarks(bean.getRemarks());
        int keyCount = memberlabelMapper.updateByPrimaryKeySelective(memberlabel);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("更新成功！");
        }
        return ResponseEntityUtil.fail("更新失败！");
    }

    @Override
    public ResponseEntity<String> add(MemberlabelAddRequestBean bean) {
        if (bean == null || StringUtil.isEmpty(bean.getLabelname())) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        if (!bean.getLabelname().matches("^[A-Za-z0-9\u4e00-\u9fa5]+$")) {
            return ResponseEntityUtil.fail("标签名只能为汉子，字母，数字，下划线,不能含有特殊字符");
        }
        if (checkLabelName(bean.getLabelname())) {
            return ResponseEntityUtil.fail("该标签名已存在！");
        }
        Memberlabel memberlabel = new Memberlabel();
        memberlabel.setId(StringUtil.uuidNotLine());
        memberlabel.setLabelname(bean.getLabelname());
        memberlabel.setCreateBy(bean.getCreateBy());
        memberlabel.setRemarks(bean.getRemarks());
        memberlabel.setCreateDate(new Date());
        memberlabel.setDelFlag(0);
        int keyCount = memberlabelMapper.insertSelective(memberlabel);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("添加成功！");
        }
        return ResponseEntityUtil.fail("添加失败！");
    }

    private boolean checkLabelName(String name) {
        Memberlabel memberlabel = memberlabelMapper.checkName(name);
        if (memberlabel == null) {
            return false;
        }
        return true;
    }

    @Override
    public ResponseEntity<String> deleteSomeMemberlabel(String ids, String updateBy) {
        String params[] = ids.split(",");//参数jie()
        List<String> list = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            list.add(params[i]);
        }
        int keyCount = memberlabelMapper.deleteSome(list, updateBy, new Date());
        if (keyCount > 0) {
            return ResponseEntityUtil.success("批量删除成功！");
        }
        return ResponseEntityUtil.fail("批量删除失败！");
    }

}
