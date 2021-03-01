package com.scriptures.shareApp.dao.mapper;

import java.util.Date;
import java.util.List;

import com.scriptures.shareApp.vo.ShareArticleDataVo;
import org.apache.ibatis.annotations.Param;
import com.scriptures.shareApp.dao.entity.Sharedata;

public interface SharedataMapper {
    int deleteByPrimaryKey(String id);

    int insert(Sharedata record);

    int insertSelective(Sharedata record);

    Sharedata selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Sharedata record);

    int updateByPrimaryKey(Sharedata record);

    ////////////////////////////////////////////////////

    List<Sharedata> selectAll();

    Sharedata selectByIpAddress(@Param("ipAddress") String ipAddress);

//    List<Sharedata> selectByShareId(@Param("shareId")String shareId);

    List<ShareArticleDataVo> selectByTypeId(@Param("typeId") String typeId);

    List<ShareArticleDataVo> selectByTypeIdByKey(@Param("key") String key, @Param("typeId") String typeId);
}