package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.TabSpecialWorkerResultDto;
import com.egovchina.partybuilding.partybuild.service.TabSpecialWorkerService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 党员专干管理模块
 *
 * @Author zhu can
 **/
@Api(tags = "党组织-专干管理模块 朱灿")
@RestController
@RequestMapping("/dedict/")
public class TabSpecialWorkerController {

    @Autowired
    TabSpecialWorkerService tabSpecialWorkerService;

    /**
     * 专干管理模块新增
     */
    @ApiOperation(value = "专干管理模块新增", notes = "专干管理模块新增", httpMethod = "POST")
    @PostMapping("/insertOrUpdateByDeptId")
    public ReturnEntity insertOrUpdateByDeptId(@RequestBody TabSpecialWorkerResultDto tabSpecialWorkerResultDto) {
        return  ReturnUtil.buildReturn(tabSpecialWorkerService.insert(tabSpecialWorkerResultDto));
    }

    @ApiOperation(value = "删除", notes = "传入specialWorkerId删除,未删除sys_user_role表里面的值", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(Long specialWorkerId) {
        int retVal = tabSpecialWorkerService.deleteBySpecialWorkerId(specialWorkerId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "条件分页查询", notes = "status为变更状态，1为增加，-1为减少，0是不变", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "专干党员主键", name = "specialWorkerId", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query"),
            @ApiImplicitParam(value = "专干姓名", name = "username", paramType = "query"),
            @ApiImplicitParam(value = "身份证号码", name = "idCardNo", paramType = "query"),
            @ApiImplicitParam(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织", name = "orgRange", paramType = "query")
    })
    @GetMapping("/selectAll")
    public PageInfo<TabSpecialWorkerResultDto> selectAll(Page page, @ApiIgnore TabSpecialWorkerResultDto dto) {
        Long rangeDeptId = dto.getRangeDeptId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            dto.setRangeDeptId(UserContextHolder.getOrgId());
        }
        return tabSpecialWorkerService.selectAllTabSpecialWorkerDto(page, dto);
    }

    @ApiOperation(value = "修改", notes = "根据specialWorkerId修改", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "党员专干实体") @RequestBody TabSpecialWorkerResultDto dto) {
        int retVal = tabSpecialWorkerService.updateBySpecialWorkerId(dto);
        return ReturnUtil.buildReturn(retVal);
    }


    @ApiOperation(value = "详情查询根据id", notes = "详情查询根据id", httpMethod = "GET")
    @GetMapping("/selectOne")
    public TabSpecialWorkerResultDto selectOne(@RequestParam @ApiParam(value = "专干主键" ,name = "specialWorkerId")Long specialWorkerId) {
        return tabSpecialWorkerService.selectOneById(specialWorkerId);
    }
}