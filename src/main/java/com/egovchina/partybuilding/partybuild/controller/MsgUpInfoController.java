package com.egovchina.partybuilding.partybuild.controller;


import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.MsgUpInfoDTO;
import com.egovchina.partybuilding.partybuild.dto.MsgUpInfoAuditDTO;
import com.egovchina.partybuilding.partybuild.entity.MsgUpInfoQueryBean;
import com.egovchina.partybuilding.partybuild.service.MsgUpInfoSerivce;
import com.egovchina.partybuilding.partybuild.vo.MsgUpInfoVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "上传下达-信息报送模块-v1-朱灿")
@RequestMapping("/v1/info-up")
public class MsgUpInfoController {

    @Autowired
    MsgUpInfoSerivce msgUpInfoSerivce;

    @ApiOperation(value = "上报条件查询信息列表", httpMethod = "GET")
    @GetMapping("/report")
    public PageInfo<MsgUpInfoVO> selectReportMsgUpInfoList(MsgUpInfoQueryBean msgUpInfoQueryBean, Page page) {
        return msgUpInfoSerivce.selectMsgUpInfoList(msgUpInfoQueryBean, page);
    }

    @ApiOperation(value = "收到条件查询信息报送列表", httpMethod = "GET")
    @GetMapping("/receive")
    public PageInfo<MsgUpInfoVO> receiveMsgUpInfoList(MsgUpInfoQueryBean msgUpInfoQueryBean, Page page) {
        Long rangeDeptId = msgUpInfoQueryBean.getRangeDeptId();
        if (rangeDeptId == null || rangeDeptId == 0) {
            msgUpInfoQueryBean.setRangeDeptId(UserContextHolder.getOrgId());
        }
        return msgUpInfoSerivce.selectReceiveMsgUpInfoList(msgUpInfoQueryBean, page);
    }

    @ApiOperation(value = "获取信息上报人（姓名，组织名称id，接受组织名称id，接受组织专干人姓名,党组织名称）", notes = "不填返回登录人的", httpMethod = "GET")
    @ApiImplicitParam(value = "上报组织主键", name = "realDeptId", paramType = "query")
    @GetMapping("/up-member-info")
    public MsgUpInfoVO retrnUpMember(Long realDeptId) {
        return msgUpInfoSerivce.returnUpMember(realDeptId);
    }

    @ApiOperation(value = "根据主键查询信息详情", notes = "根据主键查询单个详情", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "主键", paramType = "path")
    @GetMapping("/{id}")
    public MsgUpInfoVO msgUpInfodetail(@PathVariable Long id) {
        return msgUpInfoSerivce.getMsgUpInfoById(id);
    }

    @ApiOperation(value = "添加信息", notes = "信息添加", httpMethod = "POST")
    @PostMapping
    public ReturnEntity insertMsgUpInfo(@RequestBody @ApiParam(name = "信息") MsgUpInfoDTO msgUpInfoDTO) {
        return ReturnUtil.buildReturn(msgUpInfoSerivce.insertMsgUpInfo(msgUpInfoDTO));
    }

    @ApiOperation(value = "删除信息", notes = "根据主键删除", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    @ApiImplicitParam(value = "信息主键id", name = "ID", paramType = "path", required = true)
    public ReturnEntity deleteMsgUpInfo(@PathVariable Long id) {
        return ReturnUtil.buildReturn(msgUpInfoSerivce.deleteMsgUpInfo(id));
    }

    @ApiOperation(value = "修改信息", httpMethod = "PUT")
    @PutMapping
    public ReturnEntity editMsgUpInfo(@RequestBody @Validated @ApiParam(value = "信息") MsgUpInfoDTO msgUpInfoDTO) {
        return ReturnUtil.buildReturn(msgUpInfoSerivce.editMsgUpInfo(msgUpInfoDTO));
    }

    @ApiOperation(value = "信息审核", notes = "提供审核结果，审核说明,主键id", httpMethod = "PUT")
    @PutMapping("/audit")
    public ReturnEntity auditMsgUpInfo(MsgUpInfoAuditDTO msgUpInfoAuditDTO) {
        MsgUpInfoVO dbMsgUpInfo = msgUpInfoSerivce.getMsgUpInfoById(msgUpInfoAuditDTO.getId());
        if (dbMsgUpInfo == null) {
            throw new BusinessDataNotFoundException("数据不存在");
        }
        dbMsgUpInfo.setAuditAssess(msgUpInfoAuditDTO.getAuditAssess());
        dbMsgUpInfo.setAuditComment(msgUpInfoAuditDTO.getAuditComment());
        MsgUpInfoDTO tabPbMsgUpInfoDto = new MsgUpInfoDTO();
        BeanUtil.copyPropertiesIgnoreNull(dbMsgUpInfo, tabPbMsgUpInfoDto);
        return ReturnUtil.buildReturn(msgUpInfoSerivce.auditMsgUpInfo(tabPbMsgUpInfoDto));
    }

}
