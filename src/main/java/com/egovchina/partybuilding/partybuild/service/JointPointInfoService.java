package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.partybuild.dto.LinkLeaderDTO;
import com.egovchina.partybuilding.partybuild.vo.LinkLeaderVO;
import com.egovchina.partybuilding.partybuild.vo.UserDeptPositionVO;

import java.util.List;

/**
 * 描述:
 * 联点领导接口
 *
 * @author wuyunjie
 * Date 2019-04-20 10:41
 */
public interface JointPointInfoService {

    UserDeptPositionVO selectJointByUserId(Long userId);

    List<LinkLeaderVO> selectUserDeptByDeptId(Long deptId);

    int delJointPointInfoByLinkLedaerId(Long linkLedaerId);

    int saveJointPointInfo(LinkLeaderDTO linkLeaderDTO);
}
