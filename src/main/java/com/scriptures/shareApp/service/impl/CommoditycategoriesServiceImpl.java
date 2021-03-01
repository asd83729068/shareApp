package com.scriptures.shareApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.controller.request.CommoditycategoriesAddRequestBean;
import com.scriptures.shareApp.controller.request.CommoditycategoriesUptRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Admin;
import com.scriptures.shareApp.dao.entity.Article;
import com.scriptures.shareApp.dao.entity.Commodity;
import com.scriptures.shareApp.dao.entity.Commoditycategories;
import com.scriptures.shareApp.dao.mapper.CommodityMapper;
import com.scriptures.shareApp.dao.mapper.CommoditycategoriesMapper;
import com.scriptures.shareApp.service.CommoditycategoriesService;
import com.scriptures.shareApp.util.DateUtil;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;
import com.scriptures.shareApp.vo.ArticleVo;
import com.scriptures.shareApp.vo.CommoditycategoriesVo;


@Service
public class CommoditycategoriesServiceImpl implements CommoditycategoriesService {

	@Resource
	private CommoditycategoriesMapper categorysMapper;
	@Resource
	private CommodityMapper commodityMapper;

	// 添加
	@Override
	public ResponseEntity<Object> categoryAdd(CommoditycategoriesAddRequestBean bean) {
		if (StringUtil.isEmpty(bean.getCreateBy()) | StringUtil.isEmpty(bean.getCategoriesname())
				| StringUtil.isBlank(bean.getOrderby())) {
			return ResponseEntityUtil.fail("创建人,商品名,排序不能为空");
		}
		// 判断品类名称是否重复
		int count = categorysMapper.checkName(bean.getCategoriesname());
		if (count > 0) {
			return ResponseEntityUtil.fail("该类别已存在");
		}
		// 判断排序是否重复
		int count2 = categorysMapper.checkOrderby(bean.getOrderby());
		if (count2 > 0) {
			return ResponseEntityUtil.fail("该序号已被占");
		}
		Commoditycategories categorys = new Commoditycategories();

		categorys.setId(StringUtil.uuidNotLine());
		categorys.setCategoriesname(bean.getCategoriesname());
		categorys.setOrderby(bean.getOrderby());
		categorys.setCreateBy(bean.getCreateBy());
		categorys.setCreateDate(DateUtil.getCurrentTime());
		categorys.setDelFlag(0);
		;

		int resultCount = categorysMapper.insertSelective(categorys);
		if (resultCount == 0) {
			return ResponseEntityUtil.fail("商品类别添加失败");
		}
		return ResponseEntityUtil.success("商品类别添加成功");

	}

	// 编辑名称与排序
	@Override
	public ResponseEntity<Object> categoryUpt(CommoditycategoriesUptRequestBean bean) {
		if (StringUtil.isEmpty(bean.getUpdateBy()) | StringUtil.isEmpty(bean.getCategoriesname())
				| StringUtil.isBlank(bean.getOrderby())) {
			return ResponseEntityUtil.fail("序号和商品名均不能为空");
		}
		if (StringUtil.isEmpty(bean.getUpdateBy())) {
			return ResponseEntityUtil.fail("创建人不能为空");
		}
		int count = categorysMapper.checkUptName(bean.getCategoriesname(), bean.getId());
		if (count > 0) {
			return ResponseEntityUtil.fail("该类别名称已存在");
		}
		// 判断排序是否重复
		int count2 = categorysMapper.checkUptOrderby(bean.getOrderby(), bean.getId());
		if (count2 > 0) {
			return ResponseEntityUtil.fail("该序号已被占");
		}
		String oldCategoryName=categorysMapper.selectNameById(bean.getId());
		Commoditycategories categorys = new Commoditycategories();

		categorys.setId(bean.getId());
		categorys.setCategoriesname(bean.getCategoriesname());
		categorys.setOrderby(bean.getOrderby());
		categorys.setUpdateBy(bean.getUpdateBy());
		categorys.setUpdateDate(DateUtil.getCurrentTime());

		int resultCount = categorysMapper.updateByPrimaryKeySelective(categorys);
		int resultCount0 = commodityMapper.updateCategory(oldCategoryName, bean.getCategoriesname());
		if (resultCount == 0) {
			return ResponseEntityUtil.fail("商品类别修改失败");
		}
		return ResponseEntityUtil.success("商品类别修改成功");

	}

	// 删除，删除前判断是否可删
	@Override
	public ResponseEntity<String> delete(String id, String handler) {
		if (StringUtil.isBlank(id) || StringUtil.isBlank(handler)) {
			return ResponseEntityUtil.fail("id与操作员不能为空");
		}
		List<Commodity> commodities = commodityMapper.selectByCategory(categorysMapper.selectNameById(id));
		if (commodities != null && commodities.size() > 0) {
			return ResponseEntityUtil.fail("该品类下还有商品存在，无法删除");
		}
		Commoditycategories category = new Commoditycategories();
		category.setId(id);
		category.setUpdateBy(handler);
		category.setUpdateDate(DateUtil.getCurrentTime());
		category.setDelFlag(1);

		int resultCount = categorysMapper.updateByPrimaryKeySelective(category);
		if (resultCount == 0) {
			return ResponseEntityUtil.fail("商品品类删除失败");
		}
		return ResponseEntityUtil.success("商品品类删除成功");
	}

	// 列表分页查询
	@Override
	public PageResponseBean<Commoditycategories> getAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Commoditycategories> categorys = categorysMapper.selectAll();
		// List<CommoditycategoriesVo> commoditycategoriesVos=new ArrayList<>();
		// if(categorys!=null && categorys.size()>0) {
		// for(Commoditycategories category :categorys) {
		// commoditycategoriesVos.add(createCategoryVo(category));
		// }
		// }
		PageInfo pageInfo = new PageInfo<>(categorys);
		// pageInfo.setList(commoditycategoriesVos);
		PageResponseBean<Commoditycategories> pageResponseBean = new PageResponseBean<>(pageInfo);
		pageResponseBean.setCode(0);
		pageResponseBean.setHttpStatus(200);
		return pageResponseBean;
	}

	// 根据id具体查询
	@Override
	public ResponseEntity<Object> getInfo(String id) {
		Commoditycategories commoditycategories = categorysMapper.selectByPrimaryKey(id);
		if (commoditycategories == null) {
			return ResponseEntityUtil.fail("查不到数据");
		}
		// CommoditycategoriesVo categoryVo=createCategoryVo(commoditycategories);
		return ResponseEntityUtil.success(commoditycategories);
	}

	public CommoditycategoriesVo createCategoryVo(Commoditycategories commoditycategories) {
		CommoditycategoriesVo commoditycategoriesVo = new CommoditycategoriesVo();

		if (commoditycategories != null) {
			commoditycategoriesVo.setId(commoditycategories.getId());
			commoditycategoriesVo.setCategoriesname(commoditycategories.getCategoriesname());
			commoditycategoriesVo.setOrderby(commoditycategories.getOrderby());
			commoditycategoriesVo.setCreateBy(commoditycategories.getCreateBy());
		}

		return commoditycategoriesVo;
	}

	@Override
	public ResponseEntity<String> deleteSome(String ids, String handler) {
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
		int resultCount = categorysMapper.deleteSome(list, handler, update_date);
		if (resultCount == 0) {
			return ResponseEntityUtil.fail("批量删除品类失败");
		}
		return ResponseEntityUtil.success("批量删除品类成功");
	}

}
