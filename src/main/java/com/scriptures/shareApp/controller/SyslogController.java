package com.scriptures.shareApp.controller;

import com.scriptures.shareApp.vo.SysLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Share;
import com.scriptures.shareApp.dao.entity.Syslog;
import com.scriptures.shareApp.service.SyslogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="日志操作接口",produces = "application/json")
@RestController
@RequestMapping("/Syslog/")
public class SyslogController {

	@Autowired
	private SyslogService syslogService;
	
	@ApiOperation(value = "用分页显示日志操作接口",notes = "分页显示日志")
	@PostMapping(value="pageGetAll.do")
	public PageResponseBean<SysLogVo> pageGetAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
		return syslogService.pageSysLogAll(pageNum, pageSize);
	}
}
