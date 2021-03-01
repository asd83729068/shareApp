package com.scriptures.shareApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.scriptures.shareApp.vo.ArticleOrderVo;
import com.scriptures.shareApp.vo.ShareArticleDataVo;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.scriptures.shareApp.controller.request.SharedataAddRequestBean;
import com.scriptures.shareApp.controller.request.SharedataUpdateRequestBean;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Member;
import com.scriptures.shareApp.dao.entity.Sharedata;
import com.scriptures.shareApp.service.ShareDateService;
import com.scriptures.shareApp.util.HttpUtil;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "分享数据操作接口", produces = "application/json")
@RestController
@RequestMapping("/ShareDate/")
public class ShareDateController {

    @Autowired
    private ShareDateService shareDateService;

    @ApiOperation(value = "添加一个分享数据操作接口", notes = "添加一个分享数据")
    @PutMapping(value = "add.do")
    public ResponseEntity<String> add(@RequestBody SharedataAddRequestBean bean, HttpServletRequest request) {
        Member member = (Member) request.getSession().getAttribute(Const.CURRENT_USER);
        if (member == null) {
            return ResponseEntityUtil.fail(Errors.SYSTEM_NOT_LOGIN);
        }
        String ipAddress = new HttpUtil().getClientIp(request);
        return shareDateService.add(bean, ipAddress);
    }

    @ApiOperation(value = "更新一个分享数据操作接口", notes = "更新一个分享数据")
    @PostMapping(value = "update.do")
    public ResponseEntity<String> update(@RequestBody SharedataUpdateRequestBean bean) {
        return shareDateService.update(bean);
    }

//	@ApiOperation(value = "显示所有分享数据操作接口",notes = "显示所有分享数据")
//	@GetMapping(value="getAll.do")
//	public ResponseEntity<List<Sharedata>> getAll() {
//		return shareDateService.getAll();
//	}

    @ApiOperation(value = "分页显示分享数据操作接口", notes = "分页显示分享数据")
    @PostMapping(value = "pageGetAll.do")
    public PageResponseBean<Sharedata> pageGetAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return shareDateService.pageGetAll(pageNum, pageSize);
    }

    @ApiOperation(value = "分享数据二表联动显示操作接口", notes = "分享数据二表联动显示数据")
    @GetMapping(value = "pageGetSharedata.do")
    public PageResponseBean<ShareArticleDataVo> pageGetSharedata(@RequestParam String typeId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return shareDateService.pageGetShareData(typeId, pageNum, pageSize);
    }

    @ApiOperation(value = "分享数据二表联动显示模糊查询", notes = "分享数据二表联动显示模糊查询")
    @GetMapping(value = "pageGetSharedataByKey.do")
    public PageResponseBean<ShareArticleDataVo> pageGetShareDataByKey(@RequestParam String key, @RequestParam String typeId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return shareDateService.pageGetShareDataByKey(key, typeId, pageNum, pageSize);
    }

    @ApiOperation(value = "文章分享次数排名", notes = "文章有效分享次数排名，limit：记录条数")
    @GetMapping(value = "articleOrder.do")
    public ResponseEntity<List<ArticleOrderVo>> getArticleOrder(@RequestParam int limit) {
        return shareDateService.getArticleOrder(limit);
    }
}
