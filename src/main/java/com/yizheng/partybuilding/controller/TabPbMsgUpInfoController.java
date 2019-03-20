package com.yizheng.partybuilding.controller;


import com.github.pagehelper.PageInfo;
import com.yizheng.commons.exception.BusinessDataNotFoundException;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbMsgUpInfoDto;
import com.yizheng.partybuilding.entity.TabPbMsgUpInfo;

import com.yizheng.partybuilding.service.inf.TabPbMsgUpInfoSerivce;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@Api(tags = "上传下达-信息报送模块")
@RequestMapping("/tabPbMsgUpInfo")
public class TabPbMsgUpInfoController {

    @Autowired
    TabPbMsgUpInfoSerivce tabPbMsgUpInfoSerivce;

    @PostMapping("/insert")
    @ApiOperation(value = "信息添加", notes = "信息添加", httpMethod = "POST")
    public ReturnEntity insert(@RequestBody @Validated TabPbMsgUpInfoDto tabPbMsgUpInfoDto) {
        int retVal = tabPbMsgUpInfoSerivce.insert(tabPbMsgUpInfoDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @GetMapping("/selectAffter")
    @ApiOperation(value = "返回上报人的姓名，组织名称id，接受组织名称id，接受组织专干人姓名,党组织名称", notes = "不填返回登录人的", httpMethod = "GET")
    @ApiImplicitParam(value = "上报组织主键", name = "realDeptId", paramType = "query")
    public TabPbMsgUpInfo selectAffter(Long realDeptId) {
        return tabPbMsgUpInfoSerivce.selectAffter(realDeptId);
    }

    @GetMapping("/selectActive")
    @ApiOperation(value = "上报条件查询信息列表", notes = "", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "主题、专题标签 码表ZTZT", name = "titleLabel", paramType = "query"),
            @ApiImplicitParam(value = "上报日期(开始)", name = "upTime", paramType = "query"),
            @ApiImplicitParam(value = "上报日期(结束)", name = "upTimeOver", paramType = "query"),
            @ApiImplicitParam(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织", name = "orgRange", paramType = "query"),
            @ApiImplicitParam(value = "组织ID", name = "rangeDeptId", paramType = "query"),
    })
    public PageInfo<TabPbMsgUpInfoDto> selectActive(@ApiIgnore TabPbMsgUpInfoDto tabPbMsgUpInfoDto, Page page) {
        Long rangeDeptId = tabPbMsgUpInfoDto.getRangeDeptId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            tabPbMsgUpInfoDto.setRangeDeptId(UserContextHolder.getOrgId());
        }
        return tabPbMsgUpInfoSerivce.selectActive(tabPbMsgUpInfoDto, page);
    }

    @GetMapping("/selectActiveRec")
    @ApiOperation(value = "收到条件查询信息报送列表", notes = "", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "主题、专题标签 码表ZTZT", name = "titleLabel", paramType = "query"),
            @ApiImplicitParam(value = "上报日期", name = "upTime", paramType = "query"),
            @ApiImplicitParam(value = "上报日期(结束)", name = "upTimeOver", paramType = "query"),
            @ApiImplicitParam(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织", name = "orgRange", paramType = "query"),
            @ApiImplicitParam(value = "组织ID", name = "rangeDeptId", paramType = "query"),
    })
    public PageInfo<TabPbMsgUpInfoDto> selectActiveRec(@ApiIgnore TabPbMsgUpInfoDto tabPbMsgUpInfoDto, Page page) {
        Long rangeDeptId = tabPbMsgUpInfoDto.getRangeDeptId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            tabPbMsgUpInfoDto.setRangeDeptId(UserContextHolder.getOrgId());
        }
        return tabPbMsgUpInfoSerivce.selectActiveRec(tabPbMsgUpInfoDto, page);
    }

    @ApiOperation(value = "根据主键查询单个详情", notes = "根据主键查询单个详情", httpMethod = "GET")
    @GetMapping("/detail")
    public TabPbMsgUpInfoDto detail(@RequestParam @ApiParam(value = "ID") Long id) {
        return tabPbMsgUpInfoSerivce.selectActiveOne(id);
    }

    @ApiOperation(value = "删除", notes = "根据主键删除", httpMethod = "DELETE")
    @DeleteMapping("/delete")
    public ReturnEntity delete(@RequestParam @ApiParam(value = "ID") Long id) {
        int retVal = tabPbMsgUpInfoSerivce.delete(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT")
    @PutMapping("/update")
    public ReturnEntity update(@RequestBody TabPbMsgUpInfoDto tabPbMsgUpInfoDto) {
        int retVal = tabPbMsgUpInfoSerivce.update(tabPbMsgUpInfoDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "审核", notes = "提供审核结果，审核说明,主键id", httpMethod = "POST")
    @PostMapping("/audit")
    public String audit(@RequestParam @ApiParam(value = "数据ID", required = true) Long id,
                        @RequestParam @ApiParam(value = "审核结果 码表SHJG", required = true) Long auditAssess,
                        @RequestParam(required = false) @ApiParam(value = "审核说明") String auditComment) {
        TabPbMsgUpInfoDto dbMsgUpInfo = tabPbMsgUpInfoSerivce.selectActiveOne(id);
        if (dbMsgUpInfo == null) {
            throw new BusinessDataNotFoundException("数据不存在");
        }
        dbMsgUpInfo.setAuditAssess(auditAssess);
        dbMsgUpInfo.setAuditComment(auditComment);
        int retVal = tabPbMsgUpInfoSerivce.auditResult(dbMsgUpInfo);
        return retVal > 0 ? "审核成功" : "审核失败";
    }

}
