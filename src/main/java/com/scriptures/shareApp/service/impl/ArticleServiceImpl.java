package com.scriptures.shareApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.scriptures.shareApp.contants.Errors;
import com.scriptures.shareApp.controller.request.*;
import com.scriptures.shareApp.dao.entity.Share;
import com.scriptures.shareApp.dao.mapper.ShareMapper;
import com.scriptures.shareApp.vo.ArticleShareVo;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scriptures.shareApp.controller.response.PageResponseBean;
import com.scriptures.shareApp.dao.entity.Article;
import com.scriptures.shareApp.dao.mapper.ArticleMapper;
import com.scriptures.shareApp.service.ArticleService;
import com.scriptures.shareApp.util.DateUtil;
import com.scriptures.shareApp.util.ResponseEntity;
import com.scriptures.shareApp.util.ResponseEntityUtil;
import com.scriptures.shareApp.util.StringUtil;
import com.scriptures.shareApp.vo.ArticleVo;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ShareMapper shareMapper;

    //查询所有文章,分页
    @Override
    public PageResponseBean getListAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectAll();
        List<ArticleVo> articleVos = new ArrayList<>();
        if (articles != null && articles.size() > 0) {
            for (Article article : articles) {
                articleVos.add(createArticleVo(article));
            }
        }

        PageInfo pageInfo = new PageInfo<>(articles);
        pageInfo.setList(articleVos);

        return new PageResponseBean<ArticleVo>(pageInfo);
    }

    //文章模糊查询，分页
    @Override
    public PageResponseBean getListFuzzy(ArticleGetFuzzyReuqustBean bean) {
        PageHelper.startPage(bean.getPageNum(), bean.getPageSize());
        List<Article> articles = articleMapper.selectFuzzy(StringUtil.isBlank(bean.getAuthor()) ? null : bean.getAuthor(),
                StringUtil.isBlank(bean.getRemarks()) ? null : bean.getRemarks(),
                StringUtil.isBlank(bean.getArticleTitle()) ? null : bean.getArticleTitle(),
                StringUtil.isBlank(bean.getCreateBy()) ? null : bean.getCreateBy());
        List<ArticleVo> articleVos = new ArrayList<>();
        if (articles != null && articles.size() > 0) {
            for (Article article : articles) {
                articleVos.add(createArticleVo(article));
            }
        }

        PageInfo pageInfo = new PageInfo<>(articles);
        pageInfo.setList(articleVos);

        return new PageResponseBean<ArticleVo>(pageInfo);
    }

    @Override
    public ResponseEntity<Object> ArticleAdd(ArticleAddRequestBean bean) {
        if (StringUtil.isEmpty(bean.getArticleTitle())
                | StringUtil.isEmpty(bean.getArticleContent())
                | StringUtil.isEmpty(bean.getCreateBy())
                | StringUtil.isEmpty(bean.getRemarks())
                | StringUtil.isBlank(bean.getCommission())) {
            return ResponseEntityUtil.fail("参数不完整，文章标题、内容、提成比例、分享状态、创建人都不能为空");
        }
        if (articleMapper.selectByArticleTitle(bean.getArticleTitle()) != null) {
            return ResponseEntityUtil.fail("此文章名已存在");
        }
        Article article = new Article();
        article.setId(StringUtil.uuidNotLine());
        article.setCover(bean.getCover());
        article.setAuthor(bean.getAuthor());
        article.setArticleTitle(bean.getArticleTitle());
        article.setArticleContent(bean.getArticleContent());
        article.setCommission(bean.getCommission());
        article.setRemarks(bean.getRemarks());
        if (!StringUtil.isEmpty(bean.getLabel())) {
            article.setLabel(bean.getLabel());
        }
        article.setCreateBy(bean.getCreateBy());
        article.setCreateDate(DateUtil.getCurrentTime());
        article.setDelFlag(0);


        int resultCount = articleMapper.insertSelective(article);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("文章添加失败");
        }
        return ResponseEntityUtil.success("文章添加成功");
    }

    @Override
    public ResponseEntity<Object> ArticleUpdate(ArticleUptRequestBean bean) {
        if (StringUtil.isEmpty(bean.getId())
                | StringUtil.isEmpty(bean.getArticleTitle())
                | StringUtil.isEmpty(bean.getArticleContent())
                | StringUtil.isEmpty(bean.getUpdateBy())
                | StringUtil.isBlank(bean.getCommission())) {
            return ResponseEntityUtil.fail("参数不完整，文章标题、内容、提成比例、更新人都不能为空");
        }
        if (articleMapper.selectByArticleTitle(bean.getArticleTitle()) != null && !articleMapper.selectByArticleTitle(bean.getArticleTitle()).getId().equals(bean.getId())) {
            return ResponseEntityUtil.fail("此文章名已存在");
        }
        Article article = new Article();

        article.setId(bean.getId());
        article.setCover(bean.getCover());
        article.setAuthor(bean.getAuthor());
        article.setArticleTitle(bean.getArticleTitle());
        article.setArticleContent(bean.getArticleContent());
        article.setCommission(bean.getCommission());
        if (!StringUtil.isEmpty(bean.getRemarks())) {
            article.setRemarks(bean.getRemarks());
        }
        if (!StringUtil.isEmpty(bean.getLabel())) {
            article.setLabel(bean.getLabel());
        }
        article.setUpdateBy(bean.getUpdateBy());
        article.setUpdateDate(DateUtil.getCurrentTime());

        int resultCount = articleMapper.updateByPrimaryKeySelective2(article);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("文章更新失败");
        }
        return ResponseEntityUtil.success("文章更新成功");
    }

    @Override
    public ResponseEntity<Object> ArticleStatusUpt(ArticleStatusUptRequestBean bean) {
        if (StringUtil.isEmpty(bean.getId())
                | StringUtil.isEmpty(bean.getUpdateBy())
                | StringUtil.isEmpty(bean.getRemarks())
                ) {
            return ResponseEntityUtil.fail("参数不完整，文章id、分享状态、更新人都不能为空");
        }
        Article article = new Article();
        article.setId(bean.getId());
        article.setRemarks(bean.getRemarks());
        article.setUpdateBy(bean.getUpdateBy());
        article.setUpdateDate(DateUtil.getCurrentTime());

        int resultCount = articleMapper.updateByPrimaryKeySelective(article);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("分享状态修改失败");
        }
        return ResponseEntityUtil.success("分享状态修改成功");
    }

    @Override
    public ResponseEntity<Object> ArticleDelete(String id, String updateBy) {
        if (StringUtil.isEmpty(updateBy)) {
            return ResponseEntityUtil.fail("操作人不能为空");
        }
        Article article = new Article();
        article.setId(id);
        article.setUpdateBy(updateBy);
        article.setUpdateDate(DateUtil.getCurrentTime());
        article.setDelFlag(1);

        int resultCount = articleMapper.updateByPrimaryKeySelective(article);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("文章删除失败");
        }
        return ResponseEntityUtil.success("文章删除成功");
    }

    @Override
    public ResponseEntity<Object> ArticleDelSome(String ids, String handler) {
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
        int resultCount = articleMapper.deleteSome(list, handler, update_date);
        if (resultCount == 0) {
            return ResponseEntityUtil.fail("批量删除文章失败");
        }
        return ResponseEntityUtil.success("批量删除文章成功");
    }

    @Override
    public ResponseEntity<Article> getArticleInfo(String id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null) {
            return ResponseEntityUtil.fail("没有查询到相关文章");
        }
        return ResponseEntityUtil.success(article);
    }

    @Override
    public ResponseEntity<Article> getArticleByTitle(String articleTitle) {
        Article article = articleMapper.getArticleByTitle(articleTitle);
        if (article == null) {
            return ResponseEntityUtil.fail("没有查询到相关文章");
        }
        return ResponseEntityUtil.success(article);
    }

    private ArticleVo createArticleVo(Article article) {
        ArticleVo articleVo = new ArticleVo();
        if (article != null) {
            articleVo.setId(article.getId());
            articleVo.setCover(article.getCover());
            articleVo.setAuthor(article.getAuthor());
            articleVo.setArticleTitle(article.getArticleTitle());
            articleVo.setArticleContent(article.getArticleContent());
            articleVo.setCommission(article.getCommission());
            articleVo.setRemarks(article.getRemarks());
            articleVo.setCreateBy(article.getCreateBy());
            articleVo.setCreateDate(article.getCreateDate());
            articleVo.setUpdateBy(article.getUpdateBy());
            articleVo.setUpdateDate(article.getUpdateDate());
            articleVo.setDelFlag(article.getDelFlag());
            articleVo.setLabel(article.getLabel());
        }
        return articleVo;
    }

    @Override
    public PageResponseBean getListFuzzyByKey(String key, String startDay, String endDay, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectFuzzyByKey(key, startDay, endDay);
        List<ArticleVo> articleVos = new ArrayList<>();
        if (articles != null && articles.size() > 0) {
            for (Article article : articles) {
                articleVos.add(createArticleVo(article));
            }
        }

        PageInfo pageInfo = new PageInfo<>(articles);
        pageInfo.setList(articleVos);

        PageResponseBean<ArticleVo> pageResponseBean = new PageResponseBean<>(pageInfo);
        pageResponseBean.setCode(0);
        pageResponseBean.setHttpStatus(200);

        return pageResponseBean;
    }

    @Override
    public ResponseEntity<Integer> getAllCounts() {
        int counts = articleMapper.getAllCounts();
        return ResponseEntityUtil.success(counts);
    }

    @Override
    public PageResponseBean getArticleShare(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectAll();
        List<ArticleShareVo> articleShareVos = new ArrayList<>();

        for (Article article : articles) {
            int counts = 0;
            int ValidClicks = 0;
            double TotalMoney = 0;
            int TotalClicks = 0;
            String id = article.getId();
            List<Share> shares = shareMapper.selectShareById(id);
            TotalClicks = shareMapper.selectShareTotalClicksByArticle(id);
            for (Share share : shares) {
                counts += 1;
                ValidClicks += share.getClicks();
                TotalMoney += share.getReward();
            }
            TotalMoney = (double) Math.round(TotalMoney * 100) / 100;
            ArticleShareVo articleShareVo = new ArticleShareVo();
            articleShareVo.setId(article.getId());
            articleShareVo.setArticleTitle(article.getArticleTitle());
            articleShareVo.setShareTotalCounts(counts);
            articleShareVo.setShareValidClick(ValidClicks);
            articleShareVo.setShareTotalClick(TotalClicks);
            articleShareVo.setShareTotalMoney(TotalMoney);
            articleShareVos.add(articleShareVo);
        }
        PageInfo<ArticleShareVo> pageInfo = new PageInfo(articles);
        pageInfo.setList(articleShareVos);

        PageResponseBean<ArticleShareVo> page = new PageResponseBean<ArticleShareVo>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(200);
        return page;
    }


    @Override
    public PageResponseBean getArticleByTitle(String title, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectByTitle(title);
        List<ArticleShareVo> articleShareVos = new ArrayList<>();

        for (Article article : articles) {
            int counts = 0;
            int ValidClicks = 0;
            double TotalMoney = 0;
            String id = article.getId();
            List<Share> shares = shareMapper.selectShareById(id);
            int TotalClicks = shareMapper.selectShareTotalClicksByArticle(id);
            for (Share share : shares) {
                counts += 1;
                ValidClicks += share.getClicks();
                TotalMoney += share.getReward();
            }
            TotalMoney = (double) Math.round(TotalMoney * 100) / 100;
            ArticleShareVo articleShareVo = new ArticleShareVo();
            articleShareVo.setId(article.getId());
            articleShareVo.setArticleTitle(article.getArticleTitle());
            articleShareVo.setShareTotalCounts(counts);
            articleShareVo.setShareValidClick(ValidClicks);
            articleShareVo.setShareTotalClick(TotalClicks);
            articleShareVo.setShareTotalMoney(TotalMoney);
            articleShareVos.add(articleShareVo);
        }
        PageInfo<ArticleShareVo> pageInfo = new PageInfo(articles);
        pageInfo.setList(articleShareVos);

        PageResponseBean<ArticleShareVo> page = new PageResponseBean<ArticleShareVo>(pageInfo);
        page.setCode(0);
        page.setHttpStatus(200);
        return page;
    }


}
