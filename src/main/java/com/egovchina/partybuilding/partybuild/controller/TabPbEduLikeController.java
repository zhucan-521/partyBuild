package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduLike;
import com.egovchina.partybuilding.partybuild.service.TabPbEduLikeService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Deprecated
@Api(tags = "党员教育-点赞模块 朱灿")
@RestController
@RequestMapping("/tabPbEduLike")
public class TabPbEduLikeController {

    @Autowired
    TabPbEduLikeService tabPbEduLikeService;

    @Deprecated
    @ApiOperation(value = "点赞次数添加，返回点赞主键", notes = "给业务类型和业务id赋值即可", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "likeType", value = "业务类型", paramType = "query"),
            @ApiImplicitParam(name = "busId", value = "业务id", paramType = "query"),
    })
    @PostMapping("/insert")
    public int insert(@ApiIgnore TabPbEduLike tabPbEduLike) {
        return tabPbEduLikeService.addTabPbEduLikeService(tabPbEduLike);
    }

    @Deprecated
    @ApiOperation(value = "点赞查询", notes = "业务类型：1代表学习中心,2代表讲义课堂，3代表师资队伍，4代表教育阵地,5代表智能选学，6代表精品课程，7代表重点培训", httpMethod = "POST")
    @PostMapping("/select")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "likeType", value = "业务类型", paramType = "query"),
            @ApiImplicitParam(name = "busId", value = "业务id", paramType = "query"),
            @ApiImplicitParam(name = "likeId", value = "点赞主键", paramType = "query"),
    })
    public PageInfo<TabPbEduLike> select(@ApiIgnore TabPbEduLike tabPbEduLike, Page page) {
        return tabPbEduLikeService.selectTabPbEduLikeSelective(tabPbEduLike, page);
    }

    @Deprecated
    @ApiOperation(value = "修改点赞", notes = "必须附带likeId", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@RequestBody TabPbEduLike tabPbEduLike) {
        int retVal = tabPbEduLikeService.updataTabPbEduLikeSelective(tabPbEduLike);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "删除", notes = "根据id删除", httpMethod = "DELETE")
    @DeleteMapping("/delete")
    @ApiImplicitParam(value = "点赞Id", name = "likeId", paramType = "query")
    public ReturnEntity delete(Long likeId) {
        int retVal = tabPbEduLikeService.deleteTabPbEduLikeById(likeId);
        return ReturnUtil.buildReturn(retVal);
    }

}
