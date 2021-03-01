package com.scriptures.shareApp.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.scriptures.shareApp.dao.entity.Member;
import org.apache.ibatis.annotations.Update;

public interface MemberMapper {

    int deleteByPrimaryKey(String id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    ////////////////////////////////////////////////////////

    Member selectById(@Param("id") String id);

    List<Member> selectAll();

    int changePwd(@Param("id") String id, @Param("newPassword") String newPassword);


    List<Member> likeSearch(@Param("phone") String phone, @Param("nickname") String nickname, @Param("label") String label);

    int deleteSome(@Param("list") List<String> list, @Param("updateBy") String updateBy, @Param("updateDate") Date updateDate);

    Member login(@Param("phone") String phone, @Param("password") String password);

    Member checkPhone(@Param("phone") String phone);

    Member checkNickname(@Param("nickname") String nickname);

    int getAllCounts();

    @Update("update tb_member set frozen_balance=frozen_balance+#{commission} where id=#{memberId}")
    int  frozenBalanceAdd(@Param("memberId") String memberId,@Param("commission") Double commission);

    @Update("update tb_member set frozen_balance=frozen_balance-#{commission} where id=#{memberId}")
    int frozenBalanceMinus(@Param("memberId") String memberId,@Param("commission") Double commission);

    @Update("update tb_member set frozen_balance=frozen_balance-#{commission} , available_balance=available_balance+#{commission} where id=#{memberId}")
    int frozenBalanceThaw(@Param("memberId") String memberId,@Param("commission") Double commission);
}