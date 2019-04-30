package com.egovchina.partybuilding.partybuild.controller;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.exception.BusinessException;
import com.egovchina.partybuilding.common.util.ObjectUtil;
import com.egovchina.partybuilding.common.util.ReturnEntity;
import com.egovchina.partybuilding.common.util.ReturnUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.AssessUserDto;
import com.egovchina.partybuilding.partybuild.dto.TabPbActivitiesAssessDto;
import com.egovchina.partybuilding.partybuild.entity.TabPbActivitiesAssess;
import com.egovchina.partybuilding.partybuild.entity.TabPbActivitiesAssessVerify;
import com.egovchina.partybuilding.partybuild.service.TabPbActivitiesAssessService;
import com.egovchina.partybuilding.partybuild.service.TabPbActivitiesAssessVerifyService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 党员民主评议控制器
 *
 * @Author Zhang Fan
 **/
@Deprecated
@Api(tags = "党组织活动-民主评议模块 蒋安")
@RestController
@RequestMapping("/activitiesAccess")
public class TabPbActivitiesAssessController {

    @Autowired
    private TabPbActivitiesAssessService tabPbActivitiesAssessService;
    @Autowired
    private TabPbActivitiesAssessVerifyService tabPbActivitiesAssessVerifyService;

    @Deprecated
    @ApiOperation(value = "评议列表", notes = "评议列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织", paramType = "query"),
            @ApiImplicitParam(name = "rangeDeptId", value = "组织ID", paramType = "query"),
            @ApiImplicitParam(name = "year", value = "评议年度", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "党员姓名", paramType = "query"),
            @ApiImplicitParam(name = "auditMsgUpInfo", value = "审核结果 dict SHJG  + 1 未审核；2 已审核；全部不传", paramType = "query"),
            @ApiImplicitParam(name = "assessResult", value = "评议结果 dict ZS_PYJG YB_PYJG", paramType = "query"),
            @ApiImplicitParam(name = "beginCreateTime", value = "上报时间起始 yyyy-MM-dd", paramType = "query"),
            @ApiImplicitParam(name = "endCreateTime", value = "上报时间截至 yyyy-MM-dd", paramType = "query")
    })
    @GetMapping("/list")
    public PageInfo<TabPbActivitiesAssessDto> list(String orgRange, Integer rangeDeptId, String year, String username,
                                                   String auditResult, String assessResult, String beginCreateTime, String endCreateTime,
                                                   Page page) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("orgRange", orgRange);
        if (rangeDeptId == null || rangeDeptId == 0) {
            rangeDeptId = UserContextHolder.currentUser().getDeptId(); //当前用户的组织ID
        }
        conditions.put("rangeDeptId", rangeDeptId);
        conditions.put("username", username);
        conditions.put("auditResult", auditResult);
        conditions.put("assessResult", assessResult);
        conditions.put("beginCreateTime", beginCreateTime);
        conditions.put("endCreateTime", endCreateTime);
        conditions.put("years", year);
        conditions.put("delFlag", "0");
        List<TabPbActivitiesAssessDto> list = tabPbActivitiesAssessService.selectWithConditions(conditions, page);
        PageInfo<TabPbActivitiesAssessDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Deprecated
    @ApiOperation(value = "新增评议", notes = "新增评议", httpMethod = "POST")
    @PostMapping("/insert")
    public ReturnEntity insert(@ApiParam(value = "评议实体") @RequestBody @Validated TabPbActivitiesAssessDto TabPbActivitiesAssess) {
        operationalDataVerification(TabPbActivitiesAssess);
        int retVal = tabPbActivitiesAssessService.insertWithAbout(TabPbActivitiesAssess);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "评议数据修改", notes = "评议数据修改", httpMethod = "POST")
    @PostMapping("/update")
    public ReturnEntity update(@ApiParam(value = "评议实体") @RequestBody @Validated TabPbActivitiesAssess TabPbActivitiesAssess) {
        operationalDataVerification(TabPbActivitiesAssess);
        int retVal = tabPbActivitiesAssessService.updateWithAbout(TabPbActivitiesAssess);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "评议数据详情", notes = "评议数据详情", httpMethod = "GET")
    @GetMapping("/details")
    public TabPbActivitiesAssessDto details(@RequestParam() @ApiParam(name = "id", value = "数据ID", required = true) Long id) {
        return tabPbActivitiesAssessService.detailWithAbout(id);
    }

    @Deprecated
    @ApiOperation(value = "评议数据删除", notes = "评议数据删除", httpMethod = "GET")
    @GetMapping("/delete")
    public ReturnEntity delete(@RequestParam() @ApiParam(name = "id", value = "数据ID", required = true) Long id) {
        //逻辑删除
        int retVal = tabPbActivitiesAssessService.logicDeleteById(id);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "评议审核", notes = "评议审核", httpMethod = "POST")
    @PostMapping("/review")
    public ReturnEntity review(@ApiParam(value = "评议审核实体") @RequestBody @Validated TabPbActivitiesAssessVerify tabPbActivitiesAssessVerify) {
        int retVal = tabPbActivitiesAssessVerifyService.overwriteInsertSelective(tabPbActivitiesAssessVerify);
        return ReturnUtil.buildReturn(retVal);
    }

    @Deprecated
    @ApiOperation(value = "评议党员列表", notes = "根据组织ID查询待评议人员姓名及ID", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deptId", value = "组织ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "year", value = "评议年度", required = true, paramType = "query")
    })
    @GetMapping("/preAssessMemberList")
    public HashMap<String, List<AssessUserDto>> memberList(@RequestParam Long deptId, @RequestParam Long year) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("deptId", deptId);
        if (year < 1970 || year > 9999) {
            throw new BusinessDataInvalidException("无效的年份");
        }
        conditions.put("year", year);
        return tabPbActivitiesAssessService.preAssessMemberList(conditions);
    }

    @Deprecated
    @ApiOperation(value = "评议活动签到", notes = "评议活动签到", httpMethod = "GET")
    @GetMapping("/signIn")
    public ReturnEntity signIn(@RequestParam() @ApiParam(name = "id", value = "数据ID", required = true) Long id) {
        int retVal = tabPbActivitiesAssessService.signInById(id);
        return ReturnUtil.buildReturn(retVal);
    }

    /**
     * 评议数据校验
     *
     * @param tabPbActivitiesAssess
     */
    private void operationalDataVerification(TabPbActivitiesAssess tabPbActivitiesAssess) {
        Integer year = tabPbActivitiesAssess.getYears();
        Long id = tabPbActivitiesAssess.getId();
        //修改
        if (id != null && id != 0) {
            TabPbActivitiesAssess dbTabPbActivitiesAssess =
                    tabPbActivitiesAssessService.selectByUserIdAndYear(tabPbActivitiesAssess.getUserId(),
                            year);

            if (dbTabPbActivitiesAssess != null) {
                Long dbId = dbTabPbActivitiesAssess.getId();
                if (id == null || !id.equals(dbId)) {
                    throw new BusinessException(String.format("党员%s,%s年度评议已存在", tabPbActivitiesAssess.getUsername(), year));
                }
            }
        } else { //新增
            if (tabPbActivitiesAssess instanceof TabPbActivitiesAssessDto) {
                TabPbActivitiesAssessDto activitiesAssessDto = (TabPbActivitiesAssessDto) tabPbActivitiesAssess;
                if (ObjectUtil.allNull(activitiesAssessDto.getFormalUsers(), activitiesAssessDto.getPreUsers())) {
                    throw new BusinessDataCheckFailException("评议党员不能为空");
                }
            }
        }

    }
}
