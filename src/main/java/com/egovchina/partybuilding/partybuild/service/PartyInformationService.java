package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.CommunityDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyInfoDTO;
import com.egovchina.partybuilding.partybuild.entity.HistoricalPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.SysUserQueryBean;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author zhucan
 */
public interface PartyInformationService {


    /**
     * 根据社区名字模糊获取社区信息
     *
     * @return
     */
    List<CommunityVO> selectCommunityVO(CommunityDTO communityDTO);


    PageInfo<HistoryPartyVO> historyPartyPage(HistoricalPartyMemberQueryBean queryBean, Page page);


    /**
     * 查询党员根据姓名或身份证
     *
     * @param username
     * @param searchVal 查询值（姓名或身份证）
     * @param phone
     * @param page
     * @return
     */
    PageInfo<PersonnelVO> partyIdentityVerification(String username, String searchVal, String phone, Page page);


    /**
     * desc: 补录党员基本信息
     *
     * @param partyInfoDTO 党员信息数据传输对象
     * @return int
     * @author FanYanGen
     * @date 2019/4/12 14:40
     **/
    int savePartyInfo(PartyInfoDTO partyInfoDTO);

    /**
     * desc: 修改党员基本信息
     *
     * @param partyInfoDTO 党员信息数据传输对象
     * @return int
     * @author FanYanGen
     * @date 2019/4/12 14:40
     **/
    int updatePartyInfo(PartyInfoDTO partyInfoDTO);

    /**
     * 党员列表
     *
     * @return
     */
    PageInfo<PartyMemberInformationVO> getPartyList(SysUserQueryBean queryBean, Page page);

    /**
     * 获取当前用户信息
     */

    UserInfoVO getUserInfoVO();
}
