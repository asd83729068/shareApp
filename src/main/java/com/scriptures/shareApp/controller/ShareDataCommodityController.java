package com.scriptures.shareApp.controller;

import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.ShareCommodity;
import com.scriptures.shareApp.dao.entity.SharedataCommodity;
import com.scriptures.shareApp.service.impl.ShareDataCommodityServiceImpl;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.vo.CommodityOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "商品分享数据接口", produces = "application/json")
@RestController
@RequestMapping("sharedata_commodity")
public class ShareDataCommodityController {

    @Autowired
    private ShareDataCommodityServiceImpl shareDataCommodityService;

    @ApiOperation(value = "查看所有数据", notes = "分页查看所有商品分享数据")
    @GetMapping("getAll.do")
    public PageResponseBean<SharedataCommodity> getAll(@RequestParam int pageNum, @RequestParam int pageSize) {
        return shareDataCommodityService.getAll(pageNum, pageSize);
    }

    @ApiOperation(value = "根据商品编码(code)查看商品分享数据", notes = "根据商品编码(code)查看商品分享数据")
    @GetMapping("getByCode.do")
    public PageResponseBean<SharedataCommodity> getByCode(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String code) {
        return shareDataCommodityService.getByCode(pageNum, pageSize, code);
    }

    @ApiOperation(value = "根据商品编码(code)查看商品分享数据-模糊查询", notes = "根据商品编码(code)查看商品分享数据-模糊查询")
    @GetMapping("getByCodeByPhone.do")
    public PageResponseBean<SharedataCommodity> getByCodeByPhone(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String code, @RequestParam String phone) {
        return shareDataCommodityService.getByCodeByPhone(pageNum, pageSize, code, phone);
    }

    @ApiOperation(value = "根据商品Id查看商品分享数据", notes = "根据商品Id查看商品分享数据")
    @GetMapping("getShareById.do")
    public PageResponseBean<ShareCommodity> getById(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String id) {
        return shareDataCommodityService.getById(pageNum, pageSize, id);
    }

    @ApiOperation(value = "根据商品Id查看商品分享数据-模糊查询", notes = "根据商品Id查看商品分享数据-模糊查询")
    @GetMapping("getShareByIdByPhone.do")
    public PageResponseBean<ShareCommodity> getByIdByPhone(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String id,@RequestParam String phone) {
        return shareDataCommodityService.getByIdByPhone(pageNum, pageSize, id , phone);
    }

    @ApiOperation(value = "商品分享排名", notes = "商品分享排名，成交数量，成交金额；limit：记录条数")
    @GetMapping(value = "commodityOrder.do")
    public ResponseEntity<List<CommodityOrderVo>> getCommodityOrder(@RequestParam int limit) {
        return shareDataCommodityService.getCommodityOrder(limit);
    }
}
