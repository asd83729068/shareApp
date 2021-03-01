package com.scriptures.shareApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.controller.request.MemberAddRequestBean;
import com.scriptures.shareApp.controller.request.MemberLoginRequestBean;
import com.scriptures.shareApp.controller.request.MemberPageRequestBean;
import com.scriptures.shareApp.controller.request.MemberSearchRequestBean;
import com.scriptures.shareApp.controller.request.MemberUpdateRequestBean;
import com.scriptures.shareApp.controller.request.MenberChangePwdRequestBean;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.util.ResponseEntity;


public interface MemberService {

    public ResponseEntity<Member> getOne(String id);

    public ResponseEntity<List<Member>> getAll();

    public ResponseEntity<String> delete(String id, String updateBy);

    public ResponseEntity<String> update(MemberUpdateRequestBean bean);

    public ResponseEntity<String> add(MemberAddRequestBean bean);

    public ResponseEntity<String> changePwd(MenberChangePwdRequestBean bean);        //手机App

    public ResponseEntity<String> deleteSomeMember(String ids, String updateBy);

    public ResponseEntity<Member> login(MemberLoginRequestBean bean);

    public PageResponseBean<Member> pageMembers(Integer pageNum, Integer pageSize);

    public PageResponseBean<Member> pageMembersLike(Integer pageNum, Integer pageSize, String phone, String nickname, String label);

    public ResponseEntity<Integer> getAllCounts();

    ResponseEntity<String> stop(String id, String updateBy);

    ResponseEntity<String> liftStop(String id, String updateBy);
}
