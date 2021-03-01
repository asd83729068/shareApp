package com.scriptures.shareApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import javax.annotation.Resource;

import com.scriptures.shareApp.dao.entity.Share;
import com.scriptures.shareApp.dao.mapper.ShareMapper;
import com.scriptures.shareApp.vo.ArticleShareVo;
import com.scriptures.shareApp.vo.CommodityShareVo;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.controller.request.CommodityAddRequestBean;
import com.scriptures.shareApp.controller.request.CommodityGetFuzzyRequestBean;
import com.scriptures.shareApp.controller.request.CommodityUptReuquestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Article;
import com.scriptures.shareApp.dao.entity.Commodity;
import com.scriptures.shareApp.dao.entity.Commoditycategories;
import com.scriptures.shareApp.dao.mapper.CommodityMapper;
import com.scriptures.shareApp.dao.mapper.CommoditycategoriesMapper;
import com.scriptures.shareApp.service.CommodityService;
import com.scriptures.shareApp.util.DateUtil;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;

/**
 * BUG   两个分页
 * 查看商品详细信息  没有加删除 没有转换品类
 *
 * @author hp
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    @Resource
    private CommodityMapper commodityMapper;

    @Resource
    private ShareMapper shareMapper;

    @Resource
    private CommoditycategoriesMapper categoryMapper;


    @Override
    public ResponseEntity<String> commodityAdd(CommodityAddRequestBean bean) {
        if (StringUtil.isEmpty(bean.getCommodityIntroduction())
                | StringUtil.isBlank(bean.getCommission())
                | StringUtil.isBlank(bean.getCommodityLink())
                | StringUtil.isBlank(bean.getCommodityName())
                | StringUtil.isBlank(bean.getCommodityPrices())
                | StringUtil.isBlank(bean.getCreateBy())
                | StringUtil.isBlank(bean.getRemarks())
                | StringUtil.isBlank(bean.getCommodityCategory())
                | StringUtil.isBlank(bean.getCode())
                ) {
            return ResponseEntityUtil.fail("商品的标题，介绍，链接，价格，提成，状态，创建人，品类,编码不能为空");
        }
        if (commodityMapper.selectByCategoriesName(bean.getCommodityName()) != null) {
            return ResponseEntityUtil.fail("此商品名已存在");
        }
        // 根据id判读商品品类是否存在
        //int categoryCount=categoryMapper.checkById(bean.getCommodityCategory());
        //根据name判断商品品类是否存在
        int categoryCount = categoryMapper.checkName(bean.getCommodityCategory());
        if (categoryCount == 0) {
            return ResponseEntityUtil.fail("你输入的商品品类不存在");
        }
        Commodity commodity = new Commodity();
        commodity.setId(StringUtil.uuidNotLine());
        commodity.setCover(bean.getCover());
        commodity.setCommodityName(bean.getCommodityName());
        commodity.setCommodityIntroduction(bean.getCommodityIntroduction());
        commodity.setCommodityPrices(bean.getCommodityPrices());
        commodity.setCommodityLink(bean.getCommodityLink());
        commodity.setCommodityCategory(bean.getCommodityCategory());
        commodity.setCommission(bean.getCommission());
        commodity.setRemarks(bean.getRemarks());
        commodity.setCode(bean.getCode());
        if (!StringUtil.isBlank(bean.getLabel())) {
            commodity.setLabel(bean.getLabel());
        }
        commodity.setCreateBy(bean.getCreateBy());
        commodity.setCreateDate(DateUtil.getCurrentTime());

        int resultCount = commodityMapper.insertSelective(commodity);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("商品添加失败");
        }
        return ResponseEntityUtil.success("商品添加成功");
    }

    @Override
    public ResponseEntity<String> commodityUpt(CommodityUptReuquestBean bean) {
        if (StringUtil.isBlank(bean.getId())
                | StringUtil.isEmpty(bean.getCommodityIntroduction())
                | StringUtil.isBlank(bean.getCommission())
                | StringUtil.isBlank(bean.getCommodityLink())
                | StringUtil.isBlank(bean.getCommodityName())
                | StringUtil.isBlank(bean.getCommodityPrices())
                | StringUtil.isBlank(bean.getUpdateBy())
                | StringUtil.isBlank(bean.getCommodityCategory())
                ) {
            return ResponseEntityUtil.fail("商品的标题，介绍，链接，价格，提成,修改人，品类不能为空");
        }
        if (commodityMapper.selectByCategoriesName(bean.getCommodityName()) != null && !commodityMapper.selectByCategoriesName(bean.getCommodityName()).getId().equals(bean.getId())) {
            return ResponseEntityUtil.fail("此商品名已存在");
        }
        // 根据id判断商品品类是否存在
        //int categoryCount=categoryMapper.checkById(bean.getCommodityCategory());
        //根据name判断商品品类是否存在
        int categoryCount = categoryMapper.checkName(bean.getCommodityCategory());
        if (categoryCount == 0) {
            return ResponseEntityUtil.fail("你输入的商品品类不存在");
        }
        Commodity commodity = new Commodity();
        commodity.setId(bean.getId());
        commodity.setCover(bean.getCover());
        commodity.setCommodityName(bean.getCommodityName());
        commodity.setCommodityIntroduction(bean.getCommodityIntroduction());
        commodity.setCommodityPrices(bean.getCommodityPrices());
        commodity.setCommodityLink(bean.getCommodityLink());
        commodity.setCommodityCategory(bean.getCommodityCategory());
        commodity.setCommission(bean.getCommission());
        if (!StringUtil.isEmpty(bean.getRemarks())) {
            commodity.setRemarks(bean.getRemarks());
        }
        if (!StringUtil.isEmpty(bean.getLabel())) {
            commodity.setLabel(bean.getLabel());
        }
        commodity.setUpdateBy(bean.getUpdateBy());
        commodity.setUpdateDate(DateUtil.getCurrentTime());

        int resultCount = commodityMapper.updateByPrimaryKeySelective2(commodity);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("商品修改失败");
        }
        return ResponseEntityUtil.success("商品修改成功");
    }

    //分页列表查询
    @Override
    public PageResponseBean<Commodity> getAll(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Commodity> commodities = commodityMapper.selectAll();

//		if (commodities != null && commodities.size() > 0) {
//			for (Commodity commodity : commodities) {
//				String category=categoryMapper.selectNameById(commodity.getCommodityCategory());
//				if (category==null) {
//					commodity.setCommodityCategory("分类失效，请重新添加品类");
//				} else {
//					commodity.setCommodityCategory(category);
//				}
//			}
//		}

        PageInfo<Commodity> pageInfo = new PageInfo<>(commodities);
        PageResponseBean<Commodity> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);

        return pageResponseBean;
    }

    //分页模糊查询
    @Override
    public PageResponseBean<Commodity> getFuzzy(CommodityGetFuzzyRequestBean bean) {
        PageHelper.startPage(bean.getPageNum(), bean.getPageSize());
        List<Commodity> commodities = commodityMapper.selectFuzzy(StringUtil.isBlank(bean.getCommodityName()) ? null : bean.getCommodityName(),
                StringUtil.isBlank(bean.getRemarks()) ? null : bean.getRemarks(),
                StringUtil.isBlank(bean.getCommodityCategory()) ? null : bean.getCommodityCategory());

//		if (commodities != null && commodities.size() > 0) {
//			for (Commodity commodity : commodities) {
//				String category=categoryMapper.selectNameById(commodity.getCommodityCategory());
//				if (category==null) {
//					commodity.setCommodityCategory("分类失效，请重新添加品类");
//				} else {
//					commodity.setCommodityCategory(category);
//				}
//			}
//		}

        PageInfo<Commodity> pageInfo = new PageInfo<>(commodities);
        PageResponseBean<Commodity> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    @Override
    public ResponseEntity<Commodity> getInfo(String id) {
        Commodity commodity = commodityMapper.selectByPrimaryKey(id);
        if (commodity == null) {
            return ResponseEntityUtil.fail("没有查询到相关商品");
        }
//		String category=categoryMapper.selectNameById(commodity.getCommodityCategory());
//		if (category==null) {
//			commodity.setCommodityCategory("分类失效，请重新添加品类");
//		} else {
//			commodity.setCommodityCategory(category);
//		}

        return ResponseEntityUtil.success(commodity);
    }

    @Override
    public ResponseEntity<Commodity> selectInfoByName(String commodityName) {
        Commodity commodity = commodityMapper.selectInfoByName(commodityName);
        if (commodity == null) {
            return ResponseEntityUtil.fail("没有查询到相关商品");
        }
        return ResponseEntityUtil.success(commodity);
    }

    @Override
    public ResponseEntity<String> delete(String id, String handler) {
        if (StringUtil.isEmpty(handler)) {
            return ResponseEntityUtil.fail("操作人不能为空");
        }
        Commodity commodity = new Commodity();
        commodity.setId(id);
        commodity.setUpdateBy(handler);
        commodity.setUpdateDate(DateUtil.getCurrentTime());
        commodity.setDelFlag(1);

        int resultCount = commodityMapper.updateByPrimaryKeySelective(commodity);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("商品删除失败");
        }
        return ResponseEntityUtil.success("商品删除成功");
    }

    @Override
    public ResponseEntity<Object> deleteSome(String ids, String handler) {
        if (StringUtil.isEmpty(ids) || StringUtil.isEmpty(handler)) {
            return ResponseEntityUtil.fail("数据不完整，ids与操作人不能为空");
        }
        String[] temp = ids.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            String id = temp[i];
            list.add(id);
        }
        Date update_date = DateUtil.getCurrentTime();
        int resultCount = commodityMapper.deleteSome(list, handler, update_date);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("批量删除商品失败");
        }
        return ResponseEntityUtil.success("批量删除商品成功");
    }


    public void createCommodityVo() {

    }

    @Override
    public PageResponseBean<Commodity> getFuzzyByKey(int pageNum, int pageSize, String key, String category, String startDay, String endDay) {
        PageHelper.startPage(pageNum, pageSize);
        List<Commodity> commodities = commodityMapper.selectFuzzyByKey(key, category, startDay, endDay);

//		if (commodities != null && commodities.size() > 0) {
//			for (Commodity commodity : commodities) {
//				String category=categoryMapper.selectNameById(commodity.getCommodityCategory());
//				if (category==null) {
//					commodity.setCommodityCategory("分类失效，请重新添加品类");
//				} else {
//					commodity.setCommodityCategory(category);
//				}
//			}
//		}

        PageInfo<Commodity> pageInfo = new PageInfo<>(commodities);
        PageResponseBean<Commodity> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    @Override
    public ResponseEntity<Object> updateStatus(String id, String handler, String status) {
        if (StringUtil.isEmpty(id)
                | StringUtil.isEmpty(handler)
                | StringUtil.isEmpty(status)
                ) {
            return ResponseEntityUtil.fail("参数不完整，商品id、分享状态、更新人都不能为空");
        }
        Commodity commodity = new Commodity();
        commodity.setId(id);
        commodity.setUpdateBy(handler);
        commodity.setRemarks(status);
        commodity.setUpdateDate(DateUtil.getCurrentTime());

        int resultCount = commodityMapper.updateByPrimaryKeySelective(commodity);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("分享状态修改失败");
        }
        return ResponseEntityUtil.success("分享状态修改成功");
    }

    @Override
    public ResponseEntity<Integer> getAllCounts() {
        int counts = commodityMapper.getAllCounts();
        return ResponseEntityUtil.success(counts);
    }

    @Override
    public PageResponseBean getCommodityShare(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommodityShareVo> commodityShareVos = commodityMapper.selectShareData();
        PageInfo<CommodityShareVo> pageInfo = new PageInfo(commodityShareVos);
        PageResponseBean<CommodityShareVo> page = new PageResponseBean<CommodityShareVo>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(200);
        return page;
    }

    @Override
    public PageResponseBean getCommodityShareByName(int pageNum, int pageSize, String commodityName) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommodityShareVo> commodityShareVos = commodityMapper.selectShareDataByName(commodityName);
        PageInfo<CommodityShareVo> pageInfo = new PageInfo(commodityShareVos);
        PageResponseBean<CommodityShareVo> page = new PageResponseBean<CommodityShareVo>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(200);
        return page;
    }

    @Override
    public Double countCommission(String code, Double Deal){
        Commodity commodity=commodityMapper.selectbyCode(code);
        Double commission;
        if(commodity==null){
            commission=0.0;
        }else {
            commission=commodity.getCommission()*Deal;
        }
        //格式化
        return commission;
    }
}
