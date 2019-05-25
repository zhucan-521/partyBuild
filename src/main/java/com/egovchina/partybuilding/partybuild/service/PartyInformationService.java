package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.CommunityDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyInfoDTO;
import com.egovchina.partybuilding.partybuild.entity.HistoricalPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.PartyMemberChooseQueryBean;
import com.egovchina.partybuilding.partybuild.entity.SysUserQueryBean;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * liu tang gang
 */
public interface PartyInformationService {

    /**
     * 根据社区名字模糊获取社区信息
     * @param communityDTO
     * @return
     */
    List<CommunityVO> selectCommunityVO(CommunityDTO communityDTO);

    /**
     * 根据查询条件查询历史党员
     *
     * @param queryBean
     * @param page
     * @return
     */
    PageInfo<HistoryPartyVO> getPartyHistoryList(HistoricalPartyMemberQueryBean queryBean, Page page);

    /**
     * 查询党员根据姓名或身份证
     * @param username 用户名
     * @param searchVal 身份证
     * @param phone  手机号码
     * @param page
     * @return
     */
    PageInfo<PersonnelVO> partyIdentityVerification(String username, String searchVal, String phone, Page page);

    /**
     * 补录党员基本信息
     * @param partyInfoDTO 党员信息数据传输对象
     * @return int
     **/
    int savePartyInfo(PartyInfoDTO partyInfoDTO);

    /**
     * 修改党员基本信息
     * @param partyInfoDTO 党员信息数据传输对象
     * @return
     */
    int updatePartyInfo(PartyInfoDTO partyInfoDTO);

    /**
     * 根据查询条件查询党员信息
     * @param queryBean 党员查询实体
     * @param page 分页实体
     * @return
     */
    PageInfo<PartyMemberInformationVO> getPartyList(SysUserQueryBean queryBean, Page page);

    /**
     * 查询当前用户信息
     * @return
     */
    UserInfoVO getUserInfoVO();

    /**
     * 根据查询实体查询党员选择vo列表
     * @param queryBean 查询实体
     * @param page 分页实体
     * @return
     */
    List<PartyMemberChooseVO> selectPartyMemberChooseVOListByQueryBean(PartyMemberChooseQueryBean queryBean, Page page);
}
