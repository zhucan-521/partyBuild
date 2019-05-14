package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.FlowOutMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.FlowOutMemberQueryBean;
import com.egovchina.partybuilding.partybuild.service.FlowOutVoService;
import com.egovchina.partybuilding.partybuild.vo.FlowOutMemberVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 党员流出模块
 *
 * @Author zhu can
 **/
@Api(tags = "党员-党员流出-v1-朱灿")
@RestController
@RequestMapping("/v1/outflows")
public class FlowOutController {

    @Autowired
    private FlowOutVoService  flowOutVoService;

    @ApiOperation(value = "根据flowOutId主键查询流出党员单个详情", httpMethod = "GET")
    @ApiImplicitParam(name = "flowOutId", value = "流出主键", paramType = "path", required = true)
    @GetMapping("/{flowOutId}")
    public FlowOutMemberVO getFlowOutMemberById(@PathVariable Long flowOutId) {
        return flowOutVoService.getFlowOutMember(flowOutId);
    }

    @ApiOperation(value = "条件查询流出党员", notes = "条件查询流出党员", httpMethod = "GET")
    @GetMapping
    public PageInfo<FlowOutMemberVO> selectFlowOutMemberList(FlowOutMemberQueryBean flowOutMemberQueryBean, Page page) {
        return flowOutVoService.getFlowOutVoList(flowOutMemberQueryBean, page);
    }

    @ApiOperation(value = "流出党员（包括市外）登记/市外流入党员手动登记（一定要附带录入日期）", notes = "如果是流出党员流出市外登记那么就不要给流入组织id（flowOutPlace）赋值,因为系统没有流入；手动录入，一定要有录入日期，流出组织id（orgId）为空，因为外市流入本系统没有对应的流出组织", httpMethod = "POST")
    @PostMapping
    public ReturnEntity addFlowOutMember(@RequestBody @ApiParam(value = "流出党员") FlowOutMemberDTO flowOutMemberDTO) {
        return ReturnUtil.buildReturn(flowOutVoService.addFlowOutMemberDTO(flowOutMemberDTO));
    }

    @ApiOperation(value = "根据flowOutId删除流出党员(只有待报道状态才可以删除)", notes = "根据flowOutId删除流出党员", httpMethod = "DELETE")
    @ApiImplicitParam(value = "流出党员主键", name = "flowOutId", required = true, paramType = "path")
    @DeleteMapping("/{flowOutId}")
    public ReturnEntity delet(@PathVariable Long flowOutId) {
        return ReturnUtil.buildReturn(flowOutVoService.delete(flowOutId));
    }

    @ApiOperation(value = "修改流出党员", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity updateFlowOutMember(@Validated @RequestBody @ApiParam(value = "流出党员") FlowOutMemberDTO flowOutMemberDTO) {
        return ReturnUtil.buildReturn(flowOutVoService.updateFlowOutMember(flowOutMemberDTO));
    }

}
