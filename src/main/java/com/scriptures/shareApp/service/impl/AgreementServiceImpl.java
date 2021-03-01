package com.scriptures.shareApp.service.impl;

import com.scriptures.shareApp.dao.entity.Agreement;
import com.scriptures.shareApp.dao.mapper.AgreementMapper;
import com.scriptures.shareApp.service.AgreementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class AgreementServiceImpl implements AgreementService {
    @Resource
    private AgreementMapper agreementMapper;
    @Override
    public Agreement getAgreement(int type) {
        return agreementMapper.getAgreementByType(type);
    }

    @Override
    public Integer updateAgreement(int type , String content , String updateBy) {
        return agreementMapper.updateByType(type,content,updateBy);
    }
}
