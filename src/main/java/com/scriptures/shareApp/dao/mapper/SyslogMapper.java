package com.scriptures.shareApp.dao.mapper;

import java.util.List;

import com.scriptures.shareApp.dao.entity.Syslog;
import com.scriptures.shareApp.vo.SysLogVo;

public interface SyslogMapper {
    int deleteByPrimaryKey(String id);

    int insert(Syslog record);

    int insertSelective(Syslog record);

    Syslog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Syslog record);

    int updateByPrimaryKey(Syslog record);
    
    List<Syslog> selectBySyslog();
    
    ////////////////////////////////////////////////////
    
    List<SysLogVo> selectAll();
}