package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositiveRegist;
import com.egovchina.partybuilding.partybuild.entity.PositiveRegistMemberQueryBean;
import com.egovchina.partybuilding.partybuild.dto.PositiveRegistMemberDTO;
import com.egovchina.partybuilding.partybuild.vo.PositiveRegistMemberVO;

import java.util.List;

/**
 * @author YangYingXiang on 2018/11/29
 */
public interface PositiveRegistService {

    int addRegistMemberDTO(PositiveRegistMemberDTO positiveRegistMemberDto);

    List<PositiveRegistMemberVO> selectRegistMemberVOList(PositiveRegistMemberQueryBean positiveRegistMemberQueryBean, Page page);

    /**
     * 修改报到状态
     *
     * @param positiveRegistId
     */
    int changeStatus(Long positiveRegistId, Byte revokeTag);

    /**
     * 逻辑删除
     *
     * @param positiveRegistId
     */
    int delete(Long positiveRegistId);

    /**
     * 根据userId判断是否需要删除
     *
     * @param userId
     */
    int delectRegistStatus(Long userId);

    PositiveRegistMemberVO getReportMembersInfo(Long positiveRegistId);

}
