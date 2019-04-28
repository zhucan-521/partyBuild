package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.entity.FlowInMemberQueryBean;
import com.egovchina.partybuilding.partybuild.dto.FlowInMemberDTO;
import com.egovchina.partybuilding.partybuild.service.FlowInService;
import com.egovchina.partybuilding.partybuild.vo.FlowInMemberVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "流入党员", tags = {"党员-流入党员-v1-朱灿"})
@RestController
@RequestMapping("/v1/inflows")
public class FlowInControlle {

    @Autowired
    private FlowInService flowInService;


    @ApiOperation(value = "根据流入id获取流入党员DTO信息", notes = "根据流入id获取流入党员DTO信息", httpMethod = "GET")
    @GetMapping("/{flowInId}")
    public FlowInMemberVO getFlowInMember(@ApiParam(value = "流入ID",name = "flowInId",required = true) @PathVariable Long flowInId) {
        return flowInService.getFlowInMeberVoById(flowInId);
    }


    @ApiOperation(value = "分页查询流入党员", notes = "分页查询流入党员", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "组织范围  1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织）", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "flowInState", value = "状态", paramType = "query" ),
            @ApiImplicitParam(name = "idCardNo", value = "身份证号码", paramType = "query")
    })
    @GetMapping
    public PageInfo<FlowInMemberVO> flowInMemberList(FlowInMemberQueryBean flowInMemberQueryBean, Page page) {
        return flowInService.getFlowInMemberList(flowInMemberQueryBean, page);
    }

    @ApiOperation(value = "流入党员返回登记(务必带上流入主键flowInId)", notes = "传入返回登记党员信息", httpMethod = "POST")
    @PostMapping("/return-register")
    public ReturnEntity returnFlowInMember(@Validated  @RequestBody @ApiParam(value = "流入成员")  FlowInMemberDTO flowInMemberDTO) {
        return ReturnUtil.buildReturn(flowInService.returnFlowInMember(flowInMemberDTO));
    }

    @ApiOperation(value = "流入党员接收(带上flowInId)", notes = "务必带上flowInId", httpMethod = "POST")
    @PostMapping("/accept")
    public ReturnEntity acceptFlowInMember(@RequestBody @Validated @ApiParam(value = "流入成员")  FlowInMemberDTO flowInMemberDTO) {
        int insert = flowInService.acceptFlowInMember(flowInMemberDTO);
        return ReturnUtil.buildReturn(insert);
    }

    @ApiOperation(value = "删除流入党员记录", notes = "删除流入党员记录", httpMethod = "DELETE")
    @ApiImplicitParam(value = "流入主键flowInId",name = "flowInId",paramType = "path",required = true)
    @DeleteMapping("/{flowInId}")
    public ReturnEntity deleteFlowInMember(@PathVariable Long flowInId) {
        int flag = flowInService.deleteFlowInMember(flowInId);
        return ReturnUtil.buildReturn(flag);
    }

    @ApiOperation(value = "编辑流入党员(务必带上flowInId)", notes = "编辑流入党员", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateFlowInMember( @ApiParam(value = "流入党员对象") @RequestBody @Validated FlowInMemberDTO flowInMemberDTO) {
        int flag = flowInService.updateFlowInDto(flowInMemberDTO);
        return ReturnUtil.buildReturn(flag);
    }




}
