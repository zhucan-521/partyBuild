package com.yizheng.partybuilding.service.inf;

import com.github.pagehelper.PageInfo;
import com.yizheng.commons.domain.Page;
import com.yizheng.partybuilding.dto.*;
import com.yizheng.partybuilding.entity.TabPbDevPartyMember;
import com.yizheng.partybuilding.entity.TabPbDevPartyMemberDate;
import com.yizheng.partybuilding.system.entity.SysUser;

import java.util.List;

/**
 * 党员发展步骤服务接口
 *
 * @author chenshanlu
 * @version 1.0
 * @date 2018/11/28
 */
public interface ITabPbDevPartyMemberService {

    /**
     * 通过用户id获取发展步骤信息
     *
     * @param userId
     * @return
     */
    TabPbDevPartyMember getByUserId(Long userId);

    /**
     * 通过主键获取发展步骤信息
     *
     * @param primaryKey
     * @return
     */
    TabPbDevPartyMember getByPrimaryKey(Long primaryKey);


    /**
     * 更新党员发展步骤
     *
     * @param member
     * @return
     */
    int update(TabPbDevPartyMemberDto member);

    /**
     * 通过主键删除
     *
     * @param PrimaryKey
     * @return
     */
    int delete(Long PrimaryKey);

    /**
     * 分页获取党员发展信息
     *
     * @param dpid
     * @param userId
     * @param status
     * @param page
     * @return
     */
    PageInfo<TabPbDevPartyMember> list(Long dpid, Long userId, Long status, Page page);

    /**
     * 判断指定用户是否能补录
     *
     * @return
     */
    CheckPartyDto check(Long addType, String userName, String idCardNo, Short isLost);

    /**
     * 入党申请
     *
     * @param user
     */
    TabPbDevPartyMember partyApply(SysUser user);

    /**
     * 获取发展党员
     *
     * @return 返回已经分页了的用户列表
     */
    PageInfo<DevPartyUserDto> getDevPartyList(PartyApplyConditionsDto conditions, Page page);

    /**
     * 保存发展步骤时间
     *
     * @param dates 发展步骤时间对象
     * @return 成功条数
     */
    int saveDevDate(List<TabPbDevPartyMemberDate> dates);


    /**
     * 获取发展步骤时间列表
     *
     * @param hostId 步骤id
     * @return
     */
    List<TabPbDevPartyMemberDate> getDevDate(Long hostId);

    /**
     * 删除发展时间
     *
     * @param timeId
     * @return
     */
    int deleteDevDate(Long timeId);

    /**
     * 更新
     *
     * @param date
     * @return
     */
    int updateDevDate(TabPbDevPartyMemberDate date);

    /**
     * 添加附件
     *
     * @param dto
     * @return
     */
    void attach(PartyDevAttachDto dto);

    /**
     * 查询附件
     *
     * @param dpid     发展步骤id
     * @param isExtend 是否获取扩展字段
     * @return
     */
    PartyDevAttachListDto getAttach(Long dpid, Boolean isExtend);

    int updateUserInfo(SysUser user);
}

