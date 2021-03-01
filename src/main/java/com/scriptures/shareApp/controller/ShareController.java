package com.scriptures.shareApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptures.shareApp.contants.Const;
import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.controller.request.ShareAddRequestBean;
import com.scriptures.shareApp.controller.request.SharePageSearchRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.entity.Share;
import com.scriptures.shareApp.service.ShareService;
import com.scriptures.shareApp.util.HttpUtil;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="分享操作接口",produces = "application/json")
@RestController
@RequestMapping("/Share/")
public class ShareController {

	@Autowired
	private ShareService shareService;
	
	@ApiOperation(value = "添加一个分享操作接口",notes = "添加一个分享")
	@PutMapping(value="add.do")
	public ResponseEntity<String> add(@RequestBody ShareAddRequestBean bean) {
		return shareService.add(bean);
	}
	
	@ApiOperation(value = "查找一个分享操作接口",notes = "查找一个分享")
	@GetMapping(value="getOne.do")
	public ResponseEntity<Share> getOne(@RequestParam String id) {
		return shareService.getOne(id);
	}
	
	@ApiOperation(value = "删除一个分享操作接口",notes = "删除一个分享")
	@DeleteMapping(value="delete.do")
	public ResponseEntity<String> delete(@RequestParam String id,@RequestParam String updateBy) {
		return shareService.delete(id, updateBy);
	}
	
	@ApiOperation(value = "更新分享点击次数操作接口",notes = "更新分享点击次数")
	@PostMapping(value="addClick.do")
	public ResponseEntity<String> addClick(@RequestParam String id,HttpServletRequest request) {
		Member member=(Member) request.getSession().getAttribute(Const.CURRENT_USER);
		if (member==null) {
			return ResponseEntityUtil.fail(Errors.SYSTEM_NOT_LOGIN);
		}
		String ipAddres=new HttpUtil().getClientIp(request);
		return shareService.click(id,ipAddres);
	}
	
//	@ApiOperation(value = "显示所有分享操作接口",notes = "显示所有分享")
//	@GetMapping(value="getAll.do")
//	public ResponseEntity<List<Share>> getAll() {
//		return shareService.getAll();
//	}
	
	@ApiOperation(value = "用分页显示所有分享操作接口",notes = "分页显示分享")
	@GetMapping(value="pageGetAll.do")
	public PageResponseBean<Share> pageGetAll(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
		return shareService.pageSearchAll(pageNum, pageSize);
	}

	@ApiOperation(value = "用分页显示分享(type=0)操作接口",notes = "分页显示分享")
	@GetMapping(value="pageTypeZero.do")
	public PageResponseBean<Share> pageTypeZero(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
		return shareService.pageTypeZero(pageNum, pageSize);
	}

	@ApiOperation(value = "用分页显示分享(type=1)操作接口",notes = "分页显示分享")
	@GetMapping(value="pageTypeOne.do")
	public PageResponseBean<Share> pageTypeOne(@RequestParam Integer pageNum,@RequestParam Integer pageSize) {
		return shareService.pageTypeOne(pageNum, pageSize);
	}


	@ApiOperation(value = "模糊查询分页显示所有分享操作接口",notes = "模糊查询分页显示分享")
	@GetMapping(value="pageLikeSearch.do")
	public PageResponseBean<Share> pageLikeSearch(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String key) {
		return shareService.pageLikeSearch(pageNum,pageSize,key);
	}
	
	@ApiOperation(value = "批量删除分享操作接口",notes = "批量删除分享")
	@DeleteMapping(value="deleteSome.do")
	public ResponseEntity<String> deleteSome(@RequestParam String ids,@RequestParam String updateBy) {
		return shareService.deleteSomeShare(ids, updateBy);
	}
}
