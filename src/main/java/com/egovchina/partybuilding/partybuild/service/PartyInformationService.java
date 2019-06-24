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
     * 查询党员根据条件
     * @param queryValue 查询条件
     * @param page 分页实体
     * @return
     */
    PageInfo<PersonnelVO> partyIdentityVerification(String queryValue, Page page);

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
     * 根据查询实体查询党员选择vo列表
     * @param queryBean 查询实体
     * @param page 分页实体
     * @return
     */
    PageInfo<PartyMemberChooseVO> selectPartyMemberChooseVOListByQueryBean(PartyMemberChooseQueryBean queryBean, Page page);

    /**
     * 根据用户身份证查询用户信息
     * @param idCardNo
     * @return
     */
    PartyMemberChooseVO choosePartyMemberVOByIdCardNo(String idCardNo);

    /**
     * 党员历史信息图
     *
     *
     * @param page                  分页参数
     * @param orgnizeLife           组织生活
     * @param communityActivity     社区活动
     * @param partyMemberComment    党员评议
     * @param userId                用户id
     * @return
     */
    List<HistoryInformationGraphVO> getHistoryInformationGraph(Page page, Boolean orgnizeLife, Boolean communityActivity, Boolean partyMemberComment, Long userId);

    /**
     * 查询党员的工作信息
     * @param userId 党员id
     * @return
     */
    List<PartyWorkVO> getParyWorkVO(Long userId);

}
