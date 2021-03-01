package com.scriptures.shareApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.scriptures.shareApp.annotation.ACS;
import com.scriptures.shareApp.controller.request.AdminAddRequestBean;
import com.scriptures.shareApp.controller.request.AdminDelRequestBean;
import com.scriptures.shareApp.controller.request.AdminGetFuzzyRequestBean;
import com.scriptures.shareApp.controller.request.AdminLoginRequestBean;
import com.scriptures.shareApp.controller.request.AdminUptRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Admin;
import com.scriptures.shareApp.service.AdminService;
import com.scriptures.shareApp.service.impl.AdminServiceImpl;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.vo.AdminVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(description = "管理员操作接口", produces = "application/json")
@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {

    @Autowired
    private AdminService adminService;

    @ACS(allowAnonymous = true)
    @ApiOperation(value = "管理员登录接口", notes = "管理员根据手机号与密码登录。")
    @PostMapping("adminlogin.do")
    public ResponseEntity<Admin> adminLogin(@RequestBody AdminLoginRequestBean bean, HttpServletRequest request) {
        ResponseEntity<Admin> response = adminService.adminLogin(bean);
        if (response.isSuccess()) {
            Admin admin = response.getData();
            //session.setAttribute(Const.CURRENT_USER, response.getData());
            // 创建访问token
            String accessToken = super.generateAccessToken(request);
            admin.setAccessToken(accessToken);

            super.setAccessTokenAttribute(request, accessToken);
            super.sessionAdmin(request, admin);

            return ResponseEntityUtil.success(admin);
        }
        return response;
    }

    @ApiOperation(value = "管理员添加接口", notes = "添加管理员，输入手机号，真实姓名，设定密码，设定职位。")
    @PutMapping("adminAdd.do")
    public ResponseEntity<Object> adminAdd(@RequestBody AdminAddRequestBean bean) {
        return adminService.addAdmin(bean);
    }

    @ApiOperation(value = "查看管理员列表", notes = "管理员列表查询.分页")
    @GetMapping("getAll.do")
    public PageResponseBean getAllList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return adminService.getAllList(pageNum, pageSize);
    }

    //多条件模糊查询
    public ResponseEntity<PageResponseBean> getListFuzzy_S(@RequestBody AdminGetFuzzyRequestBean bean) {
        return ResponseEntityUtil.success(adminService.getFuzzy_S(bean));
    }

    @ApiOperation(value = "管理员列表模糊查询", notes = "根据value查询所有与之匹配的字段")
    @GetMapping("getFuzzy.do")
    public PageResponseBean getListFuzzy(@RequestParam String key, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return adminService.getFuzzy(key, pageNum, pageSize);
    }

    @ApiOperation(value = "查看管理员详细信息", notes = "根据id查看管理员详细信息")
    @GetMapping("getInfo.do")
    public ResponseEntity<Object> getInfo(@RequestParam String id) {
        return adminService.getAdminInfo(id);
    }

    @ApiOperation(value = "管理员修改接口", notes = "修改管理员")
    @PostMapping("update.do")
    public ResponseEntity<Object> updatePwdOrStatus(AdminUptRequestBean bean) {
        return adminService.update(bean);
    }

    @ApiOperation(value = "管理员状态修改接口", notes = "修改管理员状态")
    @PostMapping("updateStatus.do")
    public ResponseEntity<Object> updateStatus(@RequestParam String id, @RequestParam int status) {
        return adminService.updateStatus(id, status);
    }

    @ApiOperation(value = "管理员密码修改", notes = "通过旧密码修改密码")
    @PostMapping("updatePwd.do")
    public ResponseEntity<Object> updatePwd(@RequestParam String id, @RequestParam String oldPwd, @RequestParam String newPwd) {
        return adminService.updatePwd(id, oldPwd, newPwd);
    }

    @ApiOperation(value = "管理员删除接口", notes = "删除管理员，超级管理员使用")
    @DeleteMapping("delete.do")
    public ResponseEntity<Object> adminDelete(@RequestParam String id, @RequestParam String handler) {
        return adminService.adminDelete(id, handler);
    }

    @ApiOperation(value = "管理员批量删除接口", notes = "批量删除管理员，超级管理员使用")
    @DeleteMapping("deleteSome.do")
    public ResponseEntity<Object> adminDeleteSome(@RequestParam String ids, @RequestParam String handler) {
        return adminService.adminDelSome(ids, handler);
    }
}
