package com.scriptures.shareApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.scriptures.shareApp.controller.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.beust.jcommander.Parameter;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.annotation.ACS;
import com.scriptures.shareApp.contants.Const;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.service.MemberService;
import com.scriptures.shareApp.util.ResponseEntity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "会员人员操作接口", produces = "application/json")
@RestController
@RequestMapping("/Member/")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "查找一个会员人员操作接口", notes = "查找一个会员")
    @GetMapping(value = "getOne.do")
    public ResponseEntity<Member> getOne(@RequestParam String id) {
        return memberService.getOne(id);
    }

//    @ApiOperation(value = "查找所有会员人员操作接口", notes = "查找所有会员")
//    @GetMapping(value = "getAll.do")
//    public ResponseEntity<List<Member>> getAll() {
//        return memberService.getAll();
//    }

    @ApiOperation(value = "添加一个会员人员操作接口", notes = "添加一个会员")
    @PutMapping(value = "add.do")
    public ResponseEntity<String> add(@RequestBody MemberAddRequestBean bean) {
        return memberService.add(bean);
    }

    @ApiOperation(value = "更新一个会员人员操作接口", notes = "更新一个会员")
    @PostMapping(value = "update.do")
    public ResponseEntity<String> getOne(@RequestBody MemberUpdateRequestBean bean) {
        return memberService.update(bean);
    }

    @ApiOperation(value = "修改一个会员人员的密码操作接口", notes = "修改一个会员的密码")
    @PostMapping(value = "changePwd.do")
    public ResponseEntity<String> changePwd(@RequestBody MenberChangePwdRequestBean bean) {
        return memberService.changePwd(bean);
    }

    @ApiOperation(value = "批量删除会员人员操作接口", notes = "批量删除会员")
    @DeleteMapping(value = "deleteSome.do")
    public ResponseEntity<String> deleteSome(@RequestParam String ids, @RequestParam String updateBy) {
        return memberService.deleteSomeMember(ids, updateBy);
    }

    @ApiOperation(value = "删除一个会员人员操作接口", notes = "删除一个会员")
    @DeleteMapping(value = "delete.do")
    public ResponseEntity<String> delete(@RequestParam String id, @RequestParam String updateBy) {
        return memberService.delete(id, updateBy);
    }

    @ACS(allowAnonymous = true)
    @ApiOperation(value = "会员人员登录操作接口", notes = "会员登录")
    @PostMapping(value = "login.do")
    public ResponseEntity<Member> login(@RequestBody MemberLoginRequestBean bean, HttpServletRequest request) {
        ResponseEntity<Member> response = memberService.login(bean);
        if (response.isSuccess()) {
            Member member = response.getData();
            //session.setAttribute(Const.CURRENT_USER, response.getData());
            // 创建访问token
            String accessToken = super.generateAccessToken(request);
            member.setAccessToken(accessToken);

            super.setAccessTokenAttribute(request, accessToken);
            super.sessionMember(request, member);

            return ResponseEntityUtil.success(member);
        }
        return response;
    }

    @ApiOperation(value = "会员人员模糊查询分页显示操作接口", notes = "会员模糊查询分页")
    @GetMapping(value = "pageLike.do")
    public PageResponseBean<Member> pageLike(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam String phone, @RequestParam String nickname, @RequestParam String label) {
        return memberService.pageMembersLike(pageNum, pageSize, phone, nickname, label);
    }

    @ApiOperation(value = "会员人员分页操作接口", notes = "会员分页")
    @GetMapping(value = "page.do")
    public PageResponseBean<Member> page(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return memberService.pageMembers(pageNum, pageSize);
    }

    @ApiOperation(value = "会员总条数", notes = "会员总条数")
    @GetMapping("getAllCounts")
    public ResponseEntity<Integer> getAllCounts() {
        return memberService.getAllCounts();
    }

    @ApiOperation(value = "禁用会员", notes = "禁用一个会员，禁用状态无法登陆")
    @PostMapping(value = "stop.do")
    public ResponseEntity<String> stop(@RequestBody MemberStatusUptRequestBean bean) {
        return memberService.stop(bean.getId(), bean.getUpdateBy());
    }

    @ApiOperation(value = "解禁会员", notes = "解除禁用中的一个会员，禁用状态无法登陆")
    @PostMapping(value = "liftStop.do")
    public ResponseEntity<String> liftStop(@RequestBody MemberStatusUptRequestBean bean) {
        return memberService.liftStop(bean.getId(), bean.getUpdateBy());
    }
}
