package com.egovchina.partybuilding.partybuild.controller;


import com.egovchina.partybuilding.common.config.HasPermission;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.enums.PermissionMatchType;
import com.egovchina.partybuilding.common.util.ExcelUtil;
import com.egovchina.partybuilding.common.util.FileUploadUtil;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.partybuild.dto.*;
import com.egovchina.partybuilding.partybuild.entity.HistoricalPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.PartyMemberChooseQueryBean;
import com.egovchina.partybuilding.partybuild.entity.SysUserQueryBean;
import com.egovchina.partybuilding.partybuild.service.ExtendedInfoService;
import com.egovchina.partybuilding.partybuild.service.IdentityVerificationFeedbackService;
import com.egovchina.partybuilding.partybuild.service.ImportErrorFileService;
import com.egovchina.partybuilding.partybuild.service.PartyInformationService;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    private PartyInformationService partyInformationService;
    @Autowired
    private ExtendedInfoService extendedInfoService;
    @Autowired
    private IdentityVerificationFeedbackService identityVerificationFeedbackService;
    @Autowired
    private ImportErrorFileService importErrorFileService;

    private final static String fileName = "错误信息";

    @ApiOperation(value = "分页查询党员信息", notes = "分页查询党员信息", httpMethod = "GET")
    @HasPermission(value = {"party_partyInformation", "party_partySoldier", "party_partyRetired", "party_difficultMembers"}, matchType = PermissionMatchType.ANY)
    @GetMapping("/party-members")
    public PageInfo<PartyMemberInformationVO> getPartyMemberList(@Validated @ApiParam("党员信息查询实体") SysUserQueryBean queryBean, Page page) {
        return partyInformationService.getPartyList(queryBean, page);
    }

    @ApiOperation(value = "党员身份核查", notes = "党员身份核查", httpMethod = "GET")
    @HasPermission("party_member_inspect")
    @GetMapping("/party-members/identity-verification")
    public PageInfo<PersonnelVO> partyMemberIdentityVerification(@RequestParam @ApiParam(value = "查询值", required = true) String queryValue,
                                                                 Page page) {
        return partyInformationService.partyIdentityVerification(queryValue, page);
    }

    @ApiOperation(value = "党员身份核查信息反馈接口", notes = "党员身份核查信息反馈接口", httpMethod = "POST")
    @HasPermission("party_member_feedback")
    @PostMapping("/party-members/identity-verification/feedbacks")
    public ReturnEntity partyMemberIdentityVerificationFeedback(@ApiParam("身份核查反馈信息") @RequestBody @Validated IdentityVerificationFeedbackDTO identityVerificationFeedbackDTO) {
        return ReturnUtil.buildReturn(identityVerificationFeedbackService.addIdentityVerificationFeedback(identityVerificationFeedbackDTO));
    }

    @ApiOperation(value = "党员身份核查信息反馈列表", notes = "党员身份核查信息反馈列表", httpMethod = "GET")
    @ApiImplicitParam(value = "党员id", name = "userId", dataType = "long", paramType = "path", required = true)
    @HasPermission("party_member_detail")
    @GetMapping("/party-members/{userId}/identity-verification/feedbacks")
    public PageInfo<IdentityVerificationFeedbackVO> getPartyMemberIdentityVerificationFeedbackList(@PathVariable Long userId,
                                                                                                   Page page) {
        return new PageInfo<>(identityVerificationFeedbackService.selectIdentityVerificationFeedbackVOByUserId(userId, page));
    }

    @ApiOperation(value = "党员身份核查信息反馈详情", notes = "党员身份核查信息反馈详情", httpMethod = "GET")
    @ApiImplicitParam(value = "数据id", name = "id", dataType = "long", paramType = "path", required = true)
    @HasPermission("party_member_detail")
    @GetMapping("/party-members/identity-verification/feedbacks/{id}")
    public IdentityVerificationFeedbackVO getPartyMemberIdentityVerificationFeedbackList(@PathVariable Long id) {
        return identityVerificationFeedbackService.selectIdentityVerificationFeedbackVOById(id);
    }

    @ApiOperation(value = "历史党员列表", notes = "分页查询历史党员信息", httpMethod = "GET")
    @HasPermission("party_partyHistory")
    @GetMapping("/history-members")
    public PageInfo<HistoryPartyVO> getPartyHistoryList(@Validated @ApiParam("历史党员查询实体") HistoricalPartyMemberQueryBean queryBean, Page page) {
        return partyInformationService.getPartyHistoryList(queryBean, page);
    }

    @ApiOperation(value = "查看单个历史党员", notes = "查看单个历史党员", httpMethod = "GET")
    @HasPermission("party_partyHistory_detail")
    @GetMapping("/history-members/{userId}")
    @ApiImplicitParam(name = "userId", value = "用户id", paramType = "path", required = true, dataType = "long")
    public HistoryPartyVO getPartyHistoryVO(@PathVariable Long userId) {
        return extendedInfoService.selectHistoryPartyVO(userId);
    }

    @ApiOperation(value = "根据id获取党员信息附带学历等信息", notes = "根据id获取党员信息附带学历等信息", httpMethod = "GET")
    @ApiImplicitParam(value = "党员id", name = "id", dataType = "long", paramType = "path", required = true)
    @HasPermission("party_member_detail")
    @GetMapping("/party-members/{id}")
    public PartyMemberVO getParty(@PathVariable Long id) {
        return extendedInfoService.selectPartyMemberDetailsById(id);
    }

    @ApiOperation(value = "根据id获取组织信息列表里面的书记的简单信息", notes = "根据id获取组织信息列表里面的书记的简单信息", httpMethod = "GET")
    @ApiImplicitParam(value = "党员id", name = "id", dataType = "long", paramType = "path", required = true)
    @HasPermission("party_leadershipTeam")
    @GetMapping("/party-members/{id}/secretaries")
    public SecretariesPartyMemberVO getSecretariesParty(@PathVariable Long id) {
        return extendedInfoService.selectSecretariesPartyMemberVO(id);
    }

    @ApiOperation(value = "党员选择列表", notes = "根据条件获取党员列表", httpMethod = "GET")
    @HasPermission(value = {"party_partyInformation", "party_partySoldier", "party_partyRetired", "party_difficultMembers", "party_member_add", "party_partyFlow",
            "party_difficult", "resident_assistance_work", "party_teamSecretary", "party_leadershipTeam_members", "party_leadershipTeam", "party_goAbroad", "party_rewards",
            "party_partyReport", "party_PrivilegeUsers", "party_redList", "party_partyHistory", "party_partyHistory_add"})
    @GetMapping("/party-members/choose")
    public PageInfo<PartyMemberChooseVO> choosePartyMembers(PartyMemberChooseQueryBean queryBean, Page page) {
        return partyInformationService.selectPartyMemberChooseVOListByQueryBean(queryBean, page);
    }

    @ApiOperation(value = "根据id删除user信息")
    @HasPermission(value = {"party_partyHistory_add", "party_member_delete"}, matchType = PermissionMatchType.ANY)
    @PostMapping("/history-members")
    public ReturnEntity deleteUser(@RequestBody @Validated @ApiParam("删除党员信息实体") DeletePartyMemberDTO deletePartyMemberDTO) {
        return ReturnUtil.buildReturn(extendedInfoService.invalidByUserId(deletePartyMemberDTO));
    }

    @ApiOperation(value = "根据id删除历史user信息")
    @HasPermission("TODO")
    @DeleteMapping("/history-members/{userId}")
    public ReturnEntity deleteHistoryUser(@PathVariable Long userId) {
        return ReturnUtil.buildReturn(extendedInfoService.deleteByUserId(userId));
    }

    @ApiOperation(value = "历史党员修改")
    @HasPermission("TODO")
    @PutMapping("/history-members")
    public ReturnEntity upadteUser(@RequestBody @Validated @ApiParam("修改历史党员信息实体") UpdateHistoryDTO updateHistoryDTO) {
        return ReturnUtil.buildReturn(extendedInfoService.updateHistoryParty(updateHistoryDTO));
    }

    @ApiOperation(value = "根据id恢复党员信息")
    @HasPermission("party_partyHistory_recovery")
    @GetMapping("/history-members/{reduceId}/restorations")
    @ApiImplicitParam(name = "reduceId", value = "减少用户id", paramType = "path", required = true, dataType = "long")
    public ReturnEntity restoreUser(@PathVariable Long reduceId) {
        return ReturnUtil.buildReturn(extendedInfoService.restoreUser(reduceId, null));
    }

    @ApiOperation(value = "根据社区名字模糊获取社区")
    @HasPermission({"party_member_edit", "party_orgInfo_edit", "party_orgInfo_add"})
    @GetMapping("/communities")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query"),
            @ApiImplicitParam(name = "label", value = "社区名字", paramType = "query")
    })
    public PageInfo<CommunityVO> getCommunity(@ApiParam("社区模糊查询实体") CommunityDTO communityDTO, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(partyInformationService.selectCommunityVO(communityDTO));
    }

    @ApiOperation(value = "补录党员信息", notes = "补录党员信息", httpMethod = "POST")
    @HasPermission(value = "party_member_add", matchType = PermissionMatchType.ANY)
    @PostMapping("/party-members")
    public ReturnEntity insetParty(@RequestBody @Validated @ApiParam("党员基本信息") PartyInfoDTO partyInfoDTO) {
        return ReturnUtil.buildReturn(partyInformationService.savePartyInfo(partyInfoDTO));
    }

    @ApiOperation(value = "更新党员信息", notes = "更新党员信息", httpMethod = "PUT")
    @HasPermission(value = {"party_member_edit", "party_member_usertag"}, matchType = PermissionMatchType.ANY)
    @PutMapping("/party-members")
    public ReturnEntity updateParty(@RequestBody @Validated @ApiParam("党员基本信息") PartyInfoDTO partyInfoDTO) {
        return ReturnUtil.buildReturn(partyInformationService.updatePartyInfo(partyInfoDTO));
    }

    @ApiOperation(value = "根据身份证查询党员", notes = "根据身份证查询党员", httpMethod = "GET")
    @ApiImplicitParam(name = "idCardNo", value = "身份证", paramType = "path")
    @HasPermission("party_partyInformation")
    @GetMapping("/party-members/choose/{idCardNo}")
    public PartyMemberChooseVO choosePartyMemberVOByIdCardNo(@PathVariable String idCardNo) {
        return partyInformationService.choosePartyMemberVOByIdCardNo(idCardNo);
    }

    @ApiOperation(value = "党员概况", notes = "党员概况", httpMethod = "GET")
    @ApiImplicitParam(value = "党员id", name = "id", dataType = "long", paramType = "path", required = true)
    @HasPermission("party_member_detail")
    @GetMapping("/party-members/detail/{id}")
    public PartyMemberDetailVO getPartyDetail(@PathVariable Long id) {
        return extendedInfoService.selectPartyDetailById(id);
    }

    @ApiOperation(value = "党员历史信息图", notes = "党员历史信息图", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgnizeLife", value = "组织生活", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "communityActivity", value = "社区活动", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "partyMemberComment", value = "党员评议", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "partyMemberShip", value = "党籍信息", dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", paramType = "query", required = true)
    })
    @HasPermission("party_member_detail")
    @GetMapping("/party-members/history-information-graph")
    public PageInfo<HistoryInformationGraphVO> getHistoryInformationGraph(@ApiParam("分页参数") Page page, Boolean orgnizeLife, Boolean communityActivity, Boolean partyMemberComment, Boolean partyMemberShip, Long userId) {
        return new PageInfo<>(partyInformationService.getHistoryInformationGraph(page, orgnizeLife, communityActivity, partyMemberComment, partyMemberShip, userId));
    }

    @ApiOperation(value = "党员工作信息", notes = "党员工作信息", httpMethod = "GET")
    @ApiImplicitParam(value = "党员id", name = "userId", dataType = "long", paramType = "path", required = true)
    @HasPermission("party_member_detail")
    @GetMapping("/party-members/works/{userId}")
    public PageInfo<PartyWorkVO> getPartyWorkVO(@PathVariable Long userId, @ApiParam("分页参数") Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(partyInformationService.getParyWorkVO(userId));
    }

    @ApiOperation(value = "导入党员信息数据", notes = "导入党员信息数据", httpMethod = "POST")
    @HasPermission("party_member_import")
    @PostMapping("/party-members/import")
    public FileUploadResultVO importExcel(@RequestPart @ApiParam(value = "要导入文件", required = true) MultipartFile file,
                                          HttpServletResponse response) throws IOException {
        FileUploadResultVO fileUploadResultVO = new FileUploadResultVO();
        partyInformationService.excelImport(file.getInputStream());
        //错误文件下载
        HSSFWorkbook errorWb = ExcelUtil.IMPORT_WB_HOLDER.get();
        if (errorWb != null) {
            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                errorWb.write(os);
                byte[] bytes = os.toByteArray();
                String spliceFileName = fileName + "_" + file.getOriginalFilename();
                String fileAccessUrl = FileUploadUtil.postFile(bytes, FilenameUtils.getExtension(file.getOriginalFilename()), spliceFileName);
                //补充错误导入信息
                ImportErrorFileDTO tabPbImportErrorFile = new ImportErrorFileDTO();
                //类型1党员导入
                tabPbImportErrorFile.setBusinessType((byte) 1);
                tabPbImportErrorFile.setFilename(spliceFileName);
                tabPbImportErrorFile.setFileAccessUrl(fileAccessUrl);
                //添加错误导入信息
                importErrorFileService.insertImportErrorFile(tabPbImportErrorFile);
                fileUploadResultVO.setComplete(false);
                fileUploadResultVO.setErrorFileAccessUrl(fileAccessUrl);
                return fileUploadResultVO;
            }
        }
        fileUploadResultVO.setComplete(true);
        fileUploadResultVO.setErrorFileAccessUrl(null);
        return fileUploadResultVO;
    }

    @ApiOperation(value = "党员信息导入模板下载", notes = "党员信息导入模板下载", httpMethod = "GET")
    @HasPermission("party_member_import")
    @GetMapping("/party-members/template/download")
    public void excelTemplateDownload(HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = partyInformationService.excelTemplateStream();
        ExcelUtil.setResponseStream(response, "党员导入模板.xls");
        wb.write(response.getOutputStream());

    }
}
