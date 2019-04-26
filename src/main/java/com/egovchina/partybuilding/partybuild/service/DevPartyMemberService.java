package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.DevPartyMemberDTO;
import com.egovchina.partybuilding.partybuild.dto.PartyDevAttachDTO;
import com.egovchina.partybuilding.partybuild.entity.DevPartyMemberQueryBean;
import com.egovchina.partybuilding.partybuild.entity.PartyApplyQueryBean;
import com.egovchina.partybuilding.partybuild.vo.CheckDevPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyMemberDateVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.DevPartyVO;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * desc: 党员发展步骤-服务接口
 * Created by FanYanGen on 2019/4/22 19:21
 */
public interface DevPartyMemberService {

    /**
     * desc: 根据用户ID查询发展步骤
     *
     * @param userId 用户ID
     * @return DevPartyMemberDTO
     * @author FanYanGen
     * @date 2019/4/23 10:54
     **/
    DevPartyMemberVO findDevPartyMemberVOByUserId(Long userId);

    /**
     * desc: 根据deptId查询发展步骤
     *
     * @param deptId 发展ID
     * @return DevPartyMemberDTO
     * @author FanYanGen
     * @date 2019/4/23 10:54
     **/
    DevPartyMemberVO findDevPartyMemberVOByDeptId(Long deptId);

    /**
     * desc: 更新发展步骤
     *
     * @param dpId   发展ID
     * @param status 发展状态
     * @return int
     * @author FanYanGen
     * @date 2019/4/23 10:54
     **/
    int updateDevStep(Long dpId, Long status);

    /**
     * desc: 根据deptId删除
     *
     * @param deptId 发展ID
     * @return int
     * @author FanYanGen
     * @date 2019/4/23 10:54
     **/
    int deleteByDeptId(Long deptId);

    /**
     * 分页获取党员发展信息
     *
     * @param dpId   发展ID
     * @param userId 用户ID
     * @param status 发展状态
     * @param page   分页
     * @return
     */
    PageInfo<DevPartyMemberVO> findDevPartyMemberVOByConditions(Long dpId, Long userId, Long status, Page page);

    /**
     * desc: 判断指定用户是否能补录
     *
     * @param devPartyMemberQueryBean 条件集合
     * @return CheckDevMemberDTO
     * @author FanYanGen
     * @date 2019/4/23 15:26
     **/
    CheckDevPartyMemberVO checkIsParty(DevPartyMemberQueryBean devPartyMemberQueryBean);

    /**
     * desc: 获取发展党员列表
     *
     * @param partyApplyQueryBean 入党申请查询条件
     * @param page                分页实体
     * @return
     * @author FanYanGen
     * @date 2019/4/23 16:47
     **/
    PageInfo<DevPartyVO> findDevPartyVOByConditions(PartyApplyQueryBean partyApplyQueryBean, Page page);

    /**
     * desc: 根据hostId获取发展步骤时间列表
     *
     * @param hostId 附件ID
     * @return List<TabPbDevPartyMemberDate>
     * @author FanYanGen
     * @date 2019/4/23 20:42
     **/
    List<DevPartyMemberDateVO> findDevPartyMemberDateVOByHostId(Long hostId);

    /**
     * desc: 根据时间主键删除发展时间
     *
     * @param timeId 时间ID
     * @return int
     * @author FanYanGen
     * @date 2019/4/23 20:57
     **/
    int deleteDevDate(Long timeId);

    /**
     * desc: 更新发展时间
     *
     * @param devPartyMemberDTO dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/23 21:10
     **/
    int updateDevDate(DevPartyMemberDTO devPartyMemberDTO);

    /**
     * desc: 添加附件
     *
     * @param partyDevAttachDTO dto
     * @return int
     * @author FanYanGen
     * @date 2019/4/23 21:22
     **/
    int insertAttach(PartyDevAttachDTO partyDevAttachDTO);

    /**
     * desc: 查询附件
     *
     * @param dpId     发展步骤id
     * @param isExtend 是否获取扩展字段
     * @return
     * @author FanYanGen
     * @date 2019/4/23 21:27
     **/
    DevPartyMemberVO findAttach(Long dpId, Boolean isExtend);

}
