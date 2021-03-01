package com.scriptures.shareApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.controller.request.ShareAddRequestBean;
import com.scriptures.shareApp.controller.request.SharePageSearchRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.entity.Share;
import com.scriptures.shareApp.dao.entity.Sharedata;
import com.scriptures.shareApp.dao.mapper.MemberMapper;
import com.scriptures.shareApp.dao.mapper.ShareMapper;
import com.scriptures.shareApp.dao.mapper.SharedataMapper;
import com.scriptures.shareApp.service.ShareService;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;

import javax.annotation.Resource;

@Service
public class ShareServiceImpl implements ShareService{
	
	@Resource
	private ShareMapper shareMapper;
	@Resource
	private SharedataMapper sharedataMapper;
	@Resource
	private MemberMapper memberMapper;
	
	
	@Override
	public ResponseEntity<Share> getOne(String id) {
		if (id==null) {
			return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
		}
		Share share=shareMapper.selectById(id);
		if (share==null) {
			return ResponseEntityUtil.fail("该分享不存在！");
		}
		return ResponseEntityUtil.success(share);
	}
	

	@Override
	public ResponseEntity<List<Share>> getAll() {
		List<Share> list=shareMapper.selectAll();
		if (list.size()==0) {
			return ResponseEntityUtil.fail("没有任何数据!");
		}
		return ResponseEntityUtil.success(list);
	}

	
	@Override
	public ResponseEntity<String> delete(String id, String updateBy) {
		if (id==null || updateBy==null) {
			return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
		}
		Share share=shareMapper.selectById(id);
		if (share==null) {
			return ResponseEntityUtil.fail("该分享不存在或者已被删除！");
		}
		share.setDelFlag(1);
		share.setUpdateBy(updateBy);
		share.setUpdateDate(new Date());
		int keyCount=shareMapper.updateByPrimaryKeySelective(share);
		if (keyCount>0) {
			return ResponseEntityUtil.success("删除成功！");
		}
		return ResponseEntityUtil.fail("删除失败！");
	}

	
	@Override
	public ResponseEntity<String> click(String id,String ipAddress) {
		if (id==null) {
			return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
		}
		if (getByIpAddress(ipAddress)) {
			return ResponseEntityUtil.fail("该用户ip已存在！");
		}
		Share share=shareMapper.selectById(id);
		if (share==null) {
			return ResponseEntityUtil.fail("该分享不存在！");
		}
		Member member=memberMapper.selectById(share.getMemberId());
		if (member==null) {
			return ResponseEntityUtil.fail("该文章分享用户不存在或已被删除！");
		}
		share.setClicks(share.getClicks()+1);
		share.setUpdateDate(new Date());
		member.setAvailable_balance(member.getAvailable_balance()+share.getReward());
		int keyCount=shareMapper.updateByPrimaryKeySelective(share);
		memberMapper.updateByPrimaryKeySelective(member);
		if (keyCount>0) {
			return ResponseEntityUtil.success("点击数加 1！");
		}
		return ResponseEntityUtil.fail("点击数添加错误！");
	}

	
	@Override
	public ResponseEntity<String> add(ShareAddRequestBean bean) {
		if (bean==null) {
			return ResponseEntityUtil.fail(Errors.SYSTEM_REQUEST_PARAM_ERROR);
		}
		
		Share share=new Share();
		share.setClicks(0);
		share.setCreateBy(bean.getCreateBy());
		share.setCreateDate(new Date());
		share.setDelFlag(0);
		share.setId(StringUtil.uuidNotLine());
		share.setMemberId(bean.getMemberId());
		share.setReward(bean.getReward());
		share.setTypeId(bean.getTypeId());
		share.setType(bean.getType());
		share.setShareWay(bean.getShareWay());
		share.setRemarks(bean.getRemarks());
		int keyCount=shareMapper.insertSelective(share);
		if (keyCount>0) {
			return ResponseEntityUtil.success("分享信息添加成功！");
		}
		return ResponseEntityUtil.fail("分享信息添加失败！");
	}

	
	@Override
	public PageResponseBean<Share> pageLikeSearch(Integer pageNum,Integer pageSize,String key) {
		PageHelper.startPage(pageNum, pageSize);
		List<Share> shares=shareMapper.likeSearch(key);
		PageInfo<Share> pageInfo=new PageInfo<>(shares);

		PageResponseBean<Share> page=new PageResponseBean<Share>(pageInfo);
		page.setCode(0);
		page.setHttpStatus(200);
		return page;
	}
	

	@Override
	public PageResponseBean<Share> pageSearchAll(Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Share> shares=(List<Share>)shareMapper.selectAll();
		PageInfo<Share> pageInfo=new PageInfo<>(shares);

		PageResponseBean<Share> page=new PageResponseBean<Share>(pageInfo);
		page.setCode(0);
		page.setHttpStatus(200);
		return page;
	}

	@Override
	public PageResponseBean<Share> pageTypeZero(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Share> shares=(List<Share>)shareMapper.selectTypeZero();
		PageInfo<Share> pageInfo=new PageInfo<>(shares);
		PageResponseBean<Share> page=new PageResponseBean<Share>(pageInfo);
		page.setCode(0);
		page.setHttpStatus(200);
		return page;
	}

	@Override
	public PageResponseBean<Share> pageTypeOne(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Share> shares=(List<Share>)shareMapper.selectTypeOne();
		PageInfo<Share> pageInfo=new PageInfo<>();
		pageInfo.setList(shares);
		int nums=shareMapper.selectTypeOne().size();
		int totalPage=nums%pageSize==0?nums/pageSize:nums/pageSize+1;
		pageInfo.setPageNum(pageNum);
		pageInfo.setPageSize(pageSize);
		pageInfo.setTotal(nums);
		pageInfo.setPages(totalPage);
		PageResponseBean<Share> page=new PageResponseBean<Share>(pageInfo);
		page.setCode(0);
		page.setHttpStatus(200);
		return page;
	}

	private boolean getByIpAddress(String ipAddress) {
		Sharedata sharedata=sharedataMapper.selectByIpAddress(ipAddress);
		if (sharedata==null) {
			return false;
		}
		return true;
	}
	
	
	@Override
	public ResponseEntity<String> deleteSomeShare(String ids,String updateBy) {
		String params[] = ids.split(",");//参数jie()
		List<String> list=new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
         list.add(params[i]);
        }
        int keyCount=shareMapper.deleteSome(list,updateBy,new Date());
        if (keyCount>0) {
			return ResponseEntityUtil.success("批量删除成功！");
		}
		return ResponseEntityUtil.fail("批量删除失败！");
	}
}
