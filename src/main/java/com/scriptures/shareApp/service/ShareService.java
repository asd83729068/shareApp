package com.scriptures.shareApp.service;

import java.util.List;
import com.scriptures.shareApp.controller.request.ShareAddRequestBean;
import com.scriptures.shareApp.controller.request.SharePageSearchRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.entity.Share;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.vo.ArticleOrderVo;

public interface ShareService {
	
public ResponseEntity<Share> getOne(String id);
	
	public ResponseEntity<List<Share>> getAll();
	
	public ResponseEntity<String> delete(String id,String updateBy);
	
	public ResponseEntity<String> click(String id,String ipAddress);
	
	public ResponseEntity<String> add(ShareAddRequestBean bean);
	
	public PageResponseBean<Share> pageLikeSearch(Integer pageNum,Integer pageSize,String key);
	
	public PageResponseBean<Share> pageSearchAll(Integer pageNum,Integer pageSize);

	public PageResponseBean<Share> pageTypeZero(Integer pageNum,Integer pageSize);

	public PageResponseBean<Share> pageTypeOne(Integer pageNum,Integer pageSize);
	
	public ResponseEntity<String> deleteSomeShare(String ids,String updateBy);

}
