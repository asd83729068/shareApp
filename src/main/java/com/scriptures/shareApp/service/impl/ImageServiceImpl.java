package com.scriptures.shareApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.scriptures.shareApp.dao.entity.Article;
import com.scriptures.shareApp.dao.mapper.ArticleMapper;
import com.scriptures.shareApp.dao.mapper.CommodityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.controller.request.ImageAddRequestBean;
import com.scriptures.shareApp.controller.request.ImageUpdateRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Image;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.mapper.ImageMapper;
import com.scriptures.shareApp.service.ImageService;
import com.scriptures.shareApp.util.MD5Util;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;

import javax.annotation.Resource;

@Service
public class ImageServiceImpl implements ImageService {

    @Resource
    private ImageMapper imageMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CommodityMapper commodityMapper;

    @Override
    public ResponseEntity<Image> getOne(String id) {
        if (id == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Image image = imageMapper.selectById(id);
        if (image == null) {
            return ResponseEntityUtil.fail("该图片不存在或者已被删除！");
        }
        return ResponseEntityUtil.success(image);
    }

    @Override
    public ResponseEntity<List<Image>> getAll() {
        List<Image> list = imageMapper.selectAll();
        if (list.size() == 0) {
            return ResponseEntityUtil.fail("没有任何数据!");
        }
        return ResponseEntityUtil.success(list);
    }

    @Override
    public ResponseEntity<String> delete(String id, String updateBy) {
        if (id == null || updateBy == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Image image = imageMapper.selectById(id);
        if (image == null) {
            return ResponseEntityUtil.fail("该图片不存在或者已被删除！");
        }
        image.setDelFlag(1);
        image.setUpdateBy(updateBy);
        image.setUpdateDate(new Date());
        int keyCount = imageMapper.updateByPrimaryKeySelective(image);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("删除成功！");
        }
        return ResponseEntityUtil.fail("删除失败！");
    }

    @Override
    public ResponseEntity<String> update(ImageUpdateRequestBean bean) {
        if (bean == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Image image = new Image();
        image.setId(bean.getId());
        image.setImagename(bean.getImagename());
        image.setImageurl(bean.getImageurl());
        image.setUpdateDate(new Date());
        image.setUpdateBy(bean.getUpdateBy());

        int keyCount = imageMapper.updateByPrimaryKeySelective(image);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("更新成功！");
        }
        return ResponseEntityUtil.fail("更新失败！");
    }

    @Override
    public ResponseEntity<String> add(ImageAddRequestBean bean) {
        if (bean == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Image image = new Image();
        image.setId(StringUtil.uuidNotLine());
        image.setCreateBy(bean.getCreateBy());
        image.setDelFlag(0);
        image.setCreateDate(new Date());
        image.setImagename(bean.getImagename());
        image.setImageurl(bean.getImageurl());
        image.setType(bean.getType());
        int keyCount = imageMapper.insertSelective(image);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("添加成功！");
        }
        return ResponseEntityUtil.fail("添加失败！");
    }

    @Override
    public PageResponseBean<Image> pageImages(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Image> images = (List<Image>) imageMapper.selectAll();
        PageInfo<Image> pageInfo = new PageInfo<>();
        pageInfo.setList(images);
        int nums = imageMapper.selectAll().size();
        int totalPage = nums % pageSize == 0 ? nums / pageSize : nums / pageSize + 1;
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setTotal(nums);
        pageInfo.setPages(totalPage);

        PageResponseBean<Image> page = new PageResponseBean<Image>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(1);
        return page;
    }

    @Override
    public ResponseEntity<String> deleteSomeImages(String ids, String updateBy) {
        String params[] = ids.split(",");//参数jie()
        List<String> list = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            list.add(params[i]);
        }
        int keyCount = imageMapper.deleteSome(list, updateBy, new Date());
        if (keyCount > 0) {
            return ResponseEntityUtil.success("批量删除成功！");
        }
        return ResponseEntityUtil.fail("批量删除失败！");
    }

    @Override
    public ResponseEntity<Object> selectInfo(String imagename, Integer type) {
        if (type == 1) {
            return ResponseEntityUtil.success(articleMapper.selectByArticleTitle(imagename));
        } else if (type == 2) {
            return ResponseEntityUtil.success(commodityMapper.selectByCategoriesName(imagename));
        } else {
            return ResponseEntityUtil.fail("此类型不存在，请联系管理员");
        }
    }
}
