package com.scriptures.shareApp.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.scriptures.shareApp.dao.entity.Image;
import org.apache.ibatis.annotations.Select;

public interface ImageMapper {

    int deleteByPrimaryKey(String id);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);
    //////////////////////////////////////////////////

    Image selectById(@Param("id")String id);

    List<Image> selectAll();

    int deleteSome(@Param("list")List<String> list,@Param("updateBy")String updateBy,@Param("updateDate")Date updateDate);

}