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
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 党建资讯管理模块-v1
 * Created by FanYanGen on 2019-05-11 16:35
 */
@Api(tags = "党组织-党建资讯管理-v1-范焱根")
@RequestMapping("/v1/news")
@RestController
public class NewsController {

    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "添加党建资讯", notes = "添加党建资讯", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertNews(@RequestBody @Validated @ApiParam("党建资讯信息") NewsDTO newsDTO) {
        return ReturnUtil.buildReturn(newsService.insertNews(newsDTO));
    }

    @ApiOperation(value = "更新党建资讯", notes = "更新党建资讯", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateNews(@RequestBody @Validated @ApiParam("党建资讯信息") NewsDTO newsDTO) {
        return ReturnUtil.buildReturn(newsService.updateNews(newsDTO));
    }

    @ApiOperation(value = "发布党建资讯", notes = "发布党建资讯-(需要指定封面图的 hostId)", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "newsId", value = "党建资讯ID", paramType = "query", required = true),
            @ApiImplicitParam(name = "attachmentId", value = "主图附件ID", paramType = "query", required = true)
    })
    @PostMapping("/publications")
    public ReturnEntity publishNews(Long newsId, Long attachmentId) {
        return ReturnUtil.buildReturn(newsService.publishNews(newsId, attachmentId));
    }

    @ApiOperation(value = "取消发布党建资讯", notes = "取消发布党建资讯", httpMethod = "POST")
    @ApiImplicitParam(name = "newsId", value = "党建资讯ID", paramType = "path", required = true)
    @PostMapping("/recalls/{newsId}")
    public ReturnEntity obtainedNews(@PathVariable Long newsId) {
        return ReturnUtil.buildReturn(newsService.obtainedNews(newsId));
    }

    @ApiOperation(value = "置顶党建资讯", notes = "置顶党建资讯", httpMethod = "POST")
    @ApiImplicitParam(name = "newsId", value = "党建资讯ID", paramType = "path", required = true)
    @PostMapping("/tops/{newsId}")
    public ReturnEntity toppingNews(@PathVariable Long newsId) {
        return ReturnUtil.buildReturn(newsService.topNews(newsId));
    }

    @ApiOperation(value = "取消置顶党建资讯", notes = "取消置顶党建资讯", httpMethod = "POST")
    @ApiImplicitParam(name = "newsId", value = "党建资讯ID", paramType = "path", required = true)
    @PostMapping("/un-tops/{newsId}")
    public ReturnEntity unToppingNews(@PathVariable Long newsId) {
        return ReturnUtil.buildReturn(newsService.unTopNews(newsId));
    }

    @ApiOperation(value = "删除党建资讯", notes = "删除党建资讯", httpMethod = "DELETE")
    @ApiImplicitParam(name = "newsId", value = "党建资讯ID", paramType = "path", required = true)
    @DeleteMapping("/{newsId}")
    public ReturnEntity deleteNews(@PathVariable Long newsId) {
        return ReturnUtil.buildReturn(newsService.deleteNews(newsId));
    }

    @ApiOperation(value = "获取党建资讯详情", notes = "获取党建资讯详情", httpMethod = "GET")
    @ApiImplicitParam(name = "newsId", value = "党建资讯ID", paramType = "path", required = true)
    @GetMapping("/{newsId}")
    public NewsVO getNewsDetails(@PathVariable Long newsId) {
        return newsService.getNewsVODetails(newsId);
    }

    @ApiOperation(value = "获取党建资讯列表", notes = "获取党建资讯列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<NewsVO> getNewsList(@Validated NewsQueryBean newsQueryBean, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(newsService.getNewsVOList(newsQueryBean));
    }

}
