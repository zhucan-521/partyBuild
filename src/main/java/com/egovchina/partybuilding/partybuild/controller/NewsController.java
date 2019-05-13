package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.NewsDTO;
import com.egovchina.partybuilding.partybuild.entity.NewsQueryBean;
import com.egovchina.partybuilding.partybuild.service.NewsService;
import com.egovchina.partybuilding.partybuild.vo.NewsVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 新闻资讯管理模块-v1
 * Created by FanYanGen on 2019-05-11 16:35
 */
@Api(tags = "党组织-新闻资讯模块-v1-范焱根")
@RequestMapping("/v1/news")
@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "添加新闻资讯", notes = "添加新闻资讯", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertNews(@RequestBody @Validated @ApiParam("新闻资讯信息") NewsDTO newsDTO) {
        return ReturnUtil.buildReturn(newsService.insertNews(newsDTO));
    }

    @ApiOperation(value = "更新新闻资讯", notes = "更新新闻资讯", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateNews(@RequestBody @Validated @ApiParam("新闻资讯信息") NewsDTO newsDTO) {
        return ReturnUtil.buildReturn(newsService.updateNews(newsDTO));
    }

    @ApiOperation(value = "更新新闻资讯浏览数", notes = "更新新闻资讯浏览数", httpMethod = "PUT")
    @ApiImplicitParam(name = "newsId", value = "新闻资讯ID", paramType = "path", required = true)
    @PutMapping("/browse/{newsId}")
    public ReturnEntity updateNewsViews(@PathVariable Long newsId) {
        return ReturnUtil.buildReturn(newsService.updateNewsViews(newsId));
    }

    @ApiOperation(value = "删除新闻资讯", notes = "删除新闻资讯", httpMethod = "DELETE")
    @ApiImplicitParam(name = "newsId", value = "新闻资讯ID", paramType = "path", required = true)
    @DeleteMapping("/{newsId}")
    public ReturnEntity deleteNews(@PathVariable Long newsId) {
        return ReturnUtil.buildReturn(newsService.deleteNews(newsId));
    }

    @ApiOperation(value = "获取新闻资讯详情", notes = "获取新闻资讯详情", httpMethod = "GET")
    @ApiImplicitParam(name = "newsId", value = "新闻资讯ID", paramType = "path", required = true)
    @GetMapping("/{newsId}")
    public NewsVO getNewsDetails(@PathVariable Long newsId) {
        return newsService.getNewsVODetails(newsId);
    }

    @ApiOperation(value = "获取新闻资讯列表", notes = "获取新闻资讯列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<NewsVO> getNewsList(@Validated NewsQueryBean newsQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(newsService.getNewsVOList(newsQueryBean));
    }

}
