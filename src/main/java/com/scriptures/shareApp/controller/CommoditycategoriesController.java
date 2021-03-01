package com.scriptures.shareApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.scriptures.shareApp.controller.request.CommoditycategoriesAddRequestBean;
import com.scriptures.shareApp.controller.request.CommoditycategoriesUptRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Commoditycategories;
import com.scriptures.shareApp.service.CommoditycategoriesService;
import com.scriptures.shareApp.service.impl.CommoditycategoriesServiceImpl;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.vo.ArticleVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="商品品类操作接口",produces = "application/json")
@RestController
@RequestMapping("category")
public class CommoditycategoriesController {
	@Autowired 
	private CommoditycategoriesService commoditycategoriesService;
	
	@ApiOperation(value="商品品类添加接口" , notes="商品品类添加接口。")
	@PutMapping("add.do")
	public ResponseEntity<Object> categoryAdd(@RequestBody CommoditycategoriesAddRequestBean bean) {
		return commoditycategoriesService.categoryAdd(bean);
	} 
	@ApiOperation(value="商品品类更新" , notes="商品品类更新。")
	@PostMapping("update.do")
	public ResponseEntity<Object> categoryUpt(@RequestBody CommoditycategoriesUptRequestBean bean) {
		return commoditycategoriesService.categoryUpt(bean);
	}
	@ApiOperation(value="列表查询所有品类" , notes="分页。")
	@GetMapping("getList.do")
	public PageResponseBean<Commoditycategories> getAll(@RequestParam Integer pageNum ,@RequestParam Integer pageSize) {
		return commoditycategoriesService.getAll(pageNum, pageSize);
 	}
	@ApiOperation(value="根据id查看品类详细信息" , notes="根据id查看品类详细信息。")
	@GetMapping("getInfo.do")
	public ResponseEntity<Object> getInfo(@RequestParam String id) {
		return commoditycategoriesService.getInfo(id);
	}
	@ApiOperation(value="删除一个商品品类" , notes="")
	@DeleteMapping("delete.do")
	public ResponseEntity<String> delete(@RequestParam String id,@RequestParam String handler) {
		return commoditycategoriesService.delete(id, handler);
	}
	
	@ApiOperation(value="批量删除商品品类" , notes="")
	@DeleteMapping("deleteSome.do")
	public ResponseEntity<String> deleteSome(@RequestParam String ids,@RequestParam String handler) {
		return commoditycategoriesService.deleteSome(ids, handler);
	}
	
}

