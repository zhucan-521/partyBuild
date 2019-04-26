package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.DevPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.entity.DevPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.PartyApplyQueryBean;
import com.egovchina.partybuilding.partybuild.service.DevPartyMemberService;
import com.egovchina.partybuilding.partybuild.vo.CheckDevPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyMemberDateVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 党员发展步骤模块-v1
 * Created by FanYanGen on 2019/4/20 10:13
 */
@Deprecated
@Api(tags = "党员-发展步骤v1-范焱根")
@RequestMapping("/v1/develops")
@RestController
public class DevPartyMemberController {

    @Autowired
    private DevPartyMemberService devPartyMemberService;

    @Deprecated
    @ApiOperation(value = "根据发展步骤ID获取发展步骤信息", notes = "根据发展步骤ID获取发展步骤信息", httpMethod = "GET")
    @ApiImplicitParam(value = "dpId", name = "发展步骤ID", required = true)
    @GetMapping(value = "/{dpId}")
    public DevPartyMemberVO getByDpId(@PathVariable Long dpId) {
        return devPartyMemberService.findDevPartyMemberVOByDeptId(dpId);
    }

    @Deprecated
    @ApiOperation(value = "根据党员ID获取发展步骤信息", notes = "根据党员ID获取发展步骤信息", httpMethod = "GET")
    @GetMapping(value = "/party/{userId}")
    public DevPartyMemberVO getByUserId(@PathVariable Long userId) {
        return devPartyMemberService.findDevPartyMemberVOByUserId(userId);
    }

    @Deprecated
    @ApiOperation(value = "删除党员发展步骤信息", notes = "删除党员发展步骤信息", httpMethod = "DELETE")
    @DeleteMapping(value = "/{deptId}")
    public int delete(@PathVariable Long deptId) {
        return devPartyMemberService.deleteByDeptId(deptId);
    }

    @Deprecated
    @ApiOperation(value = "更新党员发展步骤信息", notes = "更新党员发展步骤信息", httpMethod = "PUT")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dpId", value = "主键id", required = true),
            @ApiImplicitParam(name = "status", value = "发展状态", required = true),
    })
    @PutMapping
    public int update(Long dpId, Long status) {
        return devPartyMemberService.updateDevStep(dpId, status);
    }

    @Deprecated
    @ApiOperation(value = "分页获取党员发展列表信息", notes = "分页获取党员发展列表信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pdId", value = "发展ID(主键ID)"),
            @ApiImplicitParam(name = "userId", value = "党员userId"),
            @ApiImplicitParam(name = "status", value = "发展状态")
    })
    @GetMapping
    public PageInfo<DevPartyMemberVO> list(Long pdId, Long userId, Long status, Page page) {
        return devPartyMemberService.findDevPartyMemberVOByConditions(pdId, userId, status, page);
    }

    @Deprecated
    @ApiOperation(value = "判断该用户是否能补录", notes = "判断该用户是否能补录", httpMethod = "GET")
    @GetMapping("/is-party")
    public CheckDevPartyMemberVO checkIsParty(DevPartyMemberQueryBean devPartyMemberQueryBean) {
        return devPartyMemberService.checkIsParty(devPartyMemberQueryBean);
    }

    @Deprecated
    @ApiOperation(value = "入党申请列表", notes = "入党申请列表", httpMethod = "GET")
    @GetMapping("/parties")
    public PageInfo<DevPartyVO> getPartyApplyList(PartyApplyQueryBean partyApplyQueryBean, Page page) {
        return devPartyMemberService.findDevPartyVOByConditions(partyApplyQueryBean, page);
    }

    @Deprecated
    @ApiOperation(value = "根据hostId获取发展步骤时间", notes = "通过hostId获取发展步骤时间", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hostId", required = true)
    })
    @GetMapping("/time/{hostId}")
    public List<DevPartyMemberDateVO> getDevDateByHostId(@PathVariable Long hostId) {
        return devPartyMemberService.findDevPartyMemberDateVOByHostId(hostId);
    }

    @Deprecated
    @ApiOperation(value = "根据时间主键删除", notes = "根据时间主键删除", httpMethod = "DELETE")
    @ApiImplicitParam(value = "timeId", name = "timeId", required = true)
    @DeleteMapping("/time/{timeId}")
    public int delDevDateByTimeId(@PathVariable Long timeId) {
        return devPartyMemberService.deleteDevDate(timeId);
    }

    @Deprecated
    @ApiOperation(value = "根据时间主键更新", notes = "根据时间主键更新", httpMethod = "PUT")
    @PutMapping("/time")
    public int updateDevDate(@RequestBody DevPartyMemberDTO devPartyMemberDTO) {
        return devPartyMemberService.updateDevDate(devPartyMemberDTO);
    }

    @Deprecated
    @ApiOperation(value = "查询党员发展指定步骤, (包含具体的附件信息和发展步骤信息)")
    @GetMapping("/attaches")
    public DevPartyMemberVO getAttach(Long dpId, Boolean isExtend) {
        return devPartyMemberService.findAttach(dpId, isExtend);
    }

}
