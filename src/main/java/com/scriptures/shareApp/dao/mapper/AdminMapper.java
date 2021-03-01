package com.scriptures.shareApp.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.scriptures.shareApp.dao.entity.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(String id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> selectFuzzy(@Param("phone") String phone, @Param("truename") String truename, @Param("status") Integer status);

    List<Admin> selectFuzzy_2(@Param("key") String key);

    //查重复，删除后不允许注册
    @Select("select count(1) from tb_admin where phone = #{phone} and del_flag = 0")
    int checkPhone(String phone);

    @Select("select * from tb_admin where del_flag = 0 and status != 8 ORDER BY create_date DESC")
    List<Admin> selectAll();

    @Select("select * from tb_admin where id = #{id} and password = #{password}")
    Admin selectById(@Param("id") String id, @Param("password") String password);

    @Select("select * from tb_admin where phone = #{phone} and password = #{password} and del_flag = 0")
    Admin selectByLogin(@Param("phone") String phone, @Param("password") String password);

    @Update("update tb_admin set del_flag = 1 , update_by = #{handler} , update_date = #{updateDate} where id = #{id} and del_flag = 0")
    int deleteById(@Param("id") String id, @Param("handler") String handler, @Param("updateDate") Date updateDate);

    int deleteSome(@Param("list") List<String> list, @Param("handler") String handler, @Param("date") Date date);

    @Select("select count(1) from tb_admin where phone = #{phone} and id != #{id}")
    int checkUptPhone(@Param("id") String id, @Param("phone") String phone);
}