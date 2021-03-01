package com.scriptures.shareApp.service;


import java.util.List;

import com.scriptures.shareApp.controller.request.WithdrawalAddRequestBean;
import com.scriptures.shareApp.controller.request.WithdrawalUpdateAgainRequestBean;
import com.scriptures.shareApp.controller.request.WithdrawalUpdateRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Withdrawal;
import com.scriptures.shareApp.util.ResponseEntity;

public interface WithdrawalService {

    ResponseEntity<String> update(WithdrawalUpdateRequestBean bean);

    //ResponseEntity<String> addAgain(WithdrawalUpdateAgainRequestBean bean);

    ResponseEntity<String> add(WithdrawalAddRequestBean bean);

    PageResponseBean<Withdrawal> pageWithdrawals(Integer pageNum, Integer pageSize);

    PageResponseBean<Withdrawal> pageWithdrawalsLike(Integer pageNum, Integer pageSize, String key, String alipay, Integer status, String startDay, String endDay);

    List<Withdrawal> downloadExcel();

    ResponseEntity<String> statusUptList(String ids, String updateBy);

    PageResponseBean<Withdrawal> likeStatus(Integer pageNum, Integer pageSize, String status);

    int commodityCommissionThaw(String memberId, Double commission);

    int commodityCommissionAdd(String memberId, Double commission);

    int commodityCommissionMinus(String memberId, Double commission);

    ResponseEntity<String> statusIllegal(String ids, String updateBy);
}
