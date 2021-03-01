package com.scriptures.shareApp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.contants.OrderStatusEnum;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.ShareCommodity;
import com.scriptures.shareApp.dao.entity.SharedataCommodity;
import com.scriptures.shareApp.dao.mapper.ShareCommodityMapper;
import com.scriptures.shareApp.dao.mapper.SharedataCommodityMapper;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.vo.CommodityOrderVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShareDataCommodityServiceImpl {

    @Resource
    private SharedataCommodityMapper sharedataCommodityMapper;

    @Resource
    private ShareCommodityMapper shareCommodityMapper;

    public void addData(SharedataCommodity sharedataCommodity) {
        //SharedataCommodity sharedataCommodity= new SharedataCommodity();
        sharedataCommodityMapper.insertSelective(sharedataCommodity);
    }

    public PageResponseBean<SharedataCommodity> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SharedataCommodity> sharedataCommodityList = sharedataCommodityMapper.selectAll();


        PageInfo pageInfo = new PageInfo<>(sharedataCommodityList);

        PageResponseBean<SharedataCommodity> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    public PageResponseBean<SharedataCommodity> getByCode(int pageNum, int pageSize, String code) {
        PageHelper.startPage(pageNum, pageSize);
        List<SharedataCommodity> sharedataCommodityList = sharedataCommodityMapper.selectByCode(code);
        PageInfo pageInfo = new PageInfo<>(sharedataCommodityList);
        PageResponseBean<SharedataCommodity> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    public PageResponseBean<SharedataCommodity> getByCodeByPhone(int pageNum, int pageSize, String code, String phone) {
        PageHelper.startPage(pageNum, pageSize);
        List<SharedataCommodity> sharedataCommodityList = sharedataCommodityMapper.getByCodeByPhone(code, phone);
        PageInfo pageInfo = new PageInfo<>(sharedataCommodityList);
        PageResponseBean<SharedataCommodity> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    public PageResponseBean<ShareCommodity> getById(int pageNum, int pageSize, String id) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShareCommodity> shareCommodities=shareCommodityMapper.getListById(id);
        PageInfo pageInfo = new PageInfo<>(shareCommodities);
        PageResponseBean<ShareCommodity> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    public PageResponseBean<ShareCommodity> getByIdByPhone(int pageNum, int pageSize, String id, String phone) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShareCommodity> shareCommodities=shareCommodityMapper.getListByIdByPhone(id,phone);
        PageInfo pageInfo = new PageInfo<>(shareCommodities);
        PageResponseBean<ShareCommodity> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    public ResponseEntity<List<CommodityOrderVo>> getCommodityOrder(int limit) {
        List<CommodityOrderVo> commodityOrderVos=sharedataCommodityMapper.selectOrder(limit);
        return ResponseEntityUtil.success(commodityOrderVos);
    }

    /**
     * 修改佣金/订单状态（解冻，冻结，已失效，已完成）
     * （失效）将“下单30天后”的订单佣金“解冻”
     * 将“冻结”的订单佣金“收回”并将订单设为“已失效”
     * 冻结
     */
    //根据商品分享数据编号修改其订单状态
    public int updateStatus(String id, String status){
        int resultCount=sharedataCommodityMapper.updateStatus(id,status);
        return resultCount;
    }
    //修改订单状态为已完成，并添加完成时间（orderdate）
    public int updateStatusToComlete(String id){
        int resultCount=sharedataCommodityMapper.updateStatusToComlete(id,OrderStatusEnum.COMLETE.getStatus());
        return resultCount;
    }

    //查询指定天数“外”的指定状态订单
    public List<SharedataCommodity> selectByStatusByTimeOut(int day , String status) {
        List<SharedataCommodity> sharedataCommodities=sharedataCommodityMapper.selectByStatusByTimeOut(day,status);
        return sharedataCommodities;
    }
    //查询指定天数“内”的指定状态订单
    public List<SharedataCommodity> selectByStatusByTimeIn(int day , String status) {
        List<SharedataCommodity> sharedataCommodities=sharedataCommodityMapper.selectByStatusByTimeIn(day,status);
        return sharedataCommodities;
    }
    public List<SharedataCommodity> selectListToFreeze(){
        List<SharedataCommodity> sharedataCommodities=sharedataCommodityMapper.selectByStatus(OrderStatusEnum.freeze.getStatus());
        return sharedataCommodities;
    }
}
