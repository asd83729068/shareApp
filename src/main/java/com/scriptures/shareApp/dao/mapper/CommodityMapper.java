package com.scriptures.shareApp.dao.mapper;

import java.util.Date;
import java.util.List;

import com.scriptures.shareApp.vo.CommodityShareVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.scriptures.shareApp.dao.entity.Article;
import com.scriptures.shareApp.dao.entity.Commodity;

public interface CommodityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(String id);

    @Select("select * from tb_commodity where del_flag = 0 AND commodity_name=#{commodityName}")
    Commodity selectInfoByName(@Param("commodityName") String commodityName);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKeySelective2(Commodity record);

    int updateByPrimaryKey(Commodity record);

    @Select("select * from tb_commodity where del_flag = 0 ORDER BY create_date DESC")
    List<Commodity> selectAll();

    List<Commodity> selectFuzzy(@Param("name") String name, @Param("remarks") String remarks, @Param("commodityCategory") String commodityCategory);

    int deleteSome(@Param("list") List<String> list, @Param("handler") String handler, @Param("date") Date update_date);


    List<Commodity> selectByCategory(String id);

    List<Commodity> selectFuzzyByKey(@Param("key") String key, @Param("category") String category, @Param("startDay") String startDay, @Param("endDay") String endDay);

    @Update("UPDATE tb_commodity set commodity_category = #{categoriesname} where commodity_category = #{oldCategoryName} and del_flag = 0")
    int updateCategory(@Param("oldCategoryName") String oldCategoryName, @Param("categoriesname") String categoriesname);

    int getAllCounts();

    List<Commodity> selectByName(@Param("name") String name);

    @Select("SELECT * FROM tb_commodity WHERE commodity_name=#{categoriesName} AND del_flag=0")
    Commodity selectByCategoriesName(@Param("categoriesName") String categoriesName);

    List<CommodityShareVo> selectShareData();

    List<CommodityShareVo> selectShareDataByName(@Param("commodityName") String commodityName);

    @Select("select * from tb_commodity where code = #{code} and del_flag = 0")
    Commodity selectbyCode(@Param("code")String code);
}