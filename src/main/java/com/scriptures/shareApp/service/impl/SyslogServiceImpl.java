package com.scriptures.shareApp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.scriptures.shareApp.vo.SysLogVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Admin;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.entity.Syslog;
import com.scriptures.shareApp.dao.entity.Withdrawal;
import com.scriptures.shareApp.dao.mapper.SyslogMapper;
import com.scriptures.shareApp.service.SyslogService;
import com.scriptures.shareApp.util.StringUtil;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SyslogServiceImpl implements SyslogService {

    @Resource
    private SyslogMapper syslogMapper;

    @Override
    public int add(Syslog sysLog, Object object) {
        Member member = null;
        Admin admin = null;

        if (object.getClass() == Member.class) {
            member = (Member) object;
        }
        if (object.getClass() == Admin.class) {
            admin = (Admin) object;
        }
        if (member != null) {
            sysLog.setId(StringUtil.uuidNotLine());
            sysLog.setCreateBy(member.getNickname());
            sysLog.setCreateDate(new Date());
            syslogMapper.insertSelective(sysLog);
        }
        if (admin != null) {
            sysLog.setId(StringUtil.uuidNotLine());
            sysLog.setCreateBy(admin.getTruename());
            sysLog.setCreateDate(new Date());
            syslogMapper.insertSelective(sysLog);
        }

        return 0;
    }

    @Override
    public PageResponseBean<SysLogVo> pageSysLogAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysLogVo> syslogs = (List<SysLogVo>) syslogMapper.selectAll();
        PageInfo<SysLogVo> pageInfo = new PageInfo<>(syslogs);
        PageResponseBean<SysLogVo> page = new PageResponseBean<SysLogVo>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(200);
        return page;
    }


}
