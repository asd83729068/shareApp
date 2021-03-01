package com.scriptures.shareApp.dao.mapper;

import com.scriptures.shareApp.dao.entity.ShareCommodity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ShareCommodityMapper {
    int deleteByPrimaryKey(String id);

    int insert(ShareCommodity record);

    int insertSelective(ShareCommodity record);

    ShareCommodity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ShareCommodity record);

    int updateByPrimaryKey(ShareCommodity record);

    @Select("select * from tb_share_commodity where commodity_id = #{id} ORDER BY create_date DESC")
    List<ShareCommodity> getListById(@Param("id") String id);

    List<ShareCommodity> getListByIdByPhone(@Param("id") String id, @Param("phone") String phone);
}