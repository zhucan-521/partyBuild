package com.yizheng.partybuilding.controller;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.util.UserContextHolder;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.TabPbFlowOutDto;
import com.yizheng.partybuilding.service.inf.FlowOutService;
import com.yizheng.commons.util.ReturnEntity;
import com.yizheng.commons.util.ReturnUtil;
import com.yizheng.partybuilding.system.entity.SysUser;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 党员流出模块
 *
 * @Author zhu can
 **/
@Api(tags = "党员-党员流出模块 朱灿")
@RestController
@RequestMapping("/partyOut/")
public class PartyOutflowController {

    @Autowired
    FlowOutService flowOutService;

    @ApiOperation(value = "流出党员登记/市外流动党员手动录入", notes = "添加", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@RequestBody @Validated TabPbFlowOutDto tabPbFlowOutDto) {
        return ReturnUtil.buildReturn(flowOutService.insert(tabPbFlowOutDto));
    }

    @ApiOperation(value = "条件查询流出党员", notes = "条件查询流出党员", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "query"),
            @ApiImplicitParam(name = "flowToUnitName", value = "流入地", paramType = "query"),
            @ApiImplicitParam(name = "orgRange",value = "组织范围  0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织，不传查当前组织" ,paramType = "query")
    })
    @GetMapping("/selectAll")
    public PageInfo<TabPbFlowOutDto> selectAll(@ApiIgnore TabPbFlowOutDto dto, Page page) {
        Long rangeDeptId = dto.getRangeDeptId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            dto.setRangeDeptId(UserContextHolder.getOrgId());
        }
        return flowOutService.selectActiveTabPbFlowOutDto(dto,page);
    }

    @ApiOperation(value = "根据flowOutId删除流出党员", notes = "根据flowOutId删除流出党员", httpMethod = "GET")
    @GetMapping("/delet")
    public ReturnEntity delet(Long flowOutId) {
        return ReturnUtil.buildReturn(flowOutService.delete(flowOutId));
    }

    @ApiOperation(value = "修改", notes = "修改", httpMethod = "POST")
    @PostMapping("/UpdateSysUserDto")
    public ReturnEntity UpdateSysUserDto(@RequestBody TabPbFlowOutDto dto) {
       return ReturnUtil.buildReturn(flowOutService.update(dto));
    }

    @ApiOperation(value = "流出党员单个详情查询", notes = "根据id查询", httpMethod = "GET")
    @GetMapping("/selectOne")
    public TabPbFlowOutDto selectOne(@RequestParam @ApiParam(value = "flowOutId")Long flowOutId) {
        return flowOutService.findOne(flowOutId);
    }

}
