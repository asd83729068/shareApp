package com.scriptures.shareApp.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.scriptures.shareApp.dao.entity.Article;

public interface ArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(String id);

    @Select("SELECT * FROM tb_article WHERE del_flag = 0 AND article_title=#{articleTitle}")
    Article getArticleByTitle(@Param("articleTitle") String articleTitle);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeySelective2(Article record);

//    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    @Select("SELECT * FROM tb_article WHERE del_flag = 0 ORDER BY create_date DESC")
    List<Article> selectAll();

    List<Article> selectFuzzy(@Param("author") String author, @Param("remarks") String remarks, @Param("articleTitle") String articleTitle, @Param("createBy") String createBy);

    int deleteSome(@Param("list") List<String> list, @Param("handler") String handler, @Param("date") Date date);

    List<Article> selectFuzzyByKey(@Param("key") String key, @Param("startDay") String startDay, @Param("endDay") String endDay);

    int getAllCounts();

    List<Article> selectByTitle(@Param("title") String title);

    @Select("SELECT * FROM tb_article WHERE article_title=#{articleTitle} AND del_flag=0")
    Article selectByArticleTitle(@Param("articleTitle") String articleTitle);
}