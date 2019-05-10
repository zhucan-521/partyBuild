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
    *
    *
    * @return: com.egovchina.partybuilding.partybuild.vo.UserDeptPositionVO
    * @Author: WuYunJie
    * @Date: 2019/5/10
    */
    UserDeptPositionVO selectJointByUserId(Long userId);

    /**
    *
    *
    *
    * @return: java.util.List<com.egovchina.partybuilding.partybuild.vo.LinkLeaderVO>
    * @Author: WuYunJie
    * @Date: 2019/5/10
    */
    List<LinkLeaderVO> selectUserDeptByDeptId(Long deptId);

    int delJointPointInfoByLinkLedaerId(Long linkLedaerId);

    int saveJointPointInfo(LinkLeaderDTO linkLeaderDTO);
}
