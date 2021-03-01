package com.scriptures.shareApp.service.impl;

import java.util.Date;
import java.util.List;

import com.scriptures.shareApp.dao.mapper.ShareMapper;
import com.scriptures.shareApp.vo.ArticleOrderVo;
import com.scriptures.shareApp.vo.ShareArticleDataVo;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.controller.request.SharedataAddRequestBean;
import com.scriptures.shareApp.controller.request.SharedataUpdateRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Sharedata;
import com.scriptures.shareApp.dao.mapper.SharedataMapper;
import com.scriptures.shareApp.service.ShareDateService;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;

import javax.annotation.Resource;

@Service
public class ShareDateServiceImpl implements ShareDateService {

    @Resource
    private SharedataMapper sharedataMapper;
    @Resource
    private ShareMapper shareMapper;

    @Override
    public ResponseEntity<List<Sharedata>> getAll() {
        List<Sharedata> list = sharedataMapper.selectAll();
        if (list.size() == 0) {
            return ResponseEntityUtil.fail("没有任何数据!");
        }
        return ResponseEntityUtil.success(list);
    }

    @Override
    public ResponseEntity<String> update(SharedataUpdateRequestBean bean) {
        if (bean == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Sharedata sharedata = new Sharedata();
        sharedata.setIssuccess(bean.getIssuccess());
        sharedata.setId(bean.getId());
        sharedata.setUpdateBy(bean.getUpdateBy());
        sharedata.setUpdateDate(new Date());
        int keyCount = sharedataMapper.updateByPrimaryKeySelective(sharedata);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("更新成功！");
        }
        return ResponseEntityUtil.fail("更新失败！");
    }

    @Override
    public ResponseEntity<String> add(SharedataAddRequestBean bean, String ipAddress) {
        if (bean == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Sharedata sharedata = new Sharedata();
        sharedata.setId(StringUtil.uuidNotLine());
        sharedata.setCreateBy(bean.getCreateBy());
        sharedata.setDelFlag(0);
        sharedata.setRemarks(bean.getRemarks());
        sharedata.setShareId(bean.getShareId());
        sharedata.setCreateDate(new Date());
        sharedata.setType(bean.getType());
        sharedata.setIpAddress(ipAddress);
        sharedata.setIssuccess(0);
        int keyCount = sharedataMapper.insertSelective(sharedata);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("添加成功！");
        }
        return ResponseEntityUtil.fail("添加失败！");
    }

    @Override
    public PageResponseBean<Sharedata> pageGetAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Sharedata> sharedatas = (List<Sharedata>) sharedataMapper.selectAll();
        PageInfo<Sharedata> pageInfo = new PageInfo<>(sharedatas);


        PageResponseBean<Sharedata> page = new PageResponseBean<Sharedata>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(1);
        return page;
    }

    @Override
    public PageResponseBean<ShareArticleDataVo> pageGetShareData(String typeId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShareArticleDataVo> shareArticleDataVos = sharedataMapper.selectByTypeId(typeId);
        PageInfo<ShareArticleDataVo> pageInfo = new PageInfo<>(shareArticleDataVos);
        PageResponseBean<ShareArticleDataVo> page = new PageResponseBean<ShareArticleDataVo>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(200);
        return page;
    }

    @Override
    public PageResponseBean<ShareArticleDataVo> pageGetShareDataByKey(String key, String typeId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShareArticleDataVo> shareArticleDataVos = sharedataMapper.selectByTypeIdByKey(key, typeId);
        PageInfo<ShareArticleDataVo> pageInfo = new PageInfo<>(shareArticleDataVos);
        PageResponseBean<ShareArticleDataVo> page = new PageResponseBean<ShareArticleDataVo>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(200);
        return page;
    }

    @Override
    public ResponseEntity<List<ArticleOrderVo>> getArticleOrder(int limit) {
        List<ArticleOrderVo> articleOrderVos=shareMapper.selectShareOrder(limit);
        return ResponseEntityUtil.success(articleOrderVos);
    }
}
