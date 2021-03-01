package com.scriptures.shareApp.controller;

import com.scriptures.shareApp.controller.request.*;
import com.scriptures.shareApp.vo.ArticleShareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Article;
import com.scriptures.shareApp.dao.mapper.ArticleMapper;
import com.scriptures.shareApp.service.ArticleService;
import com.scriptures.shareApp.service.impl.ArticleServiceImpl;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "文章操作接口", produces = "application/json")
@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "查询所有文章", notes = "分页")
    @GetMapping("getAll")
    public PageResponseBean getListAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return articleService.getListAll(pageNum, pageSize);
    }

    //多条件模糊查询
//	public ResponseEntity<PageResponseBean> getListFuzzy(@RequestBody ArticleGetFuzzyReuqustBean bean) {
//		return ResponseEntityUtil.success(articleService.getListFuzzy(bean));
//	} 

    @ApiOperation(value = "文章列表模糊查询", notes = "分页,模糊查询,根据作者，文章标题，创建人")
    @GetMapping("getFuzzy")
    public PageResponseBean getListFuzzy(@RequestParam String key, @RequestParam String startDay, @RequestParam String endDay, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return articleService.getListFuzzyByKey(key, startDay, endDay, pageNum, pageSize);
    }

    @ApiOperation(value = "根据id查看文章详细信息", notes = "根据id查看文章详细信息")
    @GetMapping("getInfo")
    public ResponseEntity<Article> getArticleInfo(@RequestParam String id) {
        return articleService.getArticleInfo(id);
    }

    @ApiOperation(value = "文章添加", notes = "添加文章")
    @PostMapping("articleAdd")
    public ResponseEntity<Object> articleAdd(@RequestBody ArticleAddRequestBean bean) {
        return articleService.ArticleAdd(bean);
    }

    @ApiOperation(value = "文章修改", notes = "修改编辑文章")
    @PostMapping("articleUpt")
    public ResponseEntity<Object> articleUpdate(@RequestBody ArticleUptRequestBean bean) {
        return articleService.ArticleUpdate(bean);
    }

    @ApiOperation(value = "文章分享状态修改", notes = "文章分享状态修改（分享，不分享）")
    @PostMapping("articleStatusUpt")
    public ResponseEntity<Object> articleStatusUpt(@RequestBody ArticleStatusUptRequestBean bean) {
        return articleService.ArticleStatusUpt(bean);
    }

    @ApiOperation(value = "文章删除", notes = "根据id删除文章")
    @DeleteMapping("articleDel")
    public ResponseEntity<Object> articleDel(@RequestParam String id, @RequestParam String handler) {
        return articleService.ArticleDelete(id, handler);
    }

    @ApiOperation(value = "文章批量删除", notes = "根据ids批量删除文章")
    @DeleteMapping("articleDelSome")
    public ResponseEntity<Object> articleDelSome(@RequestParam String ids, @RequestParam String handler) {
        return articleService.ArticleDelSome(ids, handler);
    }

    @ApiOperation(value = "文章总条数", notes = "文章总条数")
    @GetMapping("getAllCounts")
    public ResponseEntity<Integer> getAllCounts() {
        return articleService.getAllCounts();
    }

    @ApiOperation(value = "文章分享信息", notes = "文章分享信息")
    @GetMapping("getArticleShare")
    public PageResponseBean getArticleShare(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return articleService.getArticleShare(pageNum, pageSize);
    }

    @ApiOperation(value = "文章分享通过标题进行模糊查询", notes = "文章分享通过标题模糊查询")
    @GetMapping("getArticleShareLikeTitle")
    public PageResponseBean getArticleShareLikeTitle(@RequestParam(required = false) String title, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return articleService.getArticleByTitle(title, pageNum, pageSize);
    }

    @ApiOperation(value = "根据标题查看文章详细信息", notes = "根据id查看文章详细信息")
    @GetMapping("getArticleByTitle")
    public ResponseEntity<Article> getArticleByTitle(@RequestParam String articleTitle) {
        return articleService.getArticleByTitle(articleTitle);
    }

}
