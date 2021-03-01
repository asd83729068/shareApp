package com.scriptures.shareApp.service;

import com.scriptures.shareApp.controller.request.ConfigUptRequestBean;
import com.scriptures.shareApp.dao.entity.Config;

public interface ConfigService {

    Config getConfig();

    Integer setConfig(ConfigUptRequestBean bean);

}
