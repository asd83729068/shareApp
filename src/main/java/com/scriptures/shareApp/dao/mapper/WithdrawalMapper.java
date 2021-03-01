package com.scriptures.shareApp.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.scriptures.shareApp.dao.entity.Withdrawal;
import org.apache.ibatis.annotations.Select;

public interface WithdrawalMapper {
    int deleteByPrimaryKey(String id);

    int insert(Withdrawal record);

    int insertSelective(Withdrawal record);

    Withdrawal selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Withdrawal record);

    int updateByPrimaryKey(Withdrawal record);

    /////////////////////////////////////////////////////////

    List<Withdrawal> selectAll();

    List<Withdrawal> likeSearch(@Param("key") String key, @Param("alipay") String alipay, @Param("status") Integer status, @Param("startDay") String startDay, @Param("endDay") String endDay);

    @Select("SELECT * FROM tb_withdrawal WHERE del_flag = 0")
    List<Withdrawal> downloadExcel();

    int statusUptList(@Param("list") List ids, @Param("updateBy") String updateBy, @Param("date") Date date ,@Param("status") String status);

    @Select("SELECT * FROM tb_withdrawal WHERE status=#{status} AND del_flag = 0")
    List<Withdrawal> likeStatus(@Param("status") String status);

    //int statusIllegal(List<String> list, String updateBy, Date update_date);

//    @Select("SELECT * FROM tb_withdrawal WHERE createBy=#{createBy} AND del_flag = 0 AND status = 0")
//    Withdrawal selectByCreateBy(String createBy);
}