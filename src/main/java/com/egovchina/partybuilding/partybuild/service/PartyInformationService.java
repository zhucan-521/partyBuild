package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.CommunityDto;
import com.egovchina.partybuilding.partybuild.dto.SysUseDto;
import com.egovchina.partybuilding.partybuild.dto.SysUserCountDto;
import com.egovchina.partybuilding.partybuild.dto.SysUserDto;
import com.egovchina.partybuilding.partybuild.entity.OrganizationPeopleStatistics;
import com.egovchina.partybuilding.partybuild.entity.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author zhucan
 */
public interface PartyInformationService {

    /**
     * 补录党员基本信息
     *
     * @param sysUser
     * @return
     */
    int insert(SysUser sysUser);

    /**
     * 党员列表
     *
     * @param params
     * @return
     */
    PageInfo<SysUser> selectPage(Map<String, Object> params);

    /**
     * 修改党员基本信息
     *
     * @param sysUser
     * @return
     */
    int updateSysUser(SysUser sysUser);

    /***
     * 根据id查找党员基本信息
     * @param userId
     * @return
     */
    SysUseDto getSysUserById(Integer userId);

    /**
     * 根据社区名字模糊获取社区信息
     *
     * @return
     */
    List<CommunityDto> selectCommunityDto(CommunityDto dto);

    /**
     * 分页查询党员信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    PageInfo<SysUser> historyPartyPage(Map<String, Object> params);

    SysUserCountDto getTaskPartyCount(Long deptId);

    /**
     * 统计指定组织下各类型人数
     *
     * @param orgId
     * @return
     */
    OrganizationPeopleStatistics selectPeopleCountingByOrgId(Long orgId);

    /**
     * 查询党员根据姓名或身份证
     *
     * @param username
     * @param searchVal 查询值（姓名或身份证）
     * @param phone
     * @param page
     * @return
     */
    List<SysUser> partyIdentityVerification(String username, String searchVal, String phone, Page page);

    /**
     * desc: 补录党员基本信息v2
     *
     * @param sysUser 用户数据传输对象
     * @return int
     * @author FanYanGen
     * @date 2019/4/12 14:40
     **/
    int saveSysUserInfo(SysUserDto sysUser);

    /**
     * desc: 修改党员基本信息v2
     *
     * @param sysUser 用户数据传输对象
     * @return int
     * @author FanYanGen
     * @date 2019/4/12 14:40
     **/
    int updateSysUserInfo(SysUserDto sysUser);

}
