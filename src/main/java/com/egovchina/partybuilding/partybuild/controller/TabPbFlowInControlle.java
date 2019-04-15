package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.TabPbFlowInDto;
import com.egovchina.partybuilding.partybuild.service.TabPbFlowInService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@Api(value = "流入党员", tags = {"党员-流入党员 朱灿"})
@RestController
@RequestMapping("/flowIn")
public class TabPbFlowInControlle {

    @Autowired
    private TabPbFlowInService tabPbFlowInService;


    @ApiOperation(value = "录入流入党员", notes = "传入录入的流入党员", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@RequestBody TabPbFlowInDto tabPbFlowInDto) {
        int insert = tabPbFlowInService.insert(tabPbFlowInDto);
        return ReturnUtil.buildReturn(insert);
    }

    @ApiOperation(value = "分页查询流入党员", notes = "分页查询流入党员", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "组织范围  1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证号码", paramType = "query", dataType = "String")
    })
    @GetMapping("/flowInPage")
    public PageInfo<TabPbFlowInDto> flowInPage(@ApiIgnore @RequestParam Map<String, Object> params, Page page) {
        return tabPbFlowInService.select(params, page);
    }

    @ApiOperation(value = "删除流入党员记录", notes = "删除流入党员记录", httpMethod = "DELETE")
    @DeleteMapping("/delete/{flowInId}")
    public ReturnEntity delete(@ApiParam(value = "流入ID", required = true) @PathVariable Long flowInId) {
        int retVal = tabPbFlowInService.deleteByPrimaryKey(flowInId);
        return ReturnUtil.buildReturn(retVal);
    }

    @ApiOperation(value = "编辑流入党员", notes = "编辑流入党员", httpMethod = "PUT")
    @PutMapping("/update")
    public ReturnEntity update(@ApiParam(value = "流入党员对象") @RequestBody TabPbFlowInDto tabPbFlowInDto) {
        int retVal = tabPbFlowInService.updateTabPbFlowInDto(tabPbFlowInDto);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 根据流入id获取流入党员DTO信息
     *
     * @param flowInId
     * @return
     */
    @ApiOperation(value = "根据流入id获取流入党员DTO信息", notes = "根据流入id获取流入党员DTO信息", httpMethod = "GET")
    @GetMapping("/getFlowIn/{flowInId}")
    public TabPbFlowInDto getFlowIn(@ApiParam(value = "流入ID", required = true) @PathVariable Long flowInId) {
        return tabPbFlowInService.selectFlowInById(flowInId);
    }

    /**
     * 返回登记
     */
    @ApiOperation(value = "返回登记", notes = "传入返回登记党员信息", httpMethod = "PUT")
    @PutMapping("/endFlow")
    public ReturnEntity endFlow(@RequestBody TabPbFlowInDto tabPbFlowInDto) {
        int retVal = tabPbFlowInService.updateEndFlow(tabPbFlowInDto);
        return ReturnUtil.buildReturn(retVal);
    }


}
