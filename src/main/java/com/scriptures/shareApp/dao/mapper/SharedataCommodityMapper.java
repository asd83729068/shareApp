package com.scriptures.shareApp.dao.mapper;

import com.scriptures.shareApp.dao.entity.SharedataCommodity;
import com.scriptures.shareApp.vo.CommodityOrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SharedataCommodityMapper {
    int deleteByPrimaryKey(String id);

    int insert(SharedataCommodity record);

    int insertSelective(SharedataCommodity record);

    SharedataCommodity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SharedataCommodity record);

    int updateByPrimaryKey(SharedataCommodity record);

    @Select("select * from tb_sharedate_commodity where del_flag = 0")
    List<SharedataCommodity> selectAll();

    List<SharedataCommodity> selectByCode(@Param("code") String code);

    List<SharedataCommodity> getByCodeByPhone(@Param("code") String code, @Param("phone") String phone);

    List<CommodityOrderVo> selectOrder(@Param("limit") int limit);

    @Update("update tb_sharedate_commodity set remarks=#{status} where id=#{id}")
    int updateStatus(@Param("id") String id,@Param("status") String status);
    @Update("update tb_sharedate_commodity set remarks=#{status},order_date = now() where id=#{id}")
    int updateStatusToComlete(@Param("id") String id,@Param("status") String status);

    @Select("select * from tb_sharedate_commodity where remarks=#{status} and DATEDIFF(NOW(),order_date)>#{day}")
    List<SharedataCommodity> selectByStatusByTimeOut(@Param("day") int day ,@Param("status") String status);

    @Select("select * from tb_sharedate_commodity where remarks=#{status} and DATEDIFF(NOW(),order_date)<#{day}")
    List<SharedataCommodity> selectByStatusByTimeIn(@Param("day") int day , @Param("status") String status);

    @Select("select * from tb_sharedate_commodity where remarks=#{status}")
    List<SharedataCommodity> selectByStatus(String status);
}