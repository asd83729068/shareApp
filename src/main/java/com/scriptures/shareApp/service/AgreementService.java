package com.scriptures.shareApp.service;

import com.scriptures.shareApp.dao.entity.Agreement;

public interface AgreementService {

    Agreement getAgreement(int type);

    Integer updateAgreement(int type , String content , String updateBy );
}
