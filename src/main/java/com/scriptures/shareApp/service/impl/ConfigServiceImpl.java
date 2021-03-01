package com.scriptures.shareApp.service.impl;

import com.scriptures.shareApp.controller.request.ConfigUptRequestBean;
import com.scriptures.shareApp.dao.entity.Config;
import com.scriptures.shareApp.dao.mapper.ConfigMapper;
import com.scriptures.shareApp.service.ConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private ConfigMapper configMapper;

    @Override
    public Config getConfig() {
        return configMapper.selectByPrimaryKey(1);
    }

    @Override
    public Integer setConfig(ConfigUptRequestBean bean){
        Config config = new Config();
        config.setId(1);
        config.setMaxShare(bean.getMaxShare());
        config.setIntervalTime(bean.getIntervalTime());
        config.setUpdateBy(bean.getUpdateBy());
        return configMapper.updateByPrimaryKeySelective(config);
    }
}
