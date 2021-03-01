package com.scriptures.shareApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.controller.request.AdminAddRequestBean;
import com.scriptures.shareApp.controller.request.AdminGetFuzzyRequestBean;
import com.scriptures.shareApp.controller.request.AdminLoginRequestBean;
import com.scriptures.shareApp.controller.request.AdminUptRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Admin;
import com.scriptures.shareApp.dao.mapper.AdminMapper;
import com.scriptures.shareApp.service.AdminService;
import com.scriptures.shareApp.util.DateUtil;
import com.scriptures.shareApp.util.MD5Util;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;
import com.scriptures.shareApp.vo.AdminVo;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public ResponseEntity<Admin> adminLogin(AdminLoginRequestBean bean) {
        String NAME_NUMBER_REG = "^[\\\\u4E00-\\\\u9FFF]+$";
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        //String PWD_NUMBER_REG = "";
        if (!bean.getPhone().matches(PHONE_NUMBER_REG)) {
            return ResponseEntityUtil.fail("请输入正确的手机号格式！");
        }
        int count = adminMapper.checkPhone(bean.getPhone());
        if (count == 0) {
            return ResponseEntityUtil.fail("用户不存在，请注册");
        }
        String md5Password = MD5Util.MD5(bean.getPassword());
        Admin admin = adminMapper.selectByLogin(bean.getPhone(), md5Password);
        if (admin == null) {
            return ResponseEntityUtil.fail("用户名或密码不正确");
        }
        if (admin.getStatus() == 1) {
            return ResponseEntityUtil.fail("你的账号已被禁用");
        }
        return ResponseEntityUtil.success(admin);
    }

    @Override
    public ResponseEntity<Object> addAdmin(AdminAddRequestBean bean) {

        if (StringUtil.isEmpty(bean.getTruename())) {
            return ResponseEntityUtil.fail("真实姓名不能为空");
        }
        String reg = "^[\\u4E00-\\u9FFF]+$";
        if (!bean.getTruename().matches(reg)) {
            return ResponseEntityUtil.fail("真实姓名只能为汉字！");
        }
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        if (!bean.getPhone().matches(PHONE_NUMBER_REG)) {
            return ResponseEntityUtil.fail("请输入正确的手机号格式！");
        }
        String reg2 = "^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$";
        if (!bean.getPassword().matches(reg2)) {
            return ResponseEntityUtil.fail("密码必须包含字母与数字！");
        }
        if (!(bean.getPassword().length() > 6 && bean.getPassword().length() <= 12)) {
            return ResponseEntityUtil.fail("密码长度要在6-12个（不包括6）！");
        }
        int count = adminMapper.checkPhone(bean.getPhone());
        if (count > 0) {
            return ResponseEntityUtil.fail("该手机号已经注册");
        }
        Admin admin = new Admin();
        admin.setId(StringUtil.uuidNotLine());
        admin.setPhone(bean.getPhone());
        String md5Password = MD5Util.MD5(bean.getPassword());
        System.out.println(md5Password);
        admin.setPassword(md5Password);
        admin.setTruename(bean.getTruename());
        admin.setStatus(0);
        admin.setCreateBy(bean.getCreate_by());
        admin.setCreateDate(DateUtil.getCurrentTime());
        admin.setDelFlag(0);

        int resultCount = adminMapper.insertSelective(admin);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("管理员添加失败");
        }
        return ResponseEntityUtil.success("管理员添加成功");
    }

    //超级管理员修改管理员信息
    @Override
    public ResponseEntity<Object> update(AdminUptRequestBean bean) {
        if (StringUtil.isEmpty(bean.getTruename())) {
            return ResponseEntityUtil.fail("真实姓名不能为空");
        }
        String reg = "^[\\u4E00-\\u9FFF]+$";
        if (!bean.getTruename().matches(reg)) {
            return ResponseEntityUtil.fail("真实姓名只能为汉字！");
        }
        String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
        if (!bean.getPhone().matches(PHONE_NUMBER_REG)) {
            return ResponseEntityUtil.fail("请输入正确的手机号格式！");
        }
        if (!StringUtil.isEmpty(bean.getPassword())) {
            String reg2 = "^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$";
            if (!bean.getPassword().matches(reg2)) {
                return ResponseEntityUtil.fail("密码必须包含字母与数字！");
            }
            if (!(bean.getPassword().length() > 6 && bean.getPassword().length() <= 12)) {
                return ResponseEntityUtil.fail("密码长度要在6-12个（不包括6）！");
            }
        }
        int count = adminMapper.checkUptPhone(bean.getId(), bean.getPhone());
        if (count > 0) {
            return ResponseEntityUtil.fail("该手机号已经注册");
        }
        Admin uptAdmin = new Admin();
        uptAdmin.setId(bean.getId());
        uptAdmin.setPhone(bean.getPhone());
        uptAdmin.setTruename(bean.getTruename());
        uptAdmin.setStatus(bean.getStatus());
        if (!StringUtil.isEmpty(bean.getPassword())) {
            uptAdmin.setPassword(MD5Util.MD5(bean.getPassword()));
        }
        uptAdmin.setUpdateBy(bean.getUpdateBy());
        uptAdmin.setUpdateDate(DateUtil.getCurrentTime());
        int resultCount = adminMapper.updateByPrimaryKeySelective(uptAdmin);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("管理员修改失败");
        }
        return ResponseEntityUtil.success("管理员修改成功");
    }

    //删除管理员
    @Override
    public ResponseEntity<Object> adminDelete(String id, String handler) {
//		Admin admin=adminMapper.selectById(id,MD5Util.MD5(password));
//		if(admin==null) {
//			return ResponseEntityUtil.fail("密码错误");
//		}
        Date updateDate = DateUtil.getCurrentTime();
        int resultCount = adminMapper.deleteById(id, handler, updateDate);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("管理员删除失败");
        }
        return ResponseEntityUtil.success("管理员删除成功");
    }

    //批量删除管理员
    @Override
    public ResponseEntity<Object> adminDelSome(String ids, String handler) {
        if (StringUtil.isEmpty(ids) || StringUtil.isEmpty(handler)) {
            return ResponseEntityUtil.fail("数据不完整，ids与操作人不能为空");
        }
        String[] temp = ids.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            String id = temp[i];
            list.add(id);
        }
        Date update_date = DateUtil.getCurrentTime();
        int resultCount = adminMapper.deleteSome(list, handler, update_date);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("批量删除管理员失败");
        }
        return ResponseEntityUtil.success("批量删除管理员成功");
    }

    //分页查询所有
    @Override
    public PageResponseBean getAllList(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Admin> admins = adminMapper.selectAll();
        List<AdminVo> adminVos = new ArrayList<>();
        if (admins != null && admins.size() > 0) {
            for (Admin admin : admins) {
                adminVos.add(createAdminVo(admin));
            }
        }

        PageInfo pageInfo = new PageInfo<>(admins);
        pageInfo.setList(adminVos);
        PageResponseBean<AdminVo> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    //管理员模糊查询(多条件)
    @Override
    public PageResponseBean getFuzzy_S(AdminGetFuzzyRequestBean bean) {
        List<Admin> admins = new ArrayList<>();
        PageHelper.startPage(bean.getPageNum(), bean.getPageSize());
        admins = adminMapper.selectFuzzy(StringUtil.isEmpty(bean.getPhone()) ? null : bean.getPhone(),
                StringUtil.isEmpty(bean.getTruename()) ? null : bean.getTruename(),
                StringUtil.isBlank(bean.getStatus()) ? null : bean.getStatus());
        List<AdminVo> adminVos = new ArrayList<>();
        if (admins != null && admins.size() > 0) {
            for (Admin admin : admins) {
                adminVos.add(createAdminVo(admin));
            }
        }

        PageInfo pageInfo = new PageInfo<>(admins);
        pageInfo.setList(adminVos);
        PageResponseBean<AdminVo> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    //管理员模糊查询(模糊条件)
    @Override
    public PageResponseBean getFuzzy(String key, int pageNum, int pageSize) {
        List<Admin> admins = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        admins = adminMapper.selectFuzzy_2(key);
        List<AdminVo> adminVos = new ArrayList<>();
        if (admins != null && admins.size() > 0) {
            for (Admin admin : admins) {
                adminVos.add(createAdminVo(admin));
            }
        }

        PageInfo pageInfo = new PageInfo<>(admins);
        pageInfo.setList(adminVos);

        PageResponseBean<AdminVo> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);
        return pageResponseBean;
    }

    //根据id查看管理员详细信息
    @Override
    public ResponseEntity<Object> getAdminInfo(String id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        if (admin == null) {
            return ResponseEntityUtil.fail("找不到该用户");
        }
        AdminVo adminVo = createAdminVo(admin);
        return ResponseEntityUtil.success(adminVo);
    }

    private AdminVo createAdminVo(Admin admin) {
        AdminVo adminVo = new AdminVo();
        if (admin != null) {
            adminVo.setId(admin.getId());
            adminVo.setPhone(admin.getPhone());
            adminVo.setTruename(admin.getTruename());
            adminVo.setStatus(admin.getStatus());
            adminVo.setCreateBy(admin.getCreateBy());
            adminVo.setCreateDate(admin.getCreateDate());
            adminVo.setUpdateBy(admin.getUpdateBy());
            adminVo.setUpdateDate(admin.getUpdateDate());
        }
        return adminVo;
    }

    @Override
    public ResponseEntity<Object> updatePwd(String id, String oldPwd, String newPwd) {
        Admin admin = adminMapper.selectById(id, MD5Util.MD5(oldPwd));
        if (admin == null) {
            return ResponseEntityUtil.fail("原密码错误");
        }
        String reg2 = "^[A-Za-z]+[0-9]+[A-Za-z0-9]*|[0-9]+[A-Za-z]+[A-Za-z0-9]*$";
        if (!newPwd.matches(reg2)) {
            return ResponseEntityUtil.fail("新密码必须包含字母与数字！");
        }
        if (!(newPwd.length() > 6 && newPwd.length() <= 12)) {
            return ResponseEntityUtil.fail("新密码长度要在7-12个！");
        }
        admin.setPassword(MD5Util.MD5(newPwd));
        admin.setUpdateBy("管理员本人");
        admin.setUpdateDate(DateUtil.getCurrentTime());
        int resultCount = adminMapper.updateByPrimaryKeySelective(admin);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("修改密码失败");
        }
        return ResponseEntityUtil.success("修改密码成功");
    }

    @Override
    public ResponseEntity<Object> updateStatus(String id, int status) {
        if (StringUtil.isBlank(id)) {
            return ResponseEntityUtil.fail("id不能为空");
        }
        try {
            int test = status / 1;
        } catch (Exception e) {
            return ResponseEntityUtil.fail("状态格式不正确");
        }
        Admin admin = new Admin();
        admin.setId(id);
        admin.setStatus(status);
        int resultCount = adminMapper.updateByPrimaryKeySelective(admin);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("修改状态失败");
        }
        return ResponseEntityUtil.success("修改状态成功");
    }
}
