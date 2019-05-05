package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.OrgRange;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.*;
import com.egovchina.partybuilding.partybuild.entity.TabPbActivities;
import com.egovchina.partybuilding.partybuild.entity.TabPbParticipant;
import com.egovchina.partybuilding.partybuild.service.PartyOrganizationActivitiesService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @Author Jiang An
 **/
@Deprecated
@Api(tags = "党组织活动-活动模块-蒋安")
@RestController
@RequestMapping("/Party/")
public class PartyOrganizationActivitiesController {

    @Autowired
    private PartyOrganizationActivitiesService activitiesService;

    @Deprecated
    @ApiOperation(value = "新增活动", notes = "新增活动", httpMethod = "POST")
    @PostMapping("/add")
    public ReturnEntity add(@ApiParam(value = "活动实体") @RequestBody @Validated PartyOrganizationActivitiesDto activitiesDto) {
        validateActivitiesData(activitiesDto);
        int retVal = activitiesService.insertSelective(activitiesDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "修改活动", notes = "修改活动", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity updateById(@ApiParam(value = "活动实体") @RequestBody @Validated PartyOrganizationActivitiesDto activitiesDto) {
        validateActivitiesData(activitiesDto);
        int retVal = activitiesService.updateWithAboutInfo(activitiesDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "查看活动详情", notes = "查看活动详情", httpMethod = "GET")
    @GetMapping("/findById")
    public PartyOrganizationActivitiesDto findById(@RequestParam @ApiParam(value = "活动ID") Long activitiesId,
                                                   @RequestParam @ApiParam(value = "活动类型") Long activitiesType) {
        return activitiesService.findDetails(activitiesId, activitiesType);
    }

    @Deprecated
    @ApiOperation(value = "删除活动", notes = "删除活动", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam @ApiParam(value = "活动ID", required = true) Long activitiesId) {
        int retVal = activitiesService.delete(activitiesId);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "活动列表", notes = "活动列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subject", value = "活动标题", paramType = "query"),
            @ApiImplicitParam(name = "activitiesType", value = "主题类型", paramType = "query", required = true),
            @ApiImplicitParam(name = "startTime", value = "开始时间", paramType = "query"),
            @ApiImplicitParam(name = "finishedTime", value = "结束时间", paramType = "query"),
            @ApiImplicitParam(name = "reportToType", value = "上报类型", paramType = "query"),
            @ApiImplicitParam(name = "auditMsgUpInfo", value = "审核情况", paramType = "query")
    })
    @GetMapping("/findByList")
    public PageInfo<PartyOrganizationActivitiesDto> findByList(@ApiIgnore PartyOrganizationActivitiesDto activitiesDto,
                                                               Page page, OrgRange orgRange) {
        Map<String, Object> conditions = orgRange.toMap();
        conditions.put("orgName", activitiesDto.getOrgName());
        conditions.put("subject", activitiesDto.getSubject());
        conditions.put("reportToType", activitiesDto.getReportToType());
        if (activitiesDto.getActivitiesType() != null) {
            conditions.put("activitiesType", activitiesDto.getActivitiesType());
        }
        conditions.put("auditResult", activitiesDto.getAuditResult());
        conditions.put("delFlag", 0);
        conditions.put("startTime", activitiesDto.getStartTime());
        conditions.put("finishedTime", activitiesDto.getFinishedTime());
        List<PartyOrganizationActivitiesDto> list = activitiesService.selectWithConditions(conditions, page);
        return new PageInfo<>(list);
    }

    @Deprecated
    @ApiOperation(value = "人员参加活动列表", notes = "人员参加活动列表", httpMethod = "GET")
    @GetMapping("/list/{userId}")
    public PageInfo<PartyOrganizationActivitiesDto> listByUserId(@PathVariable("userId") @ApiParam(value = "用户ID", required = true) Long userId,
                                                                 Page page) {
        List<PartyOrganizationActivitiesDto> list = activitiesService.selectListByUserId(userId, page);
        return new PageInfo<>(list);
    }

    @Deprecated
    @ApiOperation(value = "二维码签到", notes = "二维码签到-蒋安", httpMethod = "GET")
    @GetMapping(value = "/sign")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activitiesId", value = "组织活动Id", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证号码", paramType = "query")
    })
    public ReturnEntity signByQRCode(@RequestParam @ApiParam(name = "activitiesId", value = "组织生活Id") Long activitiesId,
                                     @RequestParam(defaultValue = "430402194704262010") @ApiParam(name = "idCardNo", value = "身份证号码") String idCardNo) {
        int retVal = activitiesService.signByQRCode(activitiesId, idCardNo);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "活动参加侯选人列表", notes = "活动参加侯选人列表", httpMethod = "GET")
    @GetMapping("/candidateMemberList")
    public List<Personnel> candidateMemberList(@RequestParam @ApiParam(value = "组织ID", required = true) Long orgId,
                                               @RequestParam(required = false) @ApiParam(value = "结对组织ID") Long pairOrgId,
                                               @RequestParam(required = false) @ApiParam(value = "已选用户ID") String userIds) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("orgId", orgId);
        conditions.put("pairOrgId", pairOrgId);
        conditions.put("exclusion", "1");
        conditions.put("userTag", "59388,59389"); //年老体弱者、经常外出者
        conditions.put("userIds", userIds);
        return activitiesService.selectCandidateMemberList(conditions);
    }

    @Deprecated
    @ApiOperation(value = "活动参加人员排除列表", notes = "查询默认排除掉的人员  如（年老体弱者、经常外出者）", httpMethod = "GET")
    @GetMapping("/exclusionMemberList")
    public ActivityExclusionParticipantDto exclusionMemberList(@RequestParam @ApiParam(value = "组织ID", required = true) Long orgId,
                                                               @RequestParam(required = false) @ApiParam(value = "结对组织ID") Long pairOrgId) {
        return activitiesService.selectExclusionMemberList(orgId, pairOrgId);
    }

    @Deprecated
    @ApiOperation(value = "联席会活动参加侯选人列表", notes = "联席会活动参加侯选人列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "联席组织ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证号", paramType = "query"),
            @ApiImplicitParam(name = "activitiesId", value = "活动ID", paramType = "query"),
            @ApiImplicitParam(name = "exclude", value = "是否排除已有用户", paramType = "query")
    })
    @GetMapping("/candidateUsers")
    public PageInfo<JointMeetUsersDto> candidateUsers(Long orgId, String userName, String idCardNo, Long activitiesId,
                                                      Long exclude, Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("orgId", orgId);
        conditions.put("userName", userName);
        conditions.put("idCardNo", idCardNo);
        conditions.put("activitiesId", activitiesId);
        conditions.put("exclude", exclude);
        List<JointMeetUsersDto> list = activitiesService.selectJoinMeetActivitiesPreUserListWithConditions(conditions, page);
        PageInfo<JointMeetUsersDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Deprecated
    @ApiOperation(value = "新增结对共建", notes = "新增结对共建", httpMethod = "POST")
    @PostMapping(value = "/pairing/insert")
    public ReturnEntity pairingInsert(@ApiParam(value = "结对共建实体") @RequestBody @Validated PartyOrganizationActivitiesDto activitiesDto) {
        validatePairingData(activitiesDto);
        int retVal = activitiesService.insertPairWithAbout(activitiesDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "修改结对共建", notes = "修改结对共建", httpMethod = "POST")
    @PostMapping("/pairing/update")
    public ReturnEntity pairingUpdate(@ApiParam(value = "结对共建实体") @RequestBody @Validated PartyOrganizationActivitiesDto activitiesDto) {
        validatePairingData(activitiesDto);
        int retVal = activitiesService.updatePairWithAbout(activitiesDto);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "结对共建列表", notes = "结对共建列表", httpMethod = "GET")
    @GetMapping("/pairing/list")
    public PageInfo<TabPbActivitiesDto> pairingList(Page page, OrgRange orgRange) {
        HashMap<String, Object> conditions = orgRange.toMap();
        conditions.put("delFlag", "0");
        conditions.put("activitiesType", "59148");
        List<TabPbActivitiesDto> list = activitiesService.selectPairingListWithConditions(conditions, page);
        return new PageInfo<>(list);
    }

    @Deprecated
    @ApiOperation(value = "查看结对共建详情", notes = "查看结对共建详情", httpMethod = "GET")
    @GetMapping("/pairing/detail")
    public PartyOrganizationActivitiesDto pairingDetail(@RequestParam @ApiParam(value = "数据ID") Long activitiesId) {
        return activitiesService.selectPairingDetail(activitiesId);
    }

    @Deprecated
    @ApiOperation(value = "活动审核/重审", notes = "活动审核/重审", httpMethod = "POST")
    @PostMapping("/review")
    public ReturnEntity review(@RequestParam @ApiParam(value = "活动ID", required = true) Long activitiesId,
                               @RequestParam @ApiParam(value = "审核结果 dict SHJG", required = true) Long auditResult,
                               @RequestParam(required = false) @ApiParam(value = "审核说明") String auditComment) {
        TabPbActivities dbTabPbActivities = activitiesService.selectOneById(activitiesId);
        if (dbTabPbActivities == null) {
            throw new BusinessDataNotFoundException("活动数据异常");
        }
        dbTabPbActivities.setAuditResult(auditResult);
        dbTabPbActivities.setAuditComment(auditComment);
        int retVal = activitiesService.review(dbTabPbActivities);
        return ReturnUtil.buildReturn(retVal);
    }


    /**
     * 查看签到详情
     *
     * @param
     */
    @Deprecated
    @ApiOperation(value = "签到情况列表", notes = "签到情况详情", httpMethod = "GET")
    @GetMapping("/checkInList")
    public PageInfo<PartyOrganizationActivitiesDto> checkInList(@RequestParam @ApiParam(value = "活动ID", required = true) Long activitiesId,
                                                                @RequestParam(required = false) @ApiParam(value = "签到状态 1 已签到； 2 未签到") Long signType,
                                                                Page page) {
        List<PartyOrganizationActivitiesDto> list = activitiesService.selectCheckInList(activitiesId, signType, page);
        PageInfo<PartyOrganizationActivitiesDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Deprecated
    @ApiOperation(value = "签到变更", notes = "已签到会取消签到记录，未签到的会将当前时间作为签到时间", httpMethod = "GET")
    @GetMapping("/updateSignIn")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "activitiesId", value = "活动Id", paramType = "query", required = true),
            @ApiImplicitParam(name = "participantId", value = "人员Id", paramType = "query", required = true)
    })
    public ReturnEntity updateSignIn(@RequestParam Long activitiesId, @RequestParam Long participantId) {
        int retVal = activitiesService.updateSignIn(activitiesId, participantId);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 结对共建数据验证
     *
     * @param activitiesDto
     */
    private void validatePairingData(PartyOrganizationActivitiesDto activitiesDto) {
        Long pairOrgId = activitiesDto.getPairOrgId();
        if (pairOrgId == null || pairOrgId == 0) {
            throw new BusinessDataIncompleteException("结对组织不能为空");
        }
        validateActivitiesData(activitiesDto);
    }

    /**
     * 验证活动数据有效性
     *
     * @param activitiesDto
     */
    private void validateActivitiesData(PartyOrganizationActivitiesDto activitiesDto) {
        List<TabPbParticipant> tabPbParticipantList = activitiesDto.getTabPbParticipant();
        List<TabPbParticipant> collect = tabPbParticipantList.stream()
                .filter(participant -> participant.getIfModerator() == 1).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(collect)) {
            throw new BusinessDataCheckFailException("主持人不能为空");
        }

        //新增修改通用处理
        if (UserContextHolder.getOrgId().equals(activitiesDto.getOrgId())) {
            activitiesDto.setReportToType(2L); //本级办理
        } else {
            activitiesDto.setReportToType(1L); //上级代办
        }
        //应到人数
        activitiesDto.setDueCount((long) tabPbParticipantList.size());
        //实到人数
        long factCount = tabPbParticipantList.stream()
                .filter(tabPbParticipant -> tabPbParticipant.getAbsentReason() == null).count();
        activitiesDto.setFactCount(factCount);
    }

    @Deprecated
    @ApiOperation(value = "置顶功能", notes = "置顶功能", httpMethod = "POST")
    @PostMapping("/stick")
    public ReturnEntity stick(Long activitiesId) {
        int retVal = activitiesService.stick(activitiesId);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "取消置顶功能", notes = "取消置顶功能", httpMethod = "POST")
    @PostMapping("/deleteStick")
    public ReturnEntity deleteStick(Long activitiesId) {
        int retVal = activitiesService.deleteStick(activitiesId);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "签到接口加人",notes = "签到接口加人",httpMethod = "GET")
    @GetMapping("/addSignIn")
    public ReturnEntity addSignIn(@RequestParam @ApiParam(name = "activitiesId", value = "组织生活Id") Long activitiesId,
                                  @RequestParam(defaultValue = "430402194704262010") @ApiParam(name = "idCardNo", value = "身份证号码") String idCardNo){
           int retVal=activitiesService.addSignIn(activitiesId,idCardNo);
           return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @GetMapping(value = "/community")
    @ApiOperation(value = "获取社区活动信息列表", notes = "可指定条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "活动名称", name = "subject", dataType = "String"),
            @ApiImplicitParam(value = "党员名称", name = "username", dataType = "String", paramType = "query"),
            @ApiImplicitParam(value = "活动类型", name = "activitiesType", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(value = "是否直属组织 0直属，1报到", name = "deptState", paramType = "query")
    })
    public PageInfo<TabPbActivitiesDto> list(String subject, String username, Long activitiesType,
                                             Long deptState, @ApiParam Page page, OrgRange orgRange) {
        TabPbActivitiesDto tabPbActivitiesDto = new TabPbActivitiesDto();
        tabPbActivitiesDto.setSubject(subject);
        tabPbActivitiesDto.setUsername(username);
        tabPbActivitiesDto.setActivitiesType(activitiesType);
        tabPbActivitiesDto.setRangeDeptId(orgRange.getRangeDeptId());
        tabPbActivitiesDto.setOrgRange(orgRange.getOrgRange());
        tabPbActivitiesDto.setDeptState(deptState);
        return new PageInfo<>(this.activitiesService.ActivitiesDtoList(tabPbActivitiesDto, page));
    }
}
