package com.scriptures.shareApp.service;

import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Admin;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.entity.Syslog;
import com.scriptures.shareApp.vo.SysLogVo;

public interface SyslogService {

	public int add(Syslog sysLog,Object object);
	
	public PageResponseBean<SysLogVo> pageSysLogAll(Integer pageNum, Integer pageSize);

	
}
