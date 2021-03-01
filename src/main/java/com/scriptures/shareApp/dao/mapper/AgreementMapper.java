package com.scriptures.shareApp.dao.mapper;

import com.scriptures.shareApp.dao.entity.Agreement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AgreementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Agreement record);

    int insertSelective(Agreement record);

    Agreement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Agreement record);

    int updateByPrimaryKeyWithBLOBs(Agreement record);

    int updateByPrimaryKey(Agreement record);

    @Select("select * from tb_agreement where type = #{type}")
    Agreement getAgreementByType(@Param("type") int type);

    @Update("update tb_agreement set content = #{content} , update_by = #{updateBy} where type = #{type}")
    Integer updateByType(@Param("type") int type,@Param("content") String content,@Param("updateBy") String updateBy);
}