package com.scriptures.shareApp.controller;

import com.scriptures.shareApp.annotation.ACS;
import com.scriptures.shareApp.controller.request.AgreementUptRequestBean;
import com.scriptures.shareApp.dao.entity.Agreement;
import com.scriptures.shareApp.service.AgreementService;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "用户协议", produces = "application/json")
@RestController
@RequestMapping("agreement")
public class AgreementController {
    @Autowired
    private AgreementService agreementService;

    //查看用户协议
    //查看隐私政策
    @ACS(allowAnonymous = true)
    @ApiOperation(value = "查看协议接口", notes = "参数：type   0：隐私政策  1：用户协议")
    @GetMapping("getAgreement.do")
    public ResponseEntity<Agreement> getAgreement(@RequestParam int type){
        return ResponseEntityUtil.success(agreementService.getAgreement(type));
    }
    //修改用户协议和隐私政策
    @ApiOperation(value = "修改协议接口", notes = "参数：type   0：隐私政策  1：用户协议")
    @PostMapping("updateAgreement.do")
    public ResponseEntity<Object> updateAgreement(@RequestBody AgreementUptRequestBean bean){
        Integer resultCount=agreementService.updateAgreement(bean.getType() , bean.getContent() , bean.getUpdateBy());
        return ResponseEntityUtil.updMessage(resultCount);
    }
}
