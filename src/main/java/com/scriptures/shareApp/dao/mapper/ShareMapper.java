package com.scriptures.shareApp.dao.mapper;

import java.util.Date;
import java.util.List;

import com.scriptures.shareApp.vo.ArticleOrderVo;
import org.apache.ibatis.annotations.Param;

import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.entity.Share;
import org.apache.ibatis.annotations.Select;

public interface ShareMapper {
    int deleteByPrimaryKey(String id);

    int insert(Share record);

    int insertSelective(Share record);

    Share selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Share record);

    int updateByPrimaryKey(Share record);

    ////////////////////////////////////////////////////

    Share selectById(@Param("id") String id);

    List<Share> selectAll();

    List<Share> selectTypeOne();

    List<Share> selectTypeZero();

    List<Share> likeSearch(@Param("key") String key);

    int deleteSome(@Param("list") List<String> list, @Param("updateBy") String updateBy, @Param("updateDate") Date updateDate);

    List<Share> selectShareById(@Param("typeId") String typeId);

    List<ArticleOrderVo> selectShareOrder(@Param("limit") int limit);

    @Select("select count(*) from share_data_by_articleid where type_id = #{articleId}")
    Integer selectShareTotalClicksByArticle(@Param("articleId") String articleId);

//    List<Share> selectByTypeId(@Param("typeId") String typeId);
//
//    List<Share> selectByTypeIdByKey(@Param("typeId") String typeId, @Param("key") String key);
}