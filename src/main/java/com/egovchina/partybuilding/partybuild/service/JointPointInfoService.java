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

    /**
     * 查询领导职务信息
     *
     * @param userId 用户id
     * @return
     */
    UserDeptPositionVO selectJointByUserId(Long userId);


    /**
     * 查询联点领导信息
     *
     * @param deptId 组织id
     * @return
     */
    List<LinkLeaderVO> selectUserDeptByDeptId(Long deptId);

    /**
     * 删除联点领导
     *
     * @param linkLedaerId 联点领导id
     * @return
     */
    int delJointPointInfoByLinkLedaerId(Long linkLedaerId);

    /**
     * 保存联点领导
     *
     * @param linkLeaderDTO 联点领导DTO
     * @return
     */
    int saveJointPointInfo(LinkLeaderDTO linkLeaderDTO);
}
