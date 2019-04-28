package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.PositiveRegistMemberQueryBean;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegistMemberDTO;
import com.egovchina.partybuilding.partybuild.service.PositiveRegistService;
import com.egovchina.partybuilding.partybuild.vo.PositiveRegistMemberVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author zhucan on 2018/11/29
 */
@Api(value = "在职党员社区报到", tags = {"党员-在职党员社区报到接口-v1-朱灿"})
@RestController
@RequestMapping("/v1/registers")
public class PositiveRegistController {


    @Autowired
    private PositiveRegistService positiveRegistService;

    @ApiOperation(value = "保存报到党员", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addRegistMember(@RequestBody @ApiParam(value = "在职党员")PositiveRegistMemberDTO positiveRegistMemberDTO) {
        int add = positiveRegistService.addRegistMemberDTO(positiveRegistMemberDTO);
        return ReturnUtil.buildReturn(add);
    }


    @ApiOperation(value = "获取社区报到信息列表", notes = "可指定条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "党员名称", name = "userName", paramType = "query"),
            @ApiImplicitParam(value = "状态", name = "revokeTag", paramType = "query"),
            @ApiImplicitParam(value = "报到日期", name = "date", paramType = "query"),
            @ApiImplicitParam(value = "组织主键", name = "rangeDeptId", paramType = "query"),
            @ApiImplicitParam(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织", name = "orgRange", paramType = "query"),
    })
    @GetMapping
    public PageInfo<PositiveRegistMemberVO> registMemberVOList(@ApiIgnore PositiveRegistMemberQueryBean positiveRegistMemberQueryBean, Page page) {
        return new PageInfo<>(positiveRegistService.selectRegistMemberVOList(positiveRegistMemberQueryBean, page));
    }

    @ApiOperation(value = "改变登记状态", notes = "1为已报到,2为已返回")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "状态", name = "revokeTag", dataType = "Byte", required = true),
            @ApiImplicitParam(value = "主键Id", name = "positiveRegistId", dataType = "Long", required = true),
    })
    @PutMapping("/status")
    public ReturnEntity changeStatus(Long positiveRegistId, Byte revokeTag) {
        return ReturnUtil.buildReturn(positiveRegistService.ChangeStatus(positiveRegistId, revokeTag));
    }

    @ApiOperation(value = "根据主键ID删除信息", notes = "主键ID为必填")
    @ApiImplicitParam(name = "positiveRegistId", value = "主键ID", required = true, paramType = "path")
    @DeleteMapping("/{positiveRegistId}")
    public ReturnEntity deleteRegistMember(@PathVariable Long positiveRegistId) {
        int delete = positiveRegistService.delete(positiveRegistId);
        return ReturnUtil.buildReturn(delete);
    }

    @ApiOperation(value = "根据userId取消报到标识", notes = "userId为必填")
    @ApiImplicitParam(name = "userId", value = "主键ID", required = true, paramType = "path")
    @DeleteMapping("{userId}/status")
    public ReturnEntity delectRegistStatus(@PathVariable Long userId) {
        int flag = positiveRegistService.delectRegistStatus(userId);
        return ReturnUtil.buildReturn(flag);
    }


    @ApiOperation(value = "查看报到党员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "主键id", name = "positiveRegistId",paramType = "path",required = true),
    })
    @GetMapping(value = "/{positiveRegistId}")
    public PositiveRegistMemberVO getReportMembersInfo(@PathVariable Long positiveRegistId){
        return positiveRegistService.getReportMembersInfo(positiveRegistId);
    }

}
