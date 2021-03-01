package com.scriptures.shareApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.scriptures.shareApp.contants.WithdrawalStatusEnum;
import com.scriptures.shareApp.controller.request.*;
import com.scriptures.shareApp.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.entity.Withdrawal;
import com.scriptures.shareApp.dao.mapper.MemberMapper;
import com.scriptures.shareApp.dao.mapper.WithdrawalMapper;
import com.scriptures.shareApp.service.WithdrawalService;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;

import javax.annotation.Resource;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    @Resource
    private WithdrawalMapper withdrawalMapper;
    @Resource
    private MemberMapper memberMapper;

    @Override
    public ResponseEntity<String> update(WithdrawalUpdateRequestBean bean) {
        if (bean == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(bean.getId());
        withdrawal.setStatus(bean.getStatus());
        withdrawal.setUpdateBy(bean.getUpdateBy());
        withdrawal.setUpdateDate(new Date());
        int keyCount = withdrawalMapper.updateByPrimaryKeySelective(withdrawal);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("更新成功！");
        }
        return ResponseEntityUtil.success("更新失败！");
    }

    @Override
    public ResponseEntity<String> add(WithdrawalAddRequestBean bean) {
        if (bean == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
//        else if (withdrawalMapper.selectByCreateBy(bean.getCreateBy())!=null){
//
//        }
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        if (!bean.getAlipayAccount().matches(PHONE_NUMBER_REG)) {
            return ResponseEntityUtil.fail("请输入正确的支付宝账号格式！");
        }
        Member member = memberMapper.selectById(bean.getCreateBy());
        if (member == null) {
            return ResponseEntityUtil.fail("该会员不存在或者已被删除！");
        }
        if (member.getAvailable_balance() == null) {
            return ResponseEntityUtil.fail("余额不足，无法提现！");
        }
        if (bean.getWithdrawal() > member.getAvailable_balance()) {
            return ResponseEntityUtil.fail("余额不足，无法提现！");
        }
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setId(StringUtil.uuidNotLine());
        withdrawal.setStatus(bean.getStatus());
        withdrawal.setAlipayAccount(bean.getAlipayAccount());
        withdrawal.setAlipayName(bean.getAlipayName());
        withdrawal.setCreateBy(bean.getCreateBy());
        withdrawal.setDelFlag(0);
        withdrawal.setRemarks(bean.getRemarks());
        withdrawal.setWithdrawal(bean.getWithdrawal());
        withdrawal.setCreateDate(new Date());
        int keyCount = withdrawalMapper.insertSelective(withdrawal);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("添加成功！");
        }
        return ResponseEntityUtil.success("添加失败！");
    }

    @Override
    public PageResponseBean<Withdrawal> pageWithdrawals(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Withdrawal> withdrawals = (List<Withdrawal>) withdrawalMapper.selectAll();
        PageInfo<Withdrawal> pageInfo = new PageInfo<>(withdrawals);
        pageInfo.setList(withdrawals);
        PageResponseBean<Withdrawal> page = new PageResponseBean<Withdrawal>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(1);
        return page;
    }

    @Override
    public PageResponseBean<Withdrawal> pageWithdrawalsLike(Integer pageNum, Integer pageSize, String key, String alipay, Integer status, String startDay, String endDay) {
        PageHelper.startPage(pageNum, pageSize);
        List<Withdrawal> withdrawals = withdrawalMapper.likeSearch(key, alipay, status, startDay, endDay);
        PageInfo<Withdrawal> pageInfo = new PageInfo<>(withdrawals);
        PageResponseBean<Withdrawal> page = new PageResponseBean<Withdrawal>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(1);
        return page;
    }

    @Override
    public List<Withdrawal> downloadExcel() {
        return withdrawalMapper.downloadExcel();
    }

    @Override
    public ResponseEntity<String> statusUptList(String ids, String updateBy) {
        if (StringUtil.isEmpty(ids) || StringUtil.isEmpty(updateBy)) {
            return ResponseEntityUtil.fail("数据不完整，ids与操作人不能为空");
        }
        String[] temp = ids.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            String id = temp[i];
            list.add(id);
        }
        Date update_date = DateUtil.getCurrentTime();
        int resultCount = withdrawalMapper.statusUptList(list, updateBy, update_date , WithdrawalStatusEnum.SUCCESS.getStatus());
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("批量修改状提现态失败");
        }
        return ResponseEntityUtil.success("批量修改提现状态成功");
    }
    @Override
    public ResponseEntity<String> statusIllegal(String ids, String updateBy) {
        if (StringUtil.isEmpty(ids) || StringUtil.isEmpty(updateBy)) {
            return ResponseEntityUtil.fail("数据不完整，ids与操作人不能为空");
        }
        String[] temp = ids.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            String id = temp[i];
            list.add(id);
        }
        Date update_date = DateUtil.getCurrentTime();
        int resultCount = withdrawalMapper.statusUptList(list, updateBy, update_date , WithdrawalStatusEnum.ILLEGAL.getStatus());
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("批量修改状提现态失败");
        }
        return ResponseEntityUtil.success("批量修改提现状态成功");
    }
    @Override
    public PageResponseBean<Withdrawal> likeStatus(Integer pageNum, Integer pageSize, String status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Withdrawal> withdrawals = withdrawalMapper.likeStatus(status);
        PageInfo<Withdrawal> pageInfo = new PageInfo<>(withdrawals);
        PageResponseBean<Withdrawal> page = new PageResponseBean<Withdrawal>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(1);
        return page;
    }

    //资金解冻
    @Override
    public int commodityCommissionThaw(String memberId, Double commission){
        int resultCount=memberMapper.frozenBalanceThaw(memberId,commission);
        return resultCount;
    }

    //商品佣金到账
    @Override
    public int commodityCommissionAdd(String memberId, Double commission){
        int resultCount=memberMapper.frozenBalanceAdd(memberId,commission);
        return resultCount;
    }
    //冻结资金扣除（买家退款，收回佣金）
    @Override
    public int commodityCommissionMinus(String memberId, Double commission){
        int resultCount=memberMapper.frozenBalanceMinus(memberId,commission);
        return resultCount;
    }


}