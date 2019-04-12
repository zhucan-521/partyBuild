package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.partybuilding.dto.CommunityDto;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.SysUseDto;
import com.yizheng.partybuilding.dto.SysUserCountDto;
import com.yizheng.partybuilding.entity.OrganizationPeopleStatistics;
import com.yizheng.partybuilding.system.entity.SysUser;

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
}
