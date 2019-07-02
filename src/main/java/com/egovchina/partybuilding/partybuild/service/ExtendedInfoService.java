package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.DeletePartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.UpdateHistoryDTO;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageInfo;

import java.util.Date;

/**
 * liu tang gang
 */
public interface ExtendedInfoService {

    /**
     * 通过身份证 用户名查询党员
     *
     * @param idCardNo 身份证
     * @param username 用户名
     * @param page     分页实体
     * @return
     */
    PageInfo<SysUserVO> selectPartyByIdCardNoOrUserName(String idCardNo, String username, Page page);

    /**
     * 恢复党员 从历史党员重新恢复成党员
     *
     * @param reduceId 党员id
     * @return
     */
    int restoreUser(Long reduceId, Date restoreTime);

    /**
     * 查询党员详细信息
     *
     * @param userId 党员id
     * @return
     */
    PartyMemberVO selectPartyMemberDetailsById(Long userId);

    /**
     * 党员名册党员基本信息概况
     *
     * @param userId 党员id
     * @return
     */
    PartyMemberDetailVO selectPartyDetailById(Long userId);

    /**
     * 党员失效,改变是否有效状态
     *
     * @param reduce 减少原因实体
     * @return
     */
    int invalidByUserId(DeletePartyMemberDTO reduce);

    /**
     * 查询书记党员的简单详情信息
     *
     * @param userId 党员id
     * @return
     */
    SecretariesPartyMemberVO selectSecretariesPartyMemberVO(Long userId);

    /**
     * 删除历史信息
     *
     * @param userId 用户id
     * @return
     */
    int deleteByUserId(Long userId);

    /**
     * 查看历史党员信息
     *
     * @param userId
     * @return
     */
    HistoryPartyVO selectHistoryPartyVO(Long userId);

    /**
     * 修改历史党员信息
     *
     * @param updateHistoryDTO
     * @return
     */
    int updateHistoryParty(UpdateHistoryDTO updateHistoryDTO);
}
