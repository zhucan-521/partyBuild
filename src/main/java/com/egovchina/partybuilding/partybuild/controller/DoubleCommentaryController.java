package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.DoubleCommentaryDTO;
import com.egovchina.partybuilding.partybuild.entity.CommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.entity.DoubleCommentaryQueryBean;
import com.egovchina.partybuilding.partybuild.service.DoubleCommentaryService;
import com.egovchina.partybuilding.partybuild.vo.CommentaryVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * desc: 双述双评模块-v1
 * Created by FanYanGen on 2019/4/24 16:06
 */
@Api(tags = "党员-双述双评模块v1-范焱根")
@RequestMapping("/v1/commentaries")
@RestController
public class DoubleCommentaryController {

    @Autowired
    private DoubleCommentaryService commentaryService;

    @ApiOperation(value = "新增双述双评", notes = "新增双述双评", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertCommentary(@RequestBody @Validated @ApiParam("双述双评信息") DoubleCommentaryDTO doubleCommentaryDTO) {
        return ReturnUtil.buildReturn(commentaryService.insertCommentary(doubleCommentaryDTO));
    }

    @ApiOperation(value = "更新双述双评", notes = "更新双述双评", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateCommentary(@RequestBody @Validated @ApiParam("双述双评信息") DoubleCommentaryDTO doubleCommentaryDTO) {
        return ReturnUtil.buildReturn(commentaryService.updateCommentary(doubleCommentaryDTO));
    }

    @ApiOperation(value = "删除双述双评", notes = "双述双评删除", httpMethod = "DELETE")
    @ApiImplicitParam(name = "commentaryId", value = "双述双评ID", paramType = "path", required = true)
    @DeleteMapping("/{commentaryId}")
    public ReturnEntity deleteCommentary(@PathVariable Long commentaryId) {
        return ReturnUtil.buildReturn(commentaryService.deleteCommentary(commentaryId));
    }

    @ApiOperation(value = "双述双评详情", notes = "双述双评详情", httpMethod = "GET")
    @ApiImplicitParam(name = "commentaryId", value = "双述双评ID", paramType = "path", required = true)
    @GetMapping("/{commentaryId}")
    public CommentaryVO getCommentaryDetails(@PathVariable Long commentaryId) {
        return commentaryService.findCommentaryVOByCommentaryId(commentaryId);
    }

    @ApiOperation(value = "双述双评列表", notes = "双述双评列表", httpMethod = "GET")
    @GetMapping
    public PageInfo<CommentaryVO> getCommentaryList(CommentaryQueryBean commentaryQueryBean, Page page) {
        return commentaryService.findCommentaryVOWithConditions(commentaryQueryBean, page);
    }

    @ApiOperation(value = "双述双评审核", notes = "双述双评审核", httpMethod = "PUT")
    @PutMapping("/verify")
    public ReturnEntity doVerify(@RequestBody @Validated DoubleCommentaryQueryBean doubleCommentaryQueryBean) {
        return ReturnUtil.buildReturn(commentaryService.verifyCommentary(doubleCommentaryQueryBean));
    }

}
