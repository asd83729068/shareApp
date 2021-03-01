package com.scriptures.shareApp.controller;

import com.scriptures.shareApp.controller.request.CommodityUptStatusRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptures.shareApp.controller.request.CommodityAddRequestBean;
import com.scriptures.shareApp.controller.request.CommodityGetFuzzyRequestBean;
import com.scriptures.shareApp.controller.request.CommodityUptReuquestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Commodity;
import com.scriptures.shareApp.service.CommodityService;
import com.scriptures.shareApp.service.impl.CommodityServiceImpl;
import com.scriptures.shareApp.util.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "商品操作接口", produces = "application/json")
@RestController
@RequestMapping("commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;


    @ApiOperation(value = "添加一个商品接口", notes = "添加一个商品接口")
    @PostMapping("add.do")
    public ResponseEntity<String> commodityAdd(@RequestBody CommodityAddRequestBean bean) {
        return commodityService.commodityAdd(bean);
    }

    @ApiOperation(value = "修改一个商品接口", notes = "修改一个商品接口")
    @PostMapping("update.do")
    public ResponseEntity<String> commodityUpt(@RequestBody CommodityUptReuquestBean bean) {
        return commodityService.commodityUpt(bean);
    }

    @ApiOperation(value = "分页查看所有商品", notes = "分页查看所有商品")
    @GetMapping("getAll.do")
    public PageResponseBean<Commodity> getAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return commodityService.getAll(pageNum, pageSize);
    }

    //模糊查询，多条件
//	public PageResponseBean<Commodity> getFuzzy(CommodityGetFuzzyRequestBean bean) {
//		return commodityService.getFuzzy(bean);
//	}

    @ApiOperation(value = "分页模糊查询", notes = "查询商品名与品类")
    @GetMapping("getFuzzy.do")
    public PageResponseBean<Commodity> getFuzzy(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String key, @RequestParam String category, @RequestParam String startDay, @RequestParam String endDay) {
        return commodityService.getFuzzyByKey(pageNum, pageSize, key, category, startDay, endDay);
    }

    @ApiOperation(value = "查看一个商品的详细信息", notes = "查看一个商品的详细信息")
    @GetMapping("getInfo.do")
    public ResponseEntity<Commodity> getInfo(@RequestParam String id) {
        return commodityService.getInfo(id);
    }

    @ApiOperation(value = "根据商品名查询", notes = "查看一个商品的详细信息")
    @GetMapping("selectInfoByName.do")
    public ResponseEntity<Commodity> selectInfoByName(@RequestParam String commodityName) {
        return commodityService.selectInfoByName(commodityName);
    }

    @ApiOperation(value = "删除一个商品接口", notes = "删除一个商品接口")
    @DeleteMapping("delete.do")
    public ResponseEntity<String> delete(@RequestParam String id, @RequestParam String handler) {
        return commodityService.delete(id, handler);
    }

    @ApiOperation(value = "批量删除商品接口", notes = "批量删除商品接口")
    @DeleteMapping("deleteSome.do")
    public ResponseEntity<Object> deleteSome(@RequestParam String ids, @RequestParam String handler) {
        return commodityService.deleteSome(ids, handler);
    }

    @ApiOperation(value = "修改商品分享状态", notes = "修改一个商品接口")
    @PostMapping("updateStatus.do")
    public ResponseEntity<Object> commodityUptStatus(@RequestBody CommodityUptStatusRequestBean bean) {
        return commodityService.updateStatus(bean.getId(), bean.getHandler(), bean.getStatus());
    }

    @ApiOperation(value = "商品总条数", notes = "商品总条数")
    @GetMapping("getAllCounts")
    public ResponseEntity<Integer> getAllCounts() {
        return commodityService.getAllCounts();
    }

    @ApiOperation(value = "商品分享信息", notes = "商品分享信息")
    @GetMapping("getCommodityShare")
    public PageResponseBean getCommodityShare(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return commodityService.getCommodityShare(pageNum, pageSize);
    }

    @ApiOperation(value = "商品分享信息模糊查询", notes = "模糊查询商品分享信息")
    @GetMapping("getCommodityShareByName")
    public PageResponseBean getCommodityShareByName(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String commodityName) {
        return commodityService.getCommodityShareByName(pageNum, pageSize, commodityName);
    }
}
