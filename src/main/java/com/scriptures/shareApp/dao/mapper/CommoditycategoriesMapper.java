package com.scriptures.shareApp.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.scriptures.shareApp.dao.entity.Commoditycategories;

public interface CommoditycategoriesMapper {
    int deleteByPrimaryKey(String id);

    int insert(Commoditycategories record);

    int insertSelective(Commoditycategories record);

    Commoditycategories selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Commoditycategories record);

    int updateByPrimaryKey(Commoditycategories record);

    @Select("select * from tb_commoditycategories where del_flag = 0 ORDER BY orderby ASC")
    List<Commoditycategories> selectAll();

    @Select("select count(1) from tb_commoditycategories where id = #{id} and del_flag = 0 ORDER BY orderby ASC")
    int checkById(String id);

    @Select("select count(1) from tb_commoditycategories where categoriesname = #{categoriesname} and del_flag = 0 ORDER BY orderby ASC")
    int checkName(@Param("categoriesname") String categoriesname);

    @Select("select count(1) from tb_commoditycategories where categoriesname = #{categoriesname} and id != #{id} and del_flag = 0 ORDER BY orderby ASC")
    int checkUptName(@Param("categoriesname") String categoriesname, @Param("id") String id);

    @Select("select categoriesname from tb_commoditycategories WHERE id = #{id} and del_flag = 0 ORDER BY orderby ASC")
    String selectNameById(@Param("id") String id);

    @Select("select count(1) from tb_commoditycategories where orderby = #{orderby} and del_flag = 0 ORDER BY orderby ASC")
    int checkOrderby(Integer orderby);

    @Select("select count(1) from tb_commoditycategories where orderby = #{orderby} and id != #{id} and del_flag = 0 ORDER BY orderby ASC")
    int checkUptOrderby(@Param("orderby") Integer orderby, @Param("id") String id);

    int deleteSome(@Param("list") List<String> list, @Param("handler") String handler, @Param("date") Date update_date);
}