package com.egovchina.partybuilding.partybuild.controller;


import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.CommunityDTO;
import com.egovchina.partybuilding.partybuild.dto.DeletePartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyInfoDTO;
import com.egovchina.partybuilding.partybuild.entity.HistoricalPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.SysUserQueryBean;
import com.egovchina.partybuilding.partybuild.service.ExtendedInfoService;
import com.egovchina.partybuilding.partybuild.service.PartyInformationService;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 党员信息
 *
 * @Author liu tang gang
 **/
@Api(tags = "党员-党员信息模块v1-刘唐港")
@RestController
@RequestMapping("/v1")
public class PartyInformationController {

    @Autowired
    PartyInformationService partyInformationService;
    @Autowired
    private ExtendedInfoService extendedInfoService;

    @ApiOperation(value = "分页查询党员信息", notes = "分页查询党员信息", httpMethod = "GET")
    @GetMapping("/party-members")
    public PageInfo<PartyMemberInformationVO> getPartyMemberList(@Validated @ApiParam("党员信息查询实体") SysUserQueryBean queryBean, Page page) {
        return partyInformationService.getPartyList(queryBean, page);
    }

    @ApiOperation(value = "党员身份核查", notes = "党员身份核查", httpMethod = "GET")
    @GetMapping("/party-members/identities")
    public PageInfo<PersonnelVO> getPersonnelVOList(@RequestParam(required = false) @ApiParam(value = "姓名") String username,
                                                    @RequestParam(required = false) @ApiParam(value = "身份证") String idCardNo,
                                                    @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                                    Page page) {
        return partyInformationService.partyIdentityVerification(username, idCardNo, phone, page);
    }

    @ApiOperation(value = "历史党员列表", notes = "分页查询历史党员信息", httpMethod = "GET")
    @GetMapping("/history-members")
    public PageInfo<HistoryPartyVO> getPartyHistoryList(@Validated @ApiParam("历史党员查询实体") HistoricalPartyMemberQueryBean queryBean, Page page) {
        return partyInformationService.getPartyHistoryList(queryBean, page);
    }

    @ApiOperation(value = "根据id获取党员信息附带学历等信息", notes = "根据id获取党员信息附带学历等信息", httpMethod = "GET")
    @ApiImplicitParam(value = "党员id", name = "id", dataType = "long", paramType = "path", required = true)
    @GetMapping("/party-members/{id}")
    public PartyMemberVO getParty(@PathVariable Long id) {
        return extendedInfoService.selectPartyMemberDetailsById(id);
    }

    @ApiOperation(value = "根据id获取组织信息列表里面的书记的简单信息", notes = "根据id获取组织信息列表里面的书记的简单信息", httpMethod = "GET")
    @ApiImplicitParam(value = "党员id", name = "id", dataType = "long", paramType = "path", required = true)
    @GetMapping("/party-members/{id}/secretaries")
    public SecretariesPartyMemberVO getSecretariesParty(@PathVariable Long id) {
        return extendedInfoService.selectSecretariesPartyMemberVO(id);
    }

    @ApiOperation(value = "根据身份证号码或者姓名获取人员信息", notes = "根据身份证号码或者姓名获取人员信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "姓名", dataType = "String"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证号码", dataType = "String")
    })
    @GetMapping("/party-members/infos")
    public PageInfo<SysUserVO> getPartyByIdCardNoOrUserName(String idCardNo, String username, Page page) {
        return extendedInfoService.selectPartyByIdCardNoOrUserName(idCardNo, username, page);
    }

    @ApiOperation(value = "根据id删除 user信息")
    @PostMapping("/history-members")
    public ReturnEntity deleteUser(@RequestBody @Validated @ApiParam("删除党员信息实体") DeletePartyMemberDTO deletePartyMemberDTO) {
        return ReturnUtil.buildReturn(extendedInfoService.InvalidByUserId(deletePartyMemberDTO));
    }

    @ApiOperation(value = "根据id恢复党员信息")
    @GetMapping("/history-members/{userId}/restorations")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true, dataType = "long")
    public ReturnEntity restoreUser(@PathVariable Long userId) {
        return ReturnUtil.buildReturn(extendedInfoService.restoreUser(userId));
    }

    @ApiOperation(value = "根据社区名字模糊获取社区")
    @GetMapping("/communities")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query"),
            @ApiImplicitParam(name = "label", value = "社区名字", paramType = "query")
    })
    public PageInfo<CommunityVO> getCommunity(@ApiParam("社区模糊查询实体") CommunityDTO communityDTO, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(partyInformationService.selectCommunityVO(communityDTO));
    }

    @Deprecated
    @ApiOperation(value = "获取登录用户的信息")
    @GetMapping("/login-user-infos")
    public UserInfoVO getUserInfo() {
        return partyInformationService.getUserInfoVO();
    }

    @ApiOperation(value = "补录党员信息", notes = "补录党员信息", httpMethod = "POST")
    @PostMapping("/party-members")
    public ReturnEntity insetParty(@RequestBody @Validated @ApiParam("党员基本信息") PartyInfoDTO partyInfoDTO) {
        return ReturnUtil.buildReturn(partyInformationService.savePartyInfo(partyInfoDTO));
    }

    @ApiOperation(value = "更新党员信息", notes = "更新党员信息", httpMethod = "PUT")
    @PutMapping("/party-members")
    public ReturnEntity updateParty(@RequestBody @Validated @ApiParam("党员基本信息") PartyInfoDTO partyInfoDTO) {
        return ReturnUtil.buildReturn(partyInformationService.updatePartyInfo(partyInfoDTO));
    }
}
