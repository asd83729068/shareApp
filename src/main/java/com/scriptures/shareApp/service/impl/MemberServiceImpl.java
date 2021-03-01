package com.scriptures.shareApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.scriptures.shareApp.contants.MemberStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.contants.Const;
import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.controller.request.MemberAddRequestBean;
import com.scriptures.shareApp.controller.request.MemberLoginRequestBean;
import com.scriptures.shareApp.controller.request.MemberPageRequestBean;
import com.scriptures.shareApp.controller.request.MemberSearchRequestBean;
import com.scriptures.shareApp.controller.request.MemberUpdateRequestBean;
import com.scriptures.shareApp.controller.request.MenberChangePwdRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.mapper.MemberMapper;
import com.scriptures.shareApp.service.MemberService;
import com.scriptures.shareApp.util.MD5Util;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;


    @Override
    public ResponseEntity<Member> getOne(String id) {
        if (id == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Member member = memberMapper.selectById(id);
        if (member == null) {
            return ResponseEntityUtil.fail("该会员不存在或者已被删除！");
        }
        return ResponseEntityUtil.success(member);
    }

    @Override
    public ResponseEntity<List<Member>> getAll() {
        List<Member> list = memberMapper.selectAll();
        if (list.size() == 0) {
            return ResponseEntityUtil.fail("没有任何数据!");
        }
        return ResponseEntityUtil.success(list);
    }

    @Override
    public ResponseEntity<String> delete(String id, String updateBy) {
        if (id == null || updateBy == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Member member = memberMapper.selectById(id);
        if (member == null) {
            return ResponseEntityUtil.fail("该会员不存在或者已被删除！");
        }
        member.setDelFlag(1);
        member.setUpdateBy(updateBy);
        member.setUpdateDate(new Date());
        int keyCount = memberMapper.updateByPrimaryKeySelective(member);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("删除成功！");
        }
        return ResponseEntityUtil.fail("删除失败！");

    }

    @Override
    public ResponseEntity<String> update(MemberUpdateRequestBean bean) {
        if (bean == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        String reg = "^[\\u4E00-\\u9FFF]+$";
        if (!StringUtil.isEmpty(bean.getTruename()) && !bean.getTruename().matches(reg)) {
            return ResponseEntityUtil.fail("真实姓名只能为汉字！");
        }
        Member member2 = memberMapper.selectById(bean.getId());
        if (!member2.getNickname().equals(bean.getNickname())) {
            if (checkNickname(bean.getNickname())) {
                return ResponseEntityUtil.fail("该昵称已存在！");
            }
        }
        Member member = new Member();
        member.setId(bean.getId());
        member.setAddress(bean.getAddress());
        member.setAlipayAccount(bean.getAlipayAccount());
        member.setAlipayName(bean.getAlipayName());
        member.setIcon(bean.getIcon());
        member.setNickname(bean.getNickname());
        member.setUpdateBy(bean.getUpdateBy());
        member.setSex(bean.getSex());
        member.setLabel(bean.getLabel());
        member.setUpdateDate(new Date());
        member.setTruename(bean.getTruename());
        if (bean.getPassword() != null) {
            member.setPassword(MD5Util.MD5(bean.getPassword()));
        }

        int keyCount = memberMapper.updateByPrimaryKeySelective(member);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("更新成功！");
        }
        return ResponseEntityUtil.fail("更新失败！");
    }

    @Override
    public ResponseEntity<String> add(MemberAddRequestBean bean) {
        String reg = "^[\\u4E00-\\u9FFF]+$";
        if (bean == null || bean.getPhone() == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        String reg2 = "^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$";
        if (!bean.getPassword().matches(reg2)) {
            return ResponseEntityUtil.fail("密码必须包含字母与数字！");
        }
        if (!(bean.getPassword().length() > 6 && bean.getPassword().length() <= 12)) {
            return ResponseEntityUtil.fail("密码长度要在6-12个（不包括6）！");
        }
        if (checkPhone(bean.getPhone())) {
            return ResponseEntityUtil.fail("该手机号已被注册！");
        }
        if (checkNickname(bean.getNickname())) {
            return ResponseEntityUtil.fail("该昵称已存在！");
        }
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        if (!bean.getPhone().matches(PHONE_NUMBER_REG)) {
            return ResponseEntityUtil.fail("请输入正确的手机号格式！");
        }
        if (!bean.getAlipayAccount().matches(PHONE_NUMBER_REG)) {
            return ResponseEntityUtil.fail("请输入正确的支付宝账号格式！");
        }
        if (!bean.getTruename().matches(reg)) {
            return ResponseEntityUtil.fail("真实姓名只能为汉字！");
        }
        Member member = new Member();
        member.setId(StringUtil.uuidNotLine());
        member.setPassword(MD5Util.MD5(bean.getPassword()));
        member.setAddress(bean.getAddress());
        member.setAlipayAccount(bean.getAlipayAccount());
        member.setAlipayName(bean.getAlipayName());
        member.setIcon(bean.getIcon());
        member.setNickname(bean.getNickname());
        member.setCreateBy(bean.getTruename());
        member.setDelFlag(0);
        member.setAvailable_balance(0.0);
        member.setFrozen_balance(0.0);
        member.setCreateDate(new Date());
        member.setPhone(bean.getPhone());
        member.setSex(bean.getSex());
        member.setTruename(bean.getTruename());
        member.setLabel(bean.getLabel());
        int keyCount = memberMapper.insertSelective(member);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("添加成功！");
        }
        return ResponseEntityUtil.fail("添加失败！");
    }

    @Override
    public ResponseEntity<String> changePwd(MenberChangePwdRequestBean bean) {
        if (bean == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Member member = memberMapper.selectById(bean.getId());
        if (member == null) {
            return ResponseEntityUtil.fail("该会员不存在或者已被删除！");
        }
        if (!member.getPassword().equals(MD5Util.MD5(bean.getOldPassword()))) {
            return ResponseEntityUtil.fail("旧密码错误！");
        }
        String reg2 = "^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$";
        if (!bean.getNewPassword().matches(reg2)) {
            return ResponseEntityUtil.fail("密码必须包含字母与数字！");
        }
        if (bean.getNewPassword().length() > 6 && bean.getNewPassword().length() <= 12) {
            return ResponseEntityUtil.fail("密码长度要在6-12个（不包括6）！");
        }
        member.setPassword(MD5Util.MD5(bean.getNewPassword()));
        int keyCount = memberMapper.updateByPrimaryKeySelective(member);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("更新密码成功！");
        }
        return ResponseEntityUtil.fail("更新密码失败！");
    }

    @Override
    public ResponseEntity<String> deleteSomeMember(String ids, String updateBy) {
        String params[] = ids.split(",");//参数jie()
        List<String> list = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            list.add(params[i]);
        }
        int keyCount = memberMapper.deleteSome(list, updateBy, new Date());
        if (keyCount > 0) {
            return ResponseEntityUtil.success("批量删除成功！");
        }
        return ResponseEntityUtil.fail("批量删除失败！");
    }

    @Override
    public ResponseEntity<Member> login(MemberLoginRequestBean bean) {
        if (bean == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Member member = memberMapper.login(bean.getPhone(), MD5Util.MD5(bean.getPassword()));
        if (member == null) {
            return ResponseEntityUtil.fail(Errors.USER_LOGIN_ERROR.label);
        }
        return ResponseEntityUtil.success(member);

    }

    @Override
    public PageResponseBean<Member> pageMembers(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Member> members = (List<Member>) memberMapper.selectAll();
        PageInfo<Member> pageInfo = new PageInfo<>(members);
//        pageInfo.setList(members);
//        int nums = memberMapper.selectAll().size();
//        int totalPage = nums % pageSize == 0 ? nums / pageSize : nums / pageSize + 1;
//        pageInfo.setPageNum(pageNum);
//        pageInfo.setPageSize(pageSize);
//        pageInfo.setTotal(nums);
//        pageInfo.setPages(totalPage);

        PageResponseBean<Member> page = new PageResponseBean<Member>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(200);
        return page;
    }

    @Override
    public PageResponseBean<Member> pageMembersLike(Integer pageNum, Integer pageSize, String phone, String nickname, String label) {
        PageHelper.startPage(pageNum, pageSize);
        List<Member> members = memberMapper.likeSearch(phone, nickname, label);
        PageInfo<Member> pageInfo = new PageInfo<>(members);
        PageResponseBean<Member> page = new PageResponseBean<Member>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(200);
        return page;
    }

    @Override
    public ResponseEntity<Integer> getAllCounts() {
        int counts = memberMapper.getAllCounts();
        return ResponseEntityUtil.success(counts);
    }

    @Override
    public ResponseEntity<String> stop(String id, String updateBy) {
        if (id == null || updateBy == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Member member = memberMapper.selectById(id);
        if (member == null) {
            return ResponseEntityUtil.fail("该会员不存在或者已被删除！");
        }
        member.setDelFlag(MemberStatusEnum.STOP.getStatus());
        member.setUpdateBy(updateBy);
        member.setUpdateDate(new Date());
        int keyCount = memberMapper.updateByPrimaryKeySelective(member);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("禁用成功！");
        }
        return ResponseEntityUtil.fail("禁用失败！");
    }

    @Override
    public ResponseEntity<String> liftStop(String id, String updateBy) {
        if (id == null || updateBy == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
        }
        Member member = new Member();
        member.setId(id);
        member.setDelFlag(MemberStatusEnum.NORMAL.getStatus());
        member.setUpdateBy(updateBy);
        member.setUpdateDate(new Date());
        int keyCount = memberMapper.updateByPrimaryKeySelective(member);
        if (keyCount > 0) {
            return ResponseEntityUtil.success("解禁成功！");
        }
        return ResponseEntityUtil.fail("解禁失败！");
    }

    private boolean checkPhone(String phone) {
        Member member = memberMapper.checkPhone(phone);
        if (member == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkNickname(String nickname) {
        Member member = memberMapper.checkNickname(nickname);
        if (member == null) {
            return false;
        } else {
            return true;
        }
    }

//	private MemberVo assembleMemberVo(Member member) {
//		
//	}

}
