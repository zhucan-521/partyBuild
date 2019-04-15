package com.egovchina.partybuilding.partybuild.controller;


import com.alibaba.fastjson.JSONObject;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.UserLoginDto;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.CommunityDto;
import com.egovchina.partybuilding.partybuild.dto.SysUseDto;
import com.egovchina.partybuilding.partybuild.dto.SysUserCountDto;
import com.egovchina.partybuilding.partybuild.entity.OrganizationPeopleStatistics;
import com.egovchina.partybuilding.partybuild.entity.TabPbMemberReduceList;
import com.egovchina.partybuilding.partybuild.service.ExtendedInfoService;
import com.egovchina.partybuilding.partybuild.service.PartyInformationService;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 党员信息
 *
 * @Author zhu can
 **/
@Api(tags = "党员-党员信息模块")
@RestController
@RequestMapping("/party/")
public class PartyInformationController {

    @Autowired
    PartyInformationService partyInformationService;
    @Autowired
    private ExtendedInfoService extendedInfoService;


    /**
     * 党员信息补录
     *
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "党员信息补录", notes = "党员信息补录", httpMethod = "POST")
    @PostMapping("/insert")
    public int insert(@RequestBody SysUser sysUser) {
        int id = partyInformationService.insert(sysUser);
        return id;
    }

    /**
     * 党员信息修改
     *
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "根据id修改党员信息修改", notes = "党员信息修改", httpMethod = "POST")
    @PostMapping("/updateSysUser")
    public int updateSysUser(@RequestBody SysUser sysUser) {
        return partyInformationService.updateSysUser(sysUser);
    }


    /**
     * 新增或修改扩展信息
     *
     * @param sysUser 用戶信息
     * @return 修改條數
     */
    @ApiOperation(value = "新增或修改扩展信息", notes = "新增或修改扩展信息", httpMethod = "PUT")
    @PutMapping("/updateExtended")
    public int updateExtended(@RequestBody SysUser sysUser) {
        return extendedInfoService.updateByPrimaryKey(sysUser);
    }

    /**
     * 分页查询党员信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @ApiOperation(value = "分页查询党员信息", notes = "分页查询党员信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "username", value = "用户名/姓名", paramType = "query"),
            @ApiImplicitParam(name = "deptId", value = "组织ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "identityType", value = "人员类别", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证号码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gender", value = "性别", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "nation", value = "民族,多个逗号隔开", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "education", value = "学历,多个逗号隔开", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isTaiwaner", value = "是否台湾籍", dataType = "Byte", paramType = "query"),
            @ApiImplicitParam(name = "flowStatus", value = "是否正在流动/流动状态", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "registryStatus", value = "党员状态(1正常 5死亡 3出党 4停止党籍)", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "unitType", value = "单位类型(码表值DWLB)", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "maritalStatus", value = "婚姻状况,多个逗号隔开", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "isLlost", value = "是否失联", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "isPoor", value = "是否困难", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "joinTimeBegin", value = "入党时间开始(例：2018-08-08)", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "joinTimeEnd", value = "入党时间结束(例：2018-08-08)", dataType = "Date", paramType = "query"),
            @ApiImplicitParam(name = "ageBegin", value = "开始年龄", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "ageEnd", value = "结束年龄", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；" +
                    "不传查本级；3和不传查本级。（所有前提都是有deptid的情况，没有ddeptid就没有党组织筛选）", paramType = "query"),
    })
    @GetMapping("/partyMemberPage")
    public PageInfo<SysUser> partyMemberPage(@ApiIgnore @RequestParam Map<String, Object> params) {
        return partyInformationService.selectPage(params);
    }

    @ApiOperation(value = "党员身份核查", notes = "党员身份核查", httpMethod = "GET")
    @GetMapping("/partyIdentityVerification")
    public PageInfo<SysUser> partyIdentityVerification(@RequestParam(required = false) @ApiParam(value = "姓名") String username,
                                             @RequestParam(required = false) @ApiParam(value = "身份证") String idCardNo,
                                             @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                             Page page) {
        if (StringUtils.isAllBlank(username, idCardNo, phone)) {
            throw new BusinessDataIncompleteException("查询条件不能为空");
        }
        List<SysUser> list = partyInformationService.partyIdentityVerification(username, idCardNo, phone, page);
        return new PageInfo<>(list);
    }

    /**
     * 分页查询【历史党员】信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @ApiOperation(value = "分页查询【历史党员】信息", notes = "分页查询【历史党员】信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "limit", value = "每页条数", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "deptId", value = "组织ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "realName", value = "用户名/姓名", paramType = "query"),
            @ApiImplicitParam(name = "outType", value = "历史原因：出党3；停止党籍4；死亡5；其他6", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "orgRange", value = "列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；不传查本级", paramType = "query"),
    })
    @GetMapping("/historyPartyPage")
    public PageInfo<SysUser> historyPartyPage(@ApiIgnore @RequestParam Map<String, Object> params) {
        return partyInformationService.historyPartyPage(params);
    }

    @ApiOperation(value = "根据id获取党员信息", notes = "根据id获取党员信息", httpMethod = "GET")
    @GetMapping("/pertyById/{id}")
    public SysUser getPertyById(@ApiParam(value = "党员ID", required = true) @PathVariable Long id) {
        return extendedInfoService.selectByPrimaryKey(id);
    }


    @ApiOperation(value = "根据id获取党员信息,修改时用", notes = "根据id获取党员信息", httpMethod = "GET")
    @GetMapping("/getSysUserById/{id}")
    public SysUseDto getSysUserById(@ApiParam(value = "党员ID", required = true) @PathVariable Long id) {
        return partyInformationService.getSysUserById(id.intValue());
    }


    @ApiOperation(value = "根据身份证号码或者姓名获取人员信息", notes = "根据身份证号码或者姓名获取人员信息", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "姓名", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "idCardNo", value = "身份证号码", paramType = "query", dataType = "String")
    })
    @GetMapping("/getPartyByIdCardNoOrUserName")
    public PageInfo<SysUser> getPartyByIdCardNoOrUserName(@ApiIgnore SysUser sysUser, Page page) {
        return extendedInfoService.selectPartyByIdCardNoOrUserName(sysUser,page);
    }

    @ApiOperation(value = "根据id删除 user信息")
    @DeleteMapping("/deleteUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "outType", value = "减少方式：出党3；停止党籍4；死亡5；其他6", paramType = "query", dataType = "Long"),
            @ApiImplicitParam(name = "reduceTime", value = "离开党组织日期", paramType = "query", dataType = "Date"),
            @ApiImplicitParam(name = "quitType",value = "出党方式",paramType = "query",dataType = "Long")
    })
    public Boolean deleteUser(@ApiIgnore @RequestParam Map<String, Object> params) {
        TabPbMemberReduceList reduce = JSONObject.parseObject(JSONObject.toJSONString(params), TabPbMemberReduceList.class);
        return extendedInfoService.updateByUserId(reduce);
    }

    @ApiOperation(value = "根据id恢复党员信息")
    @GetMapping("/restoreUser")
    @ApiImplicitParam(name = "userId", value = "用户id",paramType = "query",required = true,dataType = "long")
    public Boolean restoreUser(Long userId) {
        return extendedInfoService.restoreUser(userId);
    }

    @ApiOperation(value = "根据社区名字模糊获取社区")
    @GetMapping("/selectCommuntiy")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", paramType = "query"),
            @ApiImplicitParam(name = "label", value = "社区名字", paramType = "query")
    })
    public List<CommunityDto> selectCommunity(@ApiIgnore CommunityDto dto) {
        return partyInformationService.selectCommunityDto(dto);
    }

    @ApiOperation(value = "获取任务面板党员统计数据", notes = "获取任务面板党员统计数据", httpMethod = "GET")
    @GetMapping("/getTaskPartyCount/{deptId}")
    public SysUserCountDto getTaskPartyCount(@ApiParam(value = "党组织id", required = true) @PathVariable Long deptId) {
        return partyInformationService.getTaskPartyCount(deptId);
    }

    @ApiOperation(value = "获取登录用户的信息")
    @GetMapping("/getLoginUser")
    public SysUser getUser() {
        UserLoginDto userLoginDto = UserContextHolder.currentUser();
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userLoginDto.getUserId());
        sysUser.setDeptId(userLoginDto.getDeptId());
        sysUser.setPhone(userLoginDto.getPhone());
        sysUser.setUsername(userLoginDto.getRealname());
        sysUser.setAvatar(userLoginDto.getAvatar());
        sysUser.setManageDeptId(userLoginDto.getManageDeptId());
        return sysUser;
    }

    @ApiOperation(value = "组织下党员数据统计", httpMethod = "GET")
    @GetMapping("/peopleCounting")
    public OrganizationPeopleStatistics peopleCounting(@RequestParam @ApiParam("组织ID") Long orgId) {
        return partyInformationService.selectPeopleCountingByOrgId(orgId);
    }
}
