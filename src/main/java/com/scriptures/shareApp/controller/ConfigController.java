package com.scriptures.shareApp.controller;

import com.scriptures.shareApp.annotation.ACS;
import com.scriptures.shareApp.controller.request.AgreementUptRequestBean;
import com.scriptures.shareApp.controller.request.ConfigUptRequestBean;
import com.scriptures.shareApp.dao.entity.Agreement;
import com.scriptures.shareApp.dao.entity.Config;
import com.scriptures.shareApp.service.ConfigService;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "分享配置项", produces = "application/json")
@RestController
@RequestMapping("config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    //查看配置
    @ApiOperation(value = "查看配置接口", notes = "")
    @GetMapping("getConfig.do")
    public ResponseEntity<Config> getConfig(){
        return ResponseEntityUtil.success(configService.getConfig());
    }
    //修改配置
    @ApiOperation(value = "修改配置接口", notes = "")
    @PostMapping("setConfig.do")
    public ResponseEntity<Object> setConfig(@RequestBody ConfigUptRequestBean bean){
        Integer resultCount=configService.setConfig(bean);
        return ResponseEntityUtil.updMessage(resultCount);
    }
}
