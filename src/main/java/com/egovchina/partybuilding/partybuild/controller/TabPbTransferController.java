package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.TransferApprovalDto;
import com.egovchina.partybuilding.partybuild.dto.TransferDetailDto;
import com.egovchina.partybuilding.partybuild.dto.TransferListSpDto;
import com.egovchina.partybuilding.partybuild.dto.TransferListWaitingDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbTransfer;
import com.egovchina.partybuilding.partybuild.service.TabPbTransferItemService;
import com.egovchina.partybuilding.partybuild.service.TabPbTransferService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

@Deprecated
//@Api(tags = "党员-组织关系接转模块")
//@RestController
//@RequestMapping("/transfer")
public class TabPbTransferController {

    @Autowired
    private TabPbTransferService transferService;
    @Autowired
    private TabPbTransferItemService transferItemService;

    @ApiOperation(value = "新增组织关系接转信息", notes = "新增组织关系接转信息", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity add(@RequestBody TabPbTransfer transfer) {
        int add = transferService.add(transfer);
        return ReturnUtil.buildReturn(add);
    }

    @ApiOperation(value = "所有组织关系接转列表", notes = "所有组织关系接转列表", httpMethod = "GET")
    @GetMapping("/list")
    public PageInfo<TabPbTransfer> getPagelist(Page page) {
        return transferService.getPageList(page);
    }

    @ApiOperation(value = "获取组织关系接转信息--介绍信", notes = "返回指定ID信息", httpMethod = "GET")
    @GetMapping("/{transferId}")
    public TabPbTransfer getById(@ApiParam(value = "transferId", required = true) @PathVariable Long transferId) {
        return transferService.getById(transferId);
    }

    @ApiOperation(value = "组织关系接转详情--包含审批信息", notes = "组织关系接转详情--包含审批信息", httpMethod = "GET")
    @GetMapping("/getTransferDetail/{transferId}")
    public TransferDetailDto getTransferDetailById(@ApiParam(value = "transferId", required = true) @PathVariable Long transferId) {
        return transferService.getTransferDetailById(transferId);
    }

    //组织关系接转 - 待办工作 - 列表
    @ApiOperation(value = "组织关系接转--待办工作列表", notes = "组织关系接转--待办工作列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", required = true, value = "组织Id", paramType = "query"),
            @ApiImplicitParam(name = "username", required = false, value = "党员姓名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", required = false, value = "身份证号码", paramType = "query")
    })
    @GetMapping("/getWaitingPageList")
    public PageInfo<TransferListWaitingDto> getWaitingPageList(Long deptId, String username, String idCardNo, Page page) {
        return transferService.getWaitingPageList(deptId, username, idCardNo, page);
    }

    //组织关系接转 - 接收、转出 - 列表
    @ApiOperation(value = "组织关系接转--接收、转出列表", notes = "组织关系接转--接收、转出列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flowType", value = "类型：0接收列表、1转出列表", paramType = "query"),
            @ApiImplicitParam(name = "flowOutDeptId", value = "转出支部Id,类型为1时传入此id", paramType = "query"),
            @ApiImplicitParam(name = "flowInDeptId", value = "接收支部Id,类型为0时传入此id", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "党员姓名", paramType = "query")
    })
    @GetMapping("/getPageList")
    public PageInfo<TabPbTransfer> getPageList(int flowType, @ApiIgnore TabPbTransfer transfer, Page page) {
        return transferService.getPageList(flowType, transfer, page);
    }

    //组织关系接转 - 未办结工作 - 列表
    @ApiOperation(value = "组织关系接转--未办结工作列表", notes = "组织关系接转--未办结工作列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", required = true, value = "组织Id", paramType = "query"),
            @ApiImplicitParam(name = "username", required = false, value = "党员姓名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", required = false, value = "身份证号码", paramType = "query")
    })
    @GetMapping("/getNotFinshedPageList")
    public PageInfo<TransferListWaitingDto> getNotFinshedPageList(Long deptId, String username, String idCardNo, Page page) {
        return transferService.getNotFinshedPageList(deptId, username, idCardNo, page);
    }


    //组织关系接转 - 即将办理工作 - 列表
    @ApiOperation(value = "组织关系接转--即将办理工作列表", notes = "组织关系接转--即将办理工作列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织Id", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "党员姓名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", required = false, value = "身份证号码", paramType = "query")
    })
    @GetMapping("/getHandlePageList")
    public PageInfo<TransferListSpDto> getHandlePageList(@ApiIgnore TransferListSpDto handle, Page page) {
        return transferService.getHandlePageList(handle, page);
    }

    //组织关系接转 - 审批记录查询 - 列表
    @ApiOperation(value = "组织关系接转--审批记录查询列表", notes = "组织关系接转--审批记录查询列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织Id", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "党员姓名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", required = false, value = "身份证号码", paramType = "query")
    })
    @GetMapping("/getSpRecordPageList")
    public PageInfo<TabPbTransfer> getSpRecordPageList(@ApiIgnore TransferListSpDto spDto, Page page) {
        return transferService.getSpRecordPageList(spDto, page);
    }


    @ApiOperation(value = "组织关系接转--审批", notes = "组织关系接转--审批", httpMethod = "POST")
    @PostMapping("/approval")
    public ReturnEntity transferApproval(@RequestBody TransferApprovalDto approvalDto) {
        transferItemService.transferApproval(approvalDto);
        return ReturnUtil.success();
    }

    @ApiOperation(value = "组织关系接转--撤销", notes = "组织关系接转--撤销", httpMethod = "POST")
    @PostMapping("/revoke{transferId}")
    public ReturnEntity transferRevoke(@ApiParam(value = "transferId", required = true) @PathVariable Long transferId) {
        transferService.transferRevoke(transferId);
        return ReturnUtil.success();
    }
}
